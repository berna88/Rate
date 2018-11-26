package com.consistent.migration.rate.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;

import com.liferay.portal.kernel.util.PwdGenerator;

import posadas_wc_sb.model.WebContent;
import posadas_wc_sb.service.WebContentLocalService;
import posadas_wc_sb.service.WebContentLocalServiceUtil;


@org.springframework.stereotype.Component
@Qualifier("mapping")
public class MappingString {


	public String getDynamicContent(String es, String en){
		String id_es="es_ES";
		String id_en="en_US";
		String dynamic="<dynamic-content language-id=\""+id_es+"\"><![CDATA["+es+"]]></dynamic-content>"
		+"<dynamic-content language-id=\""+id_en+"\"><![CDATA["+en+"]]></dynamic-content>";
		return dynamic;
	}
	
    public String getDynamicContentString(String es, String en){
		String id_es="es_ES";
		String id_en="en_US";
		String dynamic="<dynamic-content language-id=\""+id_es+"\"><![CDATA[[\""+es+"\"]]]></dynamic-content>"
		+"<dynamic-content language-id=\""+id_en+"\"><![CDATA[[\""+en+"\"]]]></dynamic-content>";
		return dynamic;
	}
	
	public String DynamicElement(String name,String type,String index,String dynamic){
		String dynamicElement=null;
		
		if(!dynamic.equals(null)){
			dynamicElement="<dynamic-element name=\""+name+"\"  instance-id=\""+generateInstanceId()+"\" type=\""+type+"\" index-type=\""+index+"\">"+dynamic+
					  "</dynamic-element>";
		}
		
		return dynamicElement;
	}
	
	public String DynamicElementTwoWays(String name,String type,String index,String dynamic,String item){
		String dynamicElement=null;
		
		if(!dynamic.equals(null)){
			dynamicElement="<dynamic-element name=\""+name+"\"  instance-id=\""+generateInstanceId()+"\" type=\""+type+"\" index-type=\""+index+"\">"+dynamic+item+
					  "</dynamic-element>";
		}
		
		return dynamicElement;
	}
	
	public String mediaLinksFacility(){
		String list="list",typeFacility="typeFacility", mediaLinksFacility="mediaLinksFacility",footerMediaLinkFacility="footerMediaLinkFacility",selection_break="selection_break",keyword="keyword",text="text", mediaLinkFacility="mediaLinkFacility",languaje_es="es_ES",languaje_en="en_US",document_library="document_library";
		String media=
				"<dynamic-element name=\""+mediaLinksFacility+"\" instance-id=\""+generateInstanceId()+"\" type=\""+selection_break+"\" index-type=\""+""+"\">"+
					"<dynamic-element name=\""+mediaLinkFacility+"\"  instance-id=\""+generateInstanceId()+"\" type=\""+document_library+ "\" index-type=\""+""+"\">"+
							"<dynamic-element name=\""+typeFacility+"\"  instance-id=\""+generateInstanceId()+"\" type=\""+list+ "\" index-type=\""+""+"\">"+
								"<dynamic-content language-id=\""+languaje_es+"\"><![CDATA[60x60]]></dynamic-content>"+
								"<dynamic-content language-id=\""+languaje_en+"\"><![CDATA[60x60]]></dynamic-content>"+
							"</dynamic-element>"+
							"<dynamic-element name=\""+footerMediaLinkFacility+"\" instance-id=\""+generateInstanceId()+"\" type=\""+text+"\" index-type=\""+keyword+"\">"+
								"<dynamic-content language-id=\""+languaje_es+"\"><![CDATA[]]></dynamic-content>"+
								"<dynamic-content language-id=\""+languaje_en+"\"><![CDATA[]]></dynamic-content>"+
							"</dynamic-element>"+
							"<dynamic-content language-id=\""+languaje_es+"\"><![CDATA[]]></dynamic-content>"+
							"<dynamic-content language-id=\""+languaje_en+"\"><![CDATA[]]></dynamic-content>"+
				   "</dynamic-element>"+
				"</dynamic-element>";
		return media;
	}
	
	
	
	
	public String DynamicHeader(String content){
		String number="1.0";
		String lang_deafult="es_ES,en_US";
		@SuppressWarnings("unused")
		String lang_deafult_es="es_ES";
		String lang="es_ES";
		String contents = null;
		if(!content.equals(null)){
			 contents ="<?xml version=\""+number+"\"?><root available-locales=\""+lang_deafult+"\" default-locale=\""+lang+"\">"+content+"</root>";

		}
		
		return contents;
	}
	
