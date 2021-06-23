package com.fcamara.minhaVaga.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fcamara.minhaVaga.repository.CarParksRepository;
import com.fcamara.minhaVaga.repository.UsersRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigs extends WebSecurityConfigurerAdapter {
	
	@Configuration
	@Order(1)
	public static class UserAuth extends WebSecurityConfigurerAdapter{
		@Autowired
		private UserAuthService userAuthService;
		
		@Autowired
		private UsersRepository userRepository;
		
		@Autowired
		private TokenService tokenService;

		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userAuthService).passwordEncoder(new BCryptPasswordEncoder());
		}
		
		@Bean @Primary
		@Qualifier("UserAuthenticationManager")
		protected AuthenticationManager authenticationUserManager() throws Exception {
			return super.authenticationManager();
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/user/**")
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/user/auth").permitAll()
				.antMatchers(HttpMethod.POST, "/user/register").permitAll()
				.anyRequest().hasAuthority("CAROWNER")
				.and().csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().addFilterBefore(new TokenAuthFilterUser(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
		}
	}
	
	@Configuration
	@Order(2)
	public static class CarParkAuth extends WebSecurityConfigurerAdapter{
		@Autowired
		private CarParkAuthService carParkAuthService;
		
		@Autowired
		private CarParksRepository carParkRepository;
		
		@Autowired
		private TokenService tokenService;
		
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(carParkAuthService).passwordEncoder(new BCryptPasswordEncoder());
		}
		
		@Bean
		@Qualifier("CarParkAuthenticationManager")
		protected AuthenticationManager authenticationCarParkManager() throws Exception {
			return super.authenticationManager();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/carpark/**")
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/carpark/auth").permitAll()
				.antMatchers(HttpMethod.POST, "/carpark/register").permitAll()
				.antMatchers(HttpMethod.GET, "/carpark/*").permitAll()
				.anyRequest().hasAuthority("CARPARK")
				.and().csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().addFilterBefore(new TokenAuthFilterCarPark(tokenService, carParkRepository), UsernamePasswordAuthenticationFilter.class);
		}
	}
	
}
