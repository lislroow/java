package samples;

import java.util.Date;

public class DateMain {
  
  public static void main(String[] args) {
    Date date = new Date();
    System.out.println(date);
    Date after = new Date(System.currentTimeMillis() + (3600 * 1000L));
    System.out.println(after);
    long seconds = (after.getTime() - System.currentTimeMillis()) / 1000L;
    System.out.println(seconds);
  }
}
