package shared;

import interfaces.Transaction;
import interfaces.Register;

import java.util.HashMap;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteTransaction implements Transaction {

  public final static AtomicInteger clock = new AtomicInteger();
  private HashMap<Register, Register> lrst;
  private HashMap<Register, Register> lwst;
  private int birthDate;

  public ConcreteTransaction() {
    lrst = new HashMap<Register, Register>();
    lwst = new HashMap<Register, Register>();
  }

  public void begin() {
    birthDate = clock.get();
    lwst.clear();
    lrst.clear();
  }

  public void tryToCommit() throws AbortException {

  }

  public boolean isCommited() {

  }

  public HashMap<Register, Integer> getLrst() { return lrst; }
  public HashMap<Register, Integer> getLwst() { return lwst; }
}
