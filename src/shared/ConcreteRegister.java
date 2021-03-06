package shared;

import interfaces.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.HashMap;

public class ConcreteRegister<T> implements Register<T>, Cloneable {

  public final AtomicBoolean lock = new AtomicBoolean();
  public T value;
  public int time;

  public ConcreteRegister(T v) {
    value = v;
    this.lock.set(false);
  }

  public T read(Transaction t) throws AbortException {
    HashMap<Register, Register> writes = t.getLwst();
    if(writes.contains(this)) {
      return writes.get(this).get();
    }
    else{
      HashMap<Register, Register> reads = t.getLrst();
      reads.put(this, this.clone());
      if(reads.get(this).getTime() > t.getBirthDate()){
        throw new AbortException("Date error ...");
      }
      else{
        return reads.get(this).get();
      }
    }
  }

  public void write(Transaction t, T v) throws AbortException {
    HashMap<Register, Register> writes = ((ConcreteTransaction)t).getLwst();
    if(!writes.containsKey(this)) {
      try {
        writes.put(this, (Register) this.clone());
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
    }

    writes.get(this).set(v);
  }

  public T get() { return value; }
  public void set(T v)  { value = v; }

  public int getTime() { return time; }
}
