package com.protectionDogs.protection.pojo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SubscriptionPlans {
	
	@Id
	private String planId;
	private String planName;
	private String planUrl;
	private String planCost;
	private String planCostString;
	private List<SinglePlan> planCosts;
	private List<String> planDescriptions;
	private String planDiscount;
	private Boolean isEnabled;
	private Boolean isActive=false;
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanUrl() {
		return planUrl;
	}
	public void setPlanUrl(String planUrl) {
		this.planUrl = planUrl;
	}
	public String getPlanCost() {
		return planCost;
	}
	public void setPlanCost(String planCost) {
		this.planCost = planCost;
	}
	public List<String> getPlanDescriptions() {
		return planDescriptions;
	}
	public void setPlanDescriptions(List<String> planDescriptions) {
		this.planDescriptions = planDescriptions;
	}
	public String getPlanDiscount() {
		return planDiscount;
	}
	public void setPlanDiscount(String planDiscount) {
		this.planDiscount = planDiscount;
	}
	public List<SinglePlan> getPlanCosts() {
		return planCosts;
	}
	public void setPlanCosts(List<SinglePlan> planCosts) {
		this.planCosts = planCosts;
	}
	public Boolean getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getPlanCostString() {
		return planCostString;
	}
	public void setPlanCostString(String planCostString) {
		this.planCostString = planCostString;
	}
}
