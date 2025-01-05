package spring.custom.common.dto;

import java.io.Serializable;

import lombok.Data;
import spring.custom.common.enumcode.ERROR_CODE;

@Data
public class ResponseDto<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private ResponseDto() {
    this(ERROR_CODE.S000.name(), ERROR_CODE.S000.message());
  }
  
  private ResponseDto(T data) {
    this(ERROR_CODE.S000.name(), ERROR_CODE.S000.message(), data);
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
