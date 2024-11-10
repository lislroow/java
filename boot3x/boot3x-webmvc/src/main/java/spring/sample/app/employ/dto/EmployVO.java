package spring.sample.app.employ.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.sample.app.audit.dto.AuditVO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployVO extends AuditVO {

  private String id;
  private String name;
  private Integer age;
  private Double eyesight;
  private String email;
  private String password;
  private String confirmPassword;
  private String employCode;
  private String roleId;
  private String allowedIp;
  
  // JsonSerializer 에서 사용할 포맷을 지정
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
  private LocalDateTime startDate;
  
  public static EmployVO of(EmployRegREQ req) {
    return EmployVO.builder()
        .id(req.getId())
        .name(req.getName())
        .age(req.getAge())
        .eyesight(req.getEyesight())
        .password(req.getPassword())
        .confirmPassword(req.getConfirmPassword())
        .employCode(req.getEmployCode())
        .roleId(req.getRoleId())
        .allowedIp(req.getAllowedIp())
        .startDate(req.getStartDate())
        .build();
  }
}
