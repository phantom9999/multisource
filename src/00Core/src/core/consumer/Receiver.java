package core.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class Receiver {
    public Properties properties;
    KafkaConsumer<String, String> receiver;
    public String topic;

    public Receiver(Properties var1, String var2) {
        topic = var2;
        properties = var1;
        receiver = new KafkaConsumer<String, String>(var1);
        receiver.subscribe(Arrays.asList("test"));
    }

    public synchronized void reveiver() {

        while (true) {
            ConsumerRecords<String, String> records = receiver.poll(1);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
        }
    }
}
