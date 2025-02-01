package snippets.collection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapMain {
  
  public static void main(String[] args) {
    Map<Integer, Integer> map = new ConcurrentHashMap<>();
    map.put(1, 2);
    map.put(3, 7);
    for (int i=1; i<=5; i++) {
      map.merge(i, 1, Integer::sum);
      System.out.println("Key: " + i + ", Value: " + map.get(i));
    }
  }
  
}
