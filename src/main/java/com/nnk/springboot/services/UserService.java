package com.nnk.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	  @Override
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	    /*Here we are using dummy data, you need to load user data from
	     database or other third party application*/
	    User user = findUserbyUername(username);

	    UserBuilder builder = null;
	    if (user != null) {
	      builder = org.springframework.security.core.userdetails.User.withUsername(username);
	      builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
	      builder.roles(user.getRole());
	    } else {
	      throw new UsernameNotFoundException("User not found.");
	    }

	    return builder.build();
	  }

	  private User findUserbyUername(String username) {
	    if(username.equalsIgnoreCase("admin")) {
	      return new User(username, "admin123", "ADMIN");
	    }
	    return null;
	  }
    
}
