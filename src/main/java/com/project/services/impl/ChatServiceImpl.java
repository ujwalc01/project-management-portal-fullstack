package com.project.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Chat;
import com.project.repositories.ChatRepository;
import com.project.services.ChatService;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private ChatRepository chatRepo;
	
	@Override
	public Chat createChat(Chat chat) {
		
		return chatRepo.save(chat);
	}
	
	
}