    public String generateInstanceId() {
		   StringBuilder instanceId = new StringBuilder(8);

		   String key = PwdGenerator.KEY1 + PwdGenerator.KEY2 + PwdGenerator.KEY3;
		       
		   for (int i = 0; i < 8; i++) {
		       int pos = (int)Math.floor(Math.random() * key.length());

		       instanceId.append(key.charAt(pos));
		   }
		   return instanceId.toString();
		}
	
    public String getDynamicContentRoomLink(String es, String en,String classPK){

		String id_es="es_ES";
		String id_en="en_US";

		String dynamic=
				       "<dynamic-content language-id=\""+id_es+"\"><![CDATA["+"{\"className\":\"com.liferay.journal.model.JournalArticle\",\"classPK\":\""+ classPK +"\"}"+"]]></dynamic-content>"+
					   "<dynamic-content language-id=\""+id_en+"\"><![CDATA["+"{\"className\":\"com.liferay.journal.model.JournalArticle\",\"classPK\":\""+ classPK+"\"}"+"]]></dynamic-content>";

		 return dynamic;
	}
    
    public String getDynamicContentRateLink(String es, String en,String classPK){

    	String id_es="es_ES";
    	String id_en="en_US";

    	String dynamic="<dynamic-content language-id=\""+id_es+"\"><![CDATA["+"{\"className\":\"com.liferay.journal.model.JournalArticle\",\"classPK\":\""+ classPK +"\"}"+"]]></dynamic-content>"
    	+"<dynamic-content language-id=\""+id_en+"\"><![CDATA["+"{\"className\":\"com.liferay.journal.model.JournalArticle\",\"classPK\":\""+ classPK+"\"}"+"]]></dynamic-content>";

    	return dynamic;
    	}
    
    
    public String DynamicElementRateLink(String name,String type,String index, String es, String en, String myBrand){
    	String dynamicRateLink = "";
    	WebContentLocalService service = WebContentLocalServiceUtil.getService();

    	List<WebContent> contents = new ArrayList<WebContent>();
    	boolean existe = false;
    	List<WebContent> con = service.getWebContents();
    	for(WebContent webContent: con){
    	 	if(webContent.getBrand().toUpperCase().equals(myBrand.toUpperCase())){
    	 		
    	 		contents.add(webContent);
    	}
    	}
    	System.out.println("Antes de crear web content");
    	for(WebContent cont: contents){

    		
    			dynamicRateLink+="<dynamic-element name=\""+name+"\"  instance-id=\""+generateInstanceId()+"\" type=\""+type+"\" index-type=\""+index+"\">"+getDynamicContentRateLink(es, en,cont.getClasspk())+
    			    	  "</dynamic-element>";
    		
    	}
    	return dynamicRateLink;
    	}

