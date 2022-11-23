package com.cometchat.pro.androiduikit.model.changeInfo;

import com.google.gson.annotations.SerializedName;

public class Profile{

	@SerializedName("data")
	private ProfileData profileData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(ProfileData profileData){
		this.profileData = profileData;
	}

	public ProfileData getData(){
		return profileData;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}