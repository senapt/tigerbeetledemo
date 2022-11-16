FROM ubuntu:22.04
RUN apt update
RUN apt --yes install openjdk-17-jdk
RUN java -version
RUN mkdir /app

COPY target/tigerbeetledemo.jar /app
CMD ["java", "-jar", "/app/tigerbeetledemo.jar"]
