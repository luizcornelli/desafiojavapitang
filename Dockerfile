FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
RUN mvn clean install
COPY . .
ADD ./target/desafiojavapitang-0.0.1-SNAPSHOT.jar desafiojavapitang.jar
ENTRYPOINT ["java","-jar","/desafiojavapitang.jar"]
