package com.blogapp.blog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogapp.blog.model.AuditInformation;
import com.blogapp.blog.model.CreatePostRequest;
import com.blogapp.blog.model.UserPostsInformation;
import com.blogapp.blog.model.Posts;
import com.blogapp.blog.service.AdminService;
import com.blogapp.blog.service.AuditService;



@RestController
@RequestMapping("blog/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AuditService auditService;
	
	/**
	 * User information having details for user and related posts
	 * @param id - user id
	 * @return 
	 */
	@GetMapping("/usersposts/{userId}")
	public UserPostsInformation getUserPostDetails(@PathVariable long userId) {
		return adminService.getUserPostDetails(userId);
	}

	/**
	 * List of UserInformation containing user details and their respective posts
	 * @return 
	 */
	@GetMapping("/usersposts")
	public List<UserPostsInformation> getAllUsersPostDetails() {
		return adminService.getAllUsersPostDetails();
	}
	
	/**
	 * Return saved UserPost object
	 * @param post
	 * @return
	 */
	@PostMapping(value = "/users/createpost", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Posts createUserPost(@RequestBody CreatePostRequest post) {

		Posts newUserPost = new Posts(post.getUserId(), post.getTitle(), post.getBody());
		return adminService.createUserPost(newUserPost, post.getLoginUser());

	}

	/**
	 * Return all user posts
	 * @return 
	 */
	@GetMapping("/posts")
	public Posts[] getAllPosts() {
		return adminService.getAllPosts();
	}

	/**
	 * Return post details of passed input post id
	 * @param postId
	 * @return 
	 */
	@GetMapping("/posts/{postId}")
	public Posts getPostById(@PathVariable long postId) {
		Posts post = adminService.getPostById(postId);
		return post;
	}

	/**
	 * Return all audit related information
	 * @return
	 */
	@GetMapping("/audit")
	public List<AuditInformation> getAllAuditInfo() {
		logger.info("In getAllAuditInfo");
		return auditService.getAuditInfo();
	}

	/**
	 * Return audit information of passed input id
	 * @param id
	 * @return
	 */
	@GetMapping("/audit/{id}")
	public Optional<AuditInformation> getAuditInfoById(@PathVariable long id) {
		return auditService.getAuditInfoById(id);
	}

	/**
	 * Return audit information of passed input user id
	 * @param userid
	 * @return 
	 */
	@GetMapping("/audit/user/{userid}")
	public List<AuditInformation> getAuditInfoByUserId(@PathVariable long userid) {
		return auditService.getAuditInfoByUserId(userid);
	}

	/**
	 * Return audit information of passed input post title
	 * @param postTitle
	 * @return 
	 */
	@GetMapping("/audit/posttitle/{postTitle}")
	public List<AuditInformation> getAuditInfoByPostTitle(@PathVariable String postTitle) {
		return auditService.getAuditInfoByPostTitle(postTitle);
	}

	/**
	 * @param postBody
	 * @return - Return audit information of passed input post body
	 */
	@GetMapping("/audit/postbody/{postBody}")
	public List<AuditInformation> getAuditInfoByPostBody(@PathVariable String postBody) {
		return auditService.getAuditInfoByPostBody(postBody);
	}
}
