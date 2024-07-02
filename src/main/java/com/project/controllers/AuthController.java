package com.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.config.JwtProvider;
import com.project.entities.Users;
import com.project.repositories.UsersRepository;
import com.project.requests.LoginRequest;
import com.project.response.AuthResponse;
import com.project.services.SubscriptionService;
import com.project.services.impl.CustomUserDetailsImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UsersRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserDetailsImpl customUserDetailsImpl;
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	//signup user
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody Users user) throws Exception{
		Users isUserExist = userRepo.findByEmail(user.getEmail());
		
		if(isUserExist != null) {
			throw new Exception("Email already exist with another account");
		}
		
		Users createdUser = new Users();
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		
		Users savedUser = userRepo.save(createdUser);
		
		subscriptionService.createSubscription(savedUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = JwtProvider.generateToken(authentication);
		
		AuthResponse res = new AuthResponse();
		res.setMessage("Signup Success");
		res.setJwt(jwt);
		
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	//signin user
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest){
		
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = JwtProvider.generateToken(authentication);
		
		AuthResponse res = new AuthResponse();
		res.setMessage("Sign in Success");
		res.setJwt(jwt);
		
		return new ResponseEntity<>(res, HttpStatus.CREATED);
		
	}

	//to authenticate while login
	private Authentication authenticate(String username, String password) {
		
		UserDetails userDetails = customUserDetailsImpl.loadUserByUsername(username);
		if(userDetails == null) {
			throw new BadCredentialsException("Invalid username");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password");
			
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
	
}
