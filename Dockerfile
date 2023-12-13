FROM openjdk:11

WORKDIR /app

COPY . .

RUN mvn clean install

ADD /app/target/desafiojavapitang-0.0.1-SNAPSHOT.jar desafiojavapitang.jar

ENTRYPOINT ["java","-jar","/desafiojavapitang.jar"]
