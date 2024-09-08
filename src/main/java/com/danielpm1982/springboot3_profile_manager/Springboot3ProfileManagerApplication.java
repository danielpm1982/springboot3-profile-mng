package com.danielpm1982.springboot3_profile_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springboot3ProfileManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot3ProfileManagerApplication.class, args);
	}

}

/*For starting this web app, you can always choose between the "dev" or the "prod" properties profiles, by using:

1) FROM INSIDE THE SOURCE PROJECT ROOT (if cloned from github, for example):
./mvnw clean package
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"
or
./mvnw clean package
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=prod"
(Docker Compose will run docker-compose.yml to create and start up the MySQL container into a user-defined bridge network, and then maven will
run the executable .jar file after packaging the web app up, connecting it to the running MySQL DBMS. Somehow Maven manages to use env variables
from the MySQL container when running the web app from the .jar, which does not happen, for instance, when running the .jar from the java -jar
command below. No other env variables needed to be passed manually at the .mvnw run statement - only the profile type).

2) FROM INSIDE THE SOURCE PROJECT ROOT (if cloned from github, for example):
./mvnw clean package
java -jar -Dspring.profiles.active=dev ./target/springboot3-profile-mng-0.0.1-SNAPSHOT.jar
or
./mvnw clean package
docker compose -f docker-compose.yml up -d
java -jar -Dspring.profiles.active=prod -DMYSQL_PORT_3306_TCP_ADDR=172.18.0.2 ./target/springboot3-profile-mng-0.0.1-SNAPSHOT.jar

3) FROM ANY FOLDER OUTSIDE (OR INSIDE) THE PROJECT, WITH THE .JAR EXECUTABLE FILE AVAILABLE (if used .mvn package inside
the source project and then copied or distributed only the .jar file elsewhere, for example):
java -jar -Dspring.profiles.active=dev springboot3-profile-mng-0.0.1-SNAPSHOT.jar
or
docker compose -f docker-compose.yml up -d
java -jar -Dspring.profiles.active=prod -DMYSQL_PORT_3306_TCP_ADDR=172.18.0.2 springboot3-profile-mng-0.0.1-SNAPSHOT.jar

(for 2 and 3 options above, gotta have and first run the docker-compose.yml, for starting MySQL with the correct env variables; then set
MYSQL_PORT_3306_TCP_ADDR with the right IP of the profile-mng-mysql-container at the user-defined network, as there's no dns resolution this way.
This IP might change and may not be the one above. For checking the IP of the MySQL running container, one may use "docker inspect" with either
the container or network, or "docker exec -it profile-mng-mysql-container cat ./etc/hosts". It also must be available the docker-entrypoint-initdb.d
folder, with the sql init file for creating the schema and populate it with the initial sample data)

4) FROM ANY FOLDER OUTSIDE (OR INSIDE) THE PROJECT, WITH THE COMPLETE DOCKER-COMPOSE FILE AVAILABLE FOR STARTING BOTH THE WEB APP (not through
java -jar directly or ./mvnw as above) AS THE MYSQL INTO TWO CONTAINERS AT THE SAME USER-DEFINED NETWORK, WITH ALL NEEDED ENV VARIABLES SET FOR BOTH:
docker compose -f docker-compose-dev.yml up
or
docker compose -f docker-compose-prod.yml up
(both at dev and prod docker-compose files, the path to the .jar and Dockerfile should be adjusted at the build properties, so that local images can
be built and later created the respective containers)

5) FROM ANY FOLDER OUTSIDE (OR INSIDE) THE PROJECT, WITH THE COMPLETE DOCKER-COMPOSE FILE AVAILABLE FOR STARTING BOTH THE WEB APP (not through
java -jar directly or ./mvnw as above) AS THE MYSQL INTO TWO CONTAINERS AT THE SAME USER-DEFINED NETWORK, WITH ALL NEEDED ENV VARIABLES SET FOR BOTH:
docker compose -f docker-compose-dev-from-hub.yml up
or
docker compose -f docker-compose-prod-from-hub.yml up
(in both cases, there's no local build of the Docker images - the images are already built and available to be pulled from Docker Hub, therefore no
need of using or adjusting the path to the .jar or Dockerfile either. The .jar and Dockerfile are already in-built at the remote Docker Hub images used)
*/

//After putting the app to run, you can test all @Controller implemented endpoints (and database access by the web app) at:
//http://localhost:8080/swagger-ui

//The in-memory DBMS H2 is available at the default path (if spring.profiles.active = dev):
//H2 data only exists while the app is running. When the app terminates, the data is lost
//http://localhost:8080/h2-console

//The physical DBMS MySQL is available through MySQL CLI (from inside the MySQL container) or MySQL Workbench GUI (from outside
// the MySQL container) (if spring.profiles.active = prod):
//MySQL data is synchronized and persisted into an external volume at ${HOME}/temp/profile-mng-mysql-data (outside MySQL container)

//The root context path of the web app is at:
//http://localhost:8080
