package com.example.retrofit.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUsers {

    @SerializedName("name")
    public List<User> users;

    public ResponseUsers(List<User> users) {
        this.users = users;
    }
}
