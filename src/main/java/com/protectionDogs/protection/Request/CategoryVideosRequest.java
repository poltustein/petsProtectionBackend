package com.protectionDogs.protection.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.protectionDogs.protection.Util.Utility;

public class CategoryVideosRequest {
	@NotBlank
	private String categoryId;
	@NotNull
	private Integer pageSize;
	@NotNull
	private Integer pageIndex;

	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}

}
