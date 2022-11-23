package com.cometchat.pro.androiduikit.model.create;

import com.google.gson.annotations.SerializedName;

public class Create{

	@SerializedName("data")
	private CreateData createData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setCreateData(CreateData createData){
		this.createData = createData;
	}

	public CreateData getCreateData(){
		return createData;
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