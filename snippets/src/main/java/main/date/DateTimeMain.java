package main.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeMain {

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
  
  public void parse() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss"); // 오류
    LocalDateTime dateTime = formatter.parse("2024-05-23T02:27:27", LocalDateTime::from);
    System.out.println(dateTime);
  }
  
  public static void main(String[] args) {
    System.out.println(toStr(1717052518));
    
    //System.out.println(LocalDate.now().isEqual(LocalDate.now()));
  }
  
}
