package com.project.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Chat;
import com.project.entities.Message;
import com.project.entities.Users;
import com.project.repositories.MessageRepository;
import com.project.repositories.UsersRepository;
import com.project.services.MessageService;
import com.project.services.ProjectService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepo;
	
	@Autowired
	private UsersRepository userRepo;
	
	@Autowired
	private ProjectService projectService;
	
	
	@Override
	public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
		
		Users sender = userRepo.findById(senderId)
				.orElseThrow(() -> new Exception("User not found with id : " + senderId));
		
		Chat chat = projectService.getProjectById(projectId).getChat();
		
		Message message = new Message();
		message.setContent(content);
		message.setSender(sender);
		message.setCreatedAt(LocalDateTime.now());
		message.setChat(chat);
		Message savedMessage = messageRepo.save(message);
		
		chat.getMessages().add(savedMessage);
		return savedMessage;
	}

	@Override
	public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
		
		Chat chat = projectService.getChatByProjectId(projectId);
		
		List<Message> findByChatIdOrderByCreatedAtAsc = messageRepo.findByChatIdOrderByCreatedAtAsc(chat.getId());
		
		return findByChatIdOrderByCreatedAtAsc;
	}

}
