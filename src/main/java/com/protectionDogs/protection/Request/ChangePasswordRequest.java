package com.protectionDogs.protection.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.protectionDogs.protection.Util.Utility;

public class ChangePasswordRequest {
	
	@NotBlank(message="Please provide an emailId")
	private String emailId;
	
	@NotBlank(message="Please provide a valid password")
	@Size(min=4,max=10)
	private String password;
	
	@NotBlank(message="Please provide an otp")
	private String otp;

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

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	
	
	
}
