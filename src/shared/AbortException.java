

public class AbortException extends Exception {
  public AbortException() {
    super("Concurrent writing or reading, aborted");
  }
}
