<%@ include file="/WEB-INF/jsp/init.jsp" %>

<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.model.PersistedModel" %>
<%@ page import="com.liferay.portal.kernel.dao.search.SearchEntry" %>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %>



<liferay-theme:defineObjects />

<portlet:resourceURL id="execute" var="execute" ></portlet:resourceURL>

<p>
	<b><liferay-ui:message key="com.consistent.migration.rates.caption"/></b>
</p>
<section>
	<article>
	<form>
    <label class="checkbox-inline">
      <input type="checkbox" value="AQUA">AQUA
    </label>
    <label class="checkbox-inline">
      <input type="checkbox" value="FA">FA
    </label>
    <label class="checkbox-inline">
      <input type="checkbox" value="FI">FI
    </label>
  </form>
  </article>
</section>
<aside>
	<button id="idAppAlfrescoButtonGetInfo">Import Content</button>
</aside>
	
<script src="<%=request.getContextPath()%>/webjars/jquery/3.1.0/jquery.js"></script>	
<script>

$("#idAppAlfrescoButtonGetInfo").click(function(){

	 $.ajax({
		 url: "${execute}" ,
		 type: 'GET',
		 datatype:'json',
		 success: function(data){
		var obj = JSON.parse(data);
		console.log(obj);
		}
		});
	});
</script>