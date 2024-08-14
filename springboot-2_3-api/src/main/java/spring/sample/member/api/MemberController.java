package spring.sample.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.member.feign.ExternalDto;
import spring.sample.member.feign.ExternalFeignClient;

@RestController
@AllArgsConstructor
@Slf4j
public class MemberController {
  
  private ExternalFeignClient externalFeignClient;
  
  @GetMapping("/api/v1/member")
  public MemberDto.MemberInfoVo getMemberInfo() {
    MemberDto.MemberInfoVo result = MemberDto.MemberInfoVo.builder()
        .id("123")
        .name("gildong.hong")
        .build();
    log.info("{}", result);
    return result;
  }
  
  @GetMapping("/api/v1/member/external")
  public ResponseEntity<ExternalDto.ExternalInfoVo> getExternalInfo() {
    ResponseEntity<ExternalDto.ExternalInfoVo> result = externalFeignClient.getExternalInfo();
    log.info("{}", result);
    return result;
  }
  
}
