### 현상

- MapperScannerConfigurer 구현체 프로퍼티 접근 불가

```java
public class MapperScannerConfigurer
    implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {
    
}
```

### 결론

- BeanDefinitionRegistryPostProcessor 인터페이스 구현체의 초기화 시점은 프로퍼티를 초기화 하기 전임
- @EnableConfigurationProperties, @ConfigurationProperties, @Value, Environment 를 사용한 프로퍼티 접근이 제한됨


