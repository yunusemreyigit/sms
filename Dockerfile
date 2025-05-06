FROM openjdk:21
COPY target/*.jar ./sms.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "sms.jar"]