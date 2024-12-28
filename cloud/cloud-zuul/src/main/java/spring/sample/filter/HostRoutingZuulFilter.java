package spring.sample.filter;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.http.HttpServletRequestWrapper;
//import com.netflix.zuul.http.HttpServletResponseWrapper;

//@Component
public class HostRoutingZuulFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "pre"; // pre, post, route, error
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
    return requestURI.startsWith("/ws");
  }
  
  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    String requestURI = ctx.getRequest().getRequestURI();
//    String forwardHost = ctx.getRouteHost().toString();
    String forwardedHost = ctx.getZuulRequestHeaders().get("x-forwarded-host");
    String forwardedProto = ctx.getZuulRequestHeaders().get("x-forwarded-proto");
//    System.out.println("forwardProto: " + forwardedProto);
//    System.out.println("forwardHost: " + forwardedHost);
//    System.out.println("requestURI: " + requestURI);
    HttpHost proxy = new HttpHost("forward.mgkim.net", 8888, "http");
    try (CloseableHttpClient httpClient = HttpClients.custom()
        .setProxy(proxy)
        .build()) {
      
      //HttpPost httpPost = new HttpPost(forwardedProto + "://" + forwardedHost + requestURI);
      HttpPost httpPost = new HttpPost("http://localhost:8083/ws");
      StringEntity entity = new StringEntity(ctx.getRequest()
          .getReader()
          .lines()
          .reduce("", (accumulator, actual) -> accumulator + actual), StandardCharsets.UTF_8);
      httpPost.setEntity(entity);
      httpPost.setHeader("Content-Type", ctx.getRequest().getContentType());
      CloseableHttpResponse response = httpClient.execute(httpPost);
      int statusCode = response.getStatusLine().getStatusCode();
      String responseBody = EntityUtils.toString(response.getEntity());
      ctx.setResponseStatusCode(statusCode);
      ctx.setResponseBody(responseBody);
      
      
//      try {
//        //String newUrl = forwardedProto + "://" + forwardedHost + requestURI;
//        String newUrl = "http://forward.mgkim.net:8888/ws";
//        System.out.println("newUrl: " + newUrl);
//        ctx.setRouteHost(new URL(newUrl));
//      } catch (MalformedURLException e) {
//          e.printStackTrace();
//      }
      
      /*
      HttpServletRequestWrapper request = new HttpServletRequestWrapper(context.getRequest());
      HttpServletResponseWrapper response = new HttpServletResponseWrapper(context.getResponse());
      InputStream responseStream = com.netflix.zuul.util.HttpUtils.execute(httpClient, proxy, request, response);
      context.setResponseDataStream(responseStream);
      */
    } catch (Exception e) {
      //throw new RuntimeException(e);
      e.printStackTrace();
    }
    
    //return super.run();
    return null;
  }
}
