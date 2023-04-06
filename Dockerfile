FROM openjdk:17
EXPOSE 8625
ADD target/distancefinder_cicd.jar  distancefinder_cicd.jar 
ENTRYPOINT ["java","-jar","/distancefinder_cicd.jar .jar"]