FROM openjdk:11

WORKDIR /app

COPY target/desafiojavapitang-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

#RUN apt update -y
#
#RUN apt install -y maven
#
#RUN mvn clean install
#COPY /app/target/*.jar app.jar

#ENTRYPOINT ["java","-jar","/app.jar"]