package com.example.pinpoint.model;

public class Ticker {
    private String symbol;
    private String url;

    public Ticker(String symbol, String url){
        this.symbol = symbol;
        this.url = url;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getUrl(){
        return url;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
