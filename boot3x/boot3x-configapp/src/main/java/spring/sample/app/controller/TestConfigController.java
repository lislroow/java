package spring.sample.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.sample.config.TestConfigProperties;

@RestController
public class TestConfigController {
  
  TestConfigProperties testConfigProperties;
  
  public TestConfigController(TestConfigProperties testConfigProperties) {
    this.testConfigProperties = testConfigProperties;
  }
  
/*
curl http://localhost:38083/api/test/config
*/
  @GetMapping("/api/test/config")
  public TestConfigProperties get() {
    return this.testConfigProperties;
  }
}
