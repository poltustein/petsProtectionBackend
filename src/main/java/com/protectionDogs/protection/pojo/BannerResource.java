package com.protectionDogs.protection.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.protectionDogs.protection.Util.ResourceTypes;

@Document
public class BannerResource {
	
	@Id
	private String bannerId;
	private ResourceTypes resources;
	private String description;
	private String url;
	private String thumbUrl;
	private String title;
	private Date createdOn;
	
	public ResourceTypes getResources() {
		return resources;
	}
	public void setResources(ResourceTypes resources) {
		this.resources = resources;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public String getBannerId() {
		return bannerId;
	}
	public void setBannerId(String bannerId) {
		this.bannerId = bannerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
}
