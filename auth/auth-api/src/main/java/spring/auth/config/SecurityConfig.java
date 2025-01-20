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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.RequestDispatcher;
import lombok.RequiredArgsConstructor;
import spring.auth.common.login.MemberLoginService;
import spring.auth.common.login.MemberLoginSuccessHandler;
import spring.auth.common.login.MemberLogoutService;
import spring.auth.common.login.MemberOAuth2LoginSuccessHandler;
import spring.auth.common.login.TokenService;
import spring.auth.common.login.TokenVerifyFilter;
//import spring.auth.common.login.UserPasswordEncoder;
import spring.custom.common.enumcode.SECURITY;
import spring.custom.common.security.TokenAuthFilter;

@Configuration
@EnableConfigurationProperties({ OAuth2ClientProperties.class })
@RequiredArgsConstructor
public class SecurityConfig {
  
  final OAuth2ClientProperties properties;
  final MemberLoginService memberLoginService;
  final TokenService tokenService;
  final ModelMapper modelMapper;
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .formLogin(config -> 
        config
          .loginProcessingUrl("/v1/member/login")
          .failureHandler((request, response, exception) -> {
            request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, request.getRequestURI());
            request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exception);
            request.setAttribute(RequestDispatcher.ERROR_MESSAGE, exception.getMessage());
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            response.sendError(status.value(), status.getReasonPhrase());
          })
          .successHandler(new MemberLoginSuccessHandler(tokenService))
      )
      .addFilterBefore(new TokenAuthFilter(modelMapper), UsernamePasswordAuthenticationFilter.class)
      .addFilterBefore(new TokenVerifyFilter(tokenService), TokenAuthFilter.class)
      .exceptionHandling(config -> 
        config.authenticationEntryPoint((request, response, authException) -> {
          HttpStatus status = HttpStatus.FORBIDDEN;
          response.sendError(status.value(), status.getReasonPhrase());
        })
      )
      .authenticationProvider(daoAuthenticationProvider())
      .authorizeHttpRequests(config -> {
        List<String> permitList = Arrays.asList(
            "/v1/member/login/oauth2/authorization/**",
            "/v1/member/login/oauth2/code/**",
            "/v1/member/login",
            "/v1/member/logout",
            "/v1/manager/login",
            "/v1/manager/logout",
            "/v1/token/**");
        permitList.stream().forEach(item -> {
          config.requestMatchers(item).permitAll();
        });
        Arrays.asList(SECURITY.PERMIT_URI.values()).stream().forEach(item -> {
          config.requestMatchers(item.getPattern()).permitAll();
        });
        config.anyRequest().authenticated();
      })
      .sessionManagement(config -> 
        config.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // NEVER: 기존 세션이 있으면 사용하지만, 새로운 세션을 생성하지 않음
      )
      .oauth2Login(config ->
        config.permitAll()
          .authorizationEndpoint(authorizationEndpointCustomizer -> 
            authorizationEndpointCustomizer
              .authorizationRequestResolver(oauth2AuthorizationRequestResolver())
          )
          .redirectionEndpoint(redirectionEndpointCustomizer ->
            redirectionEndpointCustomizer.baseUri("/v1/member/login/oauth2/code/*") // OAuth2LoginAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI = "/login/oauth2/code/*";
          )
          .successHandler(new MemberOAuth2LoginSuccessHandler(tokenService))
      )
      .logout(logout ->
        logout.logoutUrl("/v1/member/logout")
          .logoutSuccessUrl("/")
          .addLogoutHandler(new MemberLogoutService())
          .logoutSuccessHandler((request, response, authentication) -> {
            String redirectUri = "/";
            response.setStatus(HttpStatus.FOUND.value());
            response.setHeader(HttpHeaders.LOCATION, redirectUri);
          })
      );
    return http.build();
  }
  
  @Bean
  BCryptPasswordEncoder bcryptPasswordEncoder() {
    BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
    return bcryptPasswordEncoder;
  }
  
  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(memberLoginService);
    //provider.setPasswordEncoder(new UserPasswordEncoder()); // no check
    provider.setPasswordEncoder(new BCryptPasswordEncoder()); // {bcrypt}, PasswordEncoderFactories, DelegatingPasswordEncoder
    return provider;
  }
  
  @Bean
  OAuth2AuthorizationRequestResolver oauth2AuthorizationRequestResolver() {
    List<ClientRegistration> registrations = new ArrayList<>(
        new OAuth2ClientPropertiesMapper(properties).asClientRegistrations().values());
    ClientRegistrationRepository clientRegistrationRepository = 
        new InMemoryClientRegistrationRepository(registrations);
    
    String authorizationUri = "/v1/member/login/oauth2/authorization"; //OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI = "/oauth2/authorization";
    return new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, authorizationUri);
  }
  
}
