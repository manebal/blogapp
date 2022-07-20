package com.blogapp.blog.controller;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.blogapp.blog.model.AuditInformation;
import com.blogapp.blog.model.CreatePostRequest;
import com.blogapp.blog.model.Posts;
import com.blogapp.blog.model.UserDetails;
import com.blogapp.blog.model.UserPostsInformation;
import com.blogapp.blog.service.AdminService;
import com.blogapp.blog.service.AuditService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	AdminController adminController;

	@Mock
	private AdminService adminService;

	@Mock
	private AuditService auditService;

	private UserDetails userDetailsObj;
	private List<UserDetails> userObjList;

	private UserPostsInformation userInformationObj1;
	private UserPostsInformation userInformationObj2;
	private List<UserPostsInformation> wrapperObjList;

	private Posts userPostsObj1;
	private Posts userPostsObj2;
	private List<Posts> userPostsObjList;

	private AuditInformation auditInfoObj1;
	private AuditInformation auditInfoObj2;
	private List<AuditInformation> auditInfoObjList;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		// Initialise array lists
		userObjList = new ArrayList<UserDetails>();
		wrapperObjList = new ArrayList<UserPostsInformation>();
		userPostsObjList = new ArrayList<Posts>();
		auditInfoObjList = new ArrayList<AuditInformation>();

		// Mapper object
		ObjectMapper obj = new ObjectMapper();

		// populate data objects and lists
		userDetailsObj = obj.readValue(
				new String(Files.readAllBytes(Paths.get("src/test/resources/UserDetailsData.json"))),
				UserDetails.class);
		userObjList.add(userDetailsObj);

		userInformationObj1 = obj.readValue(
				new String(Files.readAllBytes(Paths.get("src/test/resources/UsersPostsData1.json"))),
				UserPostsInformation.class);
		userInformationObj2 = obj.readValue(
				new String(Files.readAllBytes(Paths.get("src/test/resources/UsersPostsData2.json"))),
				UserPostsInformation.class);
		wrapperObjList.add(userInformationObj1);
		wrapperObjList.add(userInformationObj2);

		userPostsObj1 = obj.readValue(new String(Files.readAllBytes(Paths.get("src/test/resources/PostsData1.json"))),
				Posts.class);
		userPostsObj2 = obj.readValue(new String(Files.readAllBytes(Paths.get("src/test/resources/PostsData2.json"))),
				Posts.class);
		userPostsObjList.add(userPostsObj1);
		userPostsObjList.add(userPostsObj2);

		auditInfoObj1 = obj.readValue(
				new String(Files.readAllBytes(Paths.get("src/test/resources/AuditInfoData1.json"))),
				AuditInformation.class);
		auditInfoObj2 = obj.readValue(
				new String(Files.readAllBytes(Paths.get("src/test/resources/AuditInfoData2.json"))),
				AuditInformation.class);
		auditInfoObjList.add(auditInfoObj1);
		auditInfoObjList.add(auditInfoObj2);

		// verify data object and list creation
		assertNotNull("userDetailsObj is null couldn't read from file", userDetailsObj);
		assertNotNull("wrapperObj1 is null couldn't read from file", userInformationObj1);
		assertNotNull("wrapperObj2 is null couldn't read from file", userInformationObj2);
		assertNotNull("userPostsObj1 is null couldn't read from file", userPostsObj1);
		assertNotNull("userPostsObj2 is null couldn't read from file", userPostsObj2);
		assertNotNull("auditInfoObj1 is null couldn't read from file", auditInfoObj1);
		assertNotNull("auditInfoObj2 is null couldn't read from file", auditInfoObj2);

		// verify service and controller
		assertNotNull("adminController is null", adminController);
		assertNotNull("adminService is null", adminService);

		// Inject mock objects
		mockMvc = standaloneSetup(adminController).build();
		assertNotNull("mockMvc is null", mockMvc);
	}

	@Test
	public void testGetUserDetailsWithPosts() throws Exception {

		long userId = 1L;
		Mockito.when(adminService.getUserPostDetails(userId)).thenReturn(userInformationObj1);

		mockMvc.perform(MockMvcRequestBuilders.get("/blog/admin/usersposts/1")).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.userDetails.id", is(1))).andExpect(jsonPath("$.posts[0].userId", is(1)))
				.andExpect(jsonPath("$.posts[0].title", is("post Title Name")));
	}

	@Test
	public void testGetAllUserDetailsWithPosts() throws Exception {

		Mockito.when(adminService.getAllUsersPostDetails()).thenReturn(wrapperObjList);

		mockMvc.perform(MockMvcRequestBuilders.get("/blog/admin/usersposts")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[0].userDetails.id", is(1))).andExpect(jsonPath("$.[1].userDetails.id", is(2)))
				.andExpect(jsonPath("$.[0].posts[0].userId", is(1))).andExpect(jsonPath("$.[1].posts[1].userId", is(2)))
				.andExpect(jsonPath("$.[1].posts[0].title", is("user two title 7")));
	}

	@Test
	public void testGetAllPosts() throws Exception {
		Mockito.when(adminService.getAllPosts()).thenReturn(userPostsObjList.toArray((new Posts[0])));

		mockMvc.perform(MockMvcRequestBuilders.get("/blog/admin/posts")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testGetPostById() throws Exception {
		long postId = 1;
		Mockito.when(adminService.getPostById(postId)).thenReturn(userPostsObj1);

		mockMvc.perform(MockMvcRequestBuilders.get("/blog/admin/posts/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.userId", is(3)));
	}

	@Test
	public void testCreateUserPost() throws Exception {

		Mockito.when(auditService.getAuditInfo()).thenReturn(auditInfoObjList);

		CreatePostRequest post = new CreatePostRequest();
		post.setUserId(1);
		post.setTitle("TestTitle");
		post.setBody("TestBody");
		post.setLoginUser("TestUser");

		String body = (new ObjectMapper()).valueToTree(post).toString();

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/blog/admin/users/createpost")
				.accept(MediaType.APPLICATION_JSON).content(body).contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void testGetAuditInfo() throws Exception {

		Mockito.when(auditService.getAuditInfo()).thenReturn(auditInfoObjList);

		mockMvc.perform(MockMvcRequestBuilders.get("/blog/admin/audit")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[0].id", is(1))).andExpect(jsonPath("$.[0].userId", is(11)))
				.andExpect(jsonPath("$.[1].id", is(2))).andExpect(jsonPath("$.[1].postTitle", is("title13")));

	}

}
