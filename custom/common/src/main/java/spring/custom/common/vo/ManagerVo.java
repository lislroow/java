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
public class ManagerVo implements TokenPrincipal {
  
  private String id;
  private String roles;
  private String loginId;
  private String mgrName;
  
  @Override
  @JsonIgnore
  public String getName() {
    return this.id;
  }
  
  @Override
  public String getUsername() {
    return this.mgrName;
  }
  
}
