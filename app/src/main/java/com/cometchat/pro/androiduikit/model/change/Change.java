package com.cometchat.pro.androiduikit.model.change;

import com.google.gson.annotations.SerializedName;

public class Change{

	@SerializedName("data")
	private ChangeData changeData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setChangeData(ChangeData changeData){
		this.changeData = changeData;
	}

	public ChangeData getChangeData(){
		return changeData;
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