package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Keyword;

public interface KeywordService {
	Keyword addWord(Keyword k);

	Keyword searchWord(String name);

	List<Keyword> displayAllKeywords();

	String deleteWord(String name);

	String editWord(Keyword k);
}