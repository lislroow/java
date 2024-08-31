package spring.sample.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.sample.common.aspectj.dao.NsTraceApiDao;
import spring.sample.common.vo.NsTraceApiVo;

@RestController
@RequiredArgsConstructor
public class TestController {
  
  private final NsTraceApiDao dao;
  
  @GetMapping("/card/v1/test")
  public String test() {
    NsTraceApiVo vo = dao.selectTraceById("24c33c06-c0e4-4175-92f0-7f00bd7f0857");
    return vo.toString();
  }
}
