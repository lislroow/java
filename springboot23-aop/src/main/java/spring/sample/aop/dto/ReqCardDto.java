package spring.sample.aop.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Res Card DTO")
@Data
public class ReqCardDto {
  
  @ApiModel(value = "카드 잔액조회 DTO")
  @Data
  public static class Bal {
    private String field;
  }

  @ApiModel(value = "카드 이용내역 DTO")
  @Data
  public static class UsageSearch {
    /** 페이지 번호 */
    @ApiModelProperty(value = "페이지 번호", example = "1", position = 1, required = true)
    @NotNull
    private Integer pageNum;

    /** 페이지 사이즈 */
    @ApiModelProperty(value = "페이지 사이즈", example = "10", position = 2, required = true)
    @NotNull
    private Integer pageSize;
    
    private String name;
    private String cardNo;
  }
  
  /**
   * 카드 발급이력 조회 DTO
   */
  @ApiModel(value = "카드 발급이력 조회 DTO")
  @Data
  public static class IssueSearch {
    private String field;
  }
}
