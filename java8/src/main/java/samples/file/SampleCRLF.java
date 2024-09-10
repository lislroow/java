package samples.file;

import java.io.File;
import java.io.PrintWriter;

public class SampleCRLF {
  
  public static void main(String[] args) {
    File f = new File("crlf.txt");
    try (PrintWriter pw = new PrintWriter(f)) {
      pw.write("20240109\r");
      pw.write("20240109\n");
      pw.write("20240109\r\n");
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
