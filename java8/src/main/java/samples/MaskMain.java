package samples;

public class MaskMain {

  public static void main(String[] args) {
    String input = "1234567890";
    String pattern = "###**###*#";
    StringBuilder masked = new StringBuilder();
    int mlen = input.length() > pattern.length() ? pattern.length() : input.length();
    for (int i=0; i<mlen; i++) {
      char currentPatternChar = pattern.charAt(i);
      char currentInputChar = input.charAt(i);
      if (currentPatternChar == '*') {
        masked.append('*');
      } else {
        masked.append(currentInputChar);
      }
    }
    for (int i=mlen; i<input.length(); i++) {
      masked.append(input.charAt(i));
    }
    System.out.println(masked.toString());
  }
  
}
