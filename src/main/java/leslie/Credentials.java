package leslie;

import java.util.Properties;

final class Credentials {
  private final String username;
  
  private final String password;

  private Credentials(final String username, final String password) {
    this.username = username;
    this.password = password;
  }
  
  public String getUsername() {
    return username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public static Credentials from(final Properties props) {
    return new Credentials(props.getProperty("username"), props.getProperty("password"));
  }
}
