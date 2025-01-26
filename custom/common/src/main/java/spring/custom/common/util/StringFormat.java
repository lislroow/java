package spring.custom.common.util;

import java.text.NumberFormat;

public class StringFormat {
  
  public static String toOrdinal(int n) {
    if (n % 100 >= 11 && n % 100 <= 13) {
      return n + "th";
    }
    
    switch (n % 10) {
    case 1: return n + "st";
    case 2: return n + "nd";
    case 3: return n + "rd";
    default: return n + "th";
    }
  }
  
  public static String toComma(int n) {
    NumberFormat format = NumberFormat.getNumberInstance();
    return format.format(n);
  }
}
