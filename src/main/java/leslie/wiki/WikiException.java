package leslie.wiki;

public final class WikiException extends Exception {

  private static final long serialVersionUID = 1L;

  public WikiException() {
    super();
  }

  public WikiException(final String message, final Throwable cause, final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public WikiException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public WikiException(final String message) {
    super(message);
  }

  public WikiException(final Throwable cause) {
    super(cause);
  }

}
