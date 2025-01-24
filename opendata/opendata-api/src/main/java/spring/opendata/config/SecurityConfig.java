package spring.opendata.config;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import spring.custom.common.security.TokenValueFilter;
import spring.custom.common.syscode.SECURITY;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  
  final ModelMapper modelMapper;
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .addFilterBefore(new TokenValueFilter(modelMapper), UsernamePasswordAuthenticationFilter.class)
      .authorizeHttpRequests(config -> {
        List<String> permitList = Arrays.asList();
        permitList.stream().forEach(item -> {
          config.requestMatchers(item).permitAll();
        });
        Arrays.asList(SECURITY.PERMIT_URI.values()).stream().forEach(item -> {
          config.requestMatchers(item.getPattern()).permitAll();
        });
        config.anyRequest().authenticated();
      })
      .exceptionHandling(config -> 
        config.authenticationEntryPoint((request, response, authException) -> {
          HttpStatus status = HttpStatus.FORBIDDEN;
          response.sendError(status.value(), status.getReasonPhrase());
        })
      )
      .sessionManagement(config -> 
        config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );
    return http.build();
  }
  
}
