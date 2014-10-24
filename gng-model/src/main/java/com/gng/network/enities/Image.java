package com.gng.network.enities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(catalog="sns", name="Image", schema="")
@NamedQueries({
	@NamedQuery(name="Image.findUserProfileImage", query="FROM Image img WHERE img.user.id = :userId AND img.profileImage = :profileImage"),
	@NamedQuery(name="Image.getUserImagesByUserId", query="FROM Image img WHERE img.user.id = :userId"),
	@NamedQuery(name="Image.getImageByImageId", query="FROM Image img WHERE img.id = :imageId")
})
public class Image {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne(cascade=CascadeType.REFRESH)
	private Album album;
	private Date date;
	@Column(columnDefinition="LONGBLOB")
	private byte [] image;
	private String name;
	private String description;
	@ManyToOne(cascade=CascadeType.ALL)
	private Comment comment;
	private boolean profileImage;
	private String extention;
	@ManyToOne(cascade=CascadeType.ALL)
	private User user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public byte [] getImage() {
		return image;
	}
	public void setImage(byte [] image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public boolean isProfileImage() {
		return profileImage;
	}
	public void setProfileImage(boolean profileImage) {
		this.profileImage = profileImage;
	}
	public String getExtention() {
		return extention;
	}
	public void setExtention(String extention) {
		this.extention = extention;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
