package com.cometchat.pro.androiduikit.model.subscribe;

import com.google.gson.annotations.SerializedName;

public class SubscribeData{

	@SerializedName("username")
	private String username;

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}