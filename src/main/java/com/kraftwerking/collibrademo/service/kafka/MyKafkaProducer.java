package com.kraftwerking.collibrademo.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kraftwerking.collibrademo.model.MyObject;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
@Service
public class MyKafkaProducer {

    @Value("${kafka.props}")
    private String kafkaProps;

    @Value("${kafka.topic}")
    private String kafkaTopic;


    public void sendMsgToTopic(MyObject myObject, String operation) {
        String json;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            json = ow.writeValueAsString(myObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Properties props = loadConfig(kafkaProps);
        String topic = kafkaTopic;

        try (final KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
                producer.send(
                        new ProducerRecord<>(topic, String.valueOf(myObject.getId()), myObject.getId() + " was " + operation + " " + json),
                        (event, ex) -> {
                            if (ex != null)
                                ex.printStackTrace();
                            else
                                log.info("Produced event to topic = " + topic + ", key = " + myObject.getId() + ", value = " + myObject.getId() + " was " + operation + " " + json);
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