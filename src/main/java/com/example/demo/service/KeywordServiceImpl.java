package com.example.demo.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Keyword;
import com.example.demo.repo.KeywordRepository;

//Implementation of keywprd related functions

@Service
public class KeywordServiceImpl implements KeywordService {

	@Autowired
	private KeywordRepository keyRepo;

	@Override
	public Keyword addWord(Keyword k) {
		return keyRepo.save(k);
	}

	@Override
	public Keyword searchWord(String name) {
		List<Keyword> ls = displayAllKeywords();
		for (Keyword k : ls) {
			if (k.getName().equalsIgnoreCase(name)) {
				return k;
			}
		}
		return null;
	}

	@Override
	public List<Keyword> displayAllKeywords() {
		List<Keyword> ls = keyRepo.findAll();
		Collections.sort(ls);
		return ls;
	}

	@Override
	public String deleteWord(String name) {
		List<Keyword> ls = displayAllKeywords();
		for (Keyword k : ls) {
			if (k.getName().equalsIgnoreCase(name)) {
				keyRepo.deleteById(k.getId());
				return "Keyword Deleted!";
			}
		}
		return "Keyword does not exist!";
	}

	@Override
	public String editWord(Keyword key) {
		System.out.println("Lets check values: ");
		System.out.println(key);
		List<Keyword> ls = displayAllKeywords();
		for (Keyword k : ls) {
			if (k.getName().equalsIgnoreCase(key.getName())) {
				key.setName(k.getName());
				if(key.getInventor().isEmpty()) {
					key.setInventor(k.getInventor());
				}
				if(key.getInventionyear() == 0) {
					key.setInventionyear(k.getInventionyear());
				}
				if(key.getDescription().isEmpty()) {
					key.setDescription(k.getDescription());
				}
				if(key.getAdvantages().isEmpty()) {
					key.setAdvantages(k.getAdvantages());
				}
				if(key.getDisadvantages().isEmpty()) {
					key.setDisadvantages(k.getDisadvantages());
				}
				if(key.getUrl1().isEmpty()) {
					key.setUrl1(k.getUrl1());
				}
				if(key.getUrl2().isEmpty()) {
					key.setUrl2(k.getUrl2());
				}
				if(key.getUrl3().isEmpty()) {
					key.setUrl3(k.getUrl3());
				}
				if(key.getUrl4().isEmpty()) {
					key.setUrl4(k.getUrl4());
				}
				if(key.getUrl5().isEmpty()) {
					key.setUrl5(k.getUrl5());
				}
				if(key.getUrl6().isEmpty()) {
					key.setUrl6(k.getUrl6());
				}
				keyRepo.deleteById(k.getId());
				keyRepo.save(key);
				return "Keyword Updated!";
			}
		}
		return "Keyword does not exist!";
	}

}
