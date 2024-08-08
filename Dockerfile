FROM maven:3.8.7-openjdk-18 as build-stage
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17
ARG APP_VERSION=1.0.0
WORKDIR /app
COPY --from=build-stage /build/target/blog-backend-*.jar /app/

EXPOSE 8090

ENV DB_URL=jdbc:mariadb://localhost:3306/blog
ENV JAR_VERSION=${APP_VERSION}

CMD java -jar -Dspring.datasource.url=${DB_URL} post-service-${JAR_VERSION}.jar