package com.consistent.migration.rate.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.xml.bind.JAXBException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Qualifier;

import com.consistent.migration.rate.models.Contents;
import com.consistent.migration.rate.models.Rate;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import posadas_wc_sb.exception.NoSuchWebContentException;
import posadas_wc_sb.model.WebContent;
import posadas_wc_sb.service.WebContentLocalService;
import posadas_wc_sb.service.WebContentLocalServiceUtil;



@org.springframework.stereotype.Service
@org.springframework.context.annotation.Scope("singleton")
@Qualifier("Rates")
public class RatesService {
	
	WebContentLocalService service;

	@org.springframework.beans.factory.annotation.Autowired(required=true)
	com.consistent.migration.rate.commons.Codes _codes;

	@org.springframework.beans.factory.annotation.Autowired(required=true)
	com.consistent.migration.rate.commons.MethodsCommons _commons;

	@org.springframework.beans.factory.annotation.Autowired(required=true)
	com.consistent.migration.rate.commons.LiferayServices _liferayServices;

	@org.springframework.beans.factory.annotation.Autowired(required=true)
	com.consistent.migration.rate.models.MappingString _dynamics;
	
	@org.springframework.beans.factory.annotation.Autowired(required=true)
	com.consistent.migration.rate.managefilesliferay.LiferayFiles _filesLiferay;
	
	@org.springframework.beans.factory.annotation.Autowired(required=true)
	com.consistent.migration.rate.commons.Codes _Codes;
	
	private static final Log log = LogFactoryUtil.getLog(RatesService.class);
	
	public RatesService(){
		this.service = WebContentLocalServiceUtil.getService();
	}
	
	public void insertWC(ResourceRequest resourceRequest) throws Exception{
		Map<String, String> m = new HashMap<>();
		//m.put("AQB","AQUA");
		//m.put("FVZ", "FA");
		//m.put("SLP", "FI");
		//m.put("FBC", "FAG");
		m.put("EXN", "EX");
		//m.put("OAC", "ONE");
		//m.put("GCA", "GAMMA");
		//m.put("RLC", "LARC");

		for(Map.Entry<String, String> entry :m.entrySet()){
			log.info("<------- Marca :"+ entry.getValue()+" ---------->");
			Contents contentsEng=getMappingEnglish(entry.getKey(),entry.getValue());
			Contents contentsSpa=getMappingSpanish(entry.getKey(),entry.getValue());
			GeneratetWebContent(resourceRequest,contentsEng, contentsSpa,entry.getValue());
		}
		log.info("Proceso finalizado");
			
	}
	
	
	/**
	 * @param resourceRequest
	 * @param resourceResponse
	 * @param model
	 * @return 
	 */
	public Contents getMappingSpanish(String code,String brand)
			throws HttpException, IOException, JAXBException, PortalException {
		String token = this.getAuth("user1", "user1");
		final String path_en =  "http://10.43.161.199:8080/alfresco/service/psd/ecm/getHotelRoomRates?brandcode="+brand+"&hotelcode="+code+"&language=spanish&channel=www&bookingdate=2010-01-01";
		GetMethod method_en = _commons.GetWebScript(path_en, token);
		HttpClient client_en = new HttpClient();
		int statusCode_en = client_en.executeMethod(method_en);
		if (statusCode_en != HttpStatus.SC_OK) {
			System.err.println("Method failed: " +method_en.getStatusLine());
		}
		InputStream is_en = method_en.getResponseBodyAsStream();
		Contents rates_en = (Contents) _commons.getTypeInstanceRate().createUnmarshaller().unmarshal(is_en);
	    return rates_en;
		
	}
	
