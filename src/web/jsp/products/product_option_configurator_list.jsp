<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="ConfiguratorList" class="org.aspcfs.modules.products.base.ProductOptionConfiguratorList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductOptions.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchOptions">Search Options</dhv:label></a> >
      <a href="ProductOptions.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <dhv:label name="product.optionChoice">Option Choice</dhv:label>
		</td>
	</tr>
</table>
</dhv:evaluate>
<%-- End Trails --%>
<form name="configForm" action="ProductOptions.do?command=LoadConfigurator&moduleId=<%= PermissionCategory.getId() %>" method="post">
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="product.selectOptionConfigurator.text">Select an Option Configuartor from the drop down menu to proceed</dhv:label></td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th colspan="2">
			<strong><dhv:label name="product.selectOptionConfigurator">Select Option Configurator</dhv:label></strong>
		</th>
	</tr>
	<tr class="containerBody">
		<td class="formLabel">
			<dhv:label name="product.configurator">Configurator</dhv:label>
		</td>
		<td>
			<%= ConfiguratorList.getHtmlSelect("configId", -1) %>
		</td>
	</tr>
</table>
&nbsp;<br />
<%-- popup use --%>
<input type="hidden" name="action" value="<%= request.getParameter("action") %>"/>
<input type="hidden" name="popup" value="<%= request.getParameter("popup") %>"/>
<input type="hidden" name="catalogId" value="<%= request.getParameter("catalogId") %>">
<input type="hidden" name="moduleId" value="<%= request.getParameter("moduleId") %>">
<%-- popup use --%>
    
<input type="submit" value="Submit"/>
<dhv:evaluate if="<%= isPopup(request) %>">
  <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();"/>
</dhv:evaluate>

