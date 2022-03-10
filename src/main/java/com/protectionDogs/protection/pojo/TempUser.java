package com.protectionDogs.protection.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TempUser {
	
	@Id
	private String emailId;
	private String name;
	private String contact;
	private String password;
	private String otp;
	private Date creationDate;
	private Date firstOtpDate;
	private Date lastOtpDate;
	private Integer totalOtpCount;
	
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
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getFirstOtpDate() {
		return firstOtpDate;
	}
	public void setFirstOtpDate(Date firstOtpDate) {
		this.firstOtpDate = firstOtpDate;
	}
	public Date getLastOtpDate() {
		return lastOtpDate;
	}
	public void setLastOtpDate(Date lastOtpDate) {
		this.lastOtpDate = lastOtpDate;
	}
	public Integer getTotalOtpCount() {
		return totalOtpCount;
	}
	public void setTotalOtpCount(Integer totalOtpCount) {
		this.totalOtpCount = totalOtpCount;
	}
	
}
