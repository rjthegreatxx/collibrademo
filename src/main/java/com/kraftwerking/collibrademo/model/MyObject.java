package com.kraftwerking.collibrademo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class MyObject implements Serializable {
    private List<Data> data;
    private int id;

}