package com.example.cryptocurrencyapp.model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    @SerializedName("currency")
    private String currency;

    @SerializedName("price")
    private String price;

    @SerializedName("name")
    private String name;

    @SerializedName("logo_url")
    private String logo;

    @SerializedName("price_timestamp")
    private String priceDate;

    @SerializedName("rank")
    private String rank;

    public String getCurrency() {
        return currency;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getPriceDate() {
        return priceDate;
    }

    public String getRank() {
        return rank;
    }
}
