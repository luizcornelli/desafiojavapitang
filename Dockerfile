FROM adoptopenjdk/openjdk11
LABEL maintainer="test_user@gmail.com"
EXPOSE 8080
COPY target/JenkinsCICD-1.0.jar JenkinsCICD-1.0.jar
ENTRYPOINT ["java","-jar","/JenkinsCICD-1.0.jar"]