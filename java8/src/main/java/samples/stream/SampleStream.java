package samples.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SampleStream {
  
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  static class DummyVo {
    private String id;
    private String subId;
  }
  
  public static void main(String[] args) {
    List<DummyVo> list = Arrays.asList(
        DummyVo.builder().id("Foo").subId("Foo-1").build(),
        DummyVo.builder().id("Foo").subId("Foo-2").build(),
        DummyVo.builder().id("Foo").subId("Foo-3").build(),
        DummyVo.builder().id("Bar").subId("Bar-1").build(),
        DummyVo.builder().id("Bar").subId("Bar-2").build()
        );
    Map<String, List<DummyVo>> map = list.stream().collect(Collectors.groupingBy(DummyVo::getId));
    
    map.values().stream().forEach(item -> System.out.println(item));
  }
}
