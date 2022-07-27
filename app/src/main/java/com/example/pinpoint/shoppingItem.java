package com.example.pinpoint;

public class shoppingItem {

    private String mName;

    public shoppingItem(String name){
        mName = name;
    }

    public String getmName() {
        return mName;
    }

    public void changeText(String text){
        mName = text;
    }
}
