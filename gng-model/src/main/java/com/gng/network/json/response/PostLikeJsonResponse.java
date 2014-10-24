package com.gng.network.json.response;

public class PostLikeJsonResponse {
	private Integer postLikeId;
	private Integer userId;
	private Integer postId;
	private String postLikeText;
	private Integer numberOfLikes;
	
	public Integer getPostLikeId() {
		return postLikeId;
	}
	public void setPostLikeId(Integer postLikeId) {
		this.postLikeId = postLikeId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getPostLikeText() {
		return postLikeText;
	}
	public void setPostLikeText(String postLikeText) {
		this.postLikeText = postLikeText;
	}
	
	public Integer getNumberOfLikes() {
		return numberOfLikes;
	}
	public void setNumberOfLikes(Integer numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}
	public String toString() {
        return "{ \"postLikeId\" : \"" + postLikeId + "\", \"userId\" : \"" + userId + "\", \"postId\" : \"" + postId + 
        		"\", \"postLikeText\" : \"" + postLikeText +  "\", \"numberOfLikes\" : \"" + numberOfLikes +"\"}";
    }
}
