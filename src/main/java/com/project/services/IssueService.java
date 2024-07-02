package com.project.services;

import java.util.List;
import java.util.Optional;

import com.project.entities.Issue;
import com.project.entities.Users;
import com.project.requests.IssueRequest;

public interface IssueService {
	
	Issue getIssueById(Long issueId) throws Exception;
	
	List<Issue> getIssueByProjectId(Long projectId) throws Exception;
	
	Issue createIssue(IssueRequest issue, Users user) throws Exception;
	
//	Optional<Issue> updateIssue(Long issueId, IssueRequest updatedIssue, Long userId) throws Exception;
	
	void deleteIssue(Long issueId, Long userId) throws Exception;
	
	Issue addUserToIssue(Long issueId, Long userId) throws Exception;
	
	Issue updateStatus(Long issueId, String status) throws Exception;
	
	
//	List<Issue> getIssuesByIssigneeId(Long assigneeId) throws Exception;
	
//	List<Issue> searchIssue(String title, String status, String priority, Long assigneeId) throws Exception;
	
//	List<Users> getAssigneeForIssue(Long issueId) throws Exception;
	
	
	
}
