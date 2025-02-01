package spring.redis.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@RedisHash
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundMstRvo {
  
  @Id
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
