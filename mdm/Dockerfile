FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw package -DskipTests
EXPOSE 8080
ENTRYPOINT ["./mvnw", "spring-boot:run"] 