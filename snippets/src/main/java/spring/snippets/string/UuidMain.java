package spring.snippets.string;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UuidMain {

  public static String create() {
    String uuid = Long.toString(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong(), Character.MAX_RADIX);
    for (int i = uuid.length(); i < 14; i++) {
      uuid = '0' + uuid;
    }
    return uuid;
  }

  private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static void createBase62() {
    UUID uuid = UUID.randomUUID();
    String uuidString = uuid.toString().replace("-", "");
    BigInteger decimalValue = new BigInteger(uuidString, 16);
//    System.out.println(decimalValue);
    String base62Value = toBase62(decimalValue);
    System.out.println(base62Value);
  }
  private static String toBase62(BigInteger value) {
    StringBuilder sb = new StringBuilder();
    while (value.compareTo(BigInteger.ZERO) > 0) {
      BigInteger[] divmod = value.divideAndRemainder(BigInteger.valueOf(62));
      sb.append(BASE62.charAt(divmod[1].intValue()));
      value = divmod[0];
    }
    return sb.reverse().toString();
  }
  
  public static void main(String[] args) {
    ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      map.forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));
    }));
    
    long start = System.currentTimeMillis();
    for (int i=0; i<=1000000; i++) {
      int time = (int) ((System.currentTimeMillis() - start) / 1000);
      createBase62();
      map.merge(time, 1, Integer::sum);
    }
  }
  
}
