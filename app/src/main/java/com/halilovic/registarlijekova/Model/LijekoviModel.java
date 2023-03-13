package com.halilovic.registarlijekova.Model;


import java.util.ArrayList;

public class LijekoviModel   {
    public LijekoviModel() {
    }

    private int id;
    private String name;
    private String atc;
    private String shortDescription;
    private String description;
    private int categoryId;
    private int activeSubstanceValue;
    private int activeSubstanceSelectedQuantity;
    private int minimumDailyDose;
    private String activeSubstanceMeasurementUnit;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public int getCategoryId() {
        return categoryId;
    }

    public int getActiveSubstanceValue() {
        return activeSubstanceValue;
    }

    public int getActiveSubstanceSelectedQuantity() {
        return activeSubstanceSelectedQuantity;
    }

    public int getMinimumDailyDose() {
        return minimumDailyDose;
    }

    public String getActiveSubstanceMeasurementUnit() {
        return activeSubstanceMeasurementUnit;
    }

    public LijekoviModel(int id, String name, String atc, String shortDescription, String description, int categoryId, int activeSubstanceValue, int activeSubstanceSelectedQuantity, int minimumDailyDose, String activeSubstanceMeasurementUnit) {
        this.id = id;
        this.name = name;
        this.atc = atc;
        this.shortDescription = shortDescription;
        this.description = description;
        this.categoryId = categoryId;
        this.activeSubstanceValue = activeSubstanceValue;
        this.activeSubstanceSelectedQuantity = activeSubstanceSelectedQuantity;
        this.minimumDailyDose = minimumDailyDose;
        this.activeSubstanceMeasurementUnit = activeSubstanceMeasurementUnit;
    }

    public LijekoviModel(String name, String atc) {
        this.name = name;
        this.atc = atc;
    }
}
