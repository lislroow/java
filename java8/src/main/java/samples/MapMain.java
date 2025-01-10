package samples;

import java.util.concurrent.ConcurrentHashMap;

public class MapMain {
  
  public static void main(String[] args) {
    ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
    map.put(1, 2);
    map.put(3, 7);
    for (int i=1; i<=5; i++) {
      int key = i;
      // key에 대해 값이 존재하면 1씩 증가하고, 없으면 1로 설정
      map.merge(key, 1, Integer::sum);
      System.out.println("Key: " + key + ", Value: " + map.get(key));
    }
  }
  
}
