package com.consistent.migration.rate.models;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rate")
public class Rate {
	String guid;
	String code;
	String name;
	String title;
	String language;
	String keyword;
	String description;
	String shortDescription;
	String order;
	String channel;
	String benefits;
	String restrictions;
	String currency;
	String end;
	List<Medialinks> medialinks;
	
	@XmlElement(name="end")
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
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
	@XmlElement(name = "language")
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	@XmlElement(name = "keyword")
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@XmlElement(name = "shortDescription")
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	@XmlElement(name = "order")
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@XmlElement(name = "channel")
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	@XmlElement(name = "benefits")
	public String getBenefits() {
		return benefits;
	}
	public void setBenefits(String benefits) {
		this.benefits = benefits;
	}
	@XmlElement(name = "restrictions")
	public String getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}
	@XmlElement(name = "currency")
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@XmlElement(name = "medialinks")
	public List<Medialinks> getMedialinks() {
		return medialinks;
	}
	public void setMedialinks(List<Medialinks> medialinks) {
		this.medialinks = medialinks;
	}
	
	public Rate(String guid, String code, String name, String title, String language, String keyword,
			String shortDescription, String order, String channel, String benefits, String restrictions,
			String currency, List<Medialinks> medialinks, String description, String end) {
		super();
		this.guid = guid;
		this.code = code;
		this.name = name;
		this.title = title;
		this.language = language;
		this.keyword = keyword;
		this.shortDescription = shortDescription;
		this.order = order;
		this.channel = channel;
		this.benefits = benefits;
		this.restrictions = restrictions;
		this.currency = currency;
		this.medialinks = medialinks;
		this.description = description;
		this.end = end;
	}
	
	public Rate(){
		this.guid = "";
		this.code = "";
		this.name = "";
		this.title = "";
		this.language = "";
		this.keyword = "";
		this.shortDescription = "";
		this.order = "";
		this.channel = "";
		this.benefits = "";
		this.restrictions = "";
		this.currency = "";
		this.description = "";
		this.end = "";
	}

}
