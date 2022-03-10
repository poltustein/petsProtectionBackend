package com.protectionDogs.protection.Response;

import java.util.List;

import com.protectionDogs.protection.Util.Utility;
import com.protectionDogs.protection.pojo.SavedVideo;

public class SavedVideoResponse {

	private Long count;
	private List<SavedVideo> videos;
	private String status;
	private String reason;
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<SavedVideo> getVideos() {
		return videos;
	}
	public void setVideos(List<SavedVideo> videos) {
		this.videos = videos;
	}
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
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	
}
