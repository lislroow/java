package spring.sample.app.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import spring.sample.app.code.CD_QV_DATA_TYPE;
import spring.sample.app.code.CD_REQ_RES;

@ApiModel(value = "Req DTO: QualiSrvc(Qualification-Verify Service), 자격증명 서비스")
@Data
public class ReqQualiSrvcDto {
  
  @Data
  public static class Srvc {
    private String id;
    private String srvcName;
  }
  
  @ApiModel(value = "서비스 목록 조회")
  @Data
  public static class SrvcSearch {
    @ApiModelProperty(value = "페이지 번호", example = "1", position = 1, required = true)
    @NotNull
    private Integer pageNum;
    
    @ApiModelProperty(value = "페이지 사이즈", example = "10", position = 2, required = true)
    @NotNull
    private Integer pageSize;
    
    private String id;
    private String srvcName;
  }
  
  @Data
  public static class SrvcData {
    private String qvServiceId;
    
    private Integer id;
    private String dataNameKo;
    private String dataNameEn;
    private String reqRes;
    private String dataType;
  }
}
