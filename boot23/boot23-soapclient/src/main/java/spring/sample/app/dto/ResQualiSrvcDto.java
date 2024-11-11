package spring.sample.app.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "Res DTO: QualiSrvc(Qualification-Verify Service), 자격증명 서비스")
@Data
public class ResQualiSrvcDto {

  @ApiModel(value = "서비스 목록 조회")
  @Data
  public static class SrvcList {
    private Long totCnt;
    private Integer pageNum;
    private Integer pageSize;

    private List<SubData> list;
    @ApiModel(value = "목록 Data")
    @Data
    public static class SubData {
      private String id;
      private String srvcName;
    }
  }
  
  @ApiModel(value = "서비스 조회")
  @Data
  public static class Srvc {
    private String id;
    private String srvcName;
  }
  
  @ApiModel(value = "서비스 목록 조회")
  @Data
  public static class SrvcDataList {
    private List<SubData> list;

    @Data
    public static class SubData {
      private String qvServiceId;
      
      private Integer id;
      private String dataNameKo;
      private String dataNameEn;
      private String reqRes;
      private String dataType;
    }
  }
  
}
