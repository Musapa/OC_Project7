package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Controller
public class RatingController {

	private static final Logger log = LoggerFactory.getLogger(RatingController.class);

	@Autowired
	private RatingRepository ratingRepository;

	@RequestMapping("/rating/list")
	public String home(Model model) {
		model.addAttribute("rating", ratingRepository.findAll());
		log.info("Log home rating: " + ratingRepository.findAll().size());
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating) {
		log.info("Log addRatingForm");
		return "rating/add";
	}

	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			ratingRepository.save(rating);
			log.info("Log rating Validate: id:" + rating.getId() + " orderNumber:" + rating.getOrderNumber());
			return "redirect:/rating/list";
		}
		log.error("Log rating Validate error: " + result.getErrorCount());
		return "rating/add";
	}

	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Rating rating = ratingRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
		model.addAttribute("rating", rating);
		log.info("Log showUpdateForm");
		return "rating/update";
	}

	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			log.error("Log updateRating error: " + result.getErrorCount());
			return "rating/update";
		}
		rating.setId(id);
		ratingRepository.save(rating);
		log.info("Log updateRating: id:" + rating.getId() + " orderNumber:" + rating.getOrderNumber());
		return "redirect:/rating/list";
	}

	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		Rating rating = ratingRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
		ratingRepository.delete(rating);
		log.info("Log deleteRating: id:" + rating.getId() + " orderNumber:" + rating.getOrderNumber());
		return "redirect:/rating/list";
	}
}
