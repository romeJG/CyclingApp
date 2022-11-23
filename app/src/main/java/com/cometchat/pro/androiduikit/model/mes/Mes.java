package com.cometchat.pro.androiduikit.model.mes;

import com.google.gson.annotations.SerializedName;

public class Mes{

	@SerializedName("data")
	private MesData mesData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setMesData(MesData mesData){
		this.mesData = mesData;
	}

	public MesData getMesData(){
		return mesData;
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