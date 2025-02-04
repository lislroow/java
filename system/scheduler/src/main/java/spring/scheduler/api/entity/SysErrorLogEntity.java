package spring.scheduler.api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "sy_error_log")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysErrorLogEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sy_error_log_seq")
  @SequenceGenerator(
    name = "sy_error_log_seq",
    sequenceName = "sy_error_log_id_seq",
    allocationSize = 1
  )
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
  //private String requestBody;
  private String method;
  private String requestUri;
  private String requestParam;
  private LocalDateTime createTime;
  private LocalDateTime modifyTime;
  private String createId;
  private String modifyId;
}
