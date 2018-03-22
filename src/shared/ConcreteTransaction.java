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
  private boolean isCommited;

  public ConcreteTransaction() {
    lrst = new HashMap<Register, Register>();
    lwst = new HashMap<Register, Register>();
    isCommited = false;
  }

  public void begin() {
    birthDate = clock.get();
    lwst.clear();
    lrst.clear();
    isCommited = false;
  }

  public void tryToCommit() throws AbortException {
    if(isCommited) {
      throw new AbortException("alreadyCommited");
    }
    takeDemLocks();

    for(Iterator<Register> i = rks.iterator(); i.hasNext()) {
      if (i.time > this.birthDate) {
        freeDemLocks();
        throw new AbortException("more recent writing");
      }
    }

    int commitDate = ConcreteTransaction.clock.getAndIncrement();

    for(Iterator<Register> i = lwst.keySet().iterator(); i.hasNext()) {
      i.value = lwst.get(i).value;
      i.time = commitDate;
    }

    freeDemLocks();

    isCommited = true;
  }

  private void takeDemLocks() {
    for (Iterator<Register> i = lrst.keySet().iterator(); i.hasNext()) {
      while(i.lock.get());
      i.lock.set(true);
    }
    for (Iterator<Register> i = lwst.keySet().iterator(); i.hasNext()) {
      while(i.lock.get());
      i.lock.set(true);
    }
  }

  private void freeDemLocks() {
    for (Iterator<Register> i = lrst.keySet().iterator(); i.hasNext()) {
      i.lock.set(false);
    }
    for (Iterator<Register> i = lwst.keySet(); i.hasNext()) {
      i.lock.set(false);
    }
  }

  public boolean isCommited() {
    return
  }

  public HashMap<Register, Register> getLrst() { return lrst; }
  public HashMap<Register, Register> getLwst() { return lwst; }
}
