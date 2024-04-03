FROM asia-southeast1-docker.pkg.dev/pccw-platform-mgmt/console-core-base/maven:3.8.1-jdk-11 AS MAVEN_TOOL_CHAIN

COPY pom.xml /tmp/
COPY settings.xml /tmp/

# This is to download all the deps and cache them, so we can build
# changes to the code without downloading all the deps
RUN --mount=type=cache,id=maven_deps,target=/usr/share/maven/ref/repository \
    --mount=type=secret,id=github_password --mount=type=secret,id=github_username \
    mvn -B dependency:go-offline -DskipTests \
    -Dmaven.repo.local=/usr/share/maven/ref/repository \
    -Dgithub.password=$(cat /run/secrets/github_password) -Dgithub.username=$(cat /run/secrets/github_username) \
    -f /tmp/pom.xml -s /tmp/settings.xml

COPY src /tmp/src/

WORKDIR /tmp/
RUN --mount=type=cache,id=maven_deps,target=/usr/share/maven/ref/repository \
    --mount=type=secret,id=github_password --mount=type=secret,id=github_username git init && \
    mvn -B -s /tmp/settings.xml \
    -Dmaven.repo.local=/usr/share/maven/ref/repository \
    -Dgithub.password=$(cat /run/secrets/github_password) -Dgithub.username=$(cat /run/secrets/github_username) \
    package -DskipTests

FROM asia-southeast1-docker.pkg.dev/pccw-platform-mgmt/console-core-base/adoptopenjdk:11-jre-openj9

EXPOSE 8080

COPY --from=MAVEN_TOOL_CHAIN /tmp/target/*.jar /opt/app/app.jar

USER 11020

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app/app.jar"]
