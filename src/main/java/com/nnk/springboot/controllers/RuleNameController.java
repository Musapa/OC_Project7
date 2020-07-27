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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Controller
public class RuleNameController {

	private static final Logger log = LoggerFactory.getLogger(RuleNameController.class);

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@RequestMapping("/ruleName/list")
	public String home(Model model) {
		model.addAttribute("ruleName", ruleNameRepository.findAll());
		log.info("Log home ruleName: " + ruleNameRepository.findAll().size());
		return "ruleName/list";
	}

	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName bid) {
		log.info("Log addRuleForm");
		return "ruleName/add";
	}

	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			ruleNameRepository.save(ruleName);
			model.addAttribute("ruleName", ruleNameRepository.findAll());
			log.info("Log ruleName Validate: id:" + ruleName.getId() + " name:" + ruleName.getName());
			return "redirect:/ruleName/list";
		}
		log.error("Log ruleName Validate error: " + result.getErrorCount());
		return "ruleName/add";
	}

	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
		model.addAttribute("ruleName", ruleName);
		log.info("Log showUpdateForm");
		return "ruleName/update";
	}

	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			log.error("Log updateRuleName error: " + result.getErrorCount());
			return "ruleName/update";
		}
		ruleName.setId(id);
		ruleNameRepository.save(ruleName);
		model.addAttribute("ruleName", ruleNameRepository.findAll());
		log.info("Log updateRuleName: id:" + ruleName.getId() + " name:" + ruleName.getName());
		return "redirect:/ruleName/list";
	}

	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
		ruleNameRepository.delete(ruleName);
		model.addAttribute("ruleName", ruleNameRepository.findAll());
		log.info("Log deleteRuleName: id:" + ruleName.getId() + " name:" + ruleName.getName());
		return "redirect:/ruleName/list";
	}
}
