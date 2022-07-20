package com.blogapp.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class BlogPostApplication {

	private static final Logger logger = LoggerFactory.getLogger(BlogPostApplication.class);

	@Bean
	public RestTemplate getRestemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		logger.info("Starting blog post application..........");
		SpringApplication.run(BlogPostApplication.class, args);
	}

}
