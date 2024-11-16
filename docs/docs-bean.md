#### @ConditionalOnProperty

```
# java
@ConditionalOnProperty(name = "spring.sample.datasource.maria.init", havingValue = "true", matchIfMissing = false)

# application.properties
spring.sample.mybatis.maria.enabled=true

# additional-spring-configuration-metadata.json
{
  "properties": [
    {
      "name": "spring.sample.datasource.maria.init",
      "type": "java.lang.Boolean",
      "description": "[true, false] init-maria.sql",
      "defaultValue": false
    }
  }
}
```