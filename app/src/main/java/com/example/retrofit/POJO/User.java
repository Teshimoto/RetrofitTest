package com.example.retrofit.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

	@SerializedName("id")
	public long id;

	@SerializedName("name")
	public String name;

	@SerializedName("username")
	public String userName;

	@SerializedName("email")
	public String email;

	@SerializedName("address")
	public Address addresses;

	@SerializedName("phone")
	public String phone;

	@SerializedName("website")
	public String webSite;

	@SerializedName("company")
	public Company companies;

	public User(long id, String name, String userName, String email, Address addresses, String phone, String webSite, Company companies) {
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.addresses = addresses;
		this.phone = phone;
		this.webSite = webSite;
		this.companies = companies;
	}
}
