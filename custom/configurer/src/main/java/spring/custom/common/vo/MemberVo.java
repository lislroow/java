package spring.custom.common.vo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties("passwd")
public class MemberVo {
  
  private String id;
  private String registrationId;
  private String oauth2Id;
  private String email;
  private String password;
  private String nickname;
  private String role;
  private String ip;
  private String userAgent;
  
  public Map<String, Object> toMap() {
    Map<String, Object> map = Map.ofEntries(
        Map.entry("password", this.password),
        
        Map.entry("username", this.email),
        Map.entry("role", this.role)
        );
    return map;
  }
  
  public static MemberVo fromMap(Map<String, Object> map) {
    MemberVo result = MemberVo.builder()
        .email(map.getOrDefault("username", "").toString())
        .role(map.getOrDefault("role", "").toString())
        .build();
    return result;
  }
}
