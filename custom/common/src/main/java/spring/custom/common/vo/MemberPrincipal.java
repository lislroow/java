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
public class MemberPrincipal implements Principal {

  private String userType;
  private String id;
  private String roles;
  private String loginId;
  private String nickname;
  private String registrationId;
  private String oauth2Id;
  
  @Override
  @JsonIgnore
  public String getName() {
    return this.id;
  }
}
