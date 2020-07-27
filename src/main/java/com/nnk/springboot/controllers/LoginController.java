package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("app")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserRepository userRepository;

	@GetMapping("login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		log.info("Log login");
		return mav;
	}

	@GetMapping("secure/article-details")
	public ModelAndView getAllUserArticles() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", userRepository.findAll());
		mav.setViewName("user/list");
		log.info("Log getAllUserArticles: " + userRepository.findAll().size());
		return mav;
	}

	@GetMapping("error")
	public ModelAndView error() {
		ModelAndView mav = new ModelAndView();
		String errorMessage = "You are not authorized for the requested data.";
		mav.addObject("errorMsg", errorMessage);
		mav.setViewName("403");
		log.error("Log bidList Validate error: " + errorMessage);
		return mav;
	}
}
