package spring.custom.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;
import spring.custom.api.dto.FibonacciDto;
import spring.custom.common.util.StringFormat;

@Service
@Slf4j
public class FibonacciService {
  
  // Basic
  public FibonacciDto.ResultRes fibonacciBasic(int n) {
    List<String> logs = new ArrayList<>();
    List<Map<String, String>> list = new ArrayList<>();
    for (int i=0; i<n; i++) {
      StopWatch watch = new StopWatch(StringFormat.toOrdinal(i));
      watch.start();
      int r = fibonacci(i);
      list.add(Map.of(StringFormat.toOrdinal(i), StringFormat.toComma(r)));
      if (watch != null) {
        watch.stop();
        String result = watch.shortSummary();
        logs.add(result);
        log.info(result);
      }
    }
    return FibonacciDto.ResultRes.builder()
        .list(list)
        .logs(logs)
        .build();
  }
  
  int fibonacci(int n) {
    if (n <= 1) {
      return n;
    }
    return fibonacci(n-1) + fibonacci(n - 2);
  }
  
  
  // Fork
  public FibonacciDto.ResultRes fibonacciFork(int n) {
    List<String> logs = new ArrayList<>();
    List<Map<String, String>> list = new ArrayList<>();
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    for (int i=0; i<n; i++) {
      StopWatch watch = new StopWatch(StringFormat.toOrdinal(i));
      watch.start();
      int r = forkJoinPool.invoke(new FibonacciTask(i));
      list.add(Map.of(StringFormat.toOrdinal(i), StringFormat.toComma(r)));
      watch.stop();
      String result = watch.shortSummary();
      logs.add(result);
      log.info(result);
    }
    return FibonacciDto.ResultRes.builder()
        .list(list)
        .logs(logs)
        .build();
  }
  
  @SuppressWarnings("serial")
  class FibonacciTask extends RecursiveTask<Integer> {
    private int n;
    
    public FibonacciTask(int n) {
      this.n = n;
    }
    
    @Override
    protected Integer compute() {
      if (n <= 1) {
        return n;
      }
      
      FibonacciTask task1 = new FibonacciTask(n - 1);
      FibonacciTask task2 = new FibonacciTask(n - 2);
      task1.fork();
      task2.fork();
      
      return task1.join() + task2.join();
    }
  }
  
}
