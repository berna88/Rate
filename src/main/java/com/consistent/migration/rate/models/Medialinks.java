package com.consistent.migration.rate.models;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "medialinks")
public class Medialinks {
	
	List<Medialink> medialinks;
	
	@XmlElement(name = "medialink")
	public List<Medialink> getMedialinks() {
		return medialinks;
	}

	public void setMedialinks(List<Medialink> medialinks) {
		this.medialinks = medialinks;
	}

	public Medialinks(List<Medialink> medialinks) {
		super();
		this.medialinks = medialinks;
	}
	
	public Medialinks(){
		
	}
	

}
