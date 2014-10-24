package com.gng.network.enities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.gng.network.enums.PostLikedStatus;

@Entity
@Table(catalog="sns", name="POST_LIKE")
@NamedQueries({
	@NamedQuery(name="PostLike.findPostLikeByUserAndPostIds", query="SELECT pl FROM PostLike pl WHERE pl.user.id = :userId AND pl.post.id = :postId")
	})
public class PostLike {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Date likeDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Post post;
	
	@Enumerated(EnumType.STRING)
	private PostLikedStatus likeStatus;
	
	// default constructor
	public PostLike() {}
	
	public PostLike(User user, Post post) {
		this.user = user;
		this.post = post;
		this.likeDate = new Date();
		likeStatus = PostLikedStatus.LIKED;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(Date likeDate) {
		this.likeDate = likeDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public PostLikedStatus getLikeStatus() {
		return likeStatus;
	}

	public void setLikeStatus(PostLikedStatus likeStatus) {
		this.likeStatus = likeStatus;
	}

	@Override
	public String toString() {
		return "PostLike [id=" + id + ", likeDate=" + likeDate + ", user=" + user + ", post=" + post + ", likeStatus=" + likeStatus.name() + "]";
	}
	
	
}
