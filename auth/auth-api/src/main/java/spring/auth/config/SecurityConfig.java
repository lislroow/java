package spring.auth.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.RequestDispatcher;
import lombok.RequiredArgsConstructor;
import spring.auth.common.security.LoginService;
import spring.auth.common.security.LoginSuccessHandler;
import spring.auth.common.security.LogoutService;
import spring.auth.common.security.OAuth2LoginSuccessHandler;
import spring.auth.common.security.TokenService;
import spring.custom.common.enumcode.SECURITY;
//import spring.custom.common.security.TokenAuthenticationFilter;

@Configuration
@EnableConfigurationProperties({ OAuth2ClientProperties.class })
@RequiredArgsConstructor
public class SecurityConfig {
  
  final LoginService usernamePasswordDetailsService;
  final ModelMapper modelMapper;
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .formLogin(config -> 
        config
          .loginProcessingUrl("/v1/login")
          .failureHandler((request, response, exception) -> {
            request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, request.getRequestURI());
            request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exception);
            request.setAttribute(RequestDispatcher.ERROR_MESSAGE, exception.getMessage());
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            response.sendError(status.value(), status.getReasonPhrase());
          })
          .successHandler(loginSuccessHandler(tokenService))
      )
      //.addFilterBefore(new TokenAuthenticationFilter(modelMapper), UsernamePasswordAuthenticationFilter.class)
      .exceptionHandling(config -> 
        config.authenticationEntryPoint((request, response, authException) -> {
          HttpStatus status = HttpStatus.FORBIDDEN;
          response.sendError(status.value(), status.getReasonPhrase());
        })
      )
      .authenticationProvider(daoAuthenticationProvider())
      .authorizeHttpRequests(config -> {
        List<String> permitList = Arrays.asList(
            "/oauth2/authorization/**",
            "/v1/login",
            "/v1/logout",
            "/v1/token/**");
        permitList.stream().forEach(item -> {
          config.requestMatchers(item).permitAll();
        });
        Arrays.asList(SECURITY.PERMIT_URI.values()).stream().forEach(item -> {
          config.requestMatchers(item.getPattern()).permitAll();
        });
        config.anyRequest().authenticated();
      })
      .oauth2Login(config ->
        config.permitAll()
          .authorizationEndpoint(authorizationEndpointCustomizer -> 
            authorizationEndpointCustomizer.authorizationRequestResolver(
              oauth2AuthorizationRequestResolver(clientRegistrationRepository())
            )
          )
          .successHandler(oAuth2LoginSuccessHandler(tokenService))
      )
      .sessionManagement(config -> 
        config.sessionCreationPolicy(SessionCreationPolicy.NEVER)
      )
      .logout(logout ->
        logout.logoutUrl("/v1/logout")
          .logoutSuccessUrl("/")
          .addLogoutHandler(new LogoutService())
          .logoutSuccessHandler((request, response, authentication) -> {
            String redirectUri = "/";
            response.setStatus(HttpStatus.FOUND.value());
            response.setHeader(HttpHeaders.LOCATION, redirectUri);
          })
      );
    return http.build();
  }
  
  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(usernamePasswordDetailsService);
    return provider;
  }
  
  final TokenService tokenService;
  
  @Bean
  AuthenticationSuccessHandler loginSuccessHandler(TokenService tokenService) {
    return new LoginSuccessHandler(tokenService);
  }
  
  @Bean
  OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler(TokenService tokenService) {
    return new OAuth2LoginSuccessHandler(tokenService);
  }
  
  final OAuth2ClientProperties properties;
  
  @Bean
  ClientRegistrationRepository clientRegistrationRepository() {
    List<ClientRegistration> registrations = new ArrayList<>(
        new OAuth2ClientPropertiesMapper(properties).asClientRegistrations().values());
    return new InMemoryClientRegistrationRepository(registrations);
  }
  
  @Bean
  OAuth2AuthorizationRequestResolver oauth2AuthorizationRequestResolver(
      ClientRegistrationRepository clientRegistrationRepository) {
    String authorizationRequestBaseUri = 
        OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;
    return new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, authorizationRequestBaseUri);
  }
  
}
