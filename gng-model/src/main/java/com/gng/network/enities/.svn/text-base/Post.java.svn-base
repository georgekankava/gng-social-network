package com.gng.network.enities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(catalog="sns", name="Post", schema="")
@NamedQueries({
	@NamedQuery(name="Post.getFriendsPosts", query="FROM Post p WHERE p.user.id in(:ids) order by p.postDate DESC"),
	@NamedQuery(name="Post.getFriendsActivePosts", query="FROM Post post WHERE post.user.id in(:ids) AND post.postDate > :postFromDate AND post.postDate < :postToDate ORDER BY post.postDate DESC") 
})
public class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String text;
	private String url;
	private String image;
	private Date postDate;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL)
	private List<Comment> comments;
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL)
	private List<PostLike> likes;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<PostLike> getLikes() {
		return likes;
	}
	public void setLikes(List<PostLike> likes) {
		this.likes = likes;
	}
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", text=" + text + ", url=" + url + ", image=" + image + ", postDate=" + postDate +
				", user="+ user + ", comments=" + comments + ", likes=" + likes + "]";
	}
}
