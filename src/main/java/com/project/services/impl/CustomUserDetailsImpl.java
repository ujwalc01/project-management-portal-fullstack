package com.project.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.entities.Users;
import com.project.repositories.UsersRepository;

@Service
public class CustomUserDetailsImpl implements UserDetailsService{

	@Autowired
	private UsersRepository userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepo.findByEmail(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("user not found with email " + username);
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		return new User(user.getEmail(), user.getPassword(), authorities);
		
	}
	
}
