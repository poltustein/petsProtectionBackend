package com.protectionDogs.protection.Response;

import java.util.List;

import com.protectionDogs.protection.Util.Utility;
import com.protectionDogs.protection.pojo.SubscriptionPlans;

public class SubscriptionPlansResponse {
	private List<SubscriptionPlans> plans;
	private String reason;
	private String status;
	
	public List<SubscriptionPlans> getPlans() {
		return plans;
	}
	public void setPlans(List<SubscriptionPlans> plans) {
		this.plans = plans;
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
