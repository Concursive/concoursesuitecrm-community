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
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="SearchProductCategoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="product_category_list_menu.jsp" %>
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
			<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
			<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
			<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductCategories.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchCategories">Search Categories</dhv:label></a> >
			<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<a href="ProductCategories.do?command=Add&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.addProductCategory">Add Product Category</dhv:label></a>
<center><%= SearchProductCategoryListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SearchProductCategoryListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8" <% ++columnCount; %>>
      &nbsp;
    </th>
    <th nowrap width="100%" <% ++columnCount; %>>
      <strong><a href="ProductCategories.do?command=Search&moduleId=<%= PermissionCategory.getId() %>&column=pctgy.category_name"><dhv:label name="product.CategoryName">Category Name</dhv:label></a></strong>
      <%= SearchProductCategoryListInfo.getSortIcon("pctgy.category_name") %>
    </th>
		<th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="product.parentName">Parent Name</dhv:label></strong>
    </th>
		<th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="documents.details.startDate">Start Date</dhv:label></strong>
    </th>
		<th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label></strong>
    </th>
	</tr>
<%
	Iterator j = CategoryList.iterator();
	if (j.hasNext()) {
		int rowid = 0;
		int i = 0;
		while (j.hasNext()) {
			i++;
			rowid = (rowid != 1 ? 1 : 2);
			ProductCategory thisCategory = (ProductCategory) j.next();
%>
		<tr>
			<td width="8" valign="center" nowrap class="row<%= rowid %>">
				<% int status = -1; %>
				<% status = thisCategory.getEnabled() ? 1 : 0; %>
				<a href="javascript:displayMenu('select<%= i %>', 'menuCategory', '<%= thisCategory.getId() %>', '<%= status %>');"
				onMouseOver="over(0, <%= i %>)" onMouseOut="out(0, <%= i %>); hideMenu('menuCategory');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
			</td>
			<td class="row<%= rowid %>">
    	  <a href="ProductCategories.do?command=Details&categoryId=<%=thisCategory.getId()%>&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(thisCategory.getName()) %></a>
			</td>
			<td class="row<%= rowid %>" nowrap>
				<dhv:evaluate if="<%= thisCategory.getParentId() != -1 %>">
          <a href="ProductCategories.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= thisCategory.getParentId() %>"><%= toHtml(thisCategory.getParentName()) %></a>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisCategory.getParentId() == -1 %>">
          &nbsp;
        </dhv:evaluate>
			</td>
			<td class="row<%= rowid %>">
				<zeroio:tz timestamp="<%= thisCategory.getStartDate() %>" dateOnly="true" default="&nbsp;"/>
			</td>
			<td class="row<%= rowid %>">
				<zeroio:tz timestamp="<%= thisCategory.getExpirationDate() %>" dateOnly="true" default="&nbsp;"/>
			</td>
    </tr>
<%
		}
	} else {
%>
	<tr>
		<td colspan="<%= columnCount %>">
			<dhv:label name="product.noProductCategoriesFound">No Product Categories found with the specified search parameters.</dhv:label><br />
			[ <a href="ProductCategories.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a> ]
		</td>
	</tr>
<%
}
%>
</table>
<br />
<dhv:pagedListControl object="SearchProductCategoryListInfo" tdClass="row1"/>
</form>
