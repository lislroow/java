package samples.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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
  static class ItemVo {
    private String id;
    private String subId;
  }
  @Data
  @AllArgsConstructor
  static class GroupVo {
    private String id;
  }

  public static void main(String[] args) {
    List<ItemVo> list = Arrays.asList(
        ItemVo.builder().id("Foo").subId("Foo-1").build(),
        ItemVo.builder().id("Foo").subId("Foo-2").build(),
        ItemVo.builder().id("Foo").subId("Foo-3").build(),
        ItemVo.builder().id("Bar").subId("Bar-1").build(),
        ItemVo.builder().id("Bar").subId("Bar-2").build()
        );
    Map<String, List<ItemVo>> map = list.stream().collect(Collectors.groupingBy(ItemVo::getId));

    map.values().stream().forEach(item -> System.out.println(item));

    System.out.println("----");

    list.stream().collect(Collectors.groupingBy(ItemVo::getId, Collectors.toSet()));
    List<GroupVo> distList = list.stream().filter(distinctByKey(ItemVo::getId))
        .map(item -> new GroupVo(item.getId()))
        .collect(Collectors.toList());
    System.out.println(distList);
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
      Map<Object, Boolean> map = new ConcurrentHashMap<>();
      return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }


}
