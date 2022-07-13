FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine
# Install bash to use 'wait-for-it'
RUN apk update && apk add bash && apk add --no-cache coreutils
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY wait-for-it.sh wait-for-it.sh
RUN ["chmod", "+x", "/app/wait-for-it.sh"]
ENTRYPOINT ["/bin/sh","-c","/app/wait-for-it.sh db:3306 -s -- java -jar /app/app.jar"]