	/**
	 * @param resourceRequest
	 * @param resourceResponse
	 * @param model
	 * @return 
	 */
	public Contents getMappingEnglish(String code,String brand)
			throws HttpException, IOException, JAXBException, PortalException {
		String token = this.getAuth("user1", "user1");
		log.info("peticion");
		final String path_en =  "http://10.43.161.199:8080/alfresco/service/psd/ecm/getHotelRoomRates?brandcode="+brand+"&hotelcode="+code+"&language=english&channel=www&bookingdate=2019-01-01";
		GetMethod method_en = _commons.GetWebScript(path_en, token);
		HttpClient client_en = new HttpClient();
		int statusCode_en = client_en.executeMethod(method_en);
		if (statusCode_en != HttpStatus.SC_OK) {
			System.err.println("Method failed: " +method_en.getStatusLine());
		}
		InputStream is_en = method_en.getResponseBodyAsStream();
		Contents rates_en = (Contents) _commons.getTypeInstanceRate().createUnmarshaller().unmarshal(is_en);
	    return rates_en;
		
	}
	
	
	
	public List<WebContent> validateWcByCode(String code){
		WebContentLocalService wcs= WebContentLocalServiceUtil.getService();
		
		return wcs.getWebContentByCode(code);
	}
	
	public List<posadas_wc_sb.model.WebContent> validateWcByName(String name,String code){
		log.info("search by:"+name);
		WebContentLocalService wcs= WebContentLocalServiceUtil.getService();
		
		return wcs.getWebContentByNameByCode(name, code);
	}
	
	public void delete() throws NoSuchWebContentException, PortalException{
		log.info("Entrando a metodo de borrado de tabla");
		WebContentLocalService wcs= WebContentLocalServiceUtil.getService();
		for(WebContent content : wcs.getWebContents()){
			wcs.deleteWebContent(content.getPrimaryKey());
			log.info("borrando tabla");
			}
		
	}
	
	public posadas_wc_sb.model.WebContent insertWc(long userid,
						 String code,
						 String classpk,
						 String brand,
						 String item,
						 String items,
						 String status,
						 String elements,
						 String type,
						 String arg9,
						 ServiceContext servicecontext
						 ) throws PortalException{
		WebContentLocalService wcs= WebContentLocalServiceUtil.getService();
	return	wcs.addWebContent(userid, code, classpk, brand, item, items, status, elements, type, arg9, servicecontext);
		
		
		
	}
	
	public void GeneratetWebContent(ResourceRequest resourceRequest,Contents contentsEng,Contents  contentsSpa,String brand) throws Exception{
		// TODO Auto-generated method stub
		log.info("---------Entrando a Generar el webcontent--------------");
		List<Rate> rateEngList = new ArrayList<>();
		List<Rate> rateSpaList = new ArrayList<>();
			if(contentsEng.getContents().get(0).getBrands().get(0).getRates().get(0).getRate()!=null){
				for(Rate rate : contentsEng.getContents().get(0).getBrands().get(0).getRates().get(0).getRate()){
					if(rate.getGuid() != null && !rate.getGuid().equals(""))
						rateEngList.add(rate);
				}
			}
			
			int max = 0;
			if(contentsSpa.getContents().get(0).getBrands().get(0).getRates().get(0).getRate()!=null){
				for(Rate rate : contentsSpa.getContents().get(0).getBrands().get(0).getRates().get(0).getRate()){
					if(rate.getGuid() != null && !rate.getGuid().equals(""))
						rateSpaList.add(rate);
				}
			}
			Map<Rate,Rate> mapper = new HashMap<>();
			
			List<Rate> maxList = null;
			List<Rate> minList = null;
				
			if(rateEngList.size() > rateSpaList.size())
				
			{
			log.info("ENG > ESP");
			maxList=rateEngList;
			minList=rateSpaList;
			
			}
			else if(rateSpaList.size() > rateEngList.size())
		{
				log.info("ESP > ENG");
		maxList=rateSpaList;
		minList=rateEngList;
		
		}
		else
		{
			log.info("DEFAULT");
		maxList = rateEngList;
		minList=rateSpaList;
		}
			
			for(Rate rMax: maxList){
				boolean flag = false;	
				for(Rate rMin: minList){
					if(rMax.getCode().equals(rMin.getCode()))
					{
						flag = true;
						mapper.put(rMax, rMin);
						minList.remove(rMin);
						max++;
						break;
					}
					if(flag==false){
						mapper.put(rMax, new Rate());
					}
				}
			}
			int count = 0;
		
			log.info("TamaÃ±o: "+mapper.size() + " Valores iguales: " + max);
			int count2 = 0;
			List<Rate> rateOnlyLanguage = new ArrayList<>();
			for(Map.Entry<Rate, Rate> rate12: mapper.entrySet()){
				if(!rate12.getValue().getCode().isEmpty() && !rate12.getKey().getCode().isEmpty()){
					if(rate12.getKey().getLanguage().toLowerCase().equals("english"))
					mappingRate(resourceRequest, rate12.getValue() , rate12.getKey() , brand);
					else
					mappingRate(resourceRequest, rate12.getKey(), rate12.getValue(), brand);
					log.info("key: "+rate12.getKey().getCode()+" value: "+rate12.getValue().getCode());
					count++;
				}
				if(rate12.getValue().getCode().equals("") && !rate12.getKey().getCode().equals("")){
					
					for(int i=0; i < mapper.size(); i++){
						if(rate12.getKey().getCode().equals(maxList.get(i).getCode())){
							rateOnlyLanguage.add(maxList.get(i));
							count2++;
							break;
						}
					}
				}else {
					if(rate12.getKey().getCode().equals("") && !rate12.getValue().getCode().equals("")){
						
						for(int i=0; i < mapper.size(); i++){
							if(rate12.getValue().getCode().equals(maxList.get(i).getCode())){
								rateOnlyLanguage.add(maxList.get(i));
								count2++;
								break;
							}
						}
					}
				}
			}
			for(Rate rateOnly: rateOnlyLanguage){
				log.info("<--------- Creando rate un solo idioma:"+ rateOnly.getLanguage() +" ------------> " );
				mappingRate(resourceRequest, rateOnly, rateOnly, brand);
				}
			log.info("Rates que tienen un solo idioma: "+count2+"Rates que son iguales: "+ count + "Tamaño total: "+ mapper.size() );
			readServiceRates(resourceRequest);
}
	
	
	
