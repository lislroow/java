package spring.sample.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.sample.common.constant.Constant;
import spring.sample.config.ConfigProperties;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constant.APP.BASE_URI)
public class ConfigController {
  
  final ConfigProperties configProperties;
  
  @GetMapping("/v1/config/test")
  public ConfigProperties get() {
    return this.configProperties;
  }
}
