package com.consistent.migration.rate.portlet;

import java.io.IOException;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.consistent.migration.rate.models.Marcas;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author liferay
 */
@Controller
@RequestMapping("VIEW")
public class RatesPortletViewController {

		
		@org.springframework.beans.factory.annotation.Autowired
		com.consistent.migration.rate.services.RatesService _rates;
		com.consistent.migration.rate.models.Marcas marcas;
		 
		
		@RenderMapping
		public String view(RenderRequest request, RenderResponse response, Model model, Map<String, Object> map) throws HttpException, PortalException, IOException {
			return "view";
		}
		
		@org.springframework.web.portlet.bind.annotation.ResourceMapping(value="execute")
		public void getCategories(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model) throws Exception{
			_rates.insertWC(resourceRequest);
		 }
		
		
		
		@org.springframework.web.portlet.bind.annotation.ResourceMapping(value="delete")
		public void delete(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model) throws Exception{
			_rates.delete();
		 }
		
		@ActionMapping(value="misMarcas")
		public void getDataMarca(@ModelAttribute("marcas") Marcas marcas, ActionRequest actionRequest, ActionResponse actionResponse, Model model){
			System.out.println("#############Calling getCustomerData##########");
			actionResponse.setRenderParameter("action", "success");
			model.addAttribute("successModel", marcas);
		}
		@RenderMapping(params="success")
		public String getMarca(){
			return "view";
		}
		
}