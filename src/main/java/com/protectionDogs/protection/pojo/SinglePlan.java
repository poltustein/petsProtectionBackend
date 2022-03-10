package com.protectionDogs.protection.pojo;

import com.protectionDogs.protection.Util.Utility;

public class SinglePlan {
	
	private String singlePlanId;
	private String planCost;
	
	public String getSinglePlanId() {
		return singlePlanId;
	}
	public void setSinglePlanId(String singlePlanId) {
		this.singlePlanId = singlePlanId;
	}
	public String getPlanCost() {
		return planCost;
	}
	public void setPlanCost(String planCost) {
		this.planCost = planCost;
	}
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}


}
