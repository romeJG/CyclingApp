package com.cometchat.pro.androiduikit.model.change;

import com.google.gson.annotations.SerializedName;

public class ChangeData {

	@SerializedName("username")
	private String username;

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}