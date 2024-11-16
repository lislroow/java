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