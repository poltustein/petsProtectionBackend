package com.protectionDogs.protection.Request;

import javax.validation.constraints.NotBlank;

import com.protectionDogs.protection.Util.GetInfoRequestType;
import com.protectionDogs.protection.Util.Utility;

public class GetInfoRequest {
	
	public String deviceToken;
	
	@NotBlank(message="Please provide requestType")
	public GetInfoRequestType requestType;
	
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public GetInfoRequestType getRequestType() {
		return requestType;
	}
	public void setRequestType(GetInfoRequestType requestType) {
		this.requestType = requestType;
	}
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	
	

}
