package samples.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SampleDateTime {

  public static String toStr(long time) {
    if (time < 1e12) {
      time *= 1e3;
    }
    String str = null;
    LocalDateTime localDateTime = Instant
        .ofEpochMilli(time)             // long(epochMilli) -> Instant
        .atZone(ZoneId.systemDefault()) // Instant -> ZonedDateTime
        .toLocalDateTime();             // ZonedDateTime -> LocalDateTime
    str = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
    return str;
  }
  
  public static void main(String[] args) {
    System.out.println(toStr(1717052518));
  }
}
