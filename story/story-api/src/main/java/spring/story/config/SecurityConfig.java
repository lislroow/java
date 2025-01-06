package spring.story.config;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import spring.custom.common.enumcode.PROTOTYPE_URI;
import spring.custom.common.security.TokenAuthenticationFilter;

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
      .addFilterBefore(new TokenAuthenticationFilter(modelMapper), UsernamePasswordAuthenticationFilter.class)
      .authorizeHttpRequests(authorizeRequests -> {
        List<String> permitList = Arrays.asList(
            "/actuator/**",
            "/error",
            "/v3/api-docs");
        permitList.stream().forEach(item -> {
          authorizeRequests.requestMatchers(item).permitAll();
        });
        Arrays.asList(PROTOTYPE_URI.values()).stream().forEach(item -> {
          authorizeRequests.requestMatchers(item.getPattern()).permitAll();
        });
        authorizeRequests.anyRequest().authenticated();
      })
      .exceptionHandling(exceptionHandlingCustomizer -> 
        exceptionHandlingCustomizer.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
      )
      .sessionManagement(sessionManagement -> 
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.NEVER)
      );
    return http.build();
  }
  
}
