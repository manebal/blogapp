package com.blogapp.blog.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.blogapp.blog.model.AuditInformation;
import com.blogapp.blog.model.Posts;
import com.blogapp.blog.model.UserDetails;
import com.blogapp.blog.model.UserPostsInformation;

@Service
public class AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

	public static final String API_POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
	public static final String API_POSTS_URL_Param_ID = "https://jsonplaceholder.typicode.com/posts/{pid}";

	public static final String API_USERS_URL = "https://jsonplaceholder.typicode.com/users";
	public static final String API_USERS_URL_PARAM_ID = "https://jsonplaceholder.typicode.com/users/{uid}";
	public static final String API_USERS_POSTS_URL_PARAM_ID = "https://jsonplaceholder.typicode.com/users/{uid}/posts";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	AuditService auditService;

	/**
	 * Returns all posts of provided userid as input parameter
	 * 
	 * @param userId - user id
	 * @return Wrapper for User Details and related posts
	 */
	public UserPostsInformation getUserPostDetails(long userId) {
		UserPostsInformation wrapper = null;
		// fetch response in a string object
		UserDetails userDetails = restTemplate.getForObject(API_USERS_URL_PARAM_ID, UserDetails.class, userId);

		Posts[] posts = restTemplate.getForObject(API_USERS_POSTS_URL_PARAM_ID, Posts[].class, userId);

		if (null != userDetails && null != posts) {
			wrapper = new UserPostsInformation(userDetails, Arrays.asList(posts));
		}

		return wrapper;
	}

	/**
	 * Return all user details and their respective posts This method gets all user
	 * Details and posts separately and then merge together based on matching user
	 * id
	 * 
	 * @return List of Wrapper objects contacting user and their respective posts
	 */
	public List<UserPostsInformation> getAllUsersPostDetails() {

		// fetch response in a string object UserDetails[] userDetails =
		UserDetails[] userDetails = restTemplate.getForObject(API_USERS_URL, UserDetails[].class);
		Posts[] userPosts = restTemplate.getForObject(API_POSTS_URL, Posts[].class);

		Map<Long, UserDetails> usersByUserId = Arrays.stream(userDetails)
				.collect(Collectors.toMap(UserDetails::getId, Function.identity()));

		Map<Long, List<Posts>> postsByUserId = Arrays.stream(userPosts)
				.collect(Collectors.groupingBy(Posts::getUserId));

		return postsByUserId.entrySet().stream()
				.map(e -> new UserPostsInformation(usersByUserId.get(e.getKey()), e.getValue()))
				.collect(Collectors.toList());
	}

	/**
	 * Return all posts for all users
	 * 
	 * @return Array of UserPosts object
	 */
	public Posts[] getAllPosts() {
		// fetch response in a string object
		return restTemplate.getForObject(API_POSTS_URL, Posts[].class);
	}

	/**
	 * All posts for the particular user
	 * 
	 * @param id - post id
	 * @return UserPosts object
	 */
	public Posts getPostById(long postId) {
		return restTemplate.getForObject(API_POSTS_URL_Param_ID, Posts.class, postId);
	}

	/**
	 * Create post on behalf of admin, and populate audit details
	 * 
	 * @param newUserPost - Object of new Post
	 * @param loginUser   - Logged in Admin user
	 * @return - Saved Post object
	 */
	public Posts createUserPost(Posts newUserPost, String loginUser) {
		Posts createdUserPostObj = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		// build the request
		HttpEntity<Posts> entity = new HttpEntity<>(newUserPost, headers);

		try {
			createdUserPostObj = restTemplate.postForObject(API_POSTS_URL, entity, Posts.class);

			if (null != createdUserPostObj) {
				saveAuditInfo(createdUserPostObj, loginUser, "success", null);
			} else {
				saveAuditInfo(newUserPost, loginUser, "failed", "Unable to create a new post");
			}

		} catch (RestClientException rce) {
			saveAuditInfo(newUserPost, loginUser, "failed", rce.getMessage());
			logger.error("Error occured while creating userpost", rce);
			throw rce;
		}

		return createdUserPostObj;
	}

	private AuditInformation saveAuditInfo(Posts userPostObj, String loginUser, String status, String errDesc) {

		AuditInformation auditInfoObj = new AuditInformation(userPostObj.getUserId(), userPostObj.getId(),
				userPostObj.getTitle(), userPostObj.getBody(), status, errDesc, loginUser,
				new Timestamp(new Date().getTime()));

		return auditService.saveAuditInfo(auditInfoObj);
	}

}
