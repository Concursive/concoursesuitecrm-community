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
<jsp:useBean id="ProductCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="OptionList" class="org.aspcfs.modules.products.base.ProductOptionList" scope="request" />
<jsp:useBean id="configId" class="java.lang.String" scope="request"/>
<jsp:useBean id="selectedElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="finalElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="ProductCatalogOptionListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<%@ include file="../initPage.jsp" %>
<form name="optionListView" method="post" action="ProductCatalogOptions.do?command=OptionList&moduleId=<%= PermissionCategory.getId() %>&catalogId=<%= ProductCatalog.getId() %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductCatalogs.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchProducts">Search Products</dhv:label></a> >
      <a href="ProductCatalogs.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
      <a href="ProductCatalogs.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&catalogId=<%= ProductCatalog.getId() %>"><dhv:label name="product.productDetails">Product Details</dhv:label></a> >
			<a href="ProductCatalogOptions.do?command=List&moduleId=<%= PermissionCategory.getId() %>&catalogId=<%= ProductCatalog.getId() %>"><dhv:label name="product.options">Options</dhv:label></a> >
      <a href="ProductCatalogOptions.do?command=Add&moduleId=<%= PermissionCategory.getId() %>&catalogId=<%= ProductCatalog.getId() %>"><dhv:label name="product.optionChoice">Option Choice</dhv:label></a> >
      <dhv:label name="product.optionList">Option List</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<% ProductCatalog productCatalog = ProductCatalog; %>
<%@ include file="product_catalog_header_include.jsp" %>
<% String param1 = "catalogId=" + ProductCatalog.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productcatalogs" selected="options" object="ProductCatalog" param='<%= param1 + "|" + param2 %>'>
		<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="ProductCatalogOptionListInfo" showHiddenParams="true" enableJScript="true" form="optionListView"/>
		<% int columnCount = 0; %>
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
			<tr>
				<th width="8" <% ++columnCount; %>>
					<strong><dhv:label name="accounts.accounts_add.select">Select</dhv:label></strong>
				</th>
				<th nowrap width="100%" <% ++columnCount; %>>
					<strong><dhv:label name="product.optionName">Option Name</dhv:label></strong>
				</th>
				<th nowrap <% ++columnCount; %>>
					<strong><dhv:label name="product.configurator">Configurator</dhv:label></strong>
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
					int count = 0;
					while (j.hasNext()) {
						i++;
						count++;
						rowid = (rowid != 1 ? 1 : 2);
						ProductOption thisOption = (ProductOption) j.next();
			%>
					<tr class="row<%= rowid + ((selectedElements.get(new Integer(thisOption.getId())) != null) ? "hl":"") %>">
						<td width="8" align="center">
							<input type="checkbox" name="checkelement<%= count %>" value="<%= thisOption.getId() %>" <%= ((selectedElements.get(new Integer(thisOption.getId()))!= null)?" checked":"") %> onClick="highlight(this,'<%= User.getBrowserId() %>');" />
						</td>
						<input type="hidden" name="hiddenelementid<%= count %>" value="<%= thisOption.getId() %>">
						<td nowrap><%= toHtml(thisOption.getShortDescription()) %></td>
						<td nowrap><%= toHtml(thisOption.getConfiguratorName()) %></td>
						<td nowrap>
							<zeroio:tz timestamp="<%= thisOption.getStartDate() %>" dateOnly="true" default="&nbsp;"/>
						</td>
						<td nowrap>
							<zeroio:tz timestamp="<%= thisOption.getEndDate() %>" dateOnly="true" default="&nbsp;"/>
						</td>
						<td>
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
				<tr class="containerBody">
					<td colspan="<%= columnCount %>">
						<dhv:label name="product.noProductOptionsFound">No Product Options found.</dhv:label><br />
					</td>
				</tr>
			<%
			}
			%>
			</table>
			<br />
			<input type="hidden" name="finalsubmit" value="false">
			<input type="hidden" name="rowcount" value="0">
			<input type="hidden" name="configId" value="<%= configId %>">
			<input type="submit" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:document.optionListView.finalsubmit.value='true';document.optionListView.submit();">
			<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductCatalogOptions.do?command=List&moduleId=<%= PermissionCategory.getId()%>&catalogId=<%= ProductCatalog.getId() %>'">
			[<a href="javascript:SetChecked(1,'checkelement','optionListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
			[<a href="javascript:SetChecked(0,'checkelement','optionListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>]
</dhv:container>
</form>
