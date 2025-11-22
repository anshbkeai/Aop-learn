FROM openjdk:21-jdk-slim

# Step 2: Add a jar file from your target folder (after mvn package)
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar


# Step 4: Run the app
ENTRYPOINT ["java","-jar","/app.jar"]