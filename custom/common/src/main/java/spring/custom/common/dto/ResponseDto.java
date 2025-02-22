package spring.custom.common.dto;

import lombok.Data;
import spring.custom.common.syscode.ERROR;

@Deprecated
@Data
public class ResponseDto<T> {

  private ResponseDto() {
    this(ERROR.S000.name(), ERROR.S000.message());
  }
  
  private ResponseDto(T data) {
    this(ERROR.S000.name(), ERROR.S000.message(), data);
  }
  
  private ResponseDto(String code, String message) {
    this(code, message, null);
  }
  
  private ResponseDto(String code, String message, T data) {
    this.header.code = code;
    this.header.message = message;
    this.body = data;
  }
  
  private Header header = new Header();
  private final T body;
  
  @Data
  public static class Header {
    private String code;
    private String message;
  }
  
  public static<T> ResponseDto<T> body(String code, String message, T data) {
    return new ResponseDto<>(code, message, data);
  }
  
  public static<T> ResponseDto<T> body(String code, String message) {
    return new ResponseDto<>(code, message);
  }
  
  public static<T> ResponseDto<T> body(ERROR responseCode, T data) {
    return new ResponseDto<>(responseCode.code(), responseCode.message(), data);
  }
  
  public static<T> ResponseDto<T> body(ERROR responseCode) {
    return new ResponseDto<>(responseCode.code(), responseCode.message());
  }
  
  public static<T> ResponseDto<T> body(T data) {
    return new ResponseDto<>(data);
  }

  public static<T> ResponseDto<T> body() {
    return new ResponseDto<>();
  }

}
