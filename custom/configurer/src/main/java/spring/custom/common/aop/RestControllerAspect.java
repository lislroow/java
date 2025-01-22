package spring.custom.common.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.common.annotation.Login;
import spring.custom.common.annotation.UserInfo;
import spring.custom.common.constant.Constant;
import spring.custom.common.util.XffClientIpExtractor;

@Aspect
@Order(1)
@Component
@Slf4j
@RequiredArgsConstructor
public class RestControllerAspect {
  
  @SuppressWarnings("unused")
  @Around("execution(* "+Constant.BASE_PACKAGE+"..controller.*Controller.*(..))")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    jakarta.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    jakarta.servlet.http.HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    
    log.info("{}", request.getRequestURI());
    
    String reqUri = request.getRequestURI();
    String method = request.getMethod();
    String contentType = request.getHeader("Content-Type");
    String ipAddr = XffClientIpExtractor.getClientIp(request);
    
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method refMethod = methodSignature.getMethod();
    
    /* for debug */ if (log.isDebugEnabled()) {
      String text = String.format(
              "request info\n%s: %s.%s" +
              //"\n%s: %s" +
              //"\n%s: %s" +
              //"\n%s: %s" +
              "\n%s: %s"
              , "[COM] Class       ", joinPoint.getTarget().getClass().getName(), refMethod.getName()
              , "[COM] RequestURI  ", reqUri
              //, "[COM] Method      ", method
              //, "[COM] ContentType ", contentType
              //, "[COM] ipAddr      ", ipAddr
              );
      log.info(text);
    }
    
    Annotation[][] parameterAnnotations = refMethod.getParameterAnnotations();
    Login login = refMethod.getAnnotation(Login.class);
    Object[] args = joinPoint.getArgs();
    
    for (int i=0; i<args.length; i++) {
      Object arg = args[i];
      Annotation[] annotations = parameterAnnotations[i];
      boolean hasUserInfoAnnotation = Arrays.stream(annotations)
          .anyMatch(annotation -> annotation.annotationType().equals(UserInfo.class));
    }
    
    Object result = null;
    Throwable throwable = null;
    try {
      result = joinPoint.proceed(args);
    } catch (Throwable e) {
      throwable = e;
      log.error("[error] {}, {}", joinPoint.getTarget().getClass().getName(), e.getMessage());
      throw e;
    }
    /* for debug */ if (log.isInfoEnabled()) {
      log.info("[normal] {}", joinPoint.getTarget().getClass().getName());
    }
    return result;
  }
  
}
