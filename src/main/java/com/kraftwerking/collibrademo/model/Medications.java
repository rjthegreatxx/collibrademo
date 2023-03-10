package com.kraftwerking.collibrademo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Medications implements Serializable {
    private List<Medication> aceInhibitors;
    private List<Medication> antianginal;
    private List<Medication> anticoagulants;
    private List<Medication> betaBlocker;
    private List<Medication> diuretic;
    private List<Medication> mineral;


}
