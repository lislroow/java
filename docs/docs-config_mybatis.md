### 99. snippets

#### 1) MapperScan

```java
@MapperScan(
    basePackages = { "spring.sample.app.dao." + Constant.DBMS.H2 },
    sqlSessionFactoryRef = Constant.DBMS.H2 + "SqlSessionFactoryBean",
    nameGenerator = BeanNameGenerator.class)
```

#### 2) ConfigurationProperties, EnableConfigurationProperties

```java
@EnableConfigurationProperties(MybatisMultipleProperties.class)
public class MybatisMapperConfig {
  
  @Bean
  @ConfigurationProperties(prefix = "spring.sample.mybatis")
  MybatisMultipleProperties mybatisMultipleProperties() {
    return new MybatisMultipleProperties();
  }
}
```