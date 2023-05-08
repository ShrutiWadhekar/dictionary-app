package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Keyword;
import com.example.demo.service.KeywordService;

//Keyword related operations

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class KeywordController {

	@Autowired
	KeywordService kService;

	@GetMapping(value = "/user/check")
	public String Check() {
		return "valid";
	}

	@PostMapping(value = "/add")
	public Keyword addKeyword(@RequestBody Keyword k) {
		System.out.println("In create");
		return kService.addWord(k);
	}

	@GetMapping(value = "/search")
	public Keyword searchKeyword(@RequestParam String name) {
		System.out.println("In findById");
		return kService.searchWord(name);
	}

	@GetMapping(value = "/findall")
	public List<Keyword> findAllKeywords() {
		return kService.displayAllKeywords();
	}

	@DeleteMapping(value = "/delete")
	public String deleteKeyword(@RequestParam String name) {
		System.out.println("In delete");
		return kService.deleteWord(name);
	}

	@PutMapping(value = "/edit")
	public String editKeyword(@RequestBody Keyword k) {
		System.out.println("In update");
		return kService.editWord(k);
	}
}
