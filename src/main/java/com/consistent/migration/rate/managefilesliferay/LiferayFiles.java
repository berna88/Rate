package com.consistent.migration.rate.managefilesliferay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import javax.portlet.ResourceRequest;

import org.apache.commons.io.FilenameUtils;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MimeTypesUtil;


@org.springframework.stereotype.Component
public class LiferayFiles {

	public long getRateFolder(ResourceRequest resourceRequest,ThemeDisplay themeDisplay){
		long repositoryId = themeDisplay.getScopeGroupId();//repository id is same as groupId
		long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		try {
			 List<Folder> folders = getFolder(parentFolderId,repositoryId);
			 for (Folder folder : folders) {
			     System.out.println("Folder padre Id >> "+folder.getFolderId());
			     System.out.println("Folder padre Name >>"+folder.getName());
			     if(folder.getName().toLowerCase().equals("Marcas".toLowerCase())){
			    	 List<Folder> folders_posadas = getFolder(folder.getFolderId(),repositoryId);
			    	 for (Folder folder_posadas : folders_posadas) {
			    		 System.out.println("Folder marcas Id >> "+folder_posadas.getFolderId());
					     System.out.println("Folder marcas Name >>"+folder_posadas.getName());
					     if(folder_posadas.getName().toLowerCase().equals("Rates".toLowerCase())){
					    	 System.out.println("Folder rates Id >> "+folder_posadas.getFolderId());
						     System.out.println("Folder rates Name >>"+folder_posadas.getName());
						 return  folder_posadas.getFolderId();
					     }
			    	 }
			     }
			     
			}
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	

	public static File saveImage(String imageUrl) throws IOException {
		String pt=imageUrl.replace(" ", "%20");
		System.out.println(pt);
		URL url = new URL(pt);
	  	File file = new File(FilenameUtils.getName(url.getPath()));
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(FilenameUtils.getName(url.getPath()));
		byte[] b = new byte[2048];
		int length;
		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
		is.close();
		os.close();
		return file;
	}
	
	
	public String fileUploadByApp(String description,
								String changeLog, 
								String folderName ,
								ThemeDisplay themeDisplay, 
								ResourceRequest renderRequest, 
								String path_server_file, 
								Long folderId) throws IOException
	{
		long repositoryId = themeDisplay.getScopeGroupId();
		
		String pt=path_server_file.replace(" ", "%20");
		System.out.println(pt);
		URL url = new URL(pt);
		
			FileEntry file_life;
			try {
				String uri=FilenameUtils.getName(url.getPath());
				uri.replace(" ", "%20");
				file_life = getFileByName(repositoryId, folderId, uri);
				return getPath(file_life.getFileEntryId(), themeDisplay);
				
			} catch (PortalException e1) {
				File file = saveImage(path_server_file);
			 	String mimeType = MimeTypesUtil.getContentType(file);
				String title = file.getName();
				try
			    {  
			       	ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), renderRequest);
			       	InputStream is = new FileInputStream( file );
			    	FileEntry image_lif = DLAppServiceUtil.addFileEntry(repositoryId, folderId, file.getName(), mimeType, 
			    			title, description, changeLog, is, file.length(), serviceContext);
			    	
			    	
			    	
			    	return getPath(image_lif.getFileEntryId(), themeDisplay);
			     } catch (Exception e)
			    {
			    	e.printStackTrace();
			    }
			}
			return null;

	}

	public String getPath(long id,ThemeDisplay themeDisplay) throws PortalException{
		FileEntry file = DLAppServiceUtil.getFileEntry(id);

		long currentDateTime = System.currentTimeMillis();
		System.out.println("date to long"+currentDateTime);
		String path="/documents/" + themeDisplay.getScopeGroupId() + "/" + 
			      file.getFolderId() +  "/" +file.getTitle() +"/"+file.getUuid()+"?t="+currentDateTime;
		System.out.println(path);
		return path;
	}

	public String getPathFull(long id,ThemeDisplay themeDisplay) throws PortalException{
		FileEntry file = DLAppServiceUtil.getFileEntry(id);
		return themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + "/" + 
	      file.getFolderId() +  "/" +file.getTitle() ;
	}
	
	
	public FileEntry getFileByName(long groupId,long folderId,String title) throws PortalException{
			return DLAppServiceUtil.getFileEntry(groupId, folderId, title);
	}
	
	
	public List<Folder> getFolder(Long folderId,Long repositoryId) throws PortalException{
    	List<Folder> folder =DLAppServiceUtil.getFolders(repositoryId,folderId);
    	return folder;
	}
	
	
	
	
}
