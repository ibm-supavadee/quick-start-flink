package org.myorg.quickstart;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Skeleton for a Flink DataStream Job.
 *
 * <p>For a tutorial how to write a Flink application, check the
 * tutorials and examples on the <a href="https://flink.apache.org">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class DataStreamJob {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KafkaSource<KafkaMessage> kafkaSource = KafkaSource.<KafkaMessage>builder()
                .setBootstrapServers("127.0.0.1:19092")
                .setTopics("yingTopic")
                .setGroupId("test-group")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new CustomSchema())
                .build();

        DataStream<KafkaMessage> kafkaDataStream = env.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source");

        // Define the path where the text file will be written
        //		String outputPath = "/Users/piradaboonna/Documents/Development/flink/output/textfile.txt";

        // Configure the StreamingFileSink
        //		StreamingFileSink<String> fileSink = StreamingFileSink
        //				.forRowFormat(new Path(outputPath), new SimpleStringEncoder<String>("UTF-8"))
        //				.withRollingPolicy(
        //						DefaultRollingPolicy.builder()
        //								.withRolloverInterval(TimeUnit.MINUTES.toMillis(15))
        //								.withInactivityInterval(TimeUnit.MINUTES.toMillis(5))
        //								.withMaxPartSize(1024 * 1024 * 1024)
        //								.build())
        //				.build();

        // Add the fileSink to the Flink job
        //		kafkaDataStream.addSink(fileSink).name("Text File Sink");

        // Keyed process function to handle the lookup and update
        DataStream<KafkaMessage> processedStream = kafkaDataStream
                .keyBy(message -> message.getBody().getAccountName())
                .process(new KeyedProcessFunction<String, KafkaMessage, KafkaMessage>() {
                    @Override
                    public void processElement(KafkaMessage kafkaMessage, KeyedProcessFunction<String, KafkaMessage, KafkaMessage>.Context context, Collector<KafkaMessage> collector) throws Exception {
                        String accountName = kafkaMessage.getBody().getAccountName();

                        // TODO: Implement lookup logic here
                        String lookupResult = "SomeLookupResult";

                        kafkaMessage.getBody().setAccountId(lookupResult);

                        // Emit the modified KafkaMessage
                        collector.collect(kafkaMessage);
                    }
                });

        // Call API
        System.out.println("\n----------------------------------------------" );
        System.out.println("Call API");

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:3000/")).GET().build();

        try {
            HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            System.out.println("HTTP status: " + statusCode);
            System.out.println("Response: " + response.body());
            System.out.println("----------------------------------------------" + "\n");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


        // Add the JDBC sink to the Flink job
//        processedStream.addSink(
//                JdbcSink.sink(
//                        "insert into earn_table (header_version, header_timestamp, accountName, accountId, protocol_version, protocol_command, protocol_topic) values (?, ?, ?, ?, ?, ?, ?)",
//                        (statement, kafkaMessage) -> {
//
//                            Header header = kafkaMessage.getHeader();
//                            statement.setString(1, header.getVersion());
//                            statement.setString(2, header.getTimestamp());
//
//                            Body body = kafkaMessage.getBody();
//                            statement.setString(3, body.getAccountName());
//                            statement.setString(4, body.getAccountId());
//
//                            Protocol protocol = kafkaMessage.getProtocol();
//                            statement.setString(5, protocol.getVersion());
//                            statement.setString(6, protocol.getCommand());
//                            statement.setString(7, protocol.getTopic());
//
//                        },
//				JdbcExecutionOptions.builder()
//						.withBatchSize(1000)
//						.withBatchIntervalMs(200)
//						.withMaxRetries(5)
//						.build(),
//				new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
//						.withUrl("jdbc:sqlserver://localhost:1433;trustServerCertificate=true;databaseName=earn_test") // Update the database name
//						.withDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
//						.withUsername("sa")
//						.withPassword("test_Password123")
//						.build()
//		));

        // Execute program, beginning computation.
        env.execute("Flink Kafka to SQL");
    }
}
