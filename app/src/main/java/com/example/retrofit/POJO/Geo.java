package com.example.retrofit.POJO;

import com.google.gson.annotations.SerializedName;

public class Geo {

    @SerializedName("lat")
    public String lat;

    @SerializedName("lng")
    public String lng;

    public Geo(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
