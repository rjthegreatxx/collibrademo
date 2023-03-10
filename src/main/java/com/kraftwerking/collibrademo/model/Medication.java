package com.kraftwerking.collibrademo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Medication implements Serializable {
    private String name;
    private String strength;
    private String dose;
    private String route;
    private String sig;
    private String pillCount;
    private String refills;


}