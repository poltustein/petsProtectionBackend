package com.protectionDogs.protection.Response;

import java.util.List;

import com.protectionDogs.protection.Util.Utility;
import com.protectionDogs.protection.pojo.Category;

public class CategoriesResponse {
	private List<Category> categories;
	private String status;
	private String reason;
	
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
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
	
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}

}
