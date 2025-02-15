package main.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public class ImageIoMain {
  
  public static void main(String[] args) {
    String baseDir = "C:\\project\\ui\\public\\images";
    List<String> base64Images = Stream.of(new File(baseDir).listFiles()).map(file -> {
      String base64 = null;
      BufferedImage image;
      try {
        image = ImageIO.read(file);
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
      try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
        ImageIO.write(image, "png", outputStream); // PNG로 변환하여 저장 (ImageIO는 WebP 저장을 기본 지원하지 않음)
        byte[] imageBytes = outputStream.toByteArray();
        base64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return base64;
    })
    .collect(Collectors.toList());
    
    base64Images.forEach(str -> System.out.println(str));
  }
}
