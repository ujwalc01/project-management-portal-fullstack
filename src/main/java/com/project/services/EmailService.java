package com.project.services;

public interface EmailService {

	void sendEmailWithToken(String userEmail, String link) throws Exception;
	
}
