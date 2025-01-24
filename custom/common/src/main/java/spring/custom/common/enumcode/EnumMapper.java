package spring.custom.common.enumcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumMapper {
  
  private Map<String, List<EnumCode>> factory = new LinkedHashMap<>();
  
  public EnumMapper(Set<Class<? extends EnumCodeType>> enumCodeSet) {
    for (Class<? extends EnumCodeType> enumCode : enumCodeSet) {
      this.put(enumCode);
    }
  }
  
  private void put(Class<? extends EnumCodeType> enumCodeType) {
    String key = enumCodeType.getName();
    Code codeAnnotation = enumCodeType.getAnnotation(Code.class);
    if (codeAnnotation != null) {
      key = codeAnnotation.value();
    } else {
      log.error("@Code(\"codename\") undefined: {}", enumCodeType.getName());
      return;
    }
    AtomicInteger idx = new AtomicInteger(0);
    List<EnumCode> values = Arrays.stream(enumCodeType.getEnumConstants())
        .map(item -> new EnumCode(item, idx.incrementAndGet()))
        .collect(Collectors.toList());
    //List<EnumCode> values = IntStream.of(enumCodeType.getEnumConstants().length)
    //    .mapToObj(idx -> new EnumCode(enumCodeType.getEnumConstants()[idx], idx))
    //    .collect(Collectors.toList());
    
    /* for debug */ if (log.isInfoEnabled()) log.info("{}: {}", key, values);
    this.factory.put(key, values);
  }
  
  public Map<String, List<EnumCode>> allCodes() {
    return this.factory;
  }
  
  public List<EnumCode> getCode(String key) {
    return this.factory.get(key);
  }
  
  public Map<String, List<EnumCode>> getCodes(String ... keys) {
    if (keys == null || keys.length == 0) return new LinkedHashMap<>();
    Map<String, List<EnumCode>> result = Arrays.stream(keys)
        .collect(Collectors.toMap(
            key -> key,
            key -> Optional.ofNullable(this.factory.get(key)).orElse(Collections.emptyList()),
            (existing, replacement) -> existing,
            LinkedHashMap::new));
    return result;
    /*
    Map<String, List<EnumCode>> result = new LinkedHashMap<>();
    for (String key : keys) {
      result.put(key, Optional.ofNullable(factory.get(key)).orElse(Collections.emptyList()));
    }
    return result;
    */
  }
  
}
