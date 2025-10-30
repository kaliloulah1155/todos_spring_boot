package com.ibson.todos.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibson.todos.entity.Authority;
import com.ibson.todos.entity.User;
import com.ibson.todos.repository.UserRepository;
import com.ibson.todos.request.RegisterRequest;

import jakarta.transaction.Transactional;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}



	@Override
	@Transactional
	public void register(RegisterRequest input) throws Exception {
		
		if(isEMailTaken(input.getEmail())) {
			throw new Exception("Email already taken");
		}
		User user=buildNewUser(input);
		userRepository.save(user);
	}
	
	private boolean isEMailTaken(String email) {
		return userRepository.findByEmail(email).isPresent();
	}
	
	private User buildNewUser(RegisterRequest input) {
		User user=new User();
		user.setId(0);
		user.setFirstName(input.getFirstName());
		user.setLastName(input.getLastName());
		user.setEmail(input.getEmail());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		user.setAuthorities(initialAuthority());
		return user;
	}
	
	private List<Authority> initialAuthority(){
		boolean isFirstUser=userRepository.count()==0;
		List<Authority> authorities = new ArrayList<>();
		authorities.add(new Authority("ROLE_EMPLOYEE"));
		if(isFirstUser) {
			authorities.add(new Authority("ROLE_ADMIN"));
		}
		return authorities; 
	}
	
}





























