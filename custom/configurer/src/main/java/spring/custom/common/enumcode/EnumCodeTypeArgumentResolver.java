package spring.custom.common.enumcode;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/* dead code */ public class EnumCodeTypeArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return EnumCodeType.class.isAssignableFrom(parameter.getParameterType());
  }
  
  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) throws Exception {
    String source = webRequest.getParameter(parameter.getParameterName());
    if (source == null) {
      return null;
    }
    
    Class<?> parameterType = parameter.getParameterType();
    if (parameterType.isEnum() && EnumCodeType.class.isAssignableFrom(parameterType)) {
      @SuppressWarnings("unchecked")
      Class<? extends EnumCodeType> enumType = (Class<? extends EnumCodeType>) parameterType;
      return EnumCodeType.fromValue(enumType, source);
    }
    throw new IllegalArgumentException("Unsupported parameter type: " + parameterType);
  }
  
}
