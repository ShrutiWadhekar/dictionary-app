package com.example.demo.controller;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JWTTokenHelper;
import com.example.demo.model.User;
import com.example.demo.requests.AuthenticationRequest;
import com.example.demo.response.LoginResponse;
import com.example.demo.response.UserInfo;

//User related operations

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	JWTTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest)
			throws InvalidKeySpecException, NoSuchAlgorithmException {

		System.out.println("HIII 1");
		UsernamePasswordAuthenticationToken aa = new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUserName(), authenticationRequest.getPassword());
		System.out.println("HIII 2");
		Authentication authentication = authenticationManager.authenticate(aa);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user = (User) authentication.getPrincipal();
		String jwtToken = jwtTokenHelper.generateToken(user.getUsername());
		LoginResponse response = new LoginResponse();
		response.setToken(jwtToken);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/userinfo")
	public ResponseEntity<?> getUserInfo(Principal user) {
		User userObj = (User) userDetailsService.loadUserByUsername(user.getName());

		UserInfo userInfo = new UserInfo();
		userInfo.setName(userObj.getName());
		userInfo.setUserName(userObj.getUserName());
		userInfo.setEmail(userObj.getEmail());
		userInfo.setPassword(userObj.getPassword());
		userInfo.setRoles(userObj.getAuthorities().toArray());

		return ResponseEntity.ok(userInfo);
	}

//		@GetMapping("/auth/checkAdminNoUsername")
//		public boolean checkIfAdminPrincipal(Principal user){
//			User userObj = (User)userDetailsService.loadUserByUsername(user.getName());
//			List<Authority> ls = (List<Authority>) userObj.getAuthorities();
//			for(Authority a: ls) {
//				if(a.getRoleCode().equals("ADMIN"))
//					return true;
//			}
//			return false;
//	}

}
