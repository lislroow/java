package samples;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectionMain {
  
  public static class PersonVo {
    private String name;
  }
  
  public void process() {
    Map<String, Object> map = new HashMap<>();
    map.put("name", "john");
    
    PersonVo object = new PersonVo();
    Object obj = copyMapToObject(map, object);
    System.out.println(obj);
  }
  
  public Object copyMapToObject(Map<String, Object> map, Object object) {
    Class<?> clazz = object.getClass();
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      Field field;
      try {
        field = clazz.getDeclaredField(key);
        field.setAccessible(true);
        field.set(object, value);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    return object;
  }
  
  public static void main(String[] args) {
    new ReflectionMain().process();
  }
  
}
