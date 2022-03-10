package com.protectionDogs.protection.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.protectionDogs.protection.Util.LoginMethods;
import com.protectionDogs.protection.Util.Utility;

public class LoginRequest {

	@NotBlank(message = "Please provide an emailId")
	private String emailId;

	private String password;
	
	@NotNull
	private LoginMethods method;
	
	private String imageUrl;
	private String fullName;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginMethods getMethod() {
		return method;
	}

	public void setMethod(LoginMethods method) {
		this.method = method;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}

}
