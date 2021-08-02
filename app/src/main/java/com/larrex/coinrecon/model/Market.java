package com.larrex.coinrecon.model;


import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class Market  implements Serializable {

    private String id;
    private String symbol;
    private String name;
    private String image;
    private double current_price;
    private double market_cap;
    private double market_cap_rank;
    private double fully_diluted_valuation;
    private double total_volume;
    private double high_24h;
    private double low_24h;
    private double price_change_24h;
    private double price_change_percentage_24h;
    private double market_cap_change_24h;
    private double market_cap_change_percentage_24h;
    private double circulating_supply;
    private double total_supply;
    private double max_supply;
    private double ath;
    private double ath_change_percentage;
    private String ath_date;
    private double atl;
    private double atl_change_percentage;
    private String  atl_date;
    private Object roi;
    private String  last_updated;
    private double price_change_percentage_1h_in_currency;
    private SparklineIn7d sparkline_in_7d;

    public Market(String id, String symbol, String name, String image, double current_price, double market_cap, double market_cap_rank, double fully_diluted_valuation, double total_volume, double high_24h, double low_24h, double price_change_24h, double price_change_percentage_24h, double market_cap_change_24h, double market_cap_change_percentage_24h, double circulating_supply, double total_supply, double max_supply, double ath, double ath_change_percentage, String ath_date, double atl, double atl_change_percentage, String atl_date, Object roi, String last_updated, double price_change_percentage_1h_in_currency, SparklineIn7d sparkline_in_7d) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.image = image;
        this.current_price = current_price;
        this.market_cap = market_cap;
        this.market_cap_rank = market_cap_rank;
        this.fully_diluted_valuation = fully_diluted_valuation;
        this.total_volume = total_volume;
        this.high_24h = high_24h;
        this.low_24h = low_24h;
        this.price_change_24h = price_change_24h;
        this.price_change_percentage_24h = price_change_percentage_24h;
        this.market_cap_change_24h = market_cap_change_24h;
        this.market_cap_change_percentage_24h = market_cap_change_percentage_24h;
        this.circulating_supply = circulating_supply;
        this.total_supply = total_supply;
        this.max_supply = max_supply;
        this.ath = ath;
        this.ath_change_percentage = ath_change_percentage;
        this.ath_date = ath_date;
        this.atl = atl;
        this.atl_change_percentage = atl_change_percentage;
        this.atl_date = atl_date;
        this.roi = roi;
        this.last_updated = last_updated;
        this.price_change_percentage_1h_in_currency = price_change_percentage_1h_in_currency;
        this.sparkline_in_7d = sparkline_in_7d;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(double current_price) {
        this.current_price = current_price;
    }

    public double getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(double market_cap) {
        this.market_cap = market_cap;
    }

    public double getMarket_cap_rank() {
        return market_cap_rank;
    }

    public void setMarket_cap_rank(int market_cap_rank) {
        this.market_cap_rank = market_cap_rank;
    }

    public double getFully_diluted_valuation() {
        return fully_diluted_valuation;
    }

    public void setFully_diluted_valuation(double fully_diluted_valuation) {
        this.fully_diluted_valuation = fully_diluted_valuation;
    }

    public double getTotal_volume() {
        return total_volume;
    }

    public void setTotal_volume(double total_volume) {
        this.total_volume = total_volume;
    }

    public double getHigh_24h() {
        return high_24h;
    }

    public void setHigh_24h(double high_24h) {
        this.high_24h = high_24h;
    }

    public double getLow_24h() {
        return low_24h;
    }

    public void setLow_24h(double low_24h) {
        this.low_24h = low_24h;
    }

    public double getPrice_change_24h() {
        return price_change_24h;
    }

    public void setPrice_change_24h(double price_change_24h) {
        this.price_change_24h = price_change_24h;
    }

    public double getPrice_change_percentage_24h() {
        return price_change_percentage_24h;
    }

    public void setPrice_change_percentage_24h(double price_change_percentage_24h) {
        this.price_change_percentage_24h = price_change_percentage_24h;
    }

    public double getMarket_cap_change_24h() {
        return market_cap_change_24h;
    }

    public void setMarket_cap_change_24h(double market_cap_change_24h) {
        this.market_cap_change_24h = market_cap_change_24h;
    }

    public double getMarket_cap_change_percentage_24h() {
        return market_cap_change_percentage_24h;
    }

    public void setMarket_cap_change_percentage_24h(double market_cap_change_percentage_24h) {
        this.market_cap_change_percentage_24h = market_cap_change_percentage_24h;
    }

    public double getCirculating_supply() {
        return circulating_supply;
    }

    public void setCirculating_supply(double circulating_supply) {
        this.circulating_supply = circulating_supply;
    }

    public double getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(double total_supply) {
        this.total_supply = total_supply;
    }

    public double getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(double max_supply) {
        this.max_supply = max_supply;
    }

    public double getAth() {
        return ath;
    }

    public void setAth(double ath) {
        this.ath = ath;
    }

    public double getAth_change_percentage() {
        return ath_change_percentage;
    }

    public void setAth_change_percentage(double ath_change_percentage) {
        this.ath_change_percentage = ath_change_percentage;
    }

    public String getAth_date() {
        return ath_date;
    }

    public void setAth_date(String ath_date) {
        this.ath_date = ath_date;
    }

    public double getAtl() {
        return atl;
    }

    public void setAtl(double atl) {
        this.atl = atl;
    }

    public double getAtl_change_percentage() {
        return atl_change_percentage;
    }

    public void setAtl_change_percentage(double atl_change_percentage) {
        this.atl_change_percentage = atl_change_percentage;
    }

    public String getAtl_date() {
        return atl_date;
    }

    public void setAtl_date(String atl_date) {
        this.atl_date = atl_date;
    }

    public Object getRoi() {
        return roi;
    }

    public void setRoi(Object roi) {
        this.roi = roi;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public double getPrice_change_percentage_1h_in_currency() {
        return price_change_percentage_1h_in_currency;
    }

    public void setPrice_change_percentage_1h_in_currency(double price_change_percentage_1h_in_currency) {
        this.price_change_percentage_1h_in_currency = price_change_percentage_1h_in_currency;
    }

    public SparklineIn7d getSparkline_in_7d() {
        return sparkline_in_7d;
    }

    public void setSparkline_in_7d(SparklineIn7d sparkline_in_7d) {
        this.sparkline_in_7d = sparkline_in_7d;
    }
}
