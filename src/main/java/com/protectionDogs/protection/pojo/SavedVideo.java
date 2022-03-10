package com.protectionDogs.protection.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SavedVideo {
	@Id
	private String savedId;
	private String videoId;
	private String userId;
	private String videoUrl;
	private Date savedOn;
	private String time;
	private String title;
	private List<Category> categories;
	private Date createdOn;
	
	public String getSavedId() {
		return savedId;
	}
	public void setSavedId(String savedId) {
		this.savedId = savedId;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public Date getSavedOn() {
		return savedOn;
	}
	public void setSavedOn(Date savedOn) {
		this.savedOn = savedOn;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
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
