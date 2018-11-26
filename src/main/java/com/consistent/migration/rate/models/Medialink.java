package com.consistent.migration.rate.models;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "medialink")
public class Medialink {
	
	String type;
	String keyword;
	List<Multimedia> multimedia;
	
	@XmlElement(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@XmlElement(name="keyword")
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@XmlElement(name="multimedia")
	public List<Multimedia> getMultimedia() {
		return multimedia;
	}
	public void setMultimedia(List<Multimedia> multimedia) {
		this.multimedia = multimedia;
	}
	
	public Medialink(String type, String keyword, List<Multimedia> multimedia) {
		super();
		this.type = type;
		this.keyword = keyword;
		this.multimedia = multimedia;
	}
	
	public Medialink(){
		this.type="";
		this.keyword="";
	}
	
}
