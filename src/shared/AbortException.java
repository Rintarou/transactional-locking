package shared;

public class AbortException extends Exception {
  public AbortException(String s) {
    super(s);
  }

  static final long serialVersionUID = 523698741;
}
