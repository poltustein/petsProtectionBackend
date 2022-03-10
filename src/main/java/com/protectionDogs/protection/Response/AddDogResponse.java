package com.protectionDogs.protection.Response;

import com.protectionDogs.protection.Util.Utility;

public class AddDogResponse {
	
	private String status;
	private String reason;
	private String dogId;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDogId() {
		return dogId;
	}
	public void setDogId(String dogId) {
		this.dogId = dogId;
	}
	
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	

}
