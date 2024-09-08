# springboot3-profile-mng
This is a very simple Profile Management REST Application developed with Spring Boot 3 and Spring Framework 6 

© 2024 Daniel Pinheiro Maia All Rights Reserved<br>
(see Copyright© License at the end of this text).

[**Description of this repository**]<br>
This is a very simple Profile Management REST Web Service Application (back-end, Producer) developed with Java 19, Spring Boot 3, Spring Framework 6, Spring Web MVC, Spring REST, Spring Data JDBC, Spring Actuator, Hibernate Java Bean Validator, Jonathan Halterman Model Mapper, Faster/XML Jackson, Jakarta JAX-B, Lombok, Swagger, H2 in-memory DBMS, MySQL computer's storage DBMS, MySQL Workbench, Docker and Docker Compose, Linux Ubuntu. The intent of this project is simply to demonstrate basic Spring Boot framework development features and related tools.<br>
The business logic and domain model class in this project simply represents some sample Person profile data, as id, firstName, lastName, userName, password and email. It's not an objective here to represent real-world domain models, but to use the simplest possible domain to exemplify and explain how to leverage Spring Boot 3 syntax features when developing a working REST Web Service application. Spring features and modules not used up until the latest version of this app, may be included in future versions. Client apps, for accessing and consuming this Producer REST Service API, in a language-agnostic fashion, will eventually be developed later, as well, as separate projects. For now, you can test this app by using external tools as Postman, MySQL Workbench or the browser itself, through the Swagger REST client interface or the H2 console (see sample prints below).<br>
This project has been extensively documented at the source code itself, for the purpose of explaining each development feature of Spring Framework 6 and Spring Boot 3 in practice, right where it is being used at, along with some more general observations at each class file. You can read, for instance, how to build and run the source code of this app, through 5 different ways, at the [main class](https://github.com/danielpm1982/springboot3-profile-mng/blob/master/src/main/java/com/danielpm1982/springboot3_profile_manager/Springboot3ProfileManagerApplication.java) and at the docker-compose files (available at the [root](https://github.com/danielpm1982/springboot3-profile-mng) of this project).
As minimum dependencies, for building, running and trying this project, you should have Java 19 JDK and Docker installed, and preferably have Linux Ubuntu as your Operating System. You should also use some Integrated Development Environment, as Jetbrains IntelliJ IDEA Community, for analysing the source code and use Maven to build and run the source code. But, as explained at the main class comments, you can simply run this project's dockerized build directly from Docker Hub by using Docker Compose files and the sql init folder (docker-entrypoint-initdb.d) - both available at this projects' root folder.

[**Released Versions**]
- Profile Management v.1.0.0 [Latest]<br>
  [Source Code Remote Repository - GitHub](https://github.com/danielpm1982/springboot3-profile-mng)<br>
  [Source Code Packages - GitHub](https://github.com/danielpm1982/springboot3-profile-mng/releases/tag/v.1.0.0)<br>
  [Dockerized Remote Images (with Dockerfile and compiled .jars) - Docker Hub](https://hub.docker.com/repository/docker/danielpm1982/profile-mng)<br>

[**Support**]<br>
If you have any suggestion or correction about the content of this repository, please, feel free to open an issue at the project [issues' section](https://github.com/danielpm1982/springboot3-profile-mng/issues).

[**Printscreen App Samples**]<br>

![image](https://github.com/user-attachments/assets/3b551013-6301-4981-b1b2-0eb8d508fa1f)

![image](https://github.com/user-attachments/assets/e1eeabc9-5bc0-40be-bc41-f7354d40ba47)

![image](https://github.com/user-attachments/assets/beb1d5e9-6c20-4e66-91e4-c6af8cae701d)

![image](https://github.com/user-attachments/assets/e7e24623-cb87-4a84-a1f6-71c9e3bd4f62)

![image](https://github.com/user-attachments/assets/e7675613-d5df-45bf-be00-09a0b990c59f)

![image](https://github.com/user-attachments/assets/67aafb63-74f1-4a0f-8260-badab537e604)

![image](https://github.com/user-attachments/assets/32bcac82-d05b-4db5-af02-889e6a831c4b)

![image](https://github.com/user-attachments/assets/5b814702-0696-482c-9c18-db2ecc3723ec)

![image](https://github.com/user-attachments/assets/8509a1f1-bc6d-4ab2-b265-932bddb31b3c)

[**Copyright© License**]<br>
© 2024 Daniel Pinheiro Maia All Rights Reserved<br>
This GitHub repository - and all code (software) available inside - is exclusively for academic and individual learning purposes, and is **NOT AVAILABLE FOR COMMERCIAL USE**, nor has warranty of any type. You're authorized to fork, clone, run, test, modify, branch and merge it, at your own risk and using your own GitHub account, for individual learning purposes only, but you're **NOT ALLOWED to distribute, sublicense and/or sell copies of the whole or of parts of it** without explicit and written consent from its owner / author. You can fork this repository to your individual account at GitHub, clone it to your personal notebook or PC, analyse, run and test its code, modify and extend it locally or remotely (exclusively at your own GitHub account and as a forked repository), as well as send issues or pull-requests to this parent (original) repository for eventual approval. GitHub is in charge of explicitly showing whom this respository has been forked from. **If you wish to use any of this repository content in any way other than what is expressed above, or publish it anyway or anywhere other than as a forked repository at your own GitHub account, please contact this repository owner / author** using GitHub or the contact info below. For the meaning of the technical terms used at this license, please refer to GitHub documentation, at https://help.github.com/en/github .

[**Owner and Author of this GitHub Repository**]<br>
Daniel Pinheiro Maia<br>
[danielpm1982.com](https://www.danielpm1982.com)<br>
danielpm1982@gmail.com<br>
[linkedin.com/in/danielpm1982](https://www.linkedin.com/in/danielpm1982)<br>
Brazil<br>
.
