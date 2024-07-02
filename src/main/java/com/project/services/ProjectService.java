package com.project.services;

import java.util.List;

import com.project.entities.Chat;
import com.project.entities.Project;
import com.project.entities.Users;

public interface ProjectService {
	
	Project createProject(Project project, Users user) throws Exception;
	
	List<Project> getProjectByTeam(Users user, String category, String tag) throws Exception;
	
	Project getProjectById(Long projectId) throws Exception;
	
	void deleteProject(Long projectId, Long userId) throws Exception;
	
	Project updateProject(Project updatedProject, Long id) throws Exception;
	
	void addUserToProject(Long projectId, Long userId) throws Exception;
	
	void removeUserFromProject(Long projectId, Long userId) throws Exception;
	
	Chat getChatByProjectId(Long projectId) throws Exception;
	
	List<Project> searchProject(String keyword, Users user);
}
