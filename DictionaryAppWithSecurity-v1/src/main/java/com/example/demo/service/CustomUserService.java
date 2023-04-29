package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.config.SecurityConfiguration;
import com.example.demo.model.Authority;
import com.example.demo.model.RegisterUser;
import com.example.demo.model.User;
import com.example.demo.repo.UserDetailsRepository;

//Implementation of user related functions

@Service
public class CustomUserService implements UserDetailsService {

	@Autowired
	UserDetailsRepository userDetailsRepo;

	@Autowired
	SecurityConfiguration scg;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDetailsRepo.findByUserName(username);
		System.out.println("////////////////// " + user.getUserName() + " " + user.getPassword());
		if (user == null) {
			throw new UsernameNotFoundException("User Not Found with User Name " + username);
		}
		return user;
	}

	public List<User> findAllUsers() {
		return userDetailsRepo.findAll();
	}

	public User findByUserName(String name) {
		return userDetailsRepo.findByUserName(name);
	}

	public String deleteByUserName(String name) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		System.out.println("===============================");
		System.out.println(username);

		if (username.equals(name))
			return "Sorry you cannot delete your own Account!";

		User user = findByUserName(name);
		if (user == null) {
			return "User does not exists!";
		} else {
			userDetailsRepo.delete(user);
			return "User Deleted";
		}
	}

	public String giveAdminAccess(String name) {
		User user = findByUserName(name);
		if (user == null) {
			return "User with this Username does not exist";
		} else {
			Authority auth = new Authority();
			auth.setRoleCode("ADMIN");
			List<Authority> authList = (List<Authority>) user.getAuthorities();
			for (Authority a : authList) {
				if (a.getRoleCode().equals("ADMIN")) {
					return "This User already has Admin Access!";
				}
			}
			authList.add(auth);
			user.setAuthorities(authList);
			userDetailsRepo.save(user);
			return "Admin Access Given Successfully to " + name;
		}
	}

	public String revokeAdminAccess(String name) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		System.out.println("==========================================");
		System.out.println(username);

		if (username.equals(name))
			return "Sorry you cannot revoke your own Admin Access!";

		User user = findByUserName(name);
		if (user == null) {
			return "User with this Username does not exist";
		} else {
			List<Authority> authList = (List<Authority>) user.getAuthorities();
			boolean flag = false;
			int i = 0;
			int index = -1;
			for (Authority a : authList) {
				if (a.getRoleCode().equals("ADMIN")) {
					index = i;
					flag = true;
				}
				i++;
			}
			if (index != -1)
				authList.remove(index);
			if (flag == false)
				return "This user does not have any Admin Access";
			user.setAuthorities(authList);
			userDetailsRepo.save(user);
			return "Revoked Admin Access for user " + name;
		}
	}

	public String addUser(RegisterUser uu) {
		if (findByUserName(uu.getUsername()) != null)
			return "User with this username already exists! Try another Username";

		User user = new User();
		user.setUserName(uu.getUsername());
		user.setName(uu.getName());
		user.setEmail(uu.getEmail());
		user.setPassword(scg.passwordEncoder().encode(uu.getPassword()));
		user.setEnabled(true);
		Authority a = new Authority();
		a.setRoleCode("USER");
		List<Authority> authorityList = new ArrayList<>();
		authorityList.add(a);
		user.setAuthorities(authorityList);
		userDetailsRepo.save(user);
		return "User Created Successfully";
	}

	public String checkAdmin(String username) {

		User user = findByUserName(username);
		List<Authority> ls = (List<Authority>) user.getAuthorities();
		for (Authority a : ls) {
			if (a.getRoleCode().equals("ADMIN"))
				return "true";
		}
		return "false";
	}

}
