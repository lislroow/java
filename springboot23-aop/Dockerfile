FROM 172.28.200.40:5000/amazoncorretto:8-alpine-jdk-with-curl
WORKDIR /service
COPY ./target/*.jar /service
COPY ./config /service/config
ENTRYPOINT ["/bin/sh", "-c", "java ${JAVA_OPTS} -jar *.jar"]