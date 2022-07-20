package com.blogapp.blog.model;

import java.util.List;

public class UserPostsInformation {
	
	private UserDetails userDetails;
	private List<Posts> posts; 
	
	public UserPostsInformation() {
	}
	
	public UserPostsInformation(UserDetails userDetails, List<Posts> posts) {
		this.userDetails = userDetails;
		this.posts = posts;
	}
	
	public UserDetails getUserDetails() {
		return userDetails;
	}

	public List<Posts> getPosts() {
		return posts;
	}
	
	
	
}
