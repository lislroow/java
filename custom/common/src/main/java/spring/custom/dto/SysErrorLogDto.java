package spring.custom.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.vo.AuditVo;

public class SysErrorLogDto {
  
  private SysErrorLogDto() { }
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class RedisDto {
    private LocalDateTime txTime;
    private String traceId;
    private String spanId;
    private String className;
    private String stacktrace;
    private String hostname;
    private String hostIp;
    private String serviceName;
    private String clientIp;
    private String requestBody;
    private String requestParam;
    private String requestUri;
    
    private LocalDateTime createTime;
    private String createId;
    private LocalDateTime modifyTime;
    private String modifyId;
  }
  
  @Deprecated
  @Data
  public static class SysErrorLogReq extends AuditVo {
    private LocalDateTime txTime;
    private String traceId;
    private String className;
    private String stacktrace;
    private String serviceName;
    private String clientIp;
    private String requestUri;
  }
  
  @Data
  public static class SysErrorLogRes {
    private Long id;
    private LocalDateTime txTime;
    private String traceId;
    private String spanId;
    private String className;
    private String stacktrace;
    private String hostname;
    private String hostIp;
    private String serviceName;
    private String clientIp;
    private String requestBody;
    private String requestUri;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private String createId;
    private String modifyId;
  }
}
