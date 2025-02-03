package spring.custom.common.mybatis;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;
import spring.custom.common.syscode.AUDIT;
import spring.custom.common.vo.AuditVo;

@Intercepts({
  @Signature(type = Executor.class, method = "update", 
      args = { MappedStatement.class, Object.class }
  ),
})
@Slf4j
public class AuditInterceptor implements Interceptor {
  
  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }
  
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Object intercept(Invocation invocation) throws Throwable {
    Object[] args = invocation.getArgs();
    MappedStatement ms = (MappedStatement) args[0];
    Object parameter = args[1];
    log.info("[SQL] {}", ms.getId());
    switch (ms.getSqlCommandType()) {
      case INSERT, UPDATE, DELETE -> {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
          String id = authentication.getName(); // spring.custom.common.vo.User extends java.security.Principal
          if (parameter instanceof java.util.Map) {
            java.util.Map parameterMap = ((java.util.Map) parameter);
            parameterMap.put(AUDIT.CREATE_ID.getField(), id);
            parameterMap.put(AUDIT.MODIFY_ID.getField(), id);
          } else if (parameter instanceof AuditVo) {
            AuditVo parameterObj = (AuditVo)parameter;
            parameterObj.setCreateId(id);
            parameterObj.setModifyId(id);
          }
        }
        break;
      }
      default -> {}
    };
    return invocation.proceed();
  }
  
}
