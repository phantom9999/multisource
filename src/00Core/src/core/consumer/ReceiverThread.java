package core.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.*;

public class ReceiverThread extends Thread{


    protected Properties kafkaConfig;



    protected String topic;
    protected MethodIf methodIf;
    protected KafkaConsumer<String, String> receiver;

    public void setKafkaConfig(Properties kafkaConfig) {
        receiver = new KafkaConsumer<String, String>(kafkaConfig);
    }

    public void setMethodIfMap(MethodIf methodIf) {
        this.methodIf = methodIf;
    }

    public void setTopic(String topic) {
        this.topic = topic;
        System.out.println(topic);
        receiver.subscribe(Arrays.asList(topic));
    }


    public void run() {

        while (true) {
            ConsumerRecords<String, String> records = receiver.poll(100);
            for (ConsumerRecord<String, String> record : records)
            {
                // key由mac地址和类型组成
                System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());

                methodIf.store(record.key(), record.value());
            }
        }
    }


}
