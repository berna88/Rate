package com.consistent.migration.rate.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.ResourceRequest;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;


@org.springframework.stereotype.Component
public class LiferayServices {
	
	
	DDMStructureLocalService _service;
	DDMTemplateLocalService _template;
	JournalFolderLocalService _journalForlder;
	AssetCategoryLocalService _category;
	
	
	public LiferayServices() {
		DDMStructureLocalService service = DDMStructureLocalServiceUtil.getService();
		DDMTemplateLocalService template = DDMTemplateLocalServiceUtil.getService();
		JournalFolderLocalService journalFolder = JournalFolderLocalServiceUtil.getService();
		AssetCategoryLocalService category = AssetCategoryLocalServiceUtil.getService();
		this._service=service;
		this._template=template;
		this._journalForlder = journalFolder;
		this._category=category;
		
	}
	
	
	
	public JSONArray getStructuresList(ResourceRequest request) throws PortalException{
		User currentUser = PortalUtil.getUser(request);
		JSONArray structuresArray = JSONFactoryUtil.createJSONArray();
		JSONObject structures= null;
	    List<DDMStructure> listDdmStructure = _service.getStructures();
	    if(listDdmStructure != null){
	    	for (int i = 0; i < listDdmStructure.size(); i++) {
	    		 if(currentUser.getFullName().equals(listDdmStructure.get(i).getUserName())){
	    			 structures= JSONFactoryUtil.createJSONObject();
	    			 structures.put("structure", listDdmStructure.get(i).getName(Locale.getDefault()));
	    			 structures.put("key", listDdmStructure.get(i).getPrimaryKey());
	    			 structures.put("templates", getTemplatesList(request,listDdmStructure.get(i).getPrimaryKey()));
	    			 structuresArray.put(structures);
	    		 }		
			}
	    }
	    System.out.println(structuresArray);
	    return structuresArray;
	}
	public JSONArray getTemplatesList(ResourceRequest request,long key) throws PortalException{
		JSONArray templatesArray = JSONFactoryUtil.createJSONArray();
		JSONObject structures= null;
	     List<DDMTemplate> listDdmTemplate = _template.getTemplates(key);
	    if(listDdmTemplate != null){
	    	for (int i = 0; i < listDdmTemplate.size(); i++) {
	    	     	 structures= JSONFactoryUtil.createJSONObject();
	    			 structures.put("template", listDdmTemplate.get(i).getName(Locale.getDefault()));
	    			 structures.put("key", listDdmTemplate.get(i).getPrimaryKey());
	    			 templatesArray.put(structures);
			}
	    	
	    }
	    return templatesArray;
	}


	
	public JournalFolder getFolderById(long id) throws PortalException{
	return	_journalForlder.getFolder(id);
	}
	
	
	public JSONArray getRootfolder(ResourceRequest request) throws PortalException{
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		List<JournalFolder> folders = new ArrayList<JournalFolder>();
		JSONArray foldersArray = JSONFactoryUtil.createJSONArray();
		JSONObject folder_hotel= null;
		  folders = _journalForlder.getFolders(themeDisplay.getScopeGroupId(),JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		 if(folders !=null && folders.size() > 0){
			 for (int i = 0; i < folders.size(); i++) {
			  folder_hotel= JSONFactoryUtil.createJSONObject();
			  folder_hotel.put("folder",folders.get(i).getName());
			  folder_hotel.put("key",folders.get(i).getPrimaryKey());
			  if(getSubfolder(request,folders.get(i).getPrimaryKey()) != null && !getSubfolder(request,folders.get(i).getPrimaryKey()).isNull(0)){
				  folder_hotel.put("child",getSubfolder(request,folders.get(i).getPrimaryKey()) );
			  }
			  foldersArray.put(folder_hotel);
			}
			 
		 }
		 System.out.println(foldersArray);
		 return foldersArray;
		 
	}
	
	public JSONArray getSubfolder(ResourceRequest request,long sub) throws PortalException{
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		List<JournalFolder> folders = new ArrayList<JournalFolder>();
		JSONArray foldersArray = JSONFactoryUtil.createJSONArray();
		JSONObject folder_hotel= null;
		  folders = _journalForlder.getFolders(themeDisplay.getScopeGroupId(),sub);
		 if(folders !=null && folders.size() > 0){
			 for (int i = 0; i < folders.size(); i++) {
			  folder_hotel= JSONFactoryUtil.createJSONObject();
			  folder_hotel.put("folder",folders.get(i).getName());
			  folder_hotel.put("key",folders.get(i).getPrimaryKey());
			  if(getSubfolder(request,folders.get(i).getPrimaryKey()) != null && !getSubfolder(request,folders.get(i).getPrimaryKey()).isNull(0)){
				  folder_hotel.put("child",getSubfolder(request,folders.get(i).getPrimaryKey()) );
			  }
			  foldersArray.put(folder_hotel);
			}
			 
		 }
		 return foldersArray;
	}
	
	
	public long searchCategoryId(String item,long groupId){
		List<AssetCategory> listCategories=_category.getAssetCategories(0,_category.getAssetCategoriesCount());
		for (int i = 0; i < listCategories.size(); i++) {
			if(listCategories.get(i).getName().equals(item) && listCategories.get(i).getGroupId()==groupId){
				return listCategories.get(i).getPrimaryKey();
			}
		}
		return 0;
	}
	
	
	public long searchCategoryIdBycompanyId(String item,long groupId,String uuid){
		List<AssetCategory> listCategories=_category.getAssetCategoriesByUuidAndCompanyId(item, groupId);
		for (int i = 0; i < listCategories.size(); i++) {
			if(listCategories.get(i).getName().equals(item)){
				return listCategories.get(i).getPrimaryKey();
			}
		}
		return 0;
	}
	
	public JSONArray getCategories(){
		  JSONArray categoryArray = JSONFactoryUtil.createJSONArray();
	    JSONObject categoryObject=null;
	    long parentCategoryId = 0;
		List<AssetCategory> listCategories = _category.getChildCategories(parentCategoryId);
		if(listCategories != null && listCategories.size() > 0){
			for (int i = 0; i < listCategories.size(); i++) {
				categoryObject=JSONFactoryUtil.createJSONObject();
				categoryObject.put("category", listCategories.get(i).getName());
				categoryObject.put("key", listCategories.get(i).getPrimaryKey());
				if(getSubCategories(listCategories.get(i).getPrimaryKey()) != null && !getSubCategories(listCategories.get(i).getPrimaryKey()).isNull(0)){
					categoryObject.put("child", getSubCategories(listCategories.get(i).getPrimaryKey()));
				}
				categoryArray.put(categoryObject);
			}
		}
		System.out.println(categoryArray);
		return categoryArray;
	}
	public JSONArray getSubCategories(long parentCategoryId){
		 JSONArray subCategoryArray = JSONFactoryUtil.createJSONArray();
	    JSONObject subCategoryObject=null;
		List<AssetCategory> listCategories = _category.getChildCategories(parentCategoryId);
		if(listCategories != null && listCategories.size() > 0){
			for (int i = 0; i < listCategories.size(); i++) {
				subCategoryObject=JSONFactoryUtil.createJSONObject();
				subCategoryObject.put("category", listCategories.get(i).getName());
				subCategoryObject.put("key", listCategories.get(i).getPrimaryKey());
				if(getSubCategories(listCategories.get(i).getPrimaryKey()) != null && !getSubCategories(listCategories.get(i).getPrimaryKey()).isNull(0)){
				subCategoryObject.put("child", getSubCategories(listCategories.get(i).getPrimaryKey()));
				}
			
				subCategoryArray.put(subCategoryObject);
			}
		}
		return subCategoryArray;
	}
}
