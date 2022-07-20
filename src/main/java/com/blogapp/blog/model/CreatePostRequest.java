package com.blogapp.blog.model;

import java.io.Serializable;

public class CreatePostRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private long userId;
	private long id;
	private String title;
	private String body;
	private String loginUser;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	} 

}