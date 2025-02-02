package main.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import spring.custom.api.entity.rec.FundIrCountRec;

public class ListMain {
  
  public static void main(String[] args) {
    List<FundIrCountRec> list = List.of(
        new FundIrCountRec("A", 300L),
        new FundIrCountRec("B", 500L),
        new FundIrCountRec("C", 400L),  // 여기까지 1200 → 새로운 리스트 시작
        new FundIrCountRec("D", 600L),
        new FundIrCountRec("E", 200L),
        new FundIrCountRec("F", 300L)   // 여기까지 1100 → 새로운 리스트 시작
      );
    split(list, 1000L).forEach(item -> {
      System.out.println(item);
    });
  }
  
  public static List<List<FundIrCountRec>> split(List<FundIrCountRec> srcList, Long threshold) {
    List<List<FundIrCountRec>> result = new ArrayList<>();
    long sum = 0;
    int start = 0;
    int end = 0;
    for (int i=0; i<srcList.size(); i++) {
      sum += srcList.get(i).count();
      if (sum >= threshold) {
        end = i+1;
        result.add(srcList.subList(start, end));
        start = end; // next start
        sum = 0; // clear sum
      }
    }
    
    if (end < srcList.size()) {
      result.add(srcList.subList(start, srcList.size()));
    }
    
    return result;
  }
}
