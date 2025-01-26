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
  
  @GetMapping("/v1/fibonacci/basic")
  public FibonacciDto.ResultRes fibonacciBasic(
      @RequestParam(required = true) @Min(1) @Max(45) Integer n) {
    StopWatch watch = new StopWatch("fibonacci:basic");
    watch.start();
    FibonacciDto.ResultRes resDto = fibonacciService.fibonacciBasic(n);
    watch.stop();
    resDto.setSummary(watch.shortSummary());
    return resDto;
  }
  
  @GetMapping("/v1/fibonacci/fork")
  public FibonacciDto.ResultRes fibonacciFork(
      @RequestParam(required = true) @Min(1) @Max(43) Integer n) {
    StopWatch watch = new StopWatch("fibonacci:fork");
    watch.start();
    FibonacciDto.ResultRes resDto = fibonacciService.fibonacciFork(n);
    watch.stop();
    resDto.setSummary(watch.shortSummary());
    return resDto;
  }
}
