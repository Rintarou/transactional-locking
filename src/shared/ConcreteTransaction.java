package shared;

import interfaces.Transaction;
import interfaces.Register;

import java.util.HashMap;
import java.util.Calendar;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteTransaction implements Transaction {

  public static AtomicInteger clock = new AtomicInteger();
  private HashMap<Register, Calendar> lrst;
  private HashMap<Register, Calendar> lwst;
  private int birthDate;

  public ConcreteTransaction() {
    lrst = new HashMap<Object, Calendar>();
    lwst = new HashMap<Object, Calendar>();
  }

  public void begin() {
    birthDate = clock.get();
    lwst.clear();
    lrst.clear();
  }

  public void tryToCommit() throws AbortException {

  }

  public boolean isCommited();
}
