package com.kraftwerking.collibrademo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Lab implements Serializable {
    private String name;
    private String time;
    private String location;

}