<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*, org.aspcfs.modules.products.configurator.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="ProductOption" class="org.aspcfs.modules.products.base.ProductOption" scope="request"/>
<jsp:useBean id="PropertyList" class="org.aspcfs.modules.products.configurator.OptionPropertyList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<form name="paramForm" method="post" action="ProductOptions.do?command=Update&auto-populate=true&moduleId=<%= PermissionCategory.getId() %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductOptions.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchOptions">Search Options</dhv:label></a> >
      <a href="ProductOptions.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <a href="ProductOptions.do?command=Add&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.optionChoice">Option Choice</dhv:label></a> >
      <dhv:label name="product.modifyOption">Modify Option</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<%@ include file="product_option_header_include.jsp" %>
<% String param1 = "optionId=" + ProductOption.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productoptions" selected="details" object="ProductOption" param='<%= param1 + "|" + param2 %>'>
      <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>"/>
      <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick=""/><br /><br />
      <input type="hidden" name="id" value="<%= ProductOption.getId() %>"/>
      <input type="hidden" name="optionId" value="<%= ProductOption.getId() %>"/>
      <input type="hidden" name="configId" value="<%= ProductOption.getConfiguratorId() %>"/>
      <% ProductOption productOption = ProductOption; %>
      <%@ include file="product_option_configurator_field_include.jsp" %>
      <br />
      <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>"/>
      <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick=""/>
</dhv:container>
</form>
