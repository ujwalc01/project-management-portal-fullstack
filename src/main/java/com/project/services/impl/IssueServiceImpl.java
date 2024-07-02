package com.project.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Issue;
import com.project.entities.Project;
import com.project.entities.Users;
import com.project.repositories.IssueRepository;
import com.project.requests.IssueRequest;
import com.project.services.IssueService;
import com.project.services.ProjectService;
import com.project.services.UserService;

@Service
public class IssueServiceImpl implements IssueService {
	
	@Autowired
	private IssueRepository issueRepo;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public Issue getIssueById(Long issueId) throws Exception {
		
		Optional<Issue> issue = issueRepo.findById(issueId);
		
		if(issue.isPresent()) {
			return issue.get();
		}
		throw new Exception("No issues found with issue Id : " + issueId);
	}

	@Override
	public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
		
		return issueRepo.findByProjectId(projectId);
		
	}

	@Override
	public Issue createIssue(IssueRequest issueRequest, Users user) throws Exception {
		
		Project project = projectService.getProjectById(issueRequest.getProjectId());
		
		Issue issue = new Issue();
		issue.setTitle(issueRequest.getTitle());
		issue.setDescription(issueRequest.getDescription());
		issue.setDueDate(issueRequest.getDueDate());
		issue.setStatus(issueRequest.getStatus());
		issue.setProjectID(issueRequest.getProjectId());
		issue.setPriority(issueRequest.getPriority());
		
		issue.setProject(project);
		
		return issueRepo.save(issue);
		
	}

	@Override
	public void deleteIssue(Long issueId, Long userId) throws Exception {
		getIssueById(issueId);
		issueRepo.deleteById(issueId);
	}

	@Override
	public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
		
		Users user = userService.findUserById(userId);
		Issue issue = getIssueById(issueId); 
		
		issue.setAssignee(user);
		
		return issueRepo.save(issue);
	}

	@Override
	public Issue updateStatus(Long issueId, String status) throws Exception {
		
		Issue issue = getIssueById(issueId);
		
		issue.setStatus(status);
		
		return issueRepo.save(issue);
	}

}
