# Use an official Flink runtime image as the base image
FROM flink:1.18.0

# Set the working directory to /opt/flink/usrlib
WORKDIR /opt/flink/usrlib

# Copy the Flink job JAR file into the container
COPY ./target/kafka-to-sql-0.1.jar .

# Set environment variables for Flink job
ENV JOB_MANAGER_RPC_ADDRESS=flink-jobmanager
ENV REDPANDA_BROKER_LIST=redpanda:9092

# Run the Flink job when the container starts
CMD ["/opt/flink/bin/flink", "run", "-c", "org.myorg.quickstart.DataStreamJob", "./kafka-to-sql-0.1.jar", "-Dkafka.bootstrap.servers=redpanda:9092", "-Dflink.parallelism=2"]







## Use an official Flink image as the base image
#FROM apache/flink:1.18.0
#
## Copy the JAR file into the container
#COPY ./target/kafka-to-sql-0.1.jar /opt/flink/usrlib/
#
## Expose Flink Web UI port
#EXPOSE 8081
#
## Set the environment variable for the Flink job class
#ENV FLINK_JOB_CLASS org.myorg.quickstart.DataStreamJob
#
## Command to run the Flink job
#CMD ["standalone-job", "start", "--job-classname", "${FLINK_JOB_CLASS}", "--job-jar", "/opt/flink/usrlib/kafka-to-sql-0.1.jar"]


## Stage 1: Build stage
#FROM maven:3.8.4-openjdk-11 AS build
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the Maven project files
#COPY pom.xml .
#COPY src ./src
#
## Build the project
#RUN mvn clean package
#
## Stage 2: Create a lightweight image with the .jar file
#FROM flink:1.18.0-scala_2.12-java11
#
## Set the working directory in the container
#WORKDIR /app
#
### Copy the .jar file from the build stage
##COPY ./target/kafka-to-sql-0.1.jar /opt/flink/usrlib/
#
##COPY --from=build /app/target/kafka-to-sql-0.1.jar app.jar
#COPY --from=build /app/target/kafka-to-sql-0.1.jar /opt/flink/usrlib/
##
### Redpanda dependencies (replace versions accordingly)
##ARG REDPANDA_VERSION=23.3.2
##
### Install unzip
##RUN apt-get update && apt-get install -y unzip
##
### Download and install Redpanda binaries
##RUN wget -q https://github.com/redpanda-data/redpanda/releases/latest/download/rpk-linux-amd64.zip &&\
##    unzip rpk-linux-amd64.zip -d /opt && \
##    rm rpk-linux-amd64.zip
##
### Set environment variables for Redpanda
##ENV REDPANDA_HOME /opt/redpanda-${REDPANDA_VERSION}-linux-amd64
##ENV PATH="${REDPANDA_HOME}/bin:${PATH}"
##
### Print the contents of the Redpanda installation directory for debugging
##RUN /bin/bash -c ls -la $REDPANDA_HOME
#
## Flink dependencies (replace version accordingly)
#ARG FLINK_VERSION=1.18.0
#ARG FLINK_SCALA_VERSION=2.12
#
## Download and install Flink binaries
#RUN wget -q https://downloads.apache.org/flink/flink-${FLINK_VERSION}/flink-${FLINK_VERSION}-bin-scala_${FLINK_SCALA_VERSION}.tgz && \
#    tar -xzf flink-${FLINK_VERSION}-bin-scala_${FLINK_SCALA_VERSION}.tgz -C /opt && \
#    rm flink-${FLINK_VERSION}-bin-scala_${FLINK_SCALA_VERSION}.tgz
#
## Set environment variables for Flink
#ENV FLINK_HOME /opt/flink-${FLINK_VERSION}
#ENV PATH="${FLINK_HOME}/bin:${PATH}"
#
## Expose ports for Flink and Redpanda
#EXPOSE 8081
#
## Entry point
##CMD ["sh", "-c", "redpanda start && flink run /app/app.jar"]
#CMD ["standalone-job", "start", "--job-classname", "org.myorg.quickstart.DataStreamJob", "--job-jar", "/opt/flink/usrlib/kafka-to-sql-0.1.jar"]
