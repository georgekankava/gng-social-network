package com.gng.network.json.response;

public class PostResponseJson {
	private Integer postId;
	private String postContent;
	private long date;

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	
	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	
	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}
	
	public String toString() {
        return "{ \"postId\" : \"" + postId + "\", \"postContent\" : \"" + postContent + "\", \"date\" : \"" + date + "\"}";
    }
}
