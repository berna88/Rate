package com.consistent.migration.rate.portlet;

import java.io.IOException;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author liferay
 */
@Controller
@RequestMapping("VIEW")
public class RatesPortletViewController {

		
		@org.springframework.beans.factory.annotation.Autowired
		com.consistent.migration.rate.services.RatesService _rates;
		
		 
		
		@RenderMapping
		public String view(RenderRequest request, RenderResponse response) throws HttpException, PortalException, IOException {
			
			
			
			return "view";
		}
		
		@org.springframework.web.portlet.bind.annotation.ResourceMapping(value="execute")
		public void getCategories(ResourceRequest resourceRequest,ResourceResponse resourceResponse,Model model) throws Exception{
			_rates.insertWC(resourceRequest);
		 }
		
}