FROM openjdk:17
EXPOSE 8084
ADD ./build/libs/*.jar wt-visitor-management-service-0.0.1-SNAPSHOT.jar
CMD ["java", "-Xmx200m", "-jar", "/wt-visitor-management-service-0.0.1-SNAPSHOT.jar"]