### 1) eureka 설정

#### eureka.client

```property
# spring-cloud 2023.0.3 에 추가 됨
eureka.client.healthcheck.enabled=true

eureka.client.register-with-eureka=true

eureka.client.fetch-registry=true

# eureka 서버로부터 서비스 정보를 fetch 하는 주기
eureka.client.registry-fetch-interval-seconds=3

eureka.client.disable-delta=false

eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

#### eureka.instance

```property
# '임대 만료' eureka 서버에 서비스의 상태를 유지하기 위한 시간이며, 시간 내에 갱신을 하지 않으면 expire 상태가 됨
eureka.instance.lease-expiration-duration-in-seconds=3

# '임대 갱신' 클라이언트가 eureka 서버에 정상 상태임을 알리는 주기
eureka.instance.lease-renewal-interval-in-seconds=3
```

#### eureka.server

```property
eureka.server.enable-self-preservation=false

eureka.server.response-cache-update-interval-ms=3000

eureka.server.eviction-interval-timer-in-ms=1000
```