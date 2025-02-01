package spring.custom.common.util;

import java.text.NumberFormat;

public class StringFormat {
  
  public static String toOrdinal(int n) {
    if (n % 100 >= 11 && n % 100 <= 13) {
      return n + "th";
    }
    
    String ordinal = switch (n % 10) {
      case 1: yield n + "st";
      case 2: yield n + "nd";
      case 3: yield n + "rd";
      default: yield n + "th";
    };
    return ordinal;
  }
  
  public static String toComma(Number n) {
    NumberFormat format = NumberFormat.getNumberInstance();
    return format.format(n);
  }
}
