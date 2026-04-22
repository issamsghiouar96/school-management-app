FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY school-common/pom.xml school-common/pom.xml
COPY school-auth/pom.xml school-auth/pom.xml
COPY school-user/pom.xml school-user/pom.xml
COPY school-academic/pom.xml school-academic/pom.xml
COPY school-analytics/pom.xml school-analytics/pom.xml
COPY school-api/pom.xml school-api/pom.xml

COPY school-common/src school-common/src
COPY school-auth/src school-auth/src
COPY school-user/src school-user/src
COPY school-academic/src school-academic/src
COPY school-analytics/src school-analytics/src
COPY school-api/src school-api/src

RUN mvn -pl school-api -am clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/school-api/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]