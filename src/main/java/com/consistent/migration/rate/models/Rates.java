package com.consistent.migration.rate.models;

import java.util.List;



import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "rates")
public class Rates {
	
	List<Rate> rate;
	
	@XmlElement(name="rate")
	public List<Rate> getRate() {
		return rate;
	}

	public void setRate(List<Rate> rate) {
		this.rate = rate;
	}

	public Rates(List<Rate> rate) {
		super();
		this.rate = rate;
	}

	public Rates(){
	}

}
