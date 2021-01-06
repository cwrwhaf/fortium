package com.fortiumtech.scottdavies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class HttpBasicAuthenticationAdapter extends WebSecurityConfigurerAdapter {
	
//	@Autowired
//PasswordEncoder passwordEncoder;
//	PasswordEncoderFactories.createDelegatingPasswordEncoder()
//	
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
          .withUser("user").password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password"))
          .authorities("ROLE_USER");
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable().httpBasic().and().authorizeRequests().anyRequest().authenticated();
	
		start here, copy works addFilterAfter and order and see if this causes any problems
		 http
		 //.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		 
		    .authorizeRequests()
		    .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
		    .antMatchers("/api/books").authenticated()
		    .antMatchers("/ws").authenticated()
		    .and()
		    .httpBasic()
		   // 
		    .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		    ;

	}


}