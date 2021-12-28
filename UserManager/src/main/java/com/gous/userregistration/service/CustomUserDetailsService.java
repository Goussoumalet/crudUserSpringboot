package com.gous.userregistration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gous.userregistration.controller.CustomUserDetails;
import com.gous.userregistration.model.User;
import com.gous.userregistration.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if(user== null) {
			throw new UsernameNotFoundException("User not found");
		
		}
		return new CustomUserDetails(user);
	}

	public void deleteUser(long id) {
		userRepo.deleteById(id);
	}
	
	 public void saveUser(User user) {
		 userRepo.save(user);
	 }
	 
	 public void updateSaveUser(User user) {
		 userRepo.save(user);
	 }
}
