package shared;

import interfaces.Register;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteRegister<T> implements Register<T>, Cloneable {

  public final AtomicBoolean lock = new AtomicBoolean();
  public T value;
  public int time;

  public ConcreteRegister(T v) {
    value = v;
    this.lock.set(false);
  }

  public T read(Transaction t) throws AbortException {

  }

  public void write(Transaction t, T v) throws AbortException {
    HashMap<Register, Register> writes = t.getLwst();
    if(!writes.contains(this)) {
     writes.put(this, this.clone());
    }
    writes.get(this).set(v);
  }

  public T get() { return value; }
  public void set(T v)  { value = v; }
}
