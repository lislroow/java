FROM docker.mgkim.net:5000/mgkim/amazoncorretto:8-alpine3.20
ARG APP_NAME
WORKDIR /app
COPY ../scouter /app/scouter
COPY ./$APP_NAME/config /app/config
COPY ./$APP_NAME/target/$APP_NAME.jar /app
ENTRYPOINT ["/bin/bash", "-c", "\
java $APP_JAVA_OPTS \
 -javaagent:/app/scouter/scouter.agent.jar \
 -Dscouter.config=/app/scouter/scouter.agent.conf \
 -Dobj_name=$APP_NAME \
 -Dspring.profiles.active=$SPRING_PROFILE \
 -jar $APP_NAME.jar"]
