package spring.custom.api.controller.internal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import spring.custom.api.dto.MybatisSampleDto;
import spring.custom.common.mybatis.PageResponse;
import spring.custom.config.FeignClientConfig;

@FeignClient(name = "story-api", configuration = {FeignClientConfig.class})
public interface MybatisSampleInternalController {
  
  @GetMapping("/v1/mybatis-sample/scientists/all")
  public MybatisSampleDto.ScientistListRes allScientists();

  @GetMapping("/v1/mybatis-sample/scientists/search")
  public PageResponse<MybatisSampleDto.ScientistRes> searchScientists(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String fosCd,
      @RequestParam(required = false) @Min(1) @Max(21) @Nullable Integer century,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size);
  
}
