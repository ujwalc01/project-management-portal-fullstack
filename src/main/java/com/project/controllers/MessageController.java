package com.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.entities.Chat;
import com.project.entities.Message;
import com.project.entities.Users;
import com.project.requests.CreateMessageRequest;
import com.project.services.MessageService;
import com.project.services.ProjectService;
import com.project.services.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@PostMapping("/send")
	public ResponseEntity<Message> sendMessage(
			@RequestBody CreateMessageRequest request
			) throws Exception {
	
		Users user = userService.findUserById(request.getSenderId());
		Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
		
		if(chats == null) throw new Exception("Chats not found!");
		
		Message sentMessage = messageService.sendMessage(
				request.getSenderId(),request.getProjectId(), request.getContent());
		
		return ResponseEntity.ok(sentMessage);
	}
	
	@GetMapping("/chat/{projectId}")
	public ResponseEntity<List<Message>> getMessagesByChatId(
			@PathVariable Long projectId
			) throws Exception {
		
		List<Message> message = messageService.getMessagesByProjectId(projectId);
		
		return ResponseEntity.ok(message);
	}
	
}
