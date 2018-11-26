package com.consistent.migration.rate.models;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "contents")
public class Contents {

	List<Content> contents;

	
	@XmlElement(name="content")
	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public Contents() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contents(List<Content> contents) {
		super();
		this.contents = contents;
	}
	
	
	
}
