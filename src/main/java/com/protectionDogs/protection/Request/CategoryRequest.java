package com.protectionDogs.protection.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.protectionDogs.protection.Util.CategoryActions;
import com.protectionDogs.protection.Util.Utility;

public class CategoryRequest {
	
	@NotBlank
	private String categoryName;
	@NotNull
	private CategoryActions action;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public CategoryActions getAction() {
		return action;
	}
	public void setAction(CategoryActions action) {
		this.action = action;
	}
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	

}
