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
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*, org.aspcfs.modules.products.configurator.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="ProductOption" class="org.aspcfs.modules.products.base.ProductOption" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductOptions.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchOptions">Search Options</dhv:label></a> >
      <a href="ProductOptions.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <dhv:label name="product.optionDetails">Option Details</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<%@ include file="product_option_header_include.jsp" %>
<% String param1 = "optionId=" + ProductOption.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productoptions" selected="details" object="ProductOption" param='<%= param1 + "|" + param2 %>'>
  <input type="hidden" name="optionId" value="<%= ProductOption.getId() %>">
  <input type="button" value="<dhv:label name="button.modify">Modify</dhv:label>" onClick="javascript:window.location.href='ProductOptions.do?command=Modify&optionId=<%= ProductOption.getId() %>&moduleId=<%= PermissionCategory.getId() %>'">
  <input type="button" value="<dhv:label name="button.delete">Delete</dhv:label>" onClick="javascript:popURLReturn('ProductOptions.do?command=ConfirmDelete&optionId=<%=ProductOption.getId()%>&popup=true','ProductOptions.do?command=List&moduleId=<%=PermissionCategory.getId()%>', 'Delete_option','320','200','yes','no');">
  <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductOptions.do?command=Search&moduleId=<%= PermissionCategory.getId() %>'"/>
  <br><br>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="accounts.accounts_details.PrimaryInformation">Primary Information</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="documents.details.shortDescription">Short Description</dhv:label>
      </td>
      <td><%= toHtml(ProductOption.getShortDescription()) %></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="documents.details.longDescription">Long Description</dhv:label>
      </td>
      <td><%= toHtml(ProductOption.getLongDescription()) %></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="documents.details.startDate">Start Date</dhv:label>
      </td>
      <td><zeroio:tz timestamp="<%= ProductOption.getStartDate() %>" /></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="product.endDate">End Date</dhv:label>
      </td>
      <td><zeroio:tz timestamp="<%= ProductOption.getEndDate() %>" /></td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="product.configurator">Configurator</dhv:label>
      </td>
      <td><%= toHtml(ProductOption.getConfiguratorName()) %></td>
    </tr>
  </table>
</dhv:container>