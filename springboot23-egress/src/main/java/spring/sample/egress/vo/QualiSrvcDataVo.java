package spring.sample.egress.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("qualiSrvcDataVo")
@Data
public class QualiSrvcDataVo {

  private String qvServiceId;
  
  private Integer id;
  private String dataNameKo;
  private String dataNameEn;
  private String reqRes;
  private String dataType;
}
