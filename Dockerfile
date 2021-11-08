FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD /target/Timesheet-spring-boot-core-data-jpa-mvc-REST-1-0.0.3-SNAPSHOT.war Timesheet-spring-boot-core-data-jpa-mvc-REST-1-0.0.3-SNAPSHOT.war
ENTRYPOINT ["java","-war","Timesheet-spring-boot-core-data-jpa-mvc-REST-1-0.0.3-SNAPSHOT.war"]