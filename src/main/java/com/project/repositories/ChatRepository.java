package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	
	
}
