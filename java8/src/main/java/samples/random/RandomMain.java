package samples.random;

import java.security.SecureRandom;

public class RandomMain {
  
  public static void main(String[] args) {
    {
      String rnd1 = Double.toString(java.lang.Math.random()).substring(2,6);
      System.out.println(rnd1);
    }
    SecureRandom secureRandom = new SecureRandom();
    {
      String rnd2 = Double.toString(secureRandom.nextDouble()).substring(2, 6);
      System.out.println(rnd2);
    }
    {
      String rnd2 = Double.toString(secureRandom.nextDouble()).substring(2, 6);
      System.out.println(rnd2);
    }
    {
      String rnd2 = Double.toString(secureRandom.nextDouble()).substring(2, 6);
      System.out.println(rnd2);
    }
  }
}
