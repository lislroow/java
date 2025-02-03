package spring.custom.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FundDto {
  
  @Data
  public static class FundMstRes {

    private Boolean deleted;
    private String fundCd;
    
    private String amcCd;
    private String amcFundCd;
    private String fundFnm;
    private String fundGb;
    private String kitcaSortCd;
    private String xtypeLcd;
    private String xtypeCd;
    private Integer unitPrice;
    private Integer basUnit;
    private String seoljYmd;
    private String chgYmd;
    private String haejiGb;
    private String haejiYmd;
    private Double fstSeoljAek;
    private Double fstSeoljJwa;
    private Double fstGijunGa;
    private Double stockPnibRt;
    private Double kosdaqPnibRt;
    private String bmCd;
    private String bmNm;
    private String bmEnm;
    private String excHdgeYnCd;
    private String kitcaTypeCd;
  }
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class FundIrRes {
    
    private String basYmd;
    @JsonIgnore
    private String fundCd; // redis, chart 에는 불필요한 컬럼이지만 stream 에서 groupBy 를 위해 필요함

    //private Double fundSizeAmt;
    //private Double fundUnitAmt;
    private Double basPrice;
    //private Double taxBasPrice;
    //private Double ir;
    //private Double bmIr;
    //private Double actIr;
    //private Double irIdx;
    //private Double bmIrIdx;
    //private Double actIrIdx;
  }
}
