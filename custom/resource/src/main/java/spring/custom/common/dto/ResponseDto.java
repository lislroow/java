package spring.custom.common.dto;

import java.io.Serializable;

import lombok.Data;
import spring.custom.common.enumcode.ERROR_CODE;

@Data
public class ResponseDto<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private ResponseDto() {
    this.header = new Header();
    this.header.code = ERROR_CODE.S000.name();
    this.header.message = ERROR_CODE.S000.message();
    this.body = null;
  }
  
  private ResponseDto(T data) {
    this.header = new Header();
    this.header.code = ERROR_CODE.S000.name();
    this.header.message = ERROR_CODE.S000.message();
    this.body = data;
  }
  
  private ResponseDto(String code, String message, T data) {
    this.header = new Header();
    this.header.code = code;
    this.header.message = message;
    this.body = data;
  }
  
  private ResponseDto(String code, String message) {
    this.header = new Header();
    this.header.setCode(code);
    this.header.setMessage(message);
    this.body = null;
  }
  
  private Header header = new Header();
  private final T body;
  
  @Data
  public static class Header implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String code;
    private String message;
  }
  
  public static<T> ResponseDto<T> body(String code, String message, T data) {
    return new ResponseDto<>(code, message, data);
  }
  
  public static<T> ResponseDto<T> body(String code, String message) {
    return new ResponseDto<>(code, message);
  }
  
  public static<T> ResponseDto<T> body(ERROR_CODE responseCode, T data) {
    return new ResponseDto<>(responseCode.code(), responseCode.message(), data);
  }
  
  public static<T> ResponseDto<T> body(ERROR_CODE responseCode) {
    return new ResponseDto<>(responseCode.code(), responseCode.message());
  }
  
  public static<T> ResponseDto<T> body(T data) {
    return new ResponseDto<>(data);
  }

  public static<T> ResponseDto<T> body() {
    return new ResponseDto<>();
  }

}
