package spring.sample.app.vo;

import lombok.Data;

@Data
public class TraceApiVo {
  
  private String id;
  private String remoteAddr;
  private String param;
  private Long expireTime;
  private Long accessTime;
  
}
