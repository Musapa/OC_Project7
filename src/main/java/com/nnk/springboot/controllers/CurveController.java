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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Controller
public class CurveController {

	private static final Logger log = LoggerFactory.getLogger(CurveController.class);

	@Autowired
	private CurvePointRepository curvePointRepository;

	@RequestMapping("/curvePoint/list")
	public String home(Model model) {
		model.addAttribute("curvePoint", curvePointRepository.findAll());
		log.info("Log home curvePoint: " + curvePointRepository.findAll().size());
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addCurveForm(CurvePoint bid) {
		log.info("Log addCurveForm");
		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			curvePointRepository.save(curvePoint);
			log.info("Log curvePoint Validate: id:" + curvePoint.getId() + " value:" + curvePoint.getValue());
			return "redirect:/curvePoint/list";
		}
		log.error("Log curvePoint Validate error: " + result.getErrorCount());
		return "curvePoint/add";
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = curvePointRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
		model.addAttribute("curvePoint", curvePoint);
		log.info("Log showUpdateForm");
		return "curvePoint/update";
	}

	@PostMapping("/curvePoint/update/{id}")
	public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			log.error("Log updateCurvePoint error: " + result.getErrorCount());
			return "curvePoint/update";
		}
		curvePoint.setId(id);
		curvePointRepository.save(curvePoint);
		log.info("Log updateCurvePoint: id:" + curvePoint.getId() + " value:" + curvePoint.getValue());
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = curvePointRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
		curvePointRepository.delete(curvePoint);
		log.info("Log deleteCurvePoint: id:" + curvePoint.getId() + " value:" + curvePoint.getValue());
		return "redirect:/curvePoint/list";
	}
}
