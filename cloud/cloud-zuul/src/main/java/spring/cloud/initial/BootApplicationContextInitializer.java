package spring.cloud.initial;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class BootApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  
  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    System.setProperty("java.net.preferIPv4Stack", "true");
    System.out.println("java.net.preferIPv4Stack=true");
  }
}
