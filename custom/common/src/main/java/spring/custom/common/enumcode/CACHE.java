package spring.custom.common.enumcode;

import java.time.Duration;

public enum CACHE {
  
  COMMON_CODE("cache:common-code", Duration.ofSeconds(60))
  ;
  
  private String cacheName;
  private Duration ttl;
  
  private CACHE(String cacheName, Duration ttl) {
    this.cacheName = cacheName;
    this.ttl = ttl;
  }
  
  public String cacheName() {
    return this.cacheName;
  }
  
  public Duration ttl() {
    return this.ttl;
  }
  
}
