FROM maven:3.9.9-eclipse-temurin-21

RUN mkdir -p /opt/app

RUN mkdir -p /uploads

COPY ./App_api-0.0.1-SNAPSHOT.jar /opt/app

CMD ["java", "-jar", "/opt/app/App_api-0.0.1-SNAPSHOT.jar"]