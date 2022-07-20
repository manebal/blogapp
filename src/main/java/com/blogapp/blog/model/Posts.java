package com.blogapp.blog.model;

import java.io.Serializable;

public class Posts implements Serializable {

	private static final long serialVersionUID = 1L;

	private long userId;
	private long id;
	private String title;
	private String body;

	public Posts() {

	}

	public Posts(long userId, String title, String body) {
		this.userId = userId;
		this.title = title;
		this.body = body;
	}
 
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

}