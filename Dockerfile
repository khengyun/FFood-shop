# Use an official Tomcat runtime as the base image
FROM tomcat:10.0.23-jre8

# Set the working directory to /usr/local/tomcat/webapps
WORKDIR /usr/local/tomcat/webapps

# Remove the default Tomcat applications
RUN rm -rf ROOT && rm -rf examples && rm -rf docs && rm -rf manager && rm -rf host-manager

# Copy the WAR file from your local build directory to the container
COPY target/FFood-shop-1.0-SNAPSHOT.war ROOT.war

# Expose the default Tomcat port
EXPOSE 8080

# Start Tomcat when the container runs
CMD ["catalina.sh", "run"]
