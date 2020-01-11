package com.example.retrofit.pojo;

import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("street")
    public String street;
    @SerializedName("suite")
    public String suite;
    @SerializedName("city")
    public String city;
    @SerializedName("zipcode")
    public String zipcode;
    @SerializedName("geo")
    public Geo geo;

    public Address(String street, String suite, String city, String zipcode, Geo geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }
}
