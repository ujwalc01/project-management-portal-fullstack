package com.project.services;

import com.project.entities.Invitation;

import jakarta.mail.MessagingException;

public interface InvitationService {

	public void sendInvitation(String email, Long projectId) throws MessagingException;
	
	public Invitation acceptInvitation(String token, Long userId) throws Exception ;
	
	public String getTokenByUserMail(String userEmail) throws Exception;
	
	void deleteToken(String token) throws Exception;
}
