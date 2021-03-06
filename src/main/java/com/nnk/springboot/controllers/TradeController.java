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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Controller
public class TradeController {

	private static final Logger log = LoggerFactory.getLogger(TradeController.class);

	@Autowired
	private TradeRepository tradeRepository;

	@RequestMapping("/trade/list")
	public String home(Model model) {
		model.addAttribute("trade", tradeRepository.findAll());
		log.info("Log home trade: " + tradeRepository.findAll().size());
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addTradeForm(Trade bid) {
		log.info("Log addTradeForm");
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			tradeRepository.save(trade);
			log.info("Log trade Validate: id:" + trade.getTradeId() + " account:" + trade.getAccount());
			return "redirect:/trade/list";
		}
		log.error("Log trade Validate error: " + result.getErrorCount());
		return "trade/add";
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
		model.addAttribute("trade", trade);
		log.info("Log showUpdateForm");
		return "trade/update";
	}

	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			log.error("Log updateTrade error: " + result.getErrorCount());
			return "trade/update";
		}
		trade.setTradeId(id);
		tradeRepository.save(trade);
		log.info("Log updateTrade: id:" + trade.getTradeId() + " account:" + trade.getAccount());
		return "redirect:/trade/list";
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
		tradeRepository.delete(trade);
		log.info("Log deleteBid: id:" + trade.getTradeId() + " account:" + trade.getAccount());
		return "redirect:/trade/list";
	}
}
