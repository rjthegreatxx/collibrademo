package com.kraftwerking.collibrademo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Data implements Serializable {
    private List<Medications> medications;
    private List<Lab> labs;
    private List<Imaging> imaging;

}
