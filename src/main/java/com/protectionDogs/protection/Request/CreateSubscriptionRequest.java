package com.protectionDogs.protection.Request;

import com.protectionDogs.protection.Util.PaymentMethods;
import com.protectionDogs.protection.Util.Utility;

public class CreateSubscriptionRequest {
	
	private String singlePlanId;
	private PaymentMethods method;
	
	public String getSinglePlanId() {
		return singlePlanId;
	}
	public void setSinglePlanId(String singlePlanId) {
		this.singlePlanId = singlePlanId;
	}
	
	public PaymentMethods getMethod() {
		return method;
	}
	public void setMethod(PaymentMethods method) {
		this.method = method;
	}
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	
}
