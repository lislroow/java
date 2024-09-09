package samples.captcha;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import nl.captcha.Captcha;
import nl.captcha.text.producer.NumbersAnswerProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

public class SampleCaptcha {

  public static String generateCaptcha() {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      List<Color> colorList = Arrays.asList(Color.BLACK);
      List<Font> fontList = Arrays.asList(new Font("", Font.HANGING_BASELINE, 22));
      Captcha captcha = new Captcha.Builder(85, 25)
          .addText(new NumbersAnswerProducer(6), new DefaultWordRenderer(colorList, fontList))
          .addBackground()
          .addNoise().addBorder()
          .build();
      ImageIO.write(captcha.getImage(), "png", bos);
      byte[] imageBytes = bos.toByteArray();
      String imgstr = Base64.getEncoder().encodeToString(imageBytes);
      imgstr = String.format("data:image/png;base64,%s", imgstr);
      return imgstr;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
  
  public static void main(String[] args) {
    String imgstr = generateCaptcha();
    System.out.println(imgstr);
  }
}
