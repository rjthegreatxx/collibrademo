package com.kraftwerking.collibrademo.controller;

import com.kraftwerking.collibrademo.model.MyObject;
import com.kraftwerking.collibrademo.service.CollibraDemoService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api")
public class CollibraDemoController {

    @Autowired
    CollibraDemoService collibraDemoService;

    @CrossOrigin
    @PostMapping("/myobject")
    Mono<Boolean> postMyObject(@Valid @RequestBody MyObject myObject) {
        return collibraDemoService.setMyObject(myObject);
    }

    @CrossOrigin
    @GetMapping("/myobject/{id}")
    Mono<MyObject> getMyObject(@PathVariable String id) {
        return collibraDemoService.getMyObject(Integer.parseInt(id));
    }

    @CrossOrigin
    @GetMapping("/myobject/delete/{id}")
    Mono<Long> deleteMyObject(@PathVariable String id) {
        return collibraDemoService.deleteMyObject(Integer.parseInt(id));
    }

    @CrossOrigin
    @GetMapping("/getmyobjects")
    public Flux<MyObject> getMyobjects() {
        log.info("Get medical assets");
        List<MyObject> myObjects = collibraDemoService.getMyObjectsFromFile();
        return Flux.fromIterable(myObjects);
    }
}