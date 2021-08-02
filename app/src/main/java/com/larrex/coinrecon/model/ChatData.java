package com.larrex.coinrecon.model;

import java.util.List;

public class ChatData {

    private List<List<Double>> myArray;

    public ChatData(List<List<Double>> myArray) {
        this.myArray = myArray;
    }

    public List<List<Double>> getMyArray() {
        return myArray;
    }

    public void setMyArray(List<List<Double>> myArray) {
        this.myArray = myArray;
    }
}
