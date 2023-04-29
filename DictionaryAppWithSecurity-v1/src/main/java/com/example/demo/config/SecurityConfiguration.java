package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.service.CustomUserService;

//All the configurations are present in this class

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private JWTTokenHelper jwtTokenHelper;

	@Autowired
	private CustomUserService userService;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// In memory authentication (i.e. user and password defined here) and user has
		// roles defined in authorities
		// Also password must be encrypted, it will throw error if not encrypted.
		// auth.inMemoryAuthentication().withUser("shruti").password(passwordEncoder().encode("test@123")).authorities("USER","ADMIN");

		// This is used for authentication of users from database
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	// PasswordEncoder is an interface, this bean returns the instance of
	// BCryptPasswordEncoder
	// It provides encode method which is used to encrypt the password.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// permit any request that comes to this application (No security)
		// http.authorizeRequests().anyRequest().permitAll();

		// Any request that comes to this application is authenticated
		// http.authorizeRequests().anyRequest().authenticated();

		// This line means that the requests for pattern matching h2-console are
		// permitted and all other requests are authenticated
		// This will allow us to access h2 database through h2-console link
		// http.authorizeRequests((request)->request.antMatchers("/h2-console/**").permitAll().anyRequest().authenticated()).httpBasic();

		// h2-console

		// It displays the login form
		// http.formLogin();

		// It enables the basic authentication
		// (In Postman-> Authentication -> select Type= Basic Auth and enter user name
		// and password)
		// If we do not write this line then in Postman output, it will show the code
		// for login form
		// http.httpBasic(); // We added this to above line directly, so commented here

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint).and()
				.authorizeRequests((request) -> request.antMatchers("/h2-console/**", "/api/auth/**").permitAll()
						.antMatchers(HttpMethod.OPTIONS, "/**").permitAll().antMatchers("/api/admin/**")
						.hasAnyAuthority("ADMIN").anyRequest().authenticated())
				.addFilterBefore(new JWTAuthenticationFilter(userService, jwtTokenHelper),
						UsernamePasswordAuthenticationFilter.class);

		// http.cors();
		http.csrf().disable().cors().and().headers().frameOptions().disable();
	}

}
