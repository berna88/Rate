package com.consistent.migration.rate.commons;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import com.consistent.migration.rate.models.MappingString;

@org.springframework.stereotype.Component
public class MethodsCommons {
	Map<String, String> hotel = new HashMap<String, String>();
	String tel;
	MappingString mapping = new MappingString();

	private static String createEncodedText(final String username,final String password) {
        final String pair = username + ":" + password;
        final byte[] encodedBytes = Base64.encodeBase64(pair.getBytes());
        return new String(encodedBytes);
    }
	
	public String basicAuth(String username,String password) {
	  return "Basic " + createEncodedText(username, password);
	}
	public GetMethod GetWebScript(String path, String token) throws HttpException, IOException{
		GetMethod method = new GetMethod(path);
	    method.setRequestHeader("authorization", token);  
	    return method;
	}

	public JAXBContext getTypeInstanceRate() throws JAXBException{
			return JAXBContext.newInstance(com.consistent.migration.rate.models.Contents.class);
	}
	
	
	/**
	 * @param a
	 * @param b
	 */	 
	public int max(int a, int b){
		if(a<b)return b;
		if(b<a)return a;
		if(b==a)return a;
		else return a;
	}
	

	public String isNull(String variable){
		variable = (variable != null)? variable:"";
		return variable;
	}

	public Map<String, String> setHotel(String key,String value){
		hotel.put(key, isNull(value));
		return hotel;
	}

	public String getHotel(String key){
		return hotel.get(key);
	}
	
}
