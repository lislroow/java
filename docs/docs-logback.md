### 1. 설정

#### 1) logback 자체에서 발생되는 로그

```xml
  <statusListener class="ch.qos.logback.core.status.NopStatusListener" />
  <statusListener class="ch.qos.logback.core.status.OnErrorConsoleStatusListener" />
  <statusListener class="ch.qos.logback.core.status.OnConsoleStatusListener" />
```

```java
public class NopStatusListener implements StatusListener {
  public void addStatusEvent(Status status) {
    // nothing to do
  }
}
public class OnErrorConsoleStatusListener extends OnPrintStreamStatusListenerBase {
  @Override
  protected PrintStream getPrintStream() {
    return System.err;
  }
}
public class OnConsoleStatusListener extends OnPrintStreamStatusListenerBase {
  @Override
  protected PrintStream getPrintStream() {
    return System.out;
  }
}
```

#### 2) async includeCallerData

기본값 false, 성능 최적화로 인해 호출 stack 정보를 전달하지 않음

```xml
  <appender name="ASYNC" class="ch.qos.logback.classic.AsyncAppender">
    <appender-ref ref="LOGFILE" />
    <queueSize>4096</queueSize>
    <discardingThreshold>20</discardingThreshold>
    <includeCallerData>true</includeCallerData>
    <neverBlock>false</neverBlock>
  </appender>
```
