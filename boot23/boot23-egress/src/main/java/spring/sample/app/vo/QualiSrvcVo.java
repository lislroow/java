package spring.sample.app.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("qualiSrvcVo")
@Data
public class QualiSrvcVo {

  private String id;
  private String srvcName;
}
