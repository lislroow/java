package main.image;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class CommonIOMain {
  
  public static void main(String[] args) {
    String baseDir = "C:\\project\\ui\\public\\images";
    List<String> base64Images = Stream.of(new File(baseDir).listFiles()).map(file -> {
      String base64 = null;
      byte[] fileContent;
      try {
        fileContent = FileUtils.readFileToByteArray(file);
        base64 = "data:image/webp;base64," + Base64.getEncoder().encodeToString(fileContent);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return base64;
    })
    .collect(Collectors.toList());
    
    base64Images.forEach(str -> System.out.println(str));
    
    List<String> ofile = Arrays.asList("1.webp", "2.webp");
    AtomicInteger idx = new AtomicInteger(0);
    base64Images.forEach(str -> {
      byte[] decodedBytes = Base64.getDecoder().decode(str.substring(str.indexOf(";base64,")+";base64,".length()));
      try {
        FileUtils.writeByteArrayToFile(new File("C:\\temp\\"+ofile.get(idx.getAndIncrement())), decodedBytes);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }
}
