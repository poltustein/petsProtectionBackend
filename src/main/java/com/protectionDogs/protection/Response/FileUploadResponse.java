package com.protectionDogs.protection.Response;

import com.protectionDogs.protection.Util.Utility;

public class FileUploadResponse {
	private String reason;
	private String status;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
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
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}

}
