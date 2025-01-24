package spring.story.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import spring.story.common.constant.StoryApiConstant;
//import org.springdoc.core.customizers.OpenApiCustomiser;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import io.swagger.v3.core.converter.AnnotatedType;
//import io.swagger.v3.core.converter.ModelConverter;
//import io.swagger.v3.core.converter.ModelConverterContext;
//import io.swagger.v3.core.converter.ModelConverters;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.media.Schema;
//import io.swagger.v3.oas.models.media.StringSchema;
//import io.swagger.v3.oas.models.servers.Server;
//import spring.custom.common.enumcode.EnumCodeType;
//import spring.story.common.constant.StoryApiConstant;

@Configuration
public class SwaggerConfig {
  
  @Bean
  OpenAPI openAPI() {
    return new OpenAPI().addServersItem(new Server().url(StoryApiConstant.APP.BASE_URI));
  }
  
  /* dead code */
  //@Bean
  //OpenApiCustomiser customiseOpenApi() {
  //  ModelConverters.getInstance().addConverter(customEnumConverter());
  //  return openApi -> { };
  //}
  //
  //@Bean
  //ModelConverter customEnumConverter() {
  //  return new ModelConverter() {
  //    @Override
  //    public Schema resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
  //      Class<?> rawClass = type.getClass();
  //      if (EnumCodeType.class.isAssignableFrom(rawClass) && rawClass.isEnum()) {
  //        @SuppressWarnings("unchecked")
  //        Class<? extends EnumCodeType> enumType = (Class<? extends EnumCodeType>) rawClass;
  //        Schema<String> schema = new StringSchema();
  //        schema.setEnum(
  //            Arrays.stream(enumType.getEnumConstants()).map(EnumCodeType::getValue).collect(Collectors.toList()));
  //        return schema;
  //      }
  //      return chain.hasNext() ? chain.next().resolve(type, context, chain) : null;
  //    }
  //  };
  //}
}
