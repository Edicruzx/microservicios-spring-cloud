FROM maven:3.9.11-eclipse-temurin-17 AS build

ARG SERVICE
WORKDIR /workspace

# common-models is installed first so services that use it can resolve the
# local SNAPSHOT without requiring a private Maven repository.
COPY common-models/pom.xml common-models/pom.xml
COPY common-models/src common-models/src
RUN mvn -B -ntp -f common-models/pom.xml install -DskipTests

COPY ${SERVICE}/pom.xml ${SERVICE}/pom.xml
RUN mvn -B -ntp -f ${SERVICE}/pom.xml dependency:go-offline -DskipTests

COPY ${SERVICE}/src ${SERVICE}/src
RUN mvn -B -ntp -f ${SERVICE}/pom.xml package -DskipTests \
    && find ${SERVICE}/target -maxdepth 1 -type f -name '*.jar' ! -name '*.original' -exec cp '{}' /app.jar \;

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app.jar /app/app.jar
USER 10001
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
