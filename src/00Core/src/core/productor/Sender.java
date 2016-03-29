package core.productor;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Sender {
    public Properties kafka;
    public Producer<String, String> sender;
    public Properties config;

    public Sender(Properties kafkaConfig, Properties sysConfig) {
        kafka = kafkaConfig;
        config = sysConfig;
        sender = new KafkaProducer<String, String>(kafkaConfig);
    }


    public synchronized void send(String json) {
        ProducerRecord<String, String> msg = new ProducerRecord<String, String>(
                config.getProperty("producer.topic"),
                config.getProperty("key", "test"),
                json);
        sender.send(msg);
    }

    public void close() {
        sender.close();
    }
}
