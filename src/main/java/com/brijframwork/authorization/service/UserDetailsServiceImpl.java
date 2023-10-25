package com.brijframwork.authorization.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.repository.UserAccountRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private UserAccountRepository userLoginRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<EOUserAccount> findUserLogin = userLoginRepository.findUserName(username);
		if (!findUserLogin.isPresent()) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(findUserLogin.get().getUsername(), bcryptEncoder.encode(findUserLogin.get().getPassword()), getAuthority(findUserLogin.get()));
		return userDetails;
	}

	private Set<SimpleGrantedAuthority> getAuthority(EOUserAccount user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(user.getUserRole().getRoleId()));
		return authorities;
	}
	
}
