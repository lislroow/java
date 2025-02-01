package snippets.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;

public class ByteArrayInputStreamMain {
  
  public static void main(String[] args) throws IOException {
    String phone = "010   abc5678";
    
    byte[] raw = phone.getBytes();
    
    ByteArrayInputStream is = new ByteArrayInputStream(raw);
    
    byte[] first = new byte[3];
    byte[] second = new byte[6];
    byte[] third = new byte[4];
    
    is.read(first);
    is.read(second);
    is.read(third);
    
    System.out.println(new String(first));
    System.out.println(new String(second));
    System.out.println(new String(third));
    
    System.out.println("["+new String(new byte[] {0x30})+"]");
    System.out.println("["+new String(new byte[] {0x20})+"]");
    
    System.out.println(new Date(1735990371000L));
  }
  
}
