package spring.sample.common.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("nsTraceApiVo")
@Data
public class NsTraceApiVo {
  
  private String id;
  private String remoteAddr;
  private String param;
  private Long expireTime;
  private Long accessTime;
  
  @Override
  public String toString() {
    return "NonSecureApiTraceVo {remoteAddr'=" + remoteAddr +"'}";
  }
}
