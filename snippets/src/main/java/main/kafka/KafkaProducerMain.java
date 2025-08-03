package main.kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class KafkaProducerMain {
  public static void main(String[] args) {
    String bootstrapServers = "172.28.200.101:29092";
    String topic = "test-topic";

    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

    try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
      ProducerRecord<String, String> record = new ProducerRecord<>(topic, "my-key", "Hello, Kafka from Java!");
      RecordMetadata metadata = producer.send(record).get();
      System.out.printf("Message sent to topic %s, partition %d, offset %d%n",
        metadata.topic(), metadata.partition(), metadata.offset());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
