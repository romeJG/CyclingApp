package com.cometchat.pro.androiduikit.model.create;

import com.google.gson.annotations.SerializedName;

public class CreateData {

	@SerializedName("username")
	private String username;

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}