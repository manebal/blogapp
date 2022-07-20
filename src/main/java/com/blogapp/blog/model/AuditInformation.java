package com.blogapp.blog.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AuditInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long userId;

	private long userPostId;

	private String postTitle;

	private String postBody;

	private String status;

	private String errDesc;

	private String createdBy;

	private Timestamp createdDt;

	public AuditInformation() {
		super();
	}

	public AuditInformation(long userId, long userPostId, String postTitle, String postBody, String status,
			String errDesc, String createdBy, Timestamp createdDt) {
		super();
		this.userId = userId;
		this.userPostId = userPostId;
		this.postTitle = postTitle;
		this.postBody = postBody;
		this.status = status;
		this.errDesc = errDesc;
		this.createdBy = createdBy;
		this.createdDt = createdDt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserPostId() {
		return userPostId;
	}

	public void setUserPostId(long userPostId) {
		this.userPostId = userPostId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostBody() {
		return postBody;
	}

	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDt == null) ? 0 : createdDt.hashCode());
		result = prime * result + ((errDesc == null) ? 0 : errDesc.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((postBody == null) ? 0 : postBody.hashCode());
		result = prime * result + ((postTitle == null) ? 0 : postTitle.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result + (int) (userPostId ^ (userPostId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditInformation other = (AuditInformation) obj;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDt == null) {
			if (other.createdDt != null)
				return false;
		} else if (!createdDt.equals(other.createdDt))
			return false;
		if (errDesc == null) {
			if (other.errDesc != null)
				return false;
		} else if (!errDesc.equals(other.errDesc))
			return false;
		if (id != other.id)
			return false;
		if (postBody == null) {
			if (other.postBody != null)
				return false;
		} else if (!postBody.equals(other.postBody))
			return false;
		if (postTitle == null) {
			if (other.postTitle != null)
				return false;
		} else if (!postTitle.equals(other.postTitle))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (userId != other.userId)
			return false;
		if (userPostId != other.userPostId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuditInfo [id=" + id + ", userId=" + userId + ", userPostId=" + userPostId + ", postTitle=" + postTitle
				+ ", postBody=" + postBody + ", status=" + status + ", errDesc=" + errDesc + ", createdBy=" + createdBy
				+ ", createdDt=" + createdDt + "]";
	}

}
