package spring.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import spring.sample.common.security.LoginFilter;
import spring.sample.common.security.LoginService;
import spring.sample.config.properties.SecurityConfigProperties;

@Configuration
@EnableConfigurationProperties({SecurityConfigProperties.class})
public class SecurityConfig {
  
  AuthenticationConfiguration authenticationConfiguration;
  SecurityConfigProperties properties;
  
  public SecurityConfig(final AuthenticationConfiguration authenticationConfiguration,
      SecurityConfigProperties properties) {
    this.authenticationConfiguration = authenticationConfiguration;
    this.properties = properties;
  }
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AuthenticationManager authenticationManager = 
        this.authenticationConfiguration.getAuthenticationManager();
    
    http
      .csrf(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .headers((headers) -> headers.disable())
      .authorizeHttpRequests((authorizeHttpRequests) -> {
        authorizeHttpRequests.requestMatchers("/**").permitAll();
        authorizeHttpRequests.requestMatchers("/v1/security/**").authenticated();
      })
      .addFilterAt(new LoginFilter(authenticationManager, properties),
          UsernamePasswordAuthenticationFilter.class)
      .authenticationProvider(daoAuthenticationProvider())
      ;
    SecurityFilterChain securityFilterChain = http.build();
    return securityFilterChain;
  }
  
  @Autowired
  private LoginService userDetailsServiceImpl;
  
  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsServiceImpl);
    provider.setPasswordEncoder(new BCryptPasswordEncoder());
    return provider;
  }

  @Bean("bcryptPasswordEncoder")
  BCryptPasswordEncoder bcryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

}