	public String saveOrGetPathImage(ResourceRequest resourceRequest,
									 String description,
									 String changeLog,
									 String folderName,
									 String url) throws IOException{
		
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		  
		long folderId=_filesLiferay.getRateFolder(resourceRequest,themeDisplay);
	
		return _filesLiferay.fileUploadByApp(description, changeLog, folderName, themeDisplay, resourceRequest, url, folderId);
     }
	
	/**
	 * @param resourceRequest
	 * @param facility_es
	 * @param facility_en
	 * @param code
	 * @param brand
	 * @param parentFolderId
	 * @param struc
	 * @param temp
	 * @throws JAXBException
	 * @throws PortalException
	 */
	private DDMStructure getStruct(ResourceRequest resourceRequest, String item) {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<DDMStructure> ddmStructurelist = DDMStructureLocalServiceUtil.getStructures();
		DDMStructure ddmStructure1 = null;
		for (int i = 0; i < ddmStructurelist.size(); i++) {
			if (ddmStructurelist.get(i).getName(themeDisplay.getLocale()).matches(item)) {
				ddmStructure1 = ddmStructurelist.get(i);
			}
		}
		return ddmStructure1;
	}
	
	public void saveWebcontent(ResourceRequest resourceRequest,
							   String xml,
							   String title,
							   String keyword,
							   String description,
							   String brand) throws PortalException{
		log.info("<------ Guardando web content -------->");
		ServiceContext serviceContext = new ServiceContext();
		DDMStructure ddmStructure1 = getStruct(resourceRequest, "Promociones");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		if (ddmStructure1 != null) {
			List<DDMTemplate> ddmTemplatelist = DDMTemplateLocalServiceUtil.getTemplates(ddmStructure1.getPrimaryKey());// 31907
			DDMTemplate ddmTemplate1 = null;
			for (int i = 0; i < ddmTemplatelist.size(); i++) {
				if (ddmTemplatelist.get(i).getName(themeDisplay.getLocale()).matches("Preview-Rate")) {
					ddmTemplate1 = ddmTemplatelist.get(i);
					break;
				}
			}
			JournalFolder folder = createFolder(themeDisplay);
			
			if (ddmTemplate1 != null && folder!=null && title!=null) {
				log.info("insert web content"+ title);
			  JournalArticle ja=  insertWebContent(resourceRequest, 
			    				 xml,
			    				 title+"-"+keyword,
			    				 folder.getFolderId(),
			    				 description, 
			    				 ddmStructure1, 
			    				 ddmTemplate1);		
			  if(ja!=null){
			  insertWc(themeDisplay.getUserId(),
					       title,
					       title+"-"+keyword,
						   ""+ja.getResourcePrimKey(), 
						   brand,
						   ""+themeDisplay.getSiteGroupId(),
						   description,
						   ""+ja.getPrimaryKey(),
						   ""+ja.getGroupId(),
						   ""+themeDisplay.getScopeGroupId(),
						   serviceContext);
			  }
			  
				}
			
			
			}
		
	}

