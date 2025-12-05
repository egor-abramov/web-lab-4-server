FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn clean package -Dmaven.test.skip=true


FROM gcr.io/distroless/java17-debian11
COPY --from=build /app/target/*.jar app.jar
WORKDIR /
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]