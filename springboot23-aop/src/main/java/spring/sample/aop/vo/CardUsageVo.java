package spring.sample.aop.vo;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Alias("cardUsageVo")
public class CardUsageVo {
  /**storeName**/
  private String storeName;
  /**paymentTime**/
  private Timestamp paymentTime;
  /**paymentAmt**/
  private Long paymentAmt;
}
