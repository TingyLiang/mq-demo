package learn.lty.mq.producers;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


/**
 * @author liangty1
 */
public class KafkaReporter {

    public static void report(String server, String topic, String message) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1000);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 0);

        Producer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        producer.send(record, new KafkaAsyncSendCallback());
        producer.close();

    }

}

 class KafkaAsyncSendCallback implements Callback {
    private static final Logger log = LoggerFactory.getLogger(KafkaAsyncSendCallback.class);

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            log.error(" Error while send message to kafka, reason：{}", e.getMessage());
        }
        if (recordMetadata != null) {
            log.debug("r: topic->" + recordMetadata.topic() + ", partition->" + recordMetadata.partition() + ", offset->" + recordMetadata.offset());
        }
    }
}
