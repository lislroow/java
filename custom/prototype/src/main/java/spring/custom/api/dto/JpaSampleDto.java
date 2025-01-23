package spring.custom.api.dto;

import java.util.List;

import lombok.Data;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.mybatis.PageInfo;

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
  public static class StarListRes {
    private List<StarRes> list;
    
    public StarListRes(List<StarRes> list) {
      this.list = list;
    }
  }
  
  @Data
  public static class PagedStarListRes {
    private PageInfo paged;
    private List<StarRes> list;
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
