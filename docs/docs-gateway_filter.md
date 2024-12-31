

```
서비스가 실행되기 전에는 
Route{id='market-api-auth', uri=lb://market-api-auth, order=0, predicate=Paths: [/market-api-auth/**, /auth/**, /oauth2/authorization/**, /login/oauth2/**], match trailing slash: true, gatewayFilters=[[spring.sample.common.filter.AuthFilter$$Lambda$1411/0x000002a23d7ae720@1f3c543f, order = -2]], metadata={}}
이렇게 되어있었는데,
서비스를 실행하면 
Route{id='ReactiveCompositeDiscoveryClient_MARKET-API-AUTH', uri=lb://MARKET-API-AUTH, order=0, predicate=Paths: [/market-api-auth/**], match trailing slash: true, gatewayFilters=[[[RewritePath /market-api-auth/?(?<remaining>.*) = '/${remaining}'], order = 1]], metadata={jmx.port=57800}}
이렇게 gateway filter 가 변경되
어디서 변경하는 것일까?
```

```
Route 정보와 Gateway Filter가 서비스 실행 전후로 변경되는 이유는 Spring Cloud Gateway의 DiscoveryClient RouteDefinitionLocator에 의해 동적 라우팅이 적용되기 때문입니다. 이 변경은 DiscoveryClient와 관련된 라우트 동작 때문으로, Spring Cloud LoadBalancer 또는 Eureka 같은 서비스 디스커버리 메커니즘과 연관이 있습니다.
```

```
라우트 변경의 원인

DiscoveryClient RouteDefinitionLocator의 동작

Spring Cloud Gateway는 DiscoveryClientRouteDefinitionLocator를 통해 서비스 디스커버리를 기반으로 동적으로 라우트를 구성합니다.
이 컴포넌트는 DiscoveryClient에서 등록된 서비스를 탐색하고, 해당 서비스의 이름(예: MARKET-API-AUTH)에 기반한 기본 라우트를 추가합니다.
서비스가 실행되기 전에는 Gateway의 정적으로 정의된 라우트가 적용되지만, 실행 후에는 동적으로 생성된 라우트로 대체됩니다.

라우트 우선순위

DiscoveryClientRouteDefinitionLocator가 동적으로 생성한 라우트는 기본적으로 사용자 정의 라우트를 덮어쓸 수 있습니다.
이로 인해 정적으로 정의된 필터(AuthFilter)가 동적 라우트의 필터 체인에서 제거되거나 무시됩니다.

RewritePath 필터의 추가

DiscoveryClientRouteDefinitionLocator는 디스커버리된 서비스 이름을 기반으로 라우트를 생성하면서 기본적으로 RewritePath 필터를 추가합니다.
이는 서비스 이름과 라우트 경로 간의 매핑을 단순화하기 위한 자동화된 동작입니다.
```


```
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: false
```
