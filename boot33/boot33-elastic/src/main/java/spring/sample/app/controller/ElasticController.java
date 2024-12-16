package spring.sample.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.sample.app.service.ElasticService;
import spring.sample.app.vo.FibonacciVo;
import spring.sample.constant.Constant;

@RestController
@RequiredArgsConstructor
public class ElasticController {
  
  final ElasticService elasticService;
  
  @GetMapping("/v1/es/fibonacci")
  public List<FibonacciVo> findListByDays(@RequestParam("fromDays") Integer fromDays,
      @RequestParam("offset") Integer offset) {
    return elasticService.findListByDays(fromDays, offset);
  }
  
  @GetMapping("/v1/es/fibonacci/{days}")
  public Long findByDays(@PathVariable("days") Integer days) {
    return elasticService.findByDays(days);
  }
}
