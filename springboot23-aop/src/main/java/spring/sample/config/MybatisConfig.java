package spring.sample.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"spring"}, annotationClass = org.apache.ibatis.annotations.Mapper.class)
public class MybatisConfig {

}
