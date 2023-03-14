package com.halilovic.registarlijekova.Model;

public class CategoryModel {
    private int id;
    private String mark;
    private String name;
    private String color;

    public int getId() {
        return id;
    }

    public String getMark() {
        return mark;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public CategoryModel(int id, String mark, String name, String color) {
        this.id = id;
        this.mark = mark;
        this.name = name;
        this.color = color;
    }

    public CategoryModel(String name) {

        this.name = name;
    }
}
