FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /build

COPY pom.xml .
COPY . .

RUN mvn package -DskipTests

FROM openjdk:17

WORKDIR /application

COPY --from=builder /build/university-ui/target/university-ui-0.0.1-SNAPSHOT.jar university-ui.jar

ENTRYPOINT ["java",  "-jar", "university-ui.jar"]