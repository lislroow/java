package spring.sample.cloud.api.employ.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.sample.cloud.config.exception.validator.ValidStartDate;

//import jakarta.validation.constraints.Digits;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployRegREQ {

  @NotEmpty(message = "@NotEmpty 오류 입니다.")
  private String id;
  
  @NotBlank(message = "@NotBlank 오류 입니다.")
  private String name;
  
  @NotNull(message = "@NotNull 오류 입니다.")
  @Positive(message = "@Positive 오류 입니다.")
  private Integer age;
  
  @Digits(integer = 1, fraction = 1, message = "@Digits(integer = 1, fraction = 1) 오류 입니다.")
  private Double eyesight;
  
  @Email(message = "@Email 오류 입니다.")
  private String email;
  
  @NotBlank(message = "@NotBlank 오류 입니다.")
  private String password;
  
  @NotBlank(message = "@NotBlank 오류 입니다.")
  private String confirmPassword;
  
  @AssertTrue(message = "@AssertTrue 오류 입니다.")
  private boolean isPasswordsMatching() {
      return password != null && password.equals(confirmPassword);
  }
  
  @Size(min = 3, max = 4, message = "@Size 오류 입니다.")
  private String employCode;
  
  @Pattern(regexp = "ROLE_DEVELOPER|ROLE_MAINTAINER", message = "@Pattern 오류 입니다.")
  private String roleId;
  
  @Pattern(regexp = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
      , message = "@Pattern 오류 입니다.")
  private String allowedIp;
  
  @ValidStartDate
  private LocalDateTime startDate;
  // 파라미터 문자열이 표준 형식(yyyy-MM-dd'T'hh:mm:ss)이 아닐 경우,
  // 문자열 타입의 setter 를 추가해서 LocalDateTime 를 생성하는 방식을 사용할 수 있습니다.
//  public void setStartDate(String startDate) {
//    System.out.println("문자열 형식(yyyy-MM-dd)의 파라미터 입니다. startDate = "+startDate);
//    if (startDate != null &&
//        startDate.length() == 10) {
//      LocalDate date = LocalDate.parse(startDate);
//      this.startDate = LocalDateTime.of(date, LocalTime.MIN);
//    } else {
//      this.startDate = LocalDateTime.parse(startDate);
//    }
//  }
  
}
