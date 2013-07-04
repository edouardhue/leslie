package leslie.wiki;

public interface Wiki {
  void login(String username, String password) throws WikiException;
  String getPageContent(String page) throws WikiException;
  void setPageContent(String page, String content, String comment) throws WikiException;
  void logout() throws WikiException;
}
