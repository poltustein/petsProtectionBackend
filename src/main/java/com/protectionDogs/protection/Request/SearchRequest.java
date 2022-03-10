package com.protectionDogs.protection.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.protectionDogs.protection.Util.Utility;

public class SearchRequest {
	
	@NotBlank
	private String searchTerm;
	@NotNull
	private Integer pageIndex;
	@NotNull
	private Integer pageSize;
	
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
}
