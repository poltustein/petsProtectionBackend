package com.protectionDogs.protection.Response;

import java.util.List;

import com.protectionDogs.protection.Util.Utility;
import com.protectionDogs.protection.pojo.BannerResource;
import com.protectionDogs.protection.pojo.HomeResource;

public class HomeResponse {

	private List<BannerResource> resources;
	private List<HomeResource> videos;
	
	private Integer visibility;
	private Boolean isVisible;
	private String subscribePlatformText;
	private String subscribeImageUrl;
	private String subscribeTitle;
	private String subscribeDescription;
	private String buyButtonText;
	private String status;
	private String reason;
	private Long count;
	
	public List<BannerResource> getResources() {
		return resources;
	}
	public void setResources(List<BannerResource> resources) {
		this.resources = resources;
	}
	public List<HomeResource> getVideos() {
		return videos;
	}
	public void setVideos(List<HomeResource> videos) {
		this.videos = videos;
	}
	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	public String getSubscribeImageUrl() {
		return subscribeImageUrl;
	}
	public void setSubscribeImageUrl(String subscribeImageUrl) {
		this.subscribeImageUrl = subscribeImageUrl;
	}
	public String getSubscribeTitle() {
		return subscribeTitle;
	}
	public void setSubscribeTitle(String subscribeTitle) {
		this.subscribeTitle = subscribeTitle;
	}
	public String getSubscribeDescription() {
		return subscribeDescription;
	}
	public void setSubscribeDescription(String subscribeDescription) {
		this.subscribeDescription = subscribeDescription;
	}
	public String getBuyButtonText() {
		return buyButtonText;
	}
	public void setBuyButtonText(String buyButtonText) {
		this.buyButtonText = buyButtonText;
	}
	public String getStatus() {
		return status;
	}
	public String getSubscribePlatformText() {
		return subscribePlatformText;
	}
	public void setSubscribePlatformText(String subscribePlatformText) {
		this.subscribePlatformText = subscribePlatformText;
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
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
}
