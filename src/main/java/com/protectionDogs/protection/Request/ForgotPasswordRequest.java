package com.protectionDogs.protection.Request;

import javax.validation.constraints.NotBlank;

import com.protectionDogs.protection.Util.Utility;

public class ForgotPasswordRequest {

	@NotBlank(message = "Please provide an emailId")
	private String emailId;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}

	
	
	
}
