package spring.story.common.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import spring.custom.common.enumcode.TOKEN;
import spring.custom.config.FeignClientConfig;
import spring.custom.dto.TokenDto;

@FeignClient(name = "auth-api", configuration = FeignClientConfig.class)
public interface FeignTokenController {
  
  @PostMapping("/v1/token/create/{userType}/{userId}")
  public TokenDto.CreateRes create(
      @PathVariable TOKEN.USER_TYPE userType,
      @PathVariable String userId);
  
}
