package spring.custom.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientPrincipal implements Principal {

  private String userType;
  private String clientId;
  private String roles;
  private String clientName;
  
  @Override
  @JsonIgnore
  public String getName() {
    return this.clientId;
  }

  @Override
  public String getId() {
    return this.clientId;
  }
  
  @Override
  public String getLoginId() {
    return "no-login-user";
  }
  
}