	/*public String DynamicElementRoomLink(String name,String type,String index, String es, String en,String codeh,String brand,String typeH,String site){
		String dynamicRoomLink = "";

		RoomLocalService serviceRoom = RoomLocalServiceUtil.getService();

		List<posadas_sb.model.Room> roomsF = new ArrayList<posadas_sb.model.Room>();

		List<posadas_sb.model.Room> rooms = serviceRoom.getRooms();

		for(posadas_sb.model.Room room: rooms){
			if(room.getBrand().equals(brand) &&
					room.getCodeh().equals(codeh)&&
					room.getType().equals(typeH)&&
					room.getElement().equals(site)){
				roomsF.add(room);
			}
		}
		System.out.println("Tamaño callback"+roomsF.size());
		for(posadas_sb.model.Room cb: roomsF){
			int validate=Integer.parseInt(cb.getClasspk());
			if(validate!=0){
				dynamicRoomLink+="<dynamic-element name=\""+name+"\"  instance-id=\""+generateInstanceId()+"\" type=\""+type+"\" index-type=\""+index+"\">"+getDynamicContentRoomLink(es, en,cb.getClasspk())+
						  "</dynamic-element>";
			}
		}
		System.out.println("Mi dinamic"+dynamicRoomLink);
		return dynamicRoomLink;
	}

	public String DynamicElementFacilityLink(String name,String type,String index, String es, String en,String codeh,String brand,String typeH,String site){
		System.out.println("typeH"+typeH);
		System.out.println("type"+type);
		System.out.println("codeH:"+codeh);
		
		String dynamicRoomLink = "";

		FacilityLocalService serviceFacility = FacilityLocalServiceUtil.getService();

		List<Facility> facilitiesF = new ArrayList<Facility>();

		List<Facility> facilities = serviceFacility.getFacilitys();

		for(Facility facility: facilities){
			if(facility.getBrand().equals(brand) &&
					facility.getCodeh().equals(codeh)&&
					facility.getType().equals(typeH)&&
					facility.getElement().equals(site)){
				facilitiesF.add(facility);
			}
		}
		System.out.println("Tamaño callback"+facilitiesF.size());
		for(Facility cb: facilitiesF){
			int validate=Integer.parseInt(cb.getClasspk());
			if(validate!=0){
				dynamicRoomLink+="<dynamic-element name=\""+name+"\"  instance-id=\""+generateInstanceId()+"\" type=\""+type+"\" index-type=\""+index+"\">"+getDynamicContentRoomLink(es, en,cb.getClasspk())+
						  "</dynamic-element>";
			}
		}
		System.out.println("Mi dinamic"+dynamicRoomLink);
		return dynamicRoomLink;
	}
*/
    /*		public String DynamicElementDestinationLink(String name,String type,String index, String es, String en,String codeh,String brand,String typeH,String site,String code){
		String dynamicRoomLink = "";
	
	
		System.out.println("name: "+name);
		System.out.println("type:"+type);
		System.out.println("index: "+index);
		System.out.println("ES: "+es);
		System.out.println("En: "+en);
		System.out.println("codeh:"+codeh);
		System.out.println("brand: "+brand);
		System.out.println("typeH: "+typeH);
		System.out.println("site: "+site);
		System.out.println("code: "+code);*/

		
	/*	DestinationLocalService serviceDestination = DestinationLocalServiceUtil.getService();

		List<Destination> destinationsF = new ArrayList<Destination>();

		List<Destination> destinations = serviceDestination.getDestinations();
         
		for(Destination destination: destinations){
			System.out.println("1: "+destination.getBrand());
			System.out.println("2:"+destination.getClasspk());
			System.out.println("3: "+destination.getCodeh());
			System.out.println("4: "+destination.getCompanyId());
			System.out.println("5: "+destination.getDestinationId());
			System.out.println("6:"+destination.getElement());
			System.out.println("7: "+destination.getGroupId());
			System.out.println("8: "+destination.getPrimaryKey());
			System.out.println("9: "+destination.getType());
			System.out.println("10: "+destination.getTitle());
			
			if(destination.getBrand().equals(code) &&
					destination.getType().equals(typeH)&&
					destination.getElement().equals(site) && 
					destination.getCodeh().equals(codeh)){
				destinationsF.add(destination);
			}
		}
		System.out.println("Tamaño callback"+destinationsF.size());
		for(Destination cb: destinationsF){
			
			int validate=Integer.parseInt(cb.getClasspk());
			if(validate!=0){
				dynamicRoomLink+="<dynamic-element name=\""+name+"\"  instance-id=\""+generateInstanceId()+"\" type=\""+type+"\" index-type=\""+index+"\">"+getDynamicContentRoomLink(es, en,cb.getClasspk())+
						  "</dynamic-element>";
			}
		}
		System.out.println("Mi dinamic"+dynamicRoomLink);
		return dynamicRoomLink;
	}
	*/

}
