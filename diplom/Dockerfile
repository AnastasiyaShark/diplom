FROM openjdk:14-ea-12-jdk-alpine
EXPOSE 8080
ADD target/diplom-0.0.1-SNAPSHOT.jar /db.jar
ENTRYPOINT ["java","-jar","/db.jar"]