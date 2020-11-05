FROM openjdk:8-alpine
MAINTAINER Nicow2 (nicobod@gmail.com)

#after mvn package
#ADD microservice-web/target/microservice-web-1.0.0.jar microservice-web-1.0.0.jar

#without mvn package
ADD /executable/microservice-web-1.0.0.jar microservice-web-1.0.0.jar

EXPOSE 9002
ENTRYPOINT ["java","-jar","microservice-web-1.0.0.jar"]
