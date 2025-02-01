package spring.custom.api.dto;

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
    //private String fundCd;

    private Double fundSizeAmt;
    private Double fundUnitAmt;
    private Double basPrice;
    private Double taxBasPrice;
    private Double ir;
    private Double bmIr;
    private Double actIr;
    private Double irIdx;
    private Double bmIrIdx;
    private Double actIrIdx;
  }
}
