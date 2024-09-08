FROM openjdk:24
WORKDIR /
EXPOSE 8080
COPY ./springboot3-profile-mng-0.0.1-SNAPSHOT.jar /profile-mng-app.jar
CMD ["java", "-jar", "profile-mng-app.jar"]
