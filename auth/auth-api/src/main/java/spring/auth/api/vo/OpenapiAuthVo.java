package spring.auth.api.vo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.custom.common.security.AuthDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenapiAuthVo implements AuthDetails {
  
  private static final long serialVersionUID = -5566207645876050761L;
  
  private String id;
  private String userId;
  private String userName;
  private String role;
  
  @Override
  public String getUsername() {
    return this.id;
    // return this.userId;
  }

  @Override
  public String getPassword() {
    return "";
  }
  
  @Override
  public Map<String, Object> toToken() {
    // AuthDao 의 결과값 > 'not null'
    Map<String, Object> map = Map.ofEntries(
        Map.entry("id", this.id),
        Map.entry("userId", this.userId),
        Map.entry("userName", this.userName)
        );
    return map;
  }
  
}
