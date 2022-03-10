package com.protectionDogs.protection.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.protectionDogs.protection.Util.LoginMethods;

@Document
public class User {
	
	@Id
	private String emailId;
	private String name;
	private String contact;
	private String password;
	private Date creationDate;
	private String forgotOtp;
	private Date forgotPasswordDate;
	private String deviceId;
	private Boolean isSubscribed = false;
	private String subscriptionPlanId;
	private String singlePlanId;
	private String profileImageUrl;
	private LoginMethods loginMethod;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getForgotOtp() {
		return forgotOtp;
	}
	public void setForgotOtp(String forgotOtp) {
		this.forgotOtp = forgotOtp;
	}
	public Date getForgotPasswordDate() {
		return forgotPasswordDate;
	}
	public void setForgotPasswordDate(Date forgotPasswordDate) {
		this.forgotPasswordDate = forgotPasswordDate;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Boolean getIsSubscribed() {
		return isSubscribed;
	}
	public void setIsSubscribed(Boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}
	public String getSubscriptionPlanId() {
		return subscriptionPlanId;
	}
	public void setSubscriptionPlanId(String subscriptionPlanId) {
		this.subscriptionPlanId = subscriptionPlanId;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	public LoginMethods getLoginMethod() {
		return loginMethod;
	}
	public void setLoginMethod(LoginMethods loginMethod) {
		this.loginMethod = loginMethod;
	}
	public String getSinglePlanId() {
		return singlePlanId;
	}
	public void setSinglePlanId(String singlePlanId) {
		this.singlePlanId = singlePlanId;
	}
}
