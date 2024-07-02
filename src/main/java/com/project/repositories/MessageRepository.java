package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message>findByChatIdOrderByCreatedAtAsc(Long chatId) throws Exception;
	
}
