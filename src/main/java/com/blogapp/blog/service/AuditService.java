package com.blogapp.blog.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.blog.model.AuditInformation;
import com.blogapp.blog.repo.AuditInfoRepo;

@Service
public class AuditService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuditService.class);

	@Autowired
	AuditInfoRepo auditRepo;
	
	/**
	 * Save audit information
	 * @param auditInfoObj
	 * @return
	 */

	public AuditInformation saveAuditInfo(AuditInformation auditInfoObj) {
		logger.info("Saving audit information ");
		return auditRepo.save(auditInfoObj);
	}

	/**
	 * All audit info
	 * 
	 * @return List of Audit Information object
	 */
	public List<AuditInformation> getAuditInfo() {
		return auditRepo.findAll();
	}

	/**
	 * @param id - audit id
	 * @return - Return audit information of provided input id
	 */
	public Optional<AuditInformation> getAuditInfoById(long id) {
		return auditRepo.findById(id);
	}

	/**
	 * @param userId - user id
	 * @return - Return list of audit information object
	 */
	public List<AuditInformation> getAuditInfoByUserId(long userId) {
		return auditRepo.findByUserId(userId);
	}

	/**
	 * @param postTitle
	 * @return - Return audit information by post title
	 */
	public List<AuditInformation> getAuditInfoByPostTitle(String postTitle) {
		return auditRepo.findByPostTitle(postTitle);
	}

	/**
	 * 
	 * @param postBody
	 * @return - Return audit information by post body
	 */
	public List<AuditInformation> getAuditInfoByPostBody(String postBody) {
		return auditRepo.findByPostBody(postBody);
	}

}
