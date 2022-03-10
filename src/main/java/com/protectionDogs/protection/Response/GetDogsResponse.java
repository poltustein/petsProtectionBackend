package com.protectionDogs.protection.Response;

import java.util.List;

import com.protectionDogs.protection.Util.Utility;
import com.protectionDogs.protection.pojo.Dog;

public class GetDogsResponse {
	
	private String status;
	private String reason;
	private List<Dog> dogs;
	private Long total;
	
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
	public List<Dog> getDogs() {
		return dogs;
	}
	public void setDogs(List<Dog> dogs) {
		this.dogs = dogs;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	

}
