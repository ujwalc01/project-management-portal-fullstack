package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long>{
	
	Invitation findByToken(String token);
	
	Invitation findByEmail(String userEmail);
	
}
