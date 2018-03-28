package shared;

import interfaces.*;

import java.util.HashMap;
import java.util.Iterator;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteTransaction implements Transaction {

  public final static AtomicInteger clock = new AtomicInteger();
  public HashMap<Register, Register> lrst;
  public HashMap<Register, Register> lwst;

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
    ConcreteRegister i;

    takeDemLocks();

    for(Iterator<Register> it = lrst.keySet().iterator(); it.hasNext();) {
      i = (ConcreteRegister) it.next();
      if (i.time > this.birthDate) {
        freeDemLocks();
        throw new AbortException("more recent writing");
      }
    }

    int commitDate = ConcreteTransaction.clock.getAndIncrement();

    for(Iterator<Register> it = lwst.keySet().iterator(); it.hasNext();) {
      i = (ConcreteRegister) it.next();

      i.value = lwst.get(i).get();
      i.time = commitDate;
    }

    freeDemLocks();

    isCommited = true;
  }

  private void takeDemLocks() {
    ConcreteRegister i;
    for (Iterator<Register> it = lrst.keySet().iterator(); it.hasNext();) {
      i = (ConcreteRegister) it.next();
      while(i.lock.get());
      i.lock.set(true);
    }
    for (Iterator<Register> it = lwst.keySet().iterator(); it.hasNext();) {
      i = (ConcreteRegister) it.next();
      while(i.lock.get());
      i.lock.set(true);
    }
  }

  private void freeDemLocks() {
    ConcreteRegister i;
    for (Iterator<Register> it = lrst.keySet().iterator(); it.hasNext();) {
      i = (ConcreteRegister) it.next();
      i.lock.set(false);
    }
    for (Iterator<Register> it = lwst.keySet().iterator(); it.hasNext();) {
      i = (ConcreteRegister) it.next();
      i.lock.set(false);
    }
  }

  public boolean isCommited() {
    return isCommited;
  }

  public HashMap<Register, Register> getLrst() { return lrst; }
  public HashMap<Register, Register> getLwst() { return lwst; }
  public int getBirthDate(){ return birthDate; };
}
