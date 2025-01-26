package spring.custom.api.controller.internal;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import spring.custom.api.dto.FundDto;
import spring.custom.config.FeignClientConfig;

@FeignClient(name = "story-api", contextId = "fundInternalController", configuration = {FeignClientConfig.class})
public interface FundInternalController {
  
  @GetMapping("/v1/fund/fund-mst/all")
  public List<FundDto.FundMstRes> allFundMsts();
}
