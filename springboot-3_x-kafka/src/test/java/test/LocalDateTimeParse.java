package test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class LocalDateTimeParse {
  
  @Test
  public void parse() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss"); // 오류
    LocalDateTime dateTime = formatter.parse("2024-05-23T02:27:27", LocalDateTime::from);
    System.out.println(dateTime);
  }
}
