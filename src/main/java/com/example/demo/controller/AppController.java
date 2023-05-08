package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RegisterUser;
import com.example.demo.model.User;
import com.example.demo.service.CustomUserService;

//User related operations

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AppController {

	@Autowired
	CustomUserService custUserService;

	@GetMapping("/check")
	public String checkMethod() {
		return "Hello Shruti, How are you?";
	}

	@GetMapping("/admin/allUsers")
	public List<User> getAllUsers() {
		return custUserService.findAllUsers();
	}

	@GetMapping("/admin/findUser")
	public User getByUserName(@RequestParam String name) {
		return custUserService.findByUserName(name);
	}

	@DeleteMapping("/admin/deleteUser")
	public String deleteByUserName(@RequestParam String name) {
		return custUserService.deleteByUserName(name);
	}

	@PostMapping("/admin/giveAdminAccess")
	public String giveAdminAccess(@RequestParam String name) {
		return custUserService.giveAdminAccess(name);
	}

	@PostMapping("/admin/revokeAdminAccess")
	public String revokeAdminAccess(@RequestParam String name) {
		return custUserService.revokeAdminAccess(name);
	}

	@PostMapping("/auth/addUser")
	public String addUser(@RequestBody RegisterUser uu) {
		return custUserService.addUser(uu);
	}

	@PostMapping("/auth/isAdmin")
	public String checkAdmin(@RequestParam String name) {
		return custUserService.checkAdmin(name);
	}

}
