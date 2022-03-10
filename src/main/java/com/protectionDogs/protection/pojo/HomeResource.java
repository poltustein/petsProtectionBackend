package com.protectionDogs.protection.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.protectionDogs.protection.Util.ResourceTypes;
@Document
public class HomeResource {
	@Id
	private String resourceId;
	private ResourceTypes resourceType;
	private String title;
	private List<Category> categories;
	private Date createdOn;
	private String url;
	private String time;
	private Boolean isLiked = false;
	private String thumbUrl;
	
	public ResourceTypes getResourceType() {
		return resourceType;
	}
	public void setResourceType(ResourceTypes resourceType) {
		this.resourceType = resourceType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public Boolean getIsLiked() {
		return isLiked;
	}
	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	
}