FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update && apt-get install -y openjdk-17-jdk

WORKDIR /app
COPY target/sdcs-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
