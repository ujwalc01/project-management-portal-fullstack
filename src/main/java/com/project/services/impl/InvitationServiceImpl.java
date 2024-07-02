package com.project.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Invitation;
import com.project.repositories.InvitationRepository;
import com.project.services.EmailService;
import com.project.services.InvitationService;

import jakarta.mail.MessageRemovedException;
import jakarta.mail.MessagingException;

@Service
public class InvitationServiceImpl implements InvitationService {

	@Autowired
	private InvitationRepository invitationRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void sendInvitation(String email, Long projectId) throws MessagingException {
		
		String invitationToken = UUID.randomUUID().toString();
		
		Invitation invitation = new Invitation();
		
		invitation.setEmail(email);
		invitation.setProjectId(projectId);
		invitation.setToken(invitationToken);

		invitationRepo.save(invitation);
		
		String invitationLink = "http://localhost:5173/accept_invitation?token="+invitationToken;
		try {
			emailService.sendEmailWithToken(email, invitationLink);
		} catch(Exception e) {
			e.printStackTrace();
			throw new MessagingException("invitation failed");
		}
	}

	@Override
	public Invitation acceptInvitation(String token, Long userId) throws Exception {
		Invitation invitation = invitationRepo.findByToken(token);
		
		if(invitation == null) {
			throw new Exception("invalid Invitation token");
		}

		return invitation;
	}

	@Override
	public String getTokenByUserMail(String userEmail) {
		Invitation invitation = invitationRepo.findByEmail(userEmail);
		return invitation.getToken();
	}

	@Override
	public void deleteToken(String token) {
		
		Invitation invitation = invitationRepo.findByToken(token);
		invitationRepo.delete(invitation);
	}

}
