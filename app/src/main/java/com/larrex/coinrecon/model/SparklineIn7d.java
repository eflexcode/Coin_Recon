package com.larrex.coinrecon.model;

import java.util.List;

public class SparklineIn7d {
    public List<Double> price;

    public SparklineIn7d(List<Double> price) {
        this.price = price;
    }

    public List<Double> getPrice() {
        return price;
    }

    public void setPrice(List<Double> price) {
        this.price = price;
    }
}
