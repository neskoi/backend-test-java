package com.fcamara.minhaVaga.config.security;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fcamara.minhaVaga.repository.UsersRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigs extends WebSecurityConfigurerAdapter {

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Autowired
	private UserAuthService userAuthService;
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private TokenService tokenService;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userAuthService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/carpark/*").permitAll().antMatchers(HttpMethod.GET, "/carpark/show-all")
				.permitAll().antMatchers(HttpMethod.POST, "/auth/carpark").permitAll()
				.antMatchers(HttpMethod.POST, "/auth/user").permitAll()
				.antMatchers(HttpMethod.POST, "/carpark/register").permitAll()
				.antMatchers(HttpMethod.POST, "/user/register").permitAll()
				.anyRequest().authenticated().and().csrf()
				.disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().addFilterBefore(new TokenAuthFilterUser(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
	}
}
