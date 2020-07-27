package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping("/")
	public String home(Model model) {
		log.info("Log home");
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		log.info("Log AdminHome");
		return "redirect:/bidList/list";
	}

}
