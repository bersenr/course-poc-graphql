FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y curl
WORKDIR /app
COPY target/course.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]