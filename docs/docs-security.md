### 99. snippets

#### 1) UsernamePasswordAuthenticationFilter 필터 재정의

```java
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
        authorizeHttpRequests.requestMatchers("/mybatis/**").permitAll();
        authorizeHttpRequests.requestMatchers("/**").authenticated();
      })
      .addFilterAt(new AuthenticationFilter(),
          UsernamePasswordAuthenticationFilter.class);
    SecurityFilterChain securityFilterChain = http.build();
    return securityFilterChain;
  }
}
```