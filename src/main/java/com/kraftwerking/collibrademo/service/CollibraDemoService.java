package com.kraftwerking.collibrademo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kraftwerking.collibrademo.model.MyObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class CollibraDemoService {
    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;

//    public void sendMessage(String msg) {
//        kafkaTemplate.send("collibra-demo", msg);
//    }

    @Autowired
    ReactiveRedisTemplate<String, MyObject> reactiveRedisMyObjectTemplate;
    private ReactiveListOperations<String, String> reactiveListOps;
    private ReactiveValueOperations<String, MyObject> reactiveMyObjectValueOps;

    public Mono<MyObject> getMyObject(int id){
        reactiveMyObjectValueOps = reactiveRedisMyObjectTemplate.opsForValue();

        return reactiveMyObjectValueOps.get(String.valueOf(id));
    }

    public Mono<Boolean> setMyObject(MyObject myObject){
        log.info(myObject.getId() + " MyObject has been updated");
//        sendMessage(myObject.getId() + " MyObject has been updated");

        reactiveMyObjectValueOps = reactiveRedisMyObjectTemplate.opsForValue();

        return reactiveMyObjectValueOps.set(String.valueOf(myObject.getId()),
                myObject);
    }

    public Mono<Long> deleteMyObject(int id){
        return reactiveRedisMyObjectTemplate.delete(String.valueOf(id));
    }

    public List<MyObject> getMyObjectsFromFile() {
        String fileName = "medical_records.json";
        log.info("Deserializing " + fileName);

        String jsonString;
        try {
            jsonString = Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        MyObject[] myObjects;
        try {
            myObjects = objectMapper.readValue(jsonString, MyObject[].class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Arrays.asList(myObjects);
    }
}
