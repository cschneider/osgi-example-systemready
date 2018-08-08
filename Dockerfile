FROM jeanblanchard/busybox-java:8
COPY starter/target/app.jar /app/app.jar
COPY starter/logback.xml /app
WORKDIR /app
ENTRYPOINT ["java","-jar","/app/app.jar"]
