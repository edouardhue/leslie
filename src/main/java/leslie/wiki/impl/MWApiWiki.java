package leslie.wiki.impl;

import java.io.IOException;

import leslie.wiki.Wiki;
import leslie.wiki.WikiException;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.mediawiki.api.ApiResult;
import org.mediawiki.api.MWApi;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public final class MWApiWiki implements Wiki {

  private final MWApi api;

  public MWApiWiki(final String url) {
    final AbstractHttpClient client = new DefaultHttpClient();
    this.api = new MWApi(url, client);
  }

  @Override
  public void login(final String username, final String password) throws WikiException {
    try {
      api.login(username, password);
    } catch (final IOException e) {
      throw new WikiException(e);
    }
  }

  @Override
  public String getPageContent(final String page) throws WikiException {
    try {
      final ApiResult result = api.action("query").param("titles", page).param("prop", "revisions")
          .param("rvprop", "content").param("format", "xml").get();
      return result.getString("/api/query/pages/page/revisions/rev");
    } catch (final IOException e) {
      throw new WikiException(e);
    }
  }

  @Override
  public void setPageContent(final String page, final String content) throws WikiException {
    try {
      final String editToken = api.getEditToken();
      final ApiResult result = api.action("edit").param("title", page).param("text", content).param("summary", "Campagne accueil des nouveaux").param("token", editToken).post();
      final String editResult = result.getString("/api/edit/@result");
      if (!"Success".equals(editResult)) {
        final DOMImplementationRegistry reg = DOMImplementationRegistry.newInstance();
        final DOMImplementationLS impl = (DOMImplementationLS) reg.getDOMImplementation("LS");
        final LSSerializer serializer = impl.createLSSerializer();
        final StringBuilder buffer = new StringBuilder("Edit failed:\n");
        buffer.append(serializer.writeToString(result.getDocument()));
        throw new WikiException(buffer.toString());
      }
    } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e) {
      throw new WikiException(e);
    }
  }

  @Override
  public void logout() throws WikiException {
    try {
      api.logout();
    } catch (final IOException e) {
      throw new WikiException(e);
    }
  }

}
