package spring.sample.ext.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Uuid {
  
  public static String create() {
    // UUID 는 지구 상에 모래 알갱이 개수 보다 많은 수를 표현할 수 있습니다.
    //   UUID 표현 가능 개수: 340,282,366,920,938,463,463,374,607,431,768,211,456 개
    //   모래 알갱이 개수:                          1,287,3836,4032,4816,7954,050 개
    //   지구는 약 70%의 바다와 30%의 육지로 이뤄져 있고, 그중 육지의 면적은 약 5,200만㎢입니다. 
    //   육지 면적에 모래알이 가득 있다는 전제하에 계산하면 모래알의 개수는 128해 7,383경 6,403조 2,481억 6,795만 4,050개입니다.
    String uuid = Long.toString(ByteBuffer.wrap(UUID.randomUUID().toString().getBytes()).getLong(), Character.MAX_RADIX);
    for (int i = uuid.length(); i < 14; i++) {
      uuid = '0' + uuid;
    }
    return uuid;
  }
}
