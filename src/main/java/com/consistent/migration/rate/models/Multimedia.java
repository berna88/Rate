package com.consistent.migration.rate.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "multimedia")
public class Multimedia {
	String url;
	
	@XmlElement(name="url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Multimedia(String url) {
		super();
		this.url = url;
	}
	
	public Multimedia(){
		
	}
}
