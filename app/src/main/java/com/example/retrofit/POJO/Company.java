package com.example.retrofit.POJO;

import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("name")
    public String name;

    @SerializedName("catchPhrase")
    public String catchPhrase;

    @SerializedName("bs")
    public String bs;

    public Company(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }
}
