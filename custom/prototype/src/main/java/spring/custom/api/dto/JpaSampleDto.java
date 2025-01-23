package spring.custom.api.dto;

import lombok.Data;
import spring.custom.common.audit.AuditVo;

public class JpaSampleDto {
  
  private JpaSampleDto() { }
  
  @Data
  public static class StarRes extends AuditVo {
    private Integer id;
    private String name;
    private Double distance;
    private Double brightness;
    private Double mass;
    private Integer temperature;
  }
  
  @Data
  public static class AddStarReq {
    private String name;
    private Double distance;
    private Double brightness;
    private Double mass;
    private Integer temperature;
  }
  
  @Data
  public static class ModifyStarReq {
    private Integer id;
    private String name;
    private Double distance;
    private Double brightness;
    private Double mass;
    private Integer temperature;
  }
  
}
