package com.gng.network.forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserSignupData {
	@Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters long.")
	@Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[A-Za-z]{2,4}", message="Invalid email address")
	String username;
	@Size(min = 2, max = 250, message = "Firstname must be at least 2 characters long.")
	@Pattern(regexp="^[A-Za-z]+$", message="Invalid firstname")
	String firstname;
	@Size(min = 2, max = 250, message = "Lastname must be at least 2 characters long.")
	@Pattern(regexp="^[A-Za-z]+$", message="Invalid lastname")
	String lastname;
	@Size(min = 8, message = "The password must be at least 8 characters long")
	String password;
	@Size(min = 8, message = "Confirm password must be at least 8 characters long")
	String confirmpassword;
	
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
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	
}
