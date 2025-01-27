package spring.custom.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

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
  
  public FibonacciDto.ResultRes fibonacci(int n) {
    List<Double> result = CalculatorFunc.fibonacci(n);
    AtomicInteger idx = new AtomicInteger(0);
    List<Map<String, String>> list = result.stream()
        .map(val -> Map.of(StringFormat.toOrdinal(idx.incrementAndGet()), StringFormat.toComma(val)))
        .collect(Collectors.toList());
    return FibonacciDto.ResultRes.builder()
        .list(list)
        .build();
  }
  
  // other:1
  public FibonacciDto.ResultRes fibonacciOther1(int n) {
    List<Double> result = null;
    if (n < 2) {
      result = DoubleStream.of(0.).boxed().collect(Collectors.toList());
    } else if (n >= 2) {
      //result = DoubleStream.of(0., 1.).boxed().toList(); // ImmutableCollections
      result = DoubleStream.of(0., 1.).boxed().collect(Collectors.toList());
    }
    
    for (int i=2; i<n; i++) {
      double val = result.get(i-2) + result.get(i-1);
      result.add(val);
    }
    
    AtomicInteger idx = new AtomicInteger(0);
    List<Map<String, String>> list = result.stream()
        .map(val -> Map.of(StringFormat.toOrdinal(idx.incrementAndGet()), StringFormat.toComma(val)))
        .collect(Collectors.toList());
    return FibonacciDto.ResultRes.builder()
        .list(list)
        .build();
  }
  
  // Basic
  public FibonacciDto.ResultRes fibonacciExperimentalSingleThread(int n) {
    List<String> times = new ArrayList<>();
    List<Map<String, String>> list = new ArrayList<>();
    for (int i=0; i<n; i++) {
      StopWatch watch = new StopWatch(StringFormat.toOrdinal(i+1));
      watch.start();
      double r = CalculatorFunc.fibonacci_A(i);
      list.add(Map.of(StringFormat.toOrdinal(i+1), StringFormat.toComma(r)));
      if (watch != null) {
        watch.stop();
        String time = watch.shortSummary();
        times.add(time);
        log.info(time);
      }
    }
    return FibonacciDto.ResultRes.builder()
        .list(list)
        .times(times)
        .build();
  }
  
  // multi-thread
  public FibonacciDto.ResultRes fibonacciExperimentalMultiThread(int n) {
    List<String> times = new ArrayList<>();
    List<Map<String, String>> list = new ArrayList<>();
    ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(n);
    List<CompletableFuture<FibonacciResult>> futures = new ArrayList<>();
    for (int i=0; i<n; i++) {
      CompletableFuture<FibonacciResult> result = CompletableFuture
          .supplyAsync(new FibonacciTask(i, CalculatorFunc::fibonacci_A), pool);
      futures.add(result);
    }
    
    CompletableFuture<Void> allDone = CompletableFuture
        .allOf(futures.toArray(new CompletableFuture[0]));
    allDone.join();
    
    for (int i=0; i<n; i++) {
      double value = Double.MIN_VALUE;
      String time = null;
      try {
        value = futures.get(i).get().getValue();
        time = futures.get(i).get().getTime();
      } catch (InterruptedException|ExecutionException e) {
        e.printStackTrace();
      }
      list.add(Map.of(StringFormat.toOrdinal(i+1), StringFormat.toComma(value)));
      times.add(time);
    }
    pool.shutdown();
    return FibonacciDto.ResultRes.builder()
        .list(list)
        .times(times)
        .build();
  }
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class FibonacciResult {
    private int n;
    private double value;
    private String time;
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
      StopWatch watch = new StopWatch(StringFormat.toOrdinal(this.n+1));
      watch.start();
      log.info(String.format("%d start", n+1));
      double valuer = this.func.apply(n);
      watch.stop();
      String time = watch.shortSummary();
      log.info(String.format("%d: %f done", n+1, valuer));
      return FibonacciResult.builder().n(n).value(valuer).time(time).build();
    }
  }
  
}
