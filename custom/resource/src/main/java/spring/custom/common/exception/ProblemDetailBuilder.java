package spring.custom.common.exception;

import java.net.URI;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

public class ProblemDetailBuilder {
  
  private HttpStatusCode status;
  private String detail;
  private String title;
  private URI type;
  private URI instance;
  
  public static ProblemDetailBuilder builder() {
    return new ProblemDetailBuilder();
  }
  
  public ProblemDetailBuilder status(HttpStatusCode status) {
    this.status = status;
    return this;
  }
  
  public ProblemDetailBuilder detail(String detail) {
    this.detail = detail;
    return this;
  }
  
  public ProblemDetailBuilder title(String title) {
    this.title = title;
    return this;
  }
  
  public ProblemDetailBuilder type(URI type) {
    this.type = type;
    return this;
  }
  
  public ProblemDetailBuilder instance(URI instance) {
    this.instance = instance;
    return this;
  }
  
  public ProblemDetail build() {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
    if (title != null) {
      problemDetail.setTitle(title);
    }
    if (type != null) {
      problemDetail.setType(type);
    }
    if (instance != null) {
      problemDetail.setInstance(instance);
    }
    return problemDetail;
  }
  
}