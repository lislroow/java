docker-compose 로 서비스를 실행할 때 yml 파일과 같은 경로에 `.env` 파일이 있다면

```
docker-compose -f elastic.yml up -d 
```

yml 의 config 구성 시 `.env` 파일에 정의된 key=value 를 사용할 수 있다.

`.env` 파일의 이름을 변경하고 싶을 경우 yml 에 `env_file: elastic.env` 를 설정할 경우 config 구성에는 사용할 수 없다.

`env_file` 속성은 config 에 대한 env 가 아닌 execution 에 대한 env 이다.

`.env` 파일명을 변경하려면 `--env-file elastic.env` 를 docker-compose 실행 인자에 추가해야 한다.

```
docker-compose -f elastic.yml --env-file elastic.env up -d 
```
