package spring.custom.common.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import lombok.extern.slf4j.Slf4j;
import spring.custom.common.constant.Constant;

@Intercepts({
  @Signature(type = Executor.class, method = "query", 
    args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }
  ),
})
@Slf4j
public class PagingInterceptor implements Interceptor {
  
  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }
  
  @Override
  @SuppressWarnings("rawtypes")
  public Object intercept(Invocation invocation) throws Throwable {
    Object[] args = invocation.getArgs();
    MappedStatement ms = (MappedStatement) args[0];
    Object parameter = args[1];
    ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
    Executor executor = (Executor) invocation.getTarget();
    
    log.info("[SQL] {}", ms.getId());
    
    switch (ms.getSqlCommandType()) {
    case SELECT:
      Optional<PageRequest> pageRequestObj = null;
      if (parameter instanceof java.util.Map) {
        pageRequestObj = ((java.util.Map<?, ?>) parameter).entrySet()
            .stream()
            .filter(entry -> entry.getValue() != null && entry.getValue().getClass() == PageRequest.class)
            .findFirst()
            .map(map -> (PageRequest) map.getValue());
      }
      
      // PageRequest 파라미터가 있을 경우, 페이징 조회 처리
      if (pageRequestObj != null && pageRequestObj.isPresent()) {
        List<?> list = ((java.util.Map<?, ?>) parameter).entrySet()
            .stream()
            .filter(entry -> entry.getValue() != null && 
              entry.getValue().getClass() != PageRequest.class && 
              // 파라미터 타입이 "spring." 이고, 단 1개만 존재할 경우 Map 이 아닌 단일 파라미터로 간주하도록 함 
              entry.getValue().getClass().getPackageName().startsWith(Constant.BASE_PACKAGE+".") &&
              !entry.getKey().toString().startsWith(ParamNameResolver.GENERIC_NAME_PREFIX))
            .map(map -> map.getValue())
            .toList();
        /* for debug */ log.info("filtered mapper parameter: {}", list);
        
        /* for debug */ if (!pageRequestObj.isPresent()) {
          if (log.isDebugEnabled()) {
            ((java.util.Map<?, ?>) parameter).entrySet().forEach(entry -> {
              log.info("entry: {}, expr1: {}", entry, entry.getValue() instanceof PageRequest); // java.lang.Object 가 반환되므로 true 가 됨
              log.info("entry: {}, expr2: {}", entry, entry.getValue() != null && entry.getValue().getClass() == PageRequest.class);
            });
          }
        }
        
        /* for debug */ if (log.isInfoEnabled()) log.info("pageRequest: {}", pageRequestObj);
        if (pageRequestObj.isPresent()) {
          PageRequest pageRequest = pageRequestObj.get();
          PageResponse<Object> pagedList = new PageResponse<>();
          executor.query(ms,
              (list.size() == 1) ? list.get(0) : parameter,
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
          
          List<Object> result = executor.query(ms,
              (list.size() == 1) ? list.get(0) : parameter,
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
      break;
    default:
      break;
    }
    return invocation.proceed();
  }
  
}
