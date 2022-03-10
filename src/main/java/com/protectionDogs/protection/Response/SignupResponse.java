package com.protectionDogs.protection.Response;

import com.protectionDogs.protection.Util.Utility;

public class SignupResponse {
	private String reason;
	private String status;
	private String token;
	private String emailId;
	private String name;
	private String contact;
	private Boolean isLogout;
	private Boolean isSubscribed;
	private String profileImageUrl;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Boolean getIsLogout() {
		return isLogout;
	}
	public void setIsLogout(Boolean isLogout) {
		this.isLogout = isLogout;
	}
	public Boolean getIsSubscribed() {
		return isSubscribed;
	}
	public void setIsSubscribed(Boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	
}
