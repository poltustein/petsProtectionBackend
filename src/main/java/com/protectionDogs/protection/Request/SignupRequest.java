package com.protectionDogs.protection.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.protectionDogs.protection.Util.Utility;

public class SignupRequest {

	@NotBlank(message="Please provide an emailId")
	private String emailId;
	
	@NotBlank(message="Please provide a valid password")
	private String password;
	
	@NotBlank(message="Please provide a valid name")
	@Size(min=2,max=50)
	private String fullName;
	
	@NotBlank(message="Please provide a valid phone number")
	private String contactNumber;
	

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
	

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	
}
