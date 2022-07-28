package com.example.pinpoint;

public class shoppingItem {

    private String mName;

    private double mPrice;

    public shoppingItem(String name, double price){
        mName = name;
        mPrice = price;
    }

    public String getmName() {
        return mName;
    }

    public double getmPrice(){return mPrice;}

    public void changeText(String text){
        mName = text;
    }
}
