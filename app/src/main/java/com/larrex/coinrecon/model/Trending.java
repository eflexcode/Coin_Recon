package com.larrex.coinrecon.model;

import java.util.List;

public class Trending {
    private List<Coin> coins;

    public Trending(List<Coin> coins) {
        this.coins = coins;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }
}
