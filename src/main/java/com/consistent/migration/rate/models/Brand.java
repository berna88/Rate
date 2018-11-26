package com.consistent.migration.rate.models;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "brand")
public class Brand {

	
	String guid;
	String code;
	String name;
	String title;
	String language;
	String order;
	String channel;
	List<Rates> rates;
	
	
	@XmlElement(name="guid")
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@XmlElement(name="code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@XmlElement(name="language")
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	@XmlElement(name="order")
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@XmlElement(name="channel")
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	@XmlElement(name="rates")
	public List<Rates> getRates() {
		return rates;
	}
	public void setRates(List<Rates> rates) {
		this.rates = rates;
	}
	@Override
	public String toString() {
		return "Brand [guid=" + guid + ", code=" + code + ", name=" + name + ", title=" + title + ", language="
				+ language + ", order=" + order + ", channel=" + channel + ", rates=" + rates + "]";
	}
	public Brand() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
