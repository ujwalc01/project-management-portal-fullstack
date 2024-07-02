package com.project.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Chat;
import com.project.entities.Project;
import com.project.entities.Users;
import com.project.repositories.ProjectRepository;
import com.project.services.ChatService;
import com.project.services.ProjectService;
import com.project.services.UserService;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatService chatService;
	
	@Override
	public Project createProject(Project project, Users user) throws Exception {
		Project createdProject = new Project();
		
		createdProject.setOwner(user);
		createdProject.setTags(project.getTags());
		createdProject.setName(project.getName());
		createdProject.setCategory(project.getCategory());
		createdProject.setDescription(project.getDescription());
		createdProject.getTeam().add(user);
		
		Project savedProject = projectRepo.save(createdProject);
		
		Chat chat = new Chat();
		chat.setProject(savedProject);
		
		Chat projectChat = chatService.createChat(chat);
		savedProject.setChat(projectChat);
		
		return savedProject;
	}

	@Override
	public List<Project> getProjectByTeam(Users user, String category, String tag) throws Exception {
		
		List<Project> projects = projectRepo.findByTeamContainingOrOwner(user, user);
		
		if(category != null) {
			projects = projects.stream().filter(project -> project.getCategory().equals(category))
					.collect(Collectors.toList());
		}
		
		if(tag != null) {
			projects = projects.stream().filter(project -> project.getTags().contains(tag))
					.collect(Collectors.toList());
		}
		
		return projects;
	}

	@Override
	public Project getProjectById(Long projectId) throws Exception {
		
		Optional<Project> optionalProject = projectRepo.findById(projectId);
		if(optionalProject.isEmpty()) {
			throw new Exception("Project not found");
		}
		return optionalProject.get();
	}

	@Override
	public void deleteProject(Long projectId, Long userId) throws Exception {
		getProjectById(projectId);
//		userService.findUserById(userId);
		projectRepo.deleteById(projectId);

	}

	@Override
	public Project updateProject(Project updatedProject, Long id) throws Exception {
		
		Project project = getProjectById(id);
		project.setName(updatedProject.getDescription());
		project.setDescription(updatedProject.getDescription());
		project.setTags(updatedProject.getTags());
		
		return projectRepo.save(project);
	}

	@Override
	public void addUserToProject(Long projectId, Long userId) throws Exception {
		
		Project project = getProjectById(projectId);
		
		Users user = userService.findUserById(userId);
		
		if(!project.getTeam().contains(user)) {
			project.getChat().getUsers().add(user);
			project.getTeam().add(user);
			
		}
		projectRepo.save(project);
	}

	@Override
	public void removeUserFromProject(Long projectId, Long userId) throws Exception {
		
		Project project = getProjectById(projectId);
		
		Users user = userService.findUserById(userId);
		
		if(project.getTeam().contains(user)) {
			project.getChat().getUsers().remove(user);
			project.getTeam().remove(user);
			
		}
		projectRepo.save(project);

	}

	@Override
	public Chat getChatByProjectId(Long projectId) throws Exception {
		
		Project project = getProjectById(projectId);
		
		return project.getChat();
		
		
	}

	@Override
	public List<Project> searchProject(String keyword, Users user) {
		
		return projectRepo.findByNameContainingAndTeamContains(keyword, user);
	}

}
