package com.cometchat.pro.androiduikit.model.mes;

import com.google.gson.annotations.SerializedName;

public class MesData {

	@SerializedName("subject")
	private String subject;

	@SerializedName("message")
	private String message;

	@SerializedName("username")
	private String username;

	public void setSubject(String subject){
		this.subject = subject;
	}

	public String getSubject(){
		return subject;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}