	/**
	 * @param resourceRequest
	 * @param items
	 * @param title
	 * @param code
	 * @param brand
	 * @param brand
	 * @param code_hotel
	 * @param ddmStructure1
	 * @param ddmTemplate1
	 * @throws JAXBException
	 * @throws PortalException
	 */
	public void CategoryWebContent(ResourceRequest resourceRequest, 
										    String items, 
										    String title,
										    long[] assetCategoryIds,
										    String[] assetTagNames,
										    JournalArticle article)
										    throws PortalException {
		log.info("<------- Category web content --------->");
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		titleMap.put(themeDisplay.getLocale(), title);

		JournalArticleLocalServiceUtil.updateAsset(themeDisplay.getUserId(), article, assetCategoryIds, assetTagNames, null, 0.0);
	
	}
	
		/**
		 * @param resourceRequest
		 * @param items
		 * @param title
		 * @param code
		 * @param brand
		 * @param brand
		 * @param code_hotel
		 * @param ddmStructure1
		 * @param ddmTemplate1
		 * @throws JAXBException
		 * @throws PortalException
		 */
		public JournalArticle insertWebContent(ResourceRequest resourceRequest, 
											    String items, 
											    String title,
											    long folder,
											    String description,
											    DDMStructure ddmStructure1, 
											    DDMTemplate ddmTemplate1)
											    throws PortalException {
			log.info("_________Ejecutando insertWebContent __________");
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			Map<Locale, String> titleMap = new HashMap<Locale, String>();
			titleMap.put(themeDisplay.getLocale(), title);
			JournalFolder fol = _liferayServices.getFolderById(folder);
			Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
			descriptionMap.put(themeDisplay.getLocale(), description);
			ServiceContext serviceContext = new ServiceContext();
			serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
			JournalArticle objectSave = JournalArticleLocalServiceUtil.addArticle(themeDisplay.getUserId(),
					themeDisplay.getScopeGroupId(), fol.getFolderId(), titleMap, null, items,
					ddmStructure1.getStructureKey(), ddmTemplate1.getTemplateKey(), serviceContext);
			return objectSave;
		}
		
	
	public String mappingRate(ResourceRequest resourceRequest,Rate rate_es,Rate rate_en, String brand) throws PortalException, IOException{
		log.info("_________Ejecutando mapeo de rates __________");
		String url=null;
		if(rate_en.getMedialinks()!=null){
		if(rate_en.getMedialinks().size()>0){
			if(rate_en.getMedialinks().get(0).getMedialinks()!=null){
			if(rate_en.getMedialinks().get(0).getMedialinks().size()>0){
				if(rate_en.getMedialinks().get(0).getMedialinks().get(0).getMultimedia()!=null){
				if(rate_en.getMedialinks().get(0).getMedialinks().get(0).getMultimedia().size()>0){
					if(rate_en.getMedialinks().get(0).getMedialinks().get(0).getMultimedia().get(0).getUrl()!=null){
						url=rate_en.getMedialinks().get(0).getMedialinks().get(0).getMultimedia().get(0).getUrl();
					}
				}
				}
			}
			}
		}
		}
		
		log.info("_________Importando marca __________");
		log.info(brand);		
		log.info("mapenado rates");
		String media = null;
		
		if(url!=null){

			String url_liferay =saveOrGetPathImage(resourceRequest,"","","Rates","http://cms.posadas.com/posadas"+url);
			
			if(url_liferay!=null){
				media=_dynamics.DynamicElement("mediaLinksRate", "selection_break", "keyword", 

						_dynamics.DynamicElement("mediaLinkRate", "document_library", "keyword", 

								_dynamics.DynamicElement("TypeRate3", "list", "keyword", 

										_dynamics.getDynamicContent("", "")

										)+

								_dynamics.DynamicElement("PieRate4", "text", "keyword", 

										_dynamics.getDynamicContent("", "")

										)+

										_dynamics.getDynamicContent(url_liferay, url_liferay)

									)

						

						);
			}
			else{
				media=_dynamics.DynamicElement("mediaLinksRate", "selection_break", "keyword", 

						_dynamics.DynamicElement("mediaLinkRate", "document_library", "keyword", 

								_dynamics.DynamicElement("TypeRate3", "list", "keyword", 

										_dynamics.getDynamicContent("", "")

										)+

								_dynamics.DynamicElement("PieRate4", "text", "keyword", 

										_dynamics.getDynamicContent("", "")

										)+

										_dynamics.getDynamicContent("", "")

									)

						

						);
			}
			
			
		}
		else{
			media=_dynamics.DynamicElement("mediaLinksRate", "selection_break", "keyword", 

					_dynamics.DynamicElement("mediaLinkRate", "document_library", "keyword", 

							_dynamics.DynamicElement("TypeRate3", "list", "keyword", 

									_dynamics.getDynamicContent("", "")

									)+

							_dynamics.DynamicElement("PieRate4", "text", "keyword", 

									_dynamics.getDynamicContent("", "")

									)+

									_dynamics.getDynamicContent("", "")

								)

					

					);
		}
		
		
		
		String rate = _dynamics.DynamicHeader(
 
				_dynamics.DynamicElement("Rate", "selection_break", "keyword",

							_dynamics.DynamicElement("typeRate", "radio", "keyword", 

									_dynamics.getDynamicContentString("", "")

									)+
							_dynamics.DynamicElement("travelclickHotelCode", "text", "keyword", 

									_dynamics.getDynamicContentString("", "")

									)+

							_dynamics.DynamicElement("codeRate", "text", "keyword",

									_dynamics.getDynamicContent(rate_es.getCode(),rate_en.getCode())

									)+

							_dynamics.DynamicElement("nameRate", "text", "keyword", 

									_dynamics.getDynamicContent(rate_es.getName(), rate_en.getName() )

									)+

							_dynamics.DynamicElement("keywordRate", "text", "keyword", 

									_dynamics.getDynamicContent(rate_es.getKeyword(), rate_en.getKeyword())

									)+

							_dynamics.DynamicElement("descriptionRate", "selection_break", "keyword", 

									_dynamics.DynamicElement("descriptionLongRate", "text_area", "text",

											_dynamics.getDynamicContent(rate_es.getDescription(), rate_en.getDescription())

											)+

									_dynamics.DynamicElement("shortDescriptionRate", "text_area", "text", 

											_dynamics.getDynamicContent(rate_es.getShortDescription(), rate_en.getShortDescription())

											)

									)+

							_dynamics.DynamicElement("benefitsRate", "text_area", "text", 

									_dynamics.getDynamicContent(rate_es.getBenefits(), rate_en.getBenefits())

									)+

							_dynamics.DynamicElement("Restrictions1", "text_area", "text", 

									_dynamics.getDynamicContent(rate_es.getRestrictions(), rate_en.getRestrictions())

									)+

							_dynamics.DynamicElement("occupationRate", "selection_break", "keyword", 

									_dynamics.DynamicElement("rateOnlyIncludesRoomForSingleAndDoubleOccupancy", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("rateOnlyIncludesRoomForQuadrupleOccupancy", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("rateOnlyIncludesRoomForSingleOccupancy", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("rateOnlyIncludesRoomForDoubleOccupancy", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("rateOnlyIncludesRoomForTripleOccupancy", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("rateOnlyIncludesRoomForSingleDoubleAndTripleOccupancy", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("rateOnlyIncludesRoomForSingleDoubleTripleAndQuadrupleOccupancy01", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("rateOnlyIncludesRoomForDoubleAndTripleOccupancy", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("rateOnlyIncludesRoomForSingleDoubleAndTripleOccupancy1", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("rateOnlyIncludesRoomForSingleDoubleAndQuadrupleOccupancy", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("rateOnlyIncludesRoomForDoubleTripleAndQuadrupleOccupancy", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("promotionValidUntil", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("promotionIsValid", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("Other1", "text", "keyword", 

											_dynamics.getDynamicContent("", "")

											)

									)+//Fin de ocupacion

							_dynamics.DynamicElement("Benefits1", "selection_break", "keyword", 

									_dynamics.DynamicElement("wirelessInternetRate", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("breakfastBuffetRate", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("americanBreakfastBuffetRate", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("domesticPhoneCallsRate", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("tipsForBellboysRate", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("tipsForHousekeepingRate", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("tipsToWaitersRate", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("freeParkingRate", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("parkingFee75MXNPerNight", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("specialRateForBreakfastBuffet179MXNTaxesIncluded", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("15OffInFoodAndBeverages", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("15DiscountOnFoodAndNonAlcoholicBeverages", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("15OffInLaundryService", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("freeParking6", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("useToTheWashingAndIroningCenter", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("20OffInRoomService", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("10OffInMealsAndDinners", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("FreeParking10", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("15OffInSpaTreatments", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("welcomeDrinkOnArrival", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("freeAccessToTheGym", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("accessToTheGymAndWirelessInternetForFree", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("10DiscountOnYourStay", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("20DiscountOnYourStay", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("30DiscountOnYourStay", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("40DiscountOnYourStay", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("50DiscountOnYourStay", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("EarnFiestaRewardsPoints", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("AccumulateMotivaAndAppreciatePoints", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("EnjoyFreeBreakfast", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("ReceiveFreeNight", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("TheyApply3MonthsWithoutInterestInMinimumPurchasesOf2000PesosOnlyWithAmericanExpressCreditCards", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("TheyApply3MonthsWithoutInterestInMinimumPurchasesOf2000PesosOnlyWithAmericanExpressCreditCardsiidt", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("RememberThatYouCanEnjoyOurAllinclusivePlanAtLiveAquaBoutiqueResortPlayaDelCarmenLiveAquaBeachResortCancunGrandFiestaAmericanaLosCabosGrandFiestaAmericanaPuertoVallartaFiestaAmericanaCondesaCancunFiestaAmericanaCozumelFiestaAmericanaPuertoVallartaTheExploreanKohunlichAndTheExploreanCozumelft90", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("Otro9a1y", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)

									)+ // Fin de Beneficios

							_dynamics.DynamicElement("Restrictions", "selection_break", "keyword", 

									_dynamics.DynamicElement("10DeDescuentoEnTuEstancia9ymr", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("20DeDescuentoEnTuEstanciae3qr", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("30DeDescuentoEnTuEstancia4zge", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("40DeDescuentoEnTuEstanciahyvg", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("50DeDescuentoEnTuEstanciaeonn", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("AcumulaPuntosFiestaRewards8tsy", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("AcumulaPuntosMotivaYApreciarea7ab", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("DisfrutaDesayunoGratis8nrt", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("RecibeNocheGratis14rb", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("MinimumNightsStayIsRequired", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("TarifaNoAplicaEnOcupacinCudruple2yea", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("TarifaNoAplicaEnOcupacinSencillaTripleNiCudruple3f2o", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("TarifaNoAplicaEnOcupacinDobleTripleNiCudruple1a29", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("TarifaNoAplicaEnOcupacinSencillaNiCudruple3ylu", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("TarifaNoAplicaEnOcupacinSencillaNiDoble8eha", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("TarifaNoAplicaEnOcupacinSencillab1uw", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("ToAvoidChargesReviewTheCancellationAndModificationPoliciesBeforeConfirmingyourReservation", "boolean", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("Other2", "text", "keyword", 

											_dynamics.getDynamicContent("", "")

											)

									)+// Fin de Restricciones

							_dynamics.DynamicElement("websiteRate", "selection_break", "keyword", 

									_dynamics.DynamicElement("Descriptions1", "text", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("relatedContractsRate", "text", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("MediaLink1", "document_library", "keyword", 

											_dynamics.DynamicElement("TypeRate2", "list", "keyword", 

													_dynamics.getDynamicContent("", "")

													)+

											_dynamics.DynamicElement("Footer", "text", "keyword", 

													_dynamics.getDynamicContent("", "")

													)+

											_dynamics.getDynamicContent("", "")

											)

									) +// Fin de web site rate

							_dynamics.DynamicElement("bannerTravelClickRate", "selection_break", "keyword", 

									_dynamics.DynamicElement("headerRate", "text_area", "text", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("Descriptions2", "text_area", "text", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("mediaLinkTravelClickRate", "document_library", "keyword", 

											_dynamics.DynamicElement("TypeRate1", "list", "keyword", 

													_dynamics.getDynamicContent("", "")

													)+

											_dynamics.DynamicElement("Piemb2o", "text", "keyword", 

													_dynamics.getDynamicContent("", "")

													)+

											_dynamics.DynamicElement("mountRate", "text", "keyword", 

													_dynamics.getDynamicContent("", "")

													)+

											_dynamics.getDynamicContent("", "")

											)

									)+ //Fin de Media Link Travel Click

							_dynamics.DynamicElement("promoCodeRate", "text", "keyword", 

									_dynamics.getDynamicContent("", "")

									)+

							_dynamics.DynamicElement("currencyRate", "list", "keyword", 

									_dynamics.getDynamicContent("mxn","usd")

									)+

							media +
							_dynamics.DynamicElement("mountRate2", "text", "keyword", 
									_dynamics.getDynamicContent("", ""))
							+
						_dynamics.DynamicElement("bookingDateRate", "selection_break", "keyword", 

									_dynamics.DynamicElement("initialDateBooking", "ddm-date", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+
		 			                 _dynamics.DynamicElement("finalDateBooking", "ddm-date", "keyword", 

											_dynamics.getDynamicContent(rate_es.getEnd(), rate_en.getEnd())

											)

									)
						+

							_dynamics.DynamicElement("travelDateRate", "selection_break", "keyword", 

									_dynamics.DynamicElement("initialDateTravel", "ddm-date", "keyword", 

											_dynamics.getDynamicContent("", "")

											)+

									_dynamics.DynamicElement("finalDateTravel", "ddm-date", "keyword", 

											_dynamics.getDynamicContent("", "")

											)

									)	

						)

				);

   log.info("finalizando mapeo de rates");
  // String brand =getBrand(contents);
	ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);

 
   String title="";
   String code="";
   String key="";
   
   if(rate_en.getCode()!=null && rate_en.getKeyword()!=null){
	   title=rate_en.getCode()+"-"+rate_en.getKeyword();
	   code=rate_en.getCode();
	   key=rate_en.getKeyword();
	   
   }else if(rate_es.getCode()!=null  && rate_es.getKeyword()!=null){
	   title=rate_es.getCode()+"-"+rate_es.getKeyword();
	   code=rate_es.getCode();
	   key=rate_es.getKeyword();   
   }
   
   List<posadas_wc_sb.model.WebContent> pass=validateWcByName(title,code);
   
   if(pass.size() == 0){
	   saveWebcontent(resourceRequest,rate ,code,key, rate_en.getDescription(),brand);
	   log.info("Guardando ");
	   getWC();
	   
   }
   else{
	   
	   getWC();

	   if(title!=null || title!=" "){
		   
		   JournalArticleLocalServiceUtil.getArticles();
		   @SuppressWarnings("unused")
		JournalArticle jupdate=null;
		   for (int i = 0; i < JournalArticleLocalServiceUtil.getArticles().size(); i++) {
				if(JournalArticleLocalServiceUtil.getArticles().get(i).getTitle(themeDisplay.getLocale()).equals(title)){
				WebContentLocalService wcs= WebContentLocalServiceUtil.getService();
				
				if(wcs.getWebContentByName(title)!=null){
					List<WebContent> wcs_update = wcs.getWebContentByName(title);
					for (int j = 0; j < wcs_update.size(); j++) {
						 if(wcs_update.get(j).getBrand().equals(brand)){
							 log.info("update brand with self brand");
						 }
						 else{
							log.info("update brand");
							String aux=wcs_update.get(j).getBrand();
							wcs_update.get(j).setBrand(aux+","+brand);
							WebContent wcs_updated = wcs.updateWebContent(wcs_update.get(j));
					
						 }
					}
				}
				log.info("encontrado"+JournalArticleLocalServiceUtil.getArticles().get(i));
				jupdate=JournalArticleLocalServiceUtil.getArticles().get(i);
				break;
			}
		}
		  
	   }
}
   

  
	return rate;


			}
	
	public void getWC(){
		log.info("_________Ejecutando Obtener web content __________");
		WebContentLocalService wcs= WebContentLocalServiceUtil.getService();
		if(wcs.getWebContents()!=null){
		for (int i = 0; i < wcs.getWebContents().size(); i++) {
			wcs.getWebContents().get(i);
			
			
		}
		}
	}
	
	/*public void UpdateWC(){
		WebContentLocalService wcs= WebContentLocalServiceUtil.getService();
		wcs.updateWebContent(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
	}
	*/
	/**
	 * @param username
	 * @param password
	 */
	public String getAuth(String username, String password) {
		log.info("_________Ejecutando getAuth __________");
		return _commons.basicAuth(username, password);
	}
	
	/**
	 * @param item
	 * @param groupId
	 */
	public long searchCategory(String item, long groupId) {
		log.info("_________Ejecutando searchCategory __________");
		return _liferayServices.searchCategoryId(item, groupId);
	}
	
	public JournalFolder createFolder(ThemeDisplay themeDisplay) throws PortalException{
		log.info("_________Ejecutando createFolder __________");
		JournalFolder actualFolder = null;
		List<JournalFolder> folders = JournalFolderLocalServiceUtil.getFolders(themeDisplay.getScopeGroupId(),0);
		for(JournalFolder folder : folders){


		if(folder.getName().toLowerCase().equals("Posadas".toLowerCase())){
		actualFolder = folder;
		break;  
		}
		}
		 folders = JournalFolderLocalServiceUtil.getFolders(themeDisplay.getScopeGroupId(),actualFolder.getFolderId());

		JournalFolder hotelFolder = null;
		for(JournalFolder folder : folders){

		if(folder.getName().toLowerCase().equals("Rates".toLowerCase())){

		hotelFolder = folder;

		break;
		}
		}
return hotelFolder;
}
	

	public void readServiceRates(ResourceRequest resourceRequest) throws PortalException{
		log.info("_________Ejecutando readServiceRates Asignando categorias __________");
		// Accediendo al servicio
		//WebContentLocalService service = WebContentLocalServiceUtil.getService();
		// Lista de contenidos
		List<WebContent> contents = service.getWebContents();
		// Lista de articulos
		List<JournalArticle> articles = JournalArticleLocalServiceUtil.getArticles();
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String[] br=null;
		long categories[] = null;
		for(WebContent content: contents){	
			for(JournalArticle article: articles){		
				if(content.getClasspk().equals(Long.toString(article.getResourcePrimKey())) && content.getStatus().equals(Long.toString(article.getGroupId()))){
					br = convertBrandToArray(content.getBrand());
					categories= new long[br.length];
					for (int i = 0; i < br.length; i++) {
						 categories[i]= searchCategory(br[i],themeDisplay.getScopeGroupId());
					}
					if(categories!=null){
						log.info("update webcontent");
						updateJornal(categories,article,themeDisplay);
					}
				}	
			}
		
		}
		
		
	}
	
	private void updateJornal(long[] categories, JournalArticle article, ThemeDisplay themeDisplay) throws PortalException {
	    log.info("_________Ejecutando update de web content__________");
		//log.info(article.getContent());
		JournalArticleLocalServiceUtil.updateAsset(themeDisplay.getScopeGroupId(), article, categories, null, null, 0.0);
		
	}


	public String[] convertBrandToArray(String brand){
		log.info("_________Ejecutando convertBrandToArray__________");
		brand = brand.replace(" ", "");
		String s[] = null;
		if(!brand.split(",").toString().equals(" ")){
			 s = brand.split(",");
		}
		
		
		return s;
	}


				

				
		
		
	

}
