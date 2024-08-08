FROM openjdk:17-jdk

WORKDIR /app

COPY target/blog-backend-*.jar /app/blog-backend.jar

EXPOSE 8090

CMD ["java", "-jar", "blog-backend.jar"]