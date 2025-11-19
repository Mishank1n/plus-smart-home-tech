FROM amazoncorretto:21
VOLUME /tmp
COPY telemetry/collector/target/collector-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]