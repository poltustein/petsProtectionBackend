package com.protectionDogs.protection.Request;

import javax.validation.constraints.NotBlank;

import com.protectionDogs.protection.Util.Utility;

public class VerifyOtpRequest {
	
	@NotBlank(message="Please provide an otp")
	public String otp;
	
	@NotBlank(message="Please provide an emailId")
	public String emailId;
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
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
