package spring.custom.common.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({
    @Signature(type = Executor.class, method = "query", 
        args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }
    ),
})
public class PagingInterceptor implements Interceptor {
  
  Logger log = LoggerFactory.getLogger(PagingInterceptor.class);

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }
  
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    Object[] args = invocation.getArgs();
    MappedStatement ms = (MappedStatement) args[0];
    Object parameter = args[1];
    ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
    Executor executor = (Executor) invocation.getTarget();
    
    if (parameter instanceof java.util.Map) {
      Optional<Pageable> pageableEntry = ((java.util.Map<?, ?>) parameter).entrySet()
          .stream()
          .filter(entry -> entry.getValue() instanceof Pageable)
          .findFirst()
          .map(map -> (Pageable) map.getValue());
      if (pageableEntry.isPresent() && SqlCommandType.SELECT == ms.getSqlCommandType()) {
        Pageable pagable = pageableEntry.get();
        PagedList<Object> pagedList = new PagedList<>();
        executor.query(ms, parameter,
            new RowBounds(0, RowBounds.NO_ROW_LIMIT),
            new ResultHandler() {
              @Override
              public void handleResult(ResultContext resultContext) {
                pagedList.setTotal(resultContext.getResultCount());
              }
            });
        
        int page = pagable.getPage() == null ? 1 : pagable.getPage();
        int pageSize = pagable.getPageSize() == null ? 10 : pagable.getPageSize();
        int offset = (page - 1) * pageSize;
        int limit = pageSize;
        int start = offset + 1;
        int end = offset + limit;
        
        List<Object> result = executor.query(ms, parameter,
            new RowBounds(offset, limit),
            resultHandler);
        
        pagedList.setList(result);
        pagedList.setPage(page);
        pagedList.setPageSize(pageSize);
        pagedList.setStart(start);
        pagedList.setEnd(end);
        log.info("total: {}, range: {}~{}", pagedList.getTotal(), pagedList.getStart(), pagedList.getEnd());
        return pagedList;
      }
    }
    
    log.debug("sqlid: {}", ms.getId());
    
    
    return invocation.proceed();
  }
}
