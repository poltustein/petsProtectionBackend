package com.protectionDogs.protection.Request;

import javax.validation.constraints.NotBlank;

import com.protectionDogs.protection.Util.Utility;

public class SaveVideoRequest {
	
	@NotBlank
	private String videoId;
	@NotBlank
	private String videoUrl;

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	@Override
	public String toString() {
		return Utility.gson.toJson(this);
	}
	

}
