package leslie;

public final class LeslieException extends Exception {

  private static final long serialVersionUID = 1L;

  public LeslieException() {
    super();
  }

  public LeslieException(final String message, final Throwable cause, final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public LeslieException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public LeslieException(final String message) {
    super(message);
  }

  public LeslieException(final Throwable cause) {
    super(cause);
  }

}
