### boot 2.3

```java
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class BootApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  
  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    System.setProperty("java.net.preferIPv4Stack", "true");
    System.out.println("java.net.preferIPv4Stack=true");
  }
}
```

```property
#/src/main/resources/META-INF/spring.factories
org.springframework.context.ApplicationContextInitializer=\
spring.sample.initial.BootApplicationContextInitializer
```


### boot 2.4+, boot 3.x

```java
import org.springframework.boot.BootstrapRegistry;

public class BootstrapRegistryInitializer implements org.springframework.boot.BootstrapRegistryInitializer {

  @Override
  public void initialize(BootstrapRegistry registry) {
    System.setProperty("java.net.preferIPv4Stack", "true");
    System.out.println("java.net.preferIPv4Stack=true");
  }
}
```

```property
#/src/main/resources/META-INF/spring.factories
org.springframework.boot.BootstrapRegistryInitializer=\
spring.sample.initial.BootstrapInitializer
```
