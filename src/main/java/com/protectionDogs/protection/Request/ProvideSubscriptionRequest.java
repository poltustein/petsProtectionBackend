package com.protectionDogs.protection.Request;

public class ProvideSubscriptionRequest {
	private String singlePlanId;
	private String subscriptionPlanId;
	
	public String getSinglePlanId() {
		return singlePlanId;
	}
	public void setSinglePlanId(String singlePlanId) {
		this.singlePlanId = singlePlanId;
	}
	public String getSubscriptionPlanId() {
		return subscriptionPlanId;
	}
	public void setSubscriptionPlanId(String subscriptionPlanId) {
		this.subscriptionPlanId = subscriptionPlanId;
	}
}
