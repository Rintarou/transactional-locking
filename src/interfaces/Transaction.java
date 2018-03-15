package interfaces;

import shared.AbortException;

public interface Transaction {

  public void begin();

  public void tryToCommit() throws AbortException;

  public boolean isCommited();

}
