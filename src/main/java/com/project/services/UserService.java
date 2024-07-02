package com.project.services;

import com.project.entities.Users;

public interface UserService {
	
	Users findUserProfileByJwt(String jwt) throws Exception;
	
	Users findByUserEmail(String email) throws Exception;
	
	Users findUserById(Long userId) throws Exception;
	
	Users updateUsersProjectSize(Users user, int number) throws Exception;
	
	
}
