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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Controller
public class BidListController {

	private static final Logger log = LoggerFactory.getLogger(BidListController.class);

	@Autowired
	private BidListRepository bidListRepository;

	@RequestMapping("/bidList/list")
	public String home(Model model) {
		model.addAttribute("bidList", bidListRepository.findAll());
		log.info("Log home bidList: " + bidListRepository.findAll().size());
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(BidList bid) {
		log.info("Log addBidForm");
		return "bidList/add";
	}

	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bid, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			bidListRepository.save(bid);
			log.info("Log bidList Validate: id:" + bid.getBidListId() + " account:" + bid.getAccount());
			return "redirect:/bidList/list";
		}
		log.error("Log bidList Validate error: " + result.getErrorCount());
		return "bidList/add";
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		BidList bidList = bidListRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
		model.addAttribute("bidList", bidList);
		log.info("Log showUpdateForm");
		return "bidList/update";
	}

	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		if (result.hasErrors()) {
			log.error("Log updateBid error: " + result.getErrorCount());
			return "bidList/update";
		}
		bidList.setBidListId(id);
		bidListRepository.save(bidList);
		log.info("Log updateBid: id:" + bidList.getBidListId() + " account:" + bidList.getAccount());
		return "redirect:/bidList/list";
	}

	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		BidList bidList = bidListRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
		bidListRepository.delete(bidList);
		log.info("Log deleteBid: id:" + bidList.getBidListId() + " account:" + bidList.getAccount());
		return "redirect:/bidList/list";
	}
}
