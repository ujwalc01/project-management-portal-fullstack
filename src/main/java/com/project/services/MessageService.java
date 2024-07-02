package com.project.services;

import java.util.List;

import com.project.entities.Message;

public interface MessageService {
	
	Message sendMessage(Long senderId, Long projectId, String content) throws Exception;
	
	List<Message> getMessagesByProjectId(Long projectId) throws Exception;
	
}
