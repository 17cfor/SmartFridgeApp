package com.example.smartfridgeapp;

public class FridgeList {

    private String item;
    private Integer number;

    public FridgeList() {

    }

    public FridgeList(String item, Integer number) {
        this.item = item;
        this.number = number;
    }

    public String getItem() {
        return item;
    }

    public Integer getNumber() {
        return number;
    }

}
