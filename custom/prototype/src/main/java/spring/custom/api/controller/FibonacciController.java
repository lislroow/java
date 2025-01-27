package spring.custom.api.controller;

import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import spring.custom.api.dto.FibonacciDto;
import spring.custom.api.service.FibonacciService;

@RestController
@RequiredArgsConstructor
public class FibonacciController {
  
  final FibonacciService fibonacciService;
  
  @GetMapping("/v1/fibonacci")
  public FibonacciDto.ResultRes fibonacciOther2(
      @RequestParam(required = true) @Min(1) Integer n) {
    StopWatch watch = new StopWatch("fibonacci");
    watch.start();
    FibonacciDto.ResultRes resDto = fibonacciService.fibonacci(n);
    watch.stop();
    resDto.setSummary(watch.shortSummary());
    return resDto;
  }
  
  @GetMapping("/v1/fibonacci/other1")
  public FibonacciDto.ResultRes fibonacciOther1(
      @RequestParam(required = true) @Min(1) Integer n) {
    StopWatch watch = new StopWatch("fibonacci:other1");
    watch.start();
    FibonacciDto.ResultRes resDto = fibonacciService.fibonacciOther1(n);
    watch.stop();
    resDto.setSummary(watch.shortSummary());
    return resDto;
  }
  
  @GetMapping("/v1/fibonacci/experimental/single-thread")
  public FibonacciDto.ResultRes fibonacciSingleThread(
      @RequestParam(required = true, defaultValue = "40") @Min(1) @Max(45) Integer n) {
    StopWatch watch = new StopWatch("fibonacci:basic");
    watch.start();
    FibonacciDto.ResultRes resDto = fibonacciService.fibonacciExperimentalSingleThread(n);
    watch.stop();
    resDto.setSummary(watch.shortSummary());
    return resDto;
  }
  
  @GetMapping("/v1/fibonacci/experimental/multi-thread")
  public FibonacciDto.ResultRes fibonacciMultiThread(
      @RequestParam(required = true, defaultValue = "40") @Min(1) @Max(45) Integer n) {
    StopWatch watch = new StopWatch("fibonacci:thread");
    watch.start();
    FibonacciDto.ResultRes resDto = fibonacciService.fibonacciExperimentalMultiThread(n);
    watch.stop();
    resDto.setSummary(watch.shortSummary());
    return resDto;
  }
}
