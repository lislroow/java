### 1. 기본 정보


#### 1) 접속 정보 확인

- vsql 실행

```
docker exec -it vertica /opt/vertica/bin/vsql
```

- DATABASE 확인

```
dbadmin@49e62c500221(*)=> SELECT CURRENT_DATABASE();
 current_database 
------------------
 VMart
(1 row)
```

- jdbc-url

```
jdbc:log4jdbc:vertica://rocky8-market:5433/VMart
```



