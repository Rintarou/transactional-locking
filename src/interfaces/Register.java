package interfaces;

import shared.AbortException;

public interface Register<T> {

  public T read(Transaction t) throws AbortException;

  public void write(Transaction t, T v) throws AbortException;

  public void set(T v);
  public T get();
}
