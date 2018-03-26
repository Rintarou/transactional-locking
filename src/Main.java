import shared.*;

public class Main {
  public static void main(String args[]) {
    ConcreteRegister<String> cr = new ConcreteRegister<String>("hello there!");
    ConcreteTransaction ct = new ConcreteTransaction();
    
    try {

      System.out.println(cr.read(ct));
    } catch (AbortException e) {
      e.printStackTrace();
    }

  }
}
