package interfaces;

import shared.*;

import java.util.HashMap;

public interface Transaction {

  public void begin();

  public void tryToCommit() throws AbortException;

  public boolean isCommited();

  //public HashMap<Register, Register> getLrst();
  //public HashMap<Register, Register> getLwst();
}
