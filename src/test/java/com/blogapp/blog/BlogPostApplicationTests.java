package com.blogapp.blog;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blogapp.blog.controller.AdminController;

@SpringBootTest
class BlogPostApplicationTests {

	@Autowired
	private AdminController adminController;
	
	@Test
	public void contextLoads() {
		assertNotNull("Application unable to initialised.",adminController);
	}
}
