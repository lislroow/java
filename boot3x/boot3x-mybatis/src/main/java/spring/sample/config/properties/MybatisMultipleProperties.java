package spring.sample.config.properties;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.ibatis.type.JdbcType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import lombok.Data;
import spring.sample.common.enumcode.DBMS_CODE;

@ConfigurationProperties(prefix = "spring.sample.mybatis", ignoreUnknownFields = true)
@Data
public class MybatisMultipleProperties {
  
  public static final String SQL_SESSION_FACTORY_BEAN = "SqlSessionFactoryBean";
  public static final String DAO = "Dao";
  
  private DBMS_CODE primary;
  private CustomConig h2;
  private CustomConig maria;
  private CustomConig oracle;
  
  @Data
  public static class CustomConig {
    private Boolean enabled;
    private String mapperLocations;
    private String typeAliasesPackage;
    private Boolean mapUnderscoreToCamelCase;
    private JdbcType jdbcTypeForNull;
    private Integer defaultStatementTimeout;
    private Boolean cacheEnabled;
    
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    
    public Resource[] resolveMapperLocations() {
      return Stream.of(Optional.ofNullable(this.mapperLocations).orElse(new String()))
          .flatMap(location -> Stream.of(getResources(location)))
          .toArray(Resource[]::new);
    }
    
    private Resource[] getResources(String location) {
      try {
        return resourceResolver.getResources(location);
      } catch (IOException e) {
        return new Resource[0];
      }
    }
  }
}
