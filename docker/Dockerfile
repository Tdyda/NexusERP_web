# syntax=docker/dockerfile:1.7

########### BUILD STAGE ###########
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

# 1) Maven Wrapper + jego pliki (dla cache i by host nie potrzebował Javy/Mavena)
COPY .mvn/ .mvn/
COPY mvnw .
RUN chmod +x mvnw

# 2) POM i pobranie zależności (cache m2 między buildami)
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B -DskipTests dependency:go-offline

# 3) Źródła i build
COPY src src
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B clean install -DskipTests

# 4) Ustal nazwę artefaktu (weź JAR z repackage, pomiń original-*.jar)
RUN cp $(ls -1 target/*.jar | grep -v '^target/original-') app.jar

########### RUNTIME STAGE ###########
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/app.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java","-jar","app.jar"]
