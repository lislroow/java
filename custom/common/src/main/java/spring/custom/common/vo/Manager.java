package spring.custom.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manager implements User {

  private String userType;
  private String id;
  private String roles;
  private String loginId;
  private String mgrName;
  
}
