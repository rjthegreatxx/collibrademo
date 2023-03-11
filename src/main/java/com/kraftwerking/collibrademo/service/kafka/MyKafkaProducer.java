package com.kraftwerking.collibrademo.service.kafka;

import com.kraftwerking.collibrademo.model.MyObject;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
@Service
public class MyKafkaProducer {

    public void sendMsgToTopic(MyObject myObject) throws IOException {
        Properties props = loadConfig("kafka.properties");
        String topic = "topic_0";

        try (final KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
                producer.send(
                        new ProducerRecord<>(topic, String.valueOf(myObject.getId()), String.valueOf(myObject.getId()) + " was updated"),
                        (event, ex) -> {
                            if (ex != null)
                                ex.printStackTrace();
                            else
                                log.info("Produced event to topic = " + topic + ", key = " + String.valueOf(myObject.getId()) + ", value = " +  String.valueOf(myObject.getId()) + " was updated");
                        });
            }

    }

    public static Properties loadConfig(final String configFile) {
        final Properties cfg = new Properties();
        InputStream is = MyKafkaProducer.class.getClassLoader()
                .getResourceAsStream(configFile);
        try {
            cfg.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cfg;
    }
}