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
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="OptionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request"/>
<jsp:useBean id="SearchProductOptionListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="Errors" class="java.util.HashMap" scope="request" />
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="product_option_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductOptions.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchOptions">Search Options</dhv:label></a> >
      <dhv:label name="accounts.SearchResults">Search Results</dhv:label>
    </td>
	</tr>
</table>
<%-- End Trails --%>
<a href="ProductOptions.do?command=Add&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.addNewOption">Add New Option</dhv:label></a>
<center><%= SearchProductOptionListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SearchProductOptionListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8" <% ++columnCount; %>>
      &nbsp;
    </th>
    <th nowrap width="100%" <% ++columnCount; %>>
      <strong><a href="ProductOptions.do?command=List&moduleId=<%= PermissionCategory.getId() %>&column=popt.short_description"><dhv:label name="documents.details.shortDescription">Short Description</dhv:label></a></strong>
      <%= SearchProductOptionListInfo.getSortIcon("popt.short_description") %>
    </th>
		<th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="product.configurator.type">Type</dhv:label></strong>
    </th>
		<th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="documents.details.startDate">Start Date</dhv:label></strong>
    </th>
		<th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label></strong>
    </th>
		<th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="product.enabled">Enabled</dhv:label></strong>
    </th>
	</tr>
<% 
	Iterator j = OptionList.iterator();
	if (j.hasNext()) {
		int rowid = 0;
		int i = 0;
		while (j.hasNext()) {
			i++;
			rowid = (rowid != 1 ? 1 : 2);
			ProductOption thisOption = (ProductOption) j.next();
%>
		<tr>
			<td width="8" valign="center" nowrap class="row<%= rowid %>">
				<% int status = -1; %>
				<% status = thisOption.getEnabled() ? 1 : 0; %>
				<a href="javascript:displayMenu('select<%= i %>', 'menuOption', '<%= thisOption.getId() %>', '<%= status %>');"
				onMouseOver="over(0, <%= i %>)" onMouseOut="out(0, <%= i %>); hideMenu('menuOption');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
			</td>
			<td class="row<%= rowid %>">
    	  <a href="ProductOptions.do?command=Details&optionId=<%=thisOption.getId()%>&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(thisOption.getShortDescription()) %></a>
			</td>
			<td class="row<%= rowid %>" nowrap>
				<%= toHtml(thisOption.getConfiguratorName()) %>
			</td>
			<td class="row<%= rowid %>">
				<zeroio:tz timestamp="<%= thisOption.getStartDate() %>" dateOnly="true" default="&nbsp;"/>
			</td>
			<td class="row<%= rowid %>">
				<zeroio:tz timestamp="<%= thisOption.getEndDate() %>" dateOnly="true" default="&nbsp;"/>
			</td>
			<td class="row<%= rowid %>" align="center">
        <% if(thisOption.getEnabled()) {%>
          <dhv:label name="account.yes">Yes</dhv:label>
        <%} else {%>
          <dhv:label name="account.no">No</dhv:label>
        <%}%>
			</td>
	  </tr>
<%
		}
	} else {
%>
	<tr>
		<td colspan="<%= columnCount %>">
			<dhv:label name="product.noProductOptionsExist">No Product Options exist.</dhv:label><br />
		</td>
	</tr>
<%
}
%>
</table>
<br />
<dhv:pagedListControl object="SearchProductOptionListInfo" tdClass="row1"/>
