package com.gng.network.enities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CollectionId;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(catalog="sns", name="User")
@NamedQueries({
	@NamedQuery(name="User.findUserById", query="SELECT user FROM User user WHERE user.id= :id"),
	@NamedQuery(name="User.findUserByUsername", query="SELECT user FROM User user WHERE user.username = :username"),
	@NamedQuery(name="User.findUserByFirstname", query="SELECT user FROM User user WHERE user.firstname = :firstname"),
	@NamedQuery(name="User.findUserByLastname", query="SELECT user FROM User user WHERE user.lastname = :lastname"),
	@NamedQuery(name="User.findUserByFullname", query="SELECT user FROM User user WHERE user.fullname LIKE :fullname"),
	@NamedQuery(name="User.getUsersMessagedWith", query = "SELECT message.userTo FROM Message message WHERE message.userFrom.id = :id GROUP BY message.userTo ORDER BY MAX(message.time) DESC"),
	@NamedQuery(name="User.findUserBySearchString", query="SELECT user FROM User user WHERE user.firstname LIKE :searchString OR user.lastname LIKE :searchString OR user.fullname LIKE :searchString OR user.username LIKE :searchString")
	})
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String firstname;
	private String lastname;
	private String fullname;
	private String password;
	private String profileImage;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_privacy_id", referencedColumnName = "id")
	private UserPrivacy userPrivacy;
	@Transient
	private boolean isOnline;
	@ManyToMany(fetch=FetchType.LAZY)
	@CollectionId(
		columns = @Column(name = "id"),
		type = @org.hibernate.annotations.Type(type = "long"),
		generator = "identity"
	)
	@JoinTable(
		name = "FRIENDS_TABLE",
		joinColumns = {@JoinColumn(name = "USER_ID")},
		inverseJoinColumns = {@JoinColumn(name = "FRIEND_USER_ID")},
		uniqueConstraints = @UniqueConstraint(columnNames={"USER_ID", "FRIEND_USER_ID"})
	)
	@JsonIgnore
	private List<User> friends;
	
	@OneToMany(mappedBy="userTo")
	@JsonIgnore
	private List<FriendRequest> friendRequests;
	
	@OneToOne(mappedBy="user", cascade={CascadeType.ALL})
	private Roles role; // the roles of this user
	
	@OneToMany(mappedBy="user", cascade=CascadeType.PERSIST)
	@JsonIgnore
	private List<Post> posts;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private List<Address> addresses;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private List<PostLike> postLikes; 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	
	public Roles getRole() {
		return role;
	}
	
	public void setRole(Roles role) {
		this.role = role;
	}
	
	public List<FriendRequest> getFriendRequests() {
		return friendRequests;
	}
	public void setFriendRequests(List<FriendRequest> friendRequests) {
		this.friendRequests = friendRequests;
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	public List<PostLike> getPostLikes() {
		return postLikes;
	}
	public void setPostLikes(List<PostLike> postLikes) {
		this.postLikes = postLikes;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstname="
				+ firstname + ", lastname=" + lastname + ", fullname="
				+ fullname + ", password=[protected], profileImage="
				+ profileImage + ", users=" + friends + "]";
	}

	public UserPrivacy getUserPrivacy() {
		return userPrivacy;
	}

	public void setUserPrivacy(UserPrivacy userPrivacy) {
		this.userPrivacy = userPrivacy;
	}
}
