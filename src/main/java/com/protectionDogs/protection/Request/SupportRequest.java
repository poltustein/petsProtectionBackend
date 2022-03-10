package com.protectionDogs.protection.Request;

import com.protectionDogs.protection.Util.Utility;

public class SupportRequest {
	
	private String issue;

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}
	
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	

}
