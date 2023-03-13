package com.halilovic.registarlijekova.Model;

public class Model {
    private int id_lijeka;

    public int getId_lijeka() {
        return id_lijeka;
    }

    private String name;
    private String color;
    private String name_cat;
    private String mark;
    private String atc;
    private String shortDescription;
    private String description;
    private int activeSubstanceValue;
    private int activeSubstanceSelectedQuantity;
    private int minimumDailyDose;
    private int maximumDailyDose;

    public int getActiveSubstanceSelectedQuantity() {
        return activeSubstanceSelectedQuantity;
    }

    public int getMinimumDailyDose() {
        return minimumDailyDose;
    }

    public int getMaximumDailyDose() {
        return maximumDailyDose;
    }

    public int getActiveSubstanceValue() {
        return activeSubstanceValue;
    }

    public Model(int id_lijeka,String name, String color, String name_cat, String atc, String shortDescription, String description, int activeSubstanceValue, int activeSubstanceSelectedQuantity,int minimumDailyDose, int maximumDailyDose) {
        this.id_lijeka = id_lijeka;
        this.name = name;
        this.color = color;
        this.name_cat = name_cat;
        this.atc = atc;
        this.shortDescription = shortDescription;
        this.description = description;
        this.activeSubstanceValue = activeSubstanceValue;
        this.activeSubstanceSelectedQuantity = activeSubstanceSelectedQuantity;
        this.minimumDailyDose = minimumDailyDose;
        this.maximumDailyDose = maximumDailyDose;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getName_cat() {
        return name_cat;
    }

    public String getMark() {
        return mark;
    }

    public String getAtc() {
        return atc;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

}
