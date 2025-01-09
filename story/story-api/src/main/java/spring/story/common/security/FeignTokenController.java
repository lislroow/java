package spring.story.common.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import spring.custom.common.enumcode.TOKEN;
import spring.custom.config.FeignClientConfig;
import spring.custom.dto.TokenResDto;

@FeignClient(name = "auth-api", configuration = FeignClientConfig.class)
public interface FeignTokenController {
  
  @PostMapping("/v1/token/create/{userType}/{userId}")
  public TokenResDto.Create create(
      @PathVariable TOKEN.USER userType,
      @PathVariable String userId);
  
}
