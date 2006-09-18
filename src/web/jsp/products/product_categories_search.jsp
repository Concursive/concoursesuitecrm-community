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
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SearchProductCategoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="Javascript">
  function clearForm() {
    document.forms['searchProductCategory'].searchName.value="";
    document.forms['searchProductCategory'].searchAbbreviation.value="";
    document.forms['searchProductCategory'].listView.options.selectedIndex = 0;
    document.forms['searchProductCategory'].listFilter1.options.selectedIndex = 0;
    document.forms['searchProductCategory'].searchName.focus();
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<dhv:label name="product.searchCategories">Search Categories</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<body onLoad="javascript:document.searchProductCategory.searchName.focus();">
<form name="searchProductCategory" action="ProductCategories.do?command=Search" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="product.searchProductCategories">Search Product Categories</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="product.CategoryName">Category Name</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchName" value="<%= SearchProductCategoryListInfo.getSearchOptionValue("searchName") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="literal.abbreviation.name">Abbreviation</dhv:label>
    </td>
    <td>
      <input type="text" size="35" name="searchAbbreviation" value="<%= SearchProductCategoryListInfo.getSearchOptionValue("searchAbbreviation") %>">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="product.catalogType">Catalog Type</dhv:label>
    </td>
    <td>
      <%= TypeSelect.getHtmlSelect("listFilter1", SearchProductCategoryListInfo.getFilterKey("listFilter1")) %>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
    </td>
    <td align="left" valign="bottom">
      <select size="1" name="listView">
        <option <%= SearchProductCategoryListInfo.getOptionValue("all") %>><dhv:label name="product.allCategories">All Categories</dhv:label></option>
        <option <%= SearchProductCategoryListInfo.getOptionValue("enabled") %>><dhv:label name="product.activeCategories">Active Categories</dhv:label></option>
        <option <%= SearchProductCategoryListInfo.getOptionValue("disabled") %>><dhv:label name="product.inactiveCategories">Inactive Categories</dhv:label></option>
      </select>
    </td>
  </tr>
</table>
&nbsp;<br>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="moduleId" value="<%= PermissionCategory.getId() %>" />
</form>
</body>
