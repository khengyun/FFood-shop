#Use the Maven image as the build stage
FROM maven:3.9.0 AS build

RUN mvn --version
RUN java -version

# Set the working directory in the build stage
WORKDIR /app

# Copy the project's pom.xml to the image
COPY . .

# Copy the entire project source code to the image
# COPY src/ /app/src/

# Download the project dependencies and build the project
RUN mvn clean install

# Switch to the Tomcat image as the final stage
FROM tomcat:10.0.23-jdk8-temurin-jammy

# Set the working directory in the final stage
WORKDIR /usr/local/tomcat/webapps

# Remove the default Tomcat applications
# RUN rm -rf ROOT && rm -rf examples && rm -rf docs && rm -rf manager && rm -rf host-manager

# Copy the WAR file from the build stage to the container
COPY --from=build /app/target/FFood-shop-1.0-SNAPSHOT.war ROOT.war
# COPY  target/FFood-shop-1.0-SNAPSHOT.war ROOT.war

# Expose the default Tomcat port
EXPOSE 8080

# Copy setenv.sh to the bin directory of Tomcat
COPY setenv.sh /usr/local/tomcat/bin/setenv.sh

# Start Tomcat when the container runs
CMD ["catalina.sh", "run"]
