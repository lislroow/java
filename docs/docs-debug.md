#### 1. remote debugging

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar boot3x-mybatis-0.1-SNAPSHOT.jar

# 원격지에서 접속
이클립스 > Run Configuration > Remote Java Application

# 시작 시 원격지의 접속을 대기함
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 -jar boot3x-mybatis-0.1-SNAPSHOT.jar

# 이전 방식
java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=5005,suspend=n -jar boot3x-mybatis-0.1-SNAPSHOT.jar
```



### 2. issue

#### 1) port 8000 Permission denied

- 기본 port 5005 를 사용할 것

```
$ java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar boot3x-mybatis-0.1-SNAPSHOT.jar
ERROR: transport error 202: bind failed: Permission denied
ERROR: JDWP Transport dt_socket failed to initialize, TRANSPORT_INIT(510)
JDWP exit error AGENT_ERROR_TRANSPORT_INIT(197): No transports initialized [s\src\jdk.jdwp.agent\share\native\libjdwp\debugInit.c:744]

$ java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8000 -jar boot3x-mybatis-0.1-SNAPSHOT.jar
ERROR: transport error 202: bind failed: Permission denied
ERROR: JDWP Transport dt_socket failed to initialize, TRANSPORT_INIT(510)
JDWP exit error AGENT_ERROR_TRANSPORT_INIT(197): No transports initialized [s\src\jdk.jdwp.agent\share\native\libjdwp\debugInit.c:744]
```