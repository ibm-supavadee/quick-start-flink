**Prerequisites**
1. Install Maven 3.8.6 (recommended or higher)
2. Use Java 11
3. Please use IntelliJ. Follow the steps at https://nightlies.apache.org/flink/flink-docs-release-1.18/docs/flinkdev/ide_setup/ for IntelliJ setup
4. (Optional) Follow the steps at https://nightlies.apache.org/flink/flink-docs-stable/docs/try-flink/local_installation/ for Flink cluster setup
5. Start Kafka/redpanda server using Docker Compose (I'm using redpanda, see https://docs.redpanda.com/current/get-started/quick-start/). Note: I'm using 127.0.0.1:19092 as the Kafka broker; you may need to update the connection string in the code if using a different one.
