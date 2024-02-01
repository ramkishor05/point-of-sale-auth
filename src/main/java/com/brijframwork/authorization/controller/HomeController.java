package com.brijframwork.authorization.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {

	@GetMapping
	public String getWelcome(){
    	return "Welcome";
	}
	
	@PostMapping
	public String putWelcome(){
    	return "Welcome";
	}
}
