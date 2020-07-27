package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/user/list")
	public String home(Model model) {
		model.addAttribute("users", userRepository.findAll());
		log.info("Log home user: " + userRepository.findAll().size());
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUser(User bid) {
		log.info("Log addUser");
		return "user/add";
	}

	@PostMapping("/user/validate")
	public String validate(@Valid User user, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);
			model.addAttribute("users", userRepository.findAll());
			log.info("Log user Validate: id:" + user.getId() + " fullName:" + user.getFullname());
			return "redirect:/user/list";
		}
		log.error("Log user Validate error: " + result.getErrorCount());
		return "user/add";
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		user.setPassword("");
		model.addAttribute("user", user);
		log.info("Log showUpdateForm");
		return "user/update";
	}

	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			log.error("Log user Validate error: " + result.getErrorCount());
			return "user/update";
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setId(id);
		userRepository.save(user);
		model.addAttribute("users", userRepository.findAll());
		log.info("Log updateUser: id:" + user.getId() + " fullName:" + user.getFullname());
		return "redirect:/user/list";
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userRepository.delete(user);
		model.addAttribute("users", userRepository.findAll());
		log.info("Log deleteUser: id:" + user.getId() + " fullName:" + user.getFullname());
		return "redirect:/user/list";
	}
}
