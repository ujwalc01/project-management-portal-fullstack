package com.project.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.config.JwtProvider;
import com.project.entities.Users;
import com.project.repositories.UsersRepository;
import com.project.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository userRepo;
	
	@Override
	public Users findUserProfileByJwt(String jwt) throws Exception {
		
		String email = JwtProvider.getEmailFromToken(jwt);
		
		return findByUserEmail(email);
		
	}

	@Override
	public Users findByUserEmail(String email) throws Exception {
		
		Users user = userRepo.findByEmail(email);
		if(user == null) {
			throw new Exception("User not found");
		}
		return user;
		
	}

	@Override
	public Users findUserById(Long userId) throws Exception {
		
		Optional<Users> optionalUser = userRepo.findById(userId);
		if(optionalUser.isEmpty()) {
			throw new Exception("User not found");
		}
		return optionalUser.get();
	}

	@Override
	public Users updateUsersProjectSize(Users user, int number) throws Exception {
		
		user.setProjectSize(user.getProjectSize()+number);
		
		return userRepo.save(user);
	}

}
