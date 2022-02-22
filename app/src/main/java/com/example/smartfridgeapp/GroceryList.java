package com.example.smartfridgeapp;

public class GroceryList {
    private String item;
    private Integer number;

    public GroceryList() {

    }

    public GroceryList(String item, Integer number) {
        this.item = item;
        this.number = number;
    }

    public String getItem() {
        return item;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer num){
        this.number = num;
    }
}
