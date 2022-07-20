package com.blogapp.blog.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import com.blogapp.blog.model.AuditInformation;
import com.blogapp.blog.model.Posts;
import com.blogapp.blog.model.UserDetails;
import com.blogapp.blog.model.UserPostsInformation;
import com.blogapp.blog.repo.AuditInfoRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminServiceTest {


	@Mock
	RestTemplate restTemplate;

	@Mock
	AuditInfoRepo auditRepo;

	@InjectMocks
	private AdminService adminService;

	@Mock
	private AuditService auditService;

	private UserDetails userDetailsObj;
	private UserDetails[] userDetailsArr;

	private UserPostsInformation userInfoObj1;
	private List<UserPostsInformation> userInfoObjList;

	private Posts userPostsObj1;
	private Posts[] userPostsObjArr;

	private AuditInformation auditInfoObj1;
	private List<AuditInformation> auditInfoObjList;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		userInfoObjList = new ArrayList<UserPostsInformation>();

		// Mapper object
		ObjectMapper obj = new ObjectMapper();

		userDetailsObj = obj.readValue(
				new String(Files.readAllBytes(Paths.get("src/test/resources/UserDetailsData.json"))),
				UserDetails.class);
		userDetailsArr = new UserDetails[1];
		userDetailsArr[0] = userDetailsObj;

		userInfoObj1 = obj.readValue(
				new String(Files.readAllBytes(Paths.get("src/test/resources/UsersPostsData1.json"))),
				UserPostsInformation.class);
		userInfoObjList = new ArrayList<UserPostsInformation>();
		userInfoObjList.add(userInfoObj1);

		userPostsObj1 = obj.readValue(new String(Files.readAllBytes(Paths.get("src/test/resources/PostsData1.json"))),
				Posts.class);
		userPostsObjArr = new Posts[1];
		userPostsObjArr[0] = userPostsObj1;

		auditInfoObj1 = obj.readValue(
				new String(Files.readAllBytes(Paths.get("src/test/resources/AuditInfoData1.json"))),
				AuditInformation.class);
		auditInfoObjList = new ArrayList<AuditInformation>();
		auditInfoObjList.add(auditInfoObj1);

		// verify service and other mock objects
		assertNotNull("adminService is null", adminService);
		assertNotNull("restTemplate is null", restTemplate);
		assertNotNull("auditRepo is null", auditRepo);
	}

	@Test
	public void testGetAllPosts() {
		Mockito.when(restTemplate.getForObject(AdminService.API_POSTS_URL, Posts[].class)).thenReturn(userPostsObjArr);

		Posts[] userPostsArr = adminService.getAllPosts();
		assertNotNull("userPosts are null", userPostsArr);
		assertTrue(userPostsArr.length == 1);
	}

	@Test
	public void testGetAllUsersPostDetails() {
		Mockito.when(restTemplate.getForObject(AdminService.API_USERS_URL, UserDetails[].class))
				.thenReturn(userDetailsArr);
		Mockito.when(restTemplate.getForObject(AdminService.API_POSTS_URL, Posts[].class)).thenReturn(userPostsObjArr);

		List<UserPostsInformation> wrapperObjList = adminService.getAllUsersPostDetails();
		assertNotNull("getAllUserDetailsWithPosts is null", wrapperObjList);
	}

	@Test
	public void testGetAuditInfo() {
		Mockito.when(auditRepo.findAll()).thenReturn(auditInfoObjList);

		List<AuditInformation> aduitInfoList = auditService.getAuditInfo();
		assertNotNull("AuditInfo is null", aduitInfoList);
	}

	@Test
	public void testCreateUserPost() {

		ArgumentCaptor<AuditInformation> captor = ArgumentCaptor.forClass(AuditInformation.class);

		Mockito.when(restTemplate.postForObject(AdminService.API_POSTS_URL, HttpEntity.class, Posts.class))
				.thenReturn(userPostsObj1);

		Posts createdPostObj = adminService.createUserPost(userPostsObj1, "admin");

		Mockito.verify(auditService, Mockito.times(1)).saveAuditInfo(captor.capture());
		assertTrue(null == createdPostObj);
		AuditInformation captured = captor.getValue();

		Assertions.assertThat(captured.getUserPostId()).isEqualTo(1L);
		Assertions.assertThat(captured.getUserId()).isEqualTo(3L);
		Assertions.assertThat(captured.getPostTitle()).isEqualTo("post Title Name");
		Assertions.assertThat(captured.getPostBody()).isEqualTo("Post body");
		Assertions.assertThat(captured.getCreatedBy()).isEqualTo("admin");

	}

}
