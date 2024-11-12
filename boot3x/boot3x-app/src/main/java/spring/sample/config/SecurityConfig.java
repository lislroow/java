package spring.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .headers((headers) -> headers.disable())
      .authorizeHttpRequests((authorizeHttpRequests) -> {
        authorizeHttpRequests.requestMatchers("/actuator/**").permitAll();
        authorizeHttpRequests.requestMatchers("/health-check/**").permitAll();
        //authorizeHttpRequests.requestMatchers("/mybatis/**").permitAll();
        authorizeHttpRequests.requestMatchers("/**").authenticated();
      });
    SecurityFilterChain securityFilterChain = http.build();
    return securityFilterChain;
  }
}
