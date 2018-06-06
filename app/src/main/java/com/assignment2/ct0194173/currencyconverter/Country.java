package com.assignment2.ct0194173.currencyconverter;

public class Country {
    private String countryName;
    private String CurrencyName;
    private int flag;
    private Double rate;

    public Country(String countryName, String currencyName, int flag, Double rate) {
        this.countryName = countryName;
        this.CurrencyName = currencyName;
        this.flag = flag;
        this.rate = rate;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public int getFlag() {
        return flag;
    }

    public Double getRate() {
        return rate;
    }
}
