package spring.auth.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import spring.auth.common.security.JwtAuthenticationFilter;
import spring.auth.common.security.LogoutHandlerImpl;
import spring.auth.common.security.LogoutSuccessHandlerImpl;
import spring.auth.common.security.SocialOAuth2LoginSuccessHandler;
import spring.auth.common.security.TokenService;
import spring.auth.common.security.UsernamePasswordAuthenticationFailureHandler;
import spring.auth.common.security.UsernamePasswordAuthenticationSuccessHandler;
import spring.auth.common.security.UsernamePasswordDetailsService;
import spring.custom.common.enumcode.PROTOTYPE_URI;

@Configuration
@EnableConfigurationProperties({ OAuth2ClientProperties.class })
@RequiredArgsConstructor
public class SecurityConfig {
  
  private final UsernamePasswordDetailsService usernamePasswordDetailsService;
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .httpBasic(AbstractHttpConfigurer::disable)
      .formLogin(formLogin -> 
        formLogin
          .loginProcessingUrl("/v1/login/process")
          .failureHandler(usernamePasswordAuthenticationFailureHandler())
          .successHandler(usernamePasswordAuthenticationSuccessHandler(tokenService))
      )
      .authenticationProvider(daoAuthenticationProvider())
      .authorizeHttpRequests(authorizeRequests -> {
        List<String> permitList = Arrays.asList(
            "/oauth2/authorization/**",
            "/v1/logout",
            "/v1/login/process",
            "/v1/token/**",
            "/v1/member/info",
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
      .addFilterBefore(new JwtAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
      .exceptionHandling(exceptionHandlingCustomizer -> 
        exceptionHandlingCustomizer.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
      )
      .oauth2Login(oauth2Login ->
        oauth2Login
          .permitAll()
          .authorizationEndpoint(authorizationEndpointCustomizer -> 
            authorizationEndpointCustomizer.authorizationRequestResolver(
              oauth2AuthorizationRequestResolver(clientRegistrationRepository())
            )
          )
          //.defaultSuccessUrl(String.format("http://%s", HOST), true)
          .successHandler(socialOAuth2LoginSuccessHandler(tokenService))
      )
      .sessionManagement(sessionManagement -> 
        // spring-security 에서 세션을 생성하지 않고 사용도 하지 않도록 함
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.NEVER)
      )
      .logout(logout ->
        logout.logoutUrl("/v1/logout")
          .logoutSuccessUrl("/")
          .addLogoutHandler(logoutHandlerImpl())
          .logoutSuccessHandler(logoutSuccessHandlerImpl())
          .deleteCookies("user")
      );
    return http.build();
  }
  
  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(usernamePasswordDetailsService);
    return provider;
  }
  
  @Bean
  AuthenticationFailureHandler usernamePasswordAuthenticationFailureHandler() {
    return new UsernamePasswordAuthenticationFailureHandler();
  }
  
  final TokenService tokenService;
  
  @Bean
  AuthenticationSuccessHandler usernamePasswordAuthenticationSuccessHandler(TokenService tokenService) {
    return new UsernamePasswordAuthenticationSuccessHandler(tokenService);
  }
  
  @Bean
  SocialOAuth2LoginSuccessHandler socialOAuth2LoginSuccessHandler(TokenService tokenService) {
    return new SocialOAuth2LoginSuccessHandler(tokenService);
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
  
  LogoutHandlerImpl logoutHandlerImpl() {
    return new LogoutHandlerImpl();
  }
  
  @Bean
  LogoutSuccessHandlerImpl logoutSuccessHandlerImpl() {
    return new LogoutSuccessHandlerImpl();
  }
}
