package com.cometchat.pro.androiduikit.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginData {

	@SerializedName("user_id")
	private String userId;

	@SerializedName("name")
	private String name;

	@SerializedName("username")
	private String username;

	@SerializedName("password")
	private String password;

	@SerializedName("unique_id")
	private String unique_id;

	@SerializedName("email")
	private String email;

	@SerializedName("access")
	private String access;

	@SerializedName("subscription")
	private String subscription;

	@SerializedName("group")
	private String group;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}


	public void setPassword(String password){ this.password = password;}

	public String getPassword(){
		return password;
	}

	public void setEmail(String email){ this.email = email;}

	public String getEmail(){
		return email;
	}

	public void setUnique_id(String email){ this.unique_id = unique_id;}

	public String getUnique_id(){
		return unique_id;
	}

	public void setAccess(String access){ this.access = access;}

	public String getAccess(){
		return access;
	}

	public void setSubscription(String subscription){ this.subscription = subscription;}

	public String getSubscription(){
		return subscription;
	}

	public void setGroup(String group){ this.group = group;}

	public String getGroup(){
		return group;
	}



}