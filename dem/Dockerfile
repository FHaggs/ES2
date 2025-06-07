FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw package -DskipTests

EXPOSE 8080 5005

# Best practice: pass debug args explicitly instead of JAVA_TOOL_OPTIONS
ENTRYPOINT ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005"]
