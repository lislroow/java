package spring.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import spring.sample.filter.AuthenticationFilter;

@Configuration
@EnableWebSecurity
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
        authorizeHttpRequests.requestMatchers("/**").authenticated();
      })
      .addFilterAt(new AuthenticationFilter(),
          UsernamePasswordAuthenticationFilter.class)
      ;
    SecurityFilterChain securityFilterChain = http.build();
    return securityFilterChain;
  }
}
