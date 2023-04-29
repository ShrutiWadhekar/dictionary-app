package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.repo.UserDetailsRepository;

@SpringBootApplication
public class DictionaryAppWithSecurityV1Application {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsRepository userDetailsRepo;

	public static void main(String[] args) {
		SpringApplication.run(DictionaryAppWithSecurityV1Application.class, args);
	}

	@PostConstruct
	protected void init() {

		List<Authority> authorityList = new ArrayList<>();
		authorityList.add(createAuthority("USER"));
		authorityList.add(createAuthority("ADMIN"));

		User user = new User();
		user.setUserName("shruti");
		user.setName("shruti");
		user.setPassword(passwordEncoder.encode("check@123"));
		user.setEmail("s@gmail.com");
		user.setEnabled(true);
		user.setAuthorities(authorityList);
		
		// Uncomment the line below if default user is not added into the database.
		// Comment this line if you are running the application second time OR it will give error because username has unique constraint
		// userDetailsRepo.save(user);
	}

	private Authority createAuthority(String roleCode) {
		Authority authority = new Authority();
		authority.setRoleCode(roleCode);
		return authority;
	}
}
