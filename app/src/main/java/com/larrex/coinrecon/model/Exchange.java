package com.larrex.coinrecon.model;

public class Exchange {

    private String id;
    private String name;
    private int year_established;
    private String country;
    private String description;
    private String url;
    private String image;
    private boolean has_trading_incentive;
    private int trust_score;
    private int trust_score_rank;
    private double trade_volume_24h_btc;
    private double trade_volume_24h_btc_normalized;

    public Exchange(String id, String name, int year_established, String country, String description, String url, String image, boolean has_trading_incentive, int trust_score, int trust_score_rank, double trade_volume_24h_btc, double trade_volume_24h_btc_normalized) {
        this.id = id;
        this.name = name;
        this.year_established = year_established;
        this.country = country;
        this.description = description;
        this.url = url;
        this.image = image;
        this.has_trading_incentive = has_trading_incentive;
        this.trust_score = trust_score;
        this.trust_score_rank = trust_score_rank;
        this.trade_volume_24h_btc = trade_volume_24h_btc;
        this.trade_volume_24h_btc_normalized = trade_volume_24h_btc_normalized;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear_established() {
        return year_established;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public boolean isHas_trading_incentive() {
        return has_trading_incentive;
    }

    public int getTrust_score() {
        return trust_score;
    }

    public int getTrust_score_rank() {
        return trust_score_rank;
    }

    public double getTrade_volume_24h_btc() {
        return trade_volume_24h_btc;
    }

    public double getTrade_volume_24h_btc_normalized() {
        return trade_volume_24h_btc_normalized;
    }
}
