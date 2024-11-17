#### 1) filter bean 생성 시 Config 관련 Casting 예외 발생

```java
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
  
  public AuthFilter() {
    // default 생성자에서 super() 호출하는 코드가 없을 경우 Casting 예외 발생 
    super(AuthFilter.Config.class);
  }
  
  @Data
  public static class Config {
    private Long id = -1L;
  }
}
``