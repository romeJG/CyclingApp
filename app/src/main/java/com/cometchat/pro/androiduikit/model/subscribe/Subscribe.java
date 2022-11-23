package com.cometchat.pro.androiduikit.model.subscribe;

import com.google.gson.annotations.SerializedName;

public class Subscribe{

	@SerializedName("data")
	private SubscribeData SubscribeData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setSubscribeData(SubscribeData SubscribeData){
		this.SubscribeData = SubscribeData;
	}

	public SubscribeData getSubscribeData(){
		return SubscribeData;
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