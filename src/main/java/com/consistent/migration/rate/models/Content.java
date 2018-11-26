package com.consistent.migration.rate.models;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "content")
public class Content {

	

	List<Brand> brands;

	@XmlElement(name="brand")
	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public Content() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Content(List<Brand> brands) {
		super();
		this.brands = brands;
	}
	
	
}
