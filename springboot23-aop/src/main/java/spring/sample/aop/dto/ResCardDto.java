package spring.sample.aop.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "카드 이용내역 Response Dto")
@Data
public class ResCardDto {
  
  /**
   * 카드 잔액조회 응답 DTO
   */
  @ApiModel(value = "카드 잔액조회 응답 DTO")
  @Data
  public static class Bal {
    private String field;
  }

  /**
   * 카드 이용내역 응답 DTO
   */
  @ApiModel(value = "카드 이용내역 응답 DTO")
  @Data
  public static class UsageList {
    private Long totCnt;
    private Integer pageNum;
    private Integer pageSize;

    private List<SubData> list;
    @ApiModel(value = "내역 Data")
    @Data
    public static class SubData {
      /** 순번 */
      @ApiModelProperty(
          value = "순번 - PK",
          position = 1
      )
      private Long seq;
      
      private String storeName;
      private String paymentTime;
      private String paymentAmt;
    }
  }
  
  /**
   * 카드 발급이력 조회 응답 DTO
   */
  @ApiModel(value = "카드 발급이력 조회 응답 DTO")
  @Data
  public static class IssueList {
    private String field;
  }
}
