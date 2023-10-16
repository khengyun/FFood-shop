# Use an official SQL Server client image as the base image
FROM mcr.microsoft.com/mssql-tools

# Copy all SQL initialization scripts to the container
COPY ./db_scripts /db_scripts

# Run a shell script to execute each SQL file
COPY init-db.sh /usr/local/bin/init-db.sh
RUN chmod +x /usr/local/bin/init-db.sh

# Run the shell script
CMD /usr/local/bin/init-db.sh

