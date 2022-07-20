package com.blogapp.blog.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.blog.model.AuditInformation;

public interface AuditInfoRepo extends JpaRepository<AuditInformation, Long> {
	
	List<AuditInformation> findByUserId(long userId);
	List<AuditInformation> findByPostTitle(String title);
	List<AuditInformation> findByPostBody(String body);
	
}
