package spring.custom.common.vo;

import java.io.Serializable;

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
public class MemberVo implements Serializable {
  
  private static final long serialVersionUID = 4358471420033975600L;
  
  private String id;
  private String registrationId;
  private String oauth2Id;
  private String email;
  private String nickname;
  private String role;
  private String ip;
  private String userAgent;
}
