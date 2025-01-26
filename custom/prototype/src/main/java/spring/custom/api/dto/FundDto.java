package spring.custom.api.dto;

import lombok.Data;

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
  public static class FundIrRes {

    private String basYmd;
    //private String fundCd;
    
    private Double basPrice;
  }
}
