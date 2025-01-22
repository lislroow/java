package spring.custom.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client implements User {

  private String userType;
  private String clientId;
  private String roles;
  private String clientName;
  
  @Override
  public String getId() {
    return this.clientId;
  }
  
  @Override
  public String getLoginId() {
    return "no-login-user";
  }
  
}
