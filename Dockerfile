FROM maven:3.9-amazoncorretto-17-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY update_prop.sh .

ENV CONFIG_LINK=/app/src/main/resources/application.yaml

RUN chmod +x update_prop.sh && \
    ./update_prop.sh && \
    mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-noble AS prod

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]