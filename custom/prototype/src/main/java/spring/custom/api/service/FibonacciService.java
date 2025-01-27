package spring.custom.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.api.dto.FibonacciDto;
import spring.custom.api.func.CalculatorFunc;
import spring.custom.common.util.StringFormat;

@Service
@Slf4j
public class FibonacciService {
  
  // Basic
  public FibonacciDto.ResultRes fibonacciBasic(int n) {
    List<String> logs = new ArrayList<>();
    List<Map<String, String>> list = new ArrayList<>();
    for (int i=0; i<n; i++) {
      StopWatch watch = new StopWatch(StringFormat.toOrdinal(i+1));
      watch.start();
      double r = CalculatorFunc.fibonacci(i);
      list.add(Map.of(StringFormat.toOrdinal(i+1), StringFormat.toComma(r)));
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
  
  
  // multi-thread
  public FibonacciDto.ResultRes fibonacciThread(int n) {
    List<String> logs = new ArrayList<>();
    List<Map<String, String>> list = new ArrayList<>();
    ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(n);
    List<CompletableFuture<FibonacciResult>> futures = new ArrayList<>();
    for (int i=0; i<n; i++) {
      CompletableFuture<FibonacciResult> result = CompletableFuture
          .supplyAsync(new FibonacciTask(i, CalculatorFunc::fibonacci), pool);
      futures.add(result);
    }
    
    CompletableFuture<Void> allDone = CompletableFuture
        .allOf(futures.toArray(new CompletableFuture[0]));
    allDone.join();
    
    for (int i=0; i<n; i++) {
      double value = Double.MIN_VALUE;
      try {
        value = futures.get(i).get().getValue();
      } catch (InterruptedException|ExecutionException e) {
        e.printStackTrace();
      }
      list.add(Map.of(StringFormat.toOrdinal(i+1), StringFormat.toComma(value)));
    }
    pool.shutdown();
    return FibonacciDto.ResultRes.builder()
        .list(list)
        .logs(logs)
        .build();
  }
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class FibonacciResult {
    private int n;
    private double value;
  }
  
  class FibonacciTask implements Supplier<FibonacciResult> {
    int n;
    Function<Integer, Double> func;
    
    public FibonacciTask(int n, Function<Integer, Double> func) {
      this.n = n;
      this.func = func;
    }
    @Override
    public FibonacciResult get() {
      log.info(String.format("%d start", n+1));
      double valuer = this.func.apply(n);
      log.info(String.format("%d: %f done", n+1, valuer));
      return FibonacciResult.builder().n(n).value(valuer).build();
    }
  }
  
}
