package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Keyword")
public class Keyword implements Comparable<Keyword> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String inventor;
	private int inventionyear;
	private String description;
	private String advantages;
	private String disadvantages;
	private String url1;
	private String url2;
	private String url3;
	private String url4;
	private String url5;
	private String url6;

	public Keyword() {

	}

	public Keyword(int id, String name, String inventor, int inventionyear, String description, String advantages,
			String disadvantages, String url1, String url2, String url3, String url4, String url5, String url6) {
		super();
		this.id = id;
		this.name = name;
		this.inventor = inventor;
		this.inventionyear = inventionyear;
		this.description = description;
		this.advantages = advantages;
		this.disadvantages = disadvantages;
		this.url1 = url1;
		this.url2 = url2;
		this.url3 = url3;
		this.url4 = url4;
		this.url5 = url5;
		this.url6 = url6;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInventor() {
		return inventor;
	}

	public void setInventor(String inventor) {
		this.inventor = inventor;
	}

	public int getInventionyear() {
		return inventionyear;
	}

	public void setInventionyear(int inventionyear) {
		this.inventionyear = inventionyear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdvantages() {
		return advantages;
	}

	public void setAdvantages(String advantages) {
		this.advantages = advantages;
	}

	public String getDisadvantages() {
		return disadvantages;
	}

	public void setDisadvantages(String disadvantages) {
		this.disadvantages = disadvantages;
	}

	public String getUrl1() {
		return url1;
	}

	public void setUrl1(String url1) {
		this.url1 = url1;
	}

	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public String getUrl3() {
		return url3;
	}

	public void setUrl3(String url3) {
		this.url3 = url3;
	}

	public String getUrl4() {
		return url4;
	}

	public void setUrl4(String url4) {
		this.url4 = url4;
	}

	public String getUrl5() {
		return url5;
	}

	public void setUrl5(String url5) {
		this.url5 = url5;
	}

	public String getUrl6() {
		return url6;
	}

	public void setUrl6(String url6) {
		this.url6 = url6;
	}

	@Override
	public String toString() {
		return "Keyword [id=" + id + ", name=" + name + ", inventor=" + inventor + ", inventionyear=" + inventionyear
				+ ", description=" + description + ", advantages=" + advantages + ", disadvantages=" + disadvantages
				+ ", url1=" + url1 + ", url2=" + url2 + ", url3=" + url3 + ", url4=" + url4 + ", url5=" + url5
				+ ", url6=" + url6 + "]";
	}

	@Override
	public int compareTo(Keyword k) {
		return this.getName().compareTo(k.getName());
	}

}
