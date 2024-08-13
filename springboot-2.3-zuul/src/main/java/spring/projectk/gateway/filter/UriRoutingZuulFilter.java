package spring.projectk.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

//@Component
public class UriRoutingZuulFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "route"; // pre, post, route, error
  }
  
  @Override
  public int filterOrder() {
    return 1; // 필터의 실행 순서
  }
  
  @Override
  public boolean shouldFilter() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    String requestURI = request.getRequestURI();
    
    // /gov/로 시작하는 URI에 대해서만 필터 적용
    return requestURI.startsWith("/gov/");
  }
  
  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    
    String originalRequestUri = request.getRequestURI();
    if (originalRequestUri.startsWith("/gov/")) {
      String newUri = originalRequestUri.replace("/gov/", "/");
      ctx.put("requestURI", newUri);
    }
    
    return null;
  }
}
