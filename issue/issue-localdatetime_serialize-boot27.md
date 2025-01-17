
### 현상

```java
LocalDateTime createTime;
```

- @EnableWebMvc 선언이 있을 경우 LocalDateTime 타입이 문자열로 serialize 되지 않음 

```json
"createTime": [
  2024,
  11,
  12,
  10,
  36,
  15,
  925000000
]

- @EnableWebMvc 선언이 없을 경우 LocalDateTime 타입이 문자열로 serialize 됨

```json
"createTime": "2024-11-12T10:36:13.933"
```

### 접근

#### @EnableWebMvc 선언 + MessageConverter 정의

- @EnableWebMvc 은 MessageConverter 를 재정의하므로 문자열로 직렬화 되지 않음
- MessageConverter 에서 LocalDateTime 을 ISO-8601 형식의 문자열로 직렬화하도록 설정

```java
// 방법1
@Configuration
public class WebConfig {
  @Bean
  MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return new MappingJackson2HttpMessageConverter(objectMapper);
  }
  
  @Bean
  HttpMessageConverters httpMessageConverters() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    HttpMessageConverter<?> converter = new MappingJackson2HttpMessageConverter(objectMapper);
    return new HttpMessageConverters(false, Collections.singletonList(converter));
  }
}
```

```property
# 방법2
spring.jackson.serialization.write-dates-as-timestamps=true
spring.jackson.date-format=yyyy-MM-dd'T'HH:mm:ss.SSS
```

### 결론

- @EnableWebMvc 을 선언하지 않고, WebMvc 설정이 필요할 경우 직접 구현함

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer { ... }
```
