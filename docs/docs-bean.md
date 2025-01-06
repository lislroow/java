#### @ConditionalOnProperty

```
# java
@ConditionalOnProperty(name = "custom.datasource.maria.init", havingValue = "false", matchIfMissing = true)

# application.properties
spring.sample.mybatis.maria.enabled=false ## false 일 경우에만 동작하지 않음

# additional-spring-configuration-metadata.json
{
  "properties": [
    {
      "name": "custom.datasource.maria.init",
      "type": "java.lang.Boolean",
      "description": "[true, false] init-maria.sql",
      "defaultValue": false
    }
  }
}
```