package com.example.retrofit;

import com.example.retrofit.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Backend {

	@GET("/users")
	Call<List<User>> listPosts();
}
