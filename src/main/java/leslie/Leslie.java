package leslie;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import leslie.wiki.Wiki;
import leslie.wiki.WikiException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class Leslie {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(Leslie.class);
  
  private static final Integer DEFAULT_STATE = 1;

  private final Options options;

  private final Wiki wiki;

  private final List<String> sourcePages;

  private Credentials credentials;
  
  private Integer state;

  public Leslie(final Options options, final Wiki wiki) throws LeslieException {
    this.options = options;
    this.wiki = wiki;
    this.sourcePages = new ArrayList<>(6);
    this.init();
  }

  private void init() throws LeslieException {
    try {
      this.readCredentials();
      this.readSourcePages();
      this.readState();
    } catch (final IOException e) {
      throw new LeslieException(e);
    }
  }

  private void readCredentials() throws IOException {
    final Properties props = new Properties();
    try (final BufferedInputStream r = new BufferedInputStream(new FileInputStream(options.getCredentials()))) {
      props.load(r);
      this.credentials = Credentials.from(props);
      LOGGER.info("Loaded credentials: {} / {}", credentials.getUsername(), credentials.getPassword().replaceAll(".", "*"));
    }
  }

  private void readSourcePages() throws IOException, LeslieException {
    try (final BufferedReader r = new BufferedReader(new InputStreamReader(
        new FileInputStream(options.getSourcePages()), "ISO-8859-1"))) {
      String line;
      while ((line = r.readLine()) != null) {
        sourcePages.add(line);
      }
      if (sourcePages.size() == 0) {
        throw new LeslieException("Source pages list is empty");
      }
      LOGGER.info("Loaded source pages: {}", sourcePages);
    }
  }
  
  private void readState() throws IOException {
      try (final BufferedReader r = new BufferedReader(new InputStreamReader(
          new FileInputStream(options.getState()), "ISO-8859-1"))) {
        final String line = r.readLine();
        try {
          state = Integer.parseInt(line);
          LOGGER.info("Loaded state: {}", state);
        } catch (final NumberFormatException e) {
          LOGGER.warn("Could not parse state: {}, defaulting to {}", line, DEFAULT_STATE);
          state = DEFAULT_STATE;
        }
      } catch (final FileNotFoundException e) {
        LOGGER.warn("Inexistant state file {}, defaulting to {}", options.getState(), DEFAULT_STATE);
        state = DEFAULT_STATE;
      }
  }
  
  private void writeState() throws IOException {
    try (final BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(options.getState()), "ISO-8859-1"))) {
      w.write(state.toString());
    }
  }

  void updateTargetPage() throws LeslieException {
    try {
      wiki.login(credentials.getUsername(), credentials.getPassword());
      final Integer next = (state + 1) % sourcePages.size();
      final String nextPageContent = wiki.getPageContent(sourcePages.get(next));
      wiki.setPageContent(options.getTargetPage(), nextPageContent, options.getEditComment());
      state = next;
      this.writeState();
      LOGGER.info("Successfully wrote content of {} to {}.", sourcePages.get(state), options.getTargetPage());
    } catch (final WikiException e) {
      throw new LeslieException(e);
    } catch (final IOException e) {
      throw new LeslieException("State has not been saved", e);
    } finally {
      try {
        wiki.logout();
      } catch (final WikiException e) {
        throw new LeslieException(e);
      }
    }
  }
}
