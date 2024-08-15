package spring.sample.mybatis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import spring.sample.mybatis.config.mybatis.Pageable;
import spring.sample.mybatis.vo.AuditVO;

@Data
public class EmployDTO {

  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class SelectVO extends Pageable {
    private String id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class InsertVO extends AuditVO {
    private String id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class UpdateVO extends AuditVO {
    private String id;
    private String name;
  }
}
