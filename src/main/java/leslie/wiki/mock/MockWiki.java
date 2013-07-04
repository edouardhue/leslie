package leslie.wiki.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import leslie.wiki.Wiki;

public final class MockWiki implements Wiki {

  private static final Logger LOGGER = LoggerFactory.getLogger(MockWiki.class);
  
  @Override
  public void login(String username, String password) {
    LOGGER.info("login: {}, {}", username, password);
  }

  @Override
  public String getPageContent(String page) {
    LOGGER.info("getPageContent: {}", page);
    return "";
  }

  @Override
  public void setPageContent(String page, String content) {
    LOGGER.info("setPageContent: {}, {}", page, content);
  }

  @Override
  public void logout() {
    LOGGER.info("logout");
  }

}
