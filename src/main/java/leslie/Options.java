package leslie;

import java.io.File;
import java.net.URL;

import org.kohsuke.args4j.Option;

final class Options {
  @Option(name = "-c", required = true, usage = "Credentials file")
  private File credentials;
  
  @Option(name="-s", required = true, usage = "State file")
  private File state;
  
  @Option(name = "-p", required = true, usage = "Source pages file")
  private File sourcePages;
  
  @Option(name = "-t", required = true, usage = "Target page")
  private String targetPage;
  
  @Option(name = "-w", required = true, usage = "MediaWiki API endpoint")
  private URL wiki;
  
  @Option(name = "-e", required = false, usage = "Edit comment")
  private String editComment = "Automatic content rotation";
  
  public File getCredentials() {
    return credentials;
  }
  
  public File getState() {
    return state;
  }
  
  public File getSourcePages() {
    return sourcePages;
  }
  
  public String getTargetPage() {
    return targetPage;
  }
  
  public URL getWiki() {
    return wiki;
  }
  
  public String getEditComment() {
    return editComment;
  }
}
