package spring.sample.common.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("nsBlockedClientVo")
@Data
public class NsBlockedClientVo {
  
  private String remoteAddr;
  private Long unblockTime;
}
