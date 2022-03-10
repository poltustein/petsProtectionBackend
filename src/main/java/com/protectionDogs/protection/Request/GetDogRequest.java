package com.protectionDogs.protection.Request;

import javax.validation.constraints.NotNull;

import com.protectionDogs.protection.Util.Utility;

public class GetDogRequest {
	
	@NotNull(message="Please provide a valid pagesize")
	private Integer pageSize;
	
	@NotNull(message="Please provide a valid index for page")
	private Integer pageIndex;
	
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
