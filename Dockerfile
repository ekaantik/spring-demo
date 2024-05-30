
#Base Image
FROM openjdk:17-jdk-slim-buster

#Create and Set base dir for project
ENV PROJECT_HOME /usr/share/demo
RUN mkdir $PROJECT_HOME

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN ./gradlew build -x test

#Copy App Jar file
COPY build/libs/demo-0.0.1-SNAPSHOT.jar $PROJECT_HOME/


#Copy resources files
ENV RESOURCES src/main/resources

ENTRYPOINT ["java", "-jar","-XX:MaxRAMPercentage=70.0","/usr/share/demo/demo-0.0.1-SNAPSHOT.jar"]
