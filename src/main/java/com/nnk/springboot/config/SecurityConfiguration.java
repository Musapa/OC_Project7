package com.nnk.springboot.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nnk.springboot.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/*@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	private final String USERS_QUERY = "SELECT username, password, 1 FROM users WHERE username=?";
	private final String ROLES_QUERY = "SELECT username, role FROM users WHERE username=?";

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(USERS_QUERY).authoritiesByUsernameQuery(ROLES_QUERY).dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		 http.authorizeRequests()
		 		.antMatchers("/").permitAll()
		 		.antMatchers("/css/**").permitAll()
		 		.antMatchers("/user/**").permitAll()
		 		.antMatchers("/bidList/**", "/rating/**", "/ruleName/**", "/trade/**", "/curvePoint/**").hasAnyAuthority("ADMIN", "USER").anyRequest()
		 		.authenticated().and().formLogin()  //login configuration
		 		.defaultSuccessUrl("/bidList/list")
		 		.and().logout()    //logout configuration
		 		.logoutUrl("/app-logout")
		 		.logoutSuccessUrl("/")
		 		.and().exceptionHandling() //exception handling configuration
		 		.accessDeniedPage("/app/error");
	}*/
	
	
	  @Bean
	  public UserDetailsService userDetailsService() {
	    return new UserService();
	  };
	  
	  @Bean
	  public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  };
	  
	  @Override
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	  }

	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests().anyRequest().hasAnyRole("ADMIN", "USER")
	    .and()
	    .formLogin()
	    .and()
	    .logout().permitAll().logoutSuccessUrl("/login")
	    .and()
	    .csrf().disable();
	  }
	
}







