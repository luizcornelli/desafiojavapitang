FROM openjdk:11

VOLUME /tmp

EXPOSE 8080

COPY /src /app/src

COPY /pom.xml /app

RUN mvn clean install

COPY /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]