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

import spring.custom.common.constant.Constant;

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
      Optional<PageRequest> pageParam = ((java.util.Map<?, ?>) parameter).entrySet()
          .stream()
          .filter(entry -> entry.getValue() != null && entry.getValue().getClass() == PageRequest.class)
          .findFirst()
          .map(map -> (PageRequest) map.getValue());
      /* for debug */ if (!pageParam.isPresent()) {
        if (log.isDebugEnabled()) {
          ((java.util.Map<?, ?>) parameter).entrySet().forEach(entry -> {
            log.info("entry: {}, expr1: {}", entry, entry.getValue() instanceof PageRequest); // java.lang.Object 가 반환되므로 true 가 됨
            log.info("entry: {}, expr2: {}", entry, entry.getValue() != null && entry.getValue().getClass() == PageRequest.class);
          });
        }
      }
      
      /* for debug */ if (log.isInfoEnabled()) log.info("pageRequest: {}", pageParam);
      if (pageParam.isPresent() && SqlCommandType.SELECT == ms.getSqlCommandType()) {
        PageRequest pageRequest = pageParam.get();
        PageResponse<Object> pagedList = new PageResponse<>();
        executor.query(ms, parameter,
            new RowBounds(0, RowBounds.NO_ROW_LIMIT),
            new ResultHandler() {
              @Override
              public void handleResult(ResultContext resultContext) {
                pagedList.setTotal(resultContext.getResultCount());
              }
            });
        
        int page = pageRequest.getPage() == null ? Constant.PAGE.PAGE_NUMBER : pageRequest.getPage();
        int size = pageRequest.getSize() == null ? Constant.PAGE.PAGE_SIZE : pageRequest.getSize();
        int offset = (page -1) * size; // 페이징 시작은 '1' 부터
        int limit = size;
        int start = offset + 1;
        int end = offset + limit > pagedList.getTotal() ? pagedList.getTotal() : offset + limit;
        
        List<Object> result = executor.query(ms, parameter,
            new RowBounds(offset, limit),
            resultHandler);
        
        pagedList.setPageData(result);
        pagedList.setPage(page);
        pagedList.setSize(size);
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
