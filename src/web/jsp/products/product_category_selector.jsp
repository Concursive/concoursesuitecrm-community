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
<jsp:useBean id="ParentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="TrailIds" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="TrailValues" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="CategoryTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Filters" class="org.aspcfs.modules.base.FilterList" scope="request"/>
<jsp:useBean id="ProductCategoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="javascript">
  function finalSubmit(categoryId, displayValue, hiddenFieldId, displayFieldId) {
    opener.changeDivContent(displayFieldId, displayValue);
    opener.setParentList(hiddenFieldId, categoryId);
		document.categoryListView.finalsubmit.value='true'; 
		document.categoryListView.submit();
  }
	
	function submitForm(catId, catName) {
		document.categoryListView.action = "ProductCategories.do?command=CategoryList&categoryId=" + catId; 
		document.categoryListView.catName.value = catName;
		document.categoryListView.submit();
	}
</script>
<%
		String hiddenFieldId = (String) request.getAttribute("hiddenFieldId");
		String displayFieldId = (String) request.getAttribute("displayFieldId");
%>
<form name="categoryListView" method="post" action="ProductCategories.do?command=CategoryList">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<% if (TrailIds.size() > 0) { %>
				<a href="javascript:document.categoryListView.submit();"><dhv:label name="product.topLevel">Top Level</dhv:label></a> >
			<% } else { %>
				<dhv:label name="product.topLevel">Top Level</dhv:label>
			<% } %>
			<%
				Iterator ids = TrailIds.iterator();
				Iterator values = TrailValues.iterator();
				while (ids.hasNext()) {
					String id = (String) ids.next();
					String value = (String) values.next();
					if (id != null && value != null &&
							!"".equals(id.trim()) && !"".equals(value.trim())) {
						if (!value.equals(request.getParameter("catName"))) {
			%>
						<a href="javascript:document.categoryListView.action='ProductCategories.do?command=CategoryList&categoryId=<%= id %>';document.categoryListView.cleanTrails.value='true';document.categoryListView.catName.value='<%= value %>';document.categoryListView.submit();"><%= value %></a> >
			<%
						}
					}
				}
			%>
			<%= toHtml(request.getParameter("catName")) %>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<%
	if (!"true".equals(request.getParameter("finalsubmit"))) {
%>
<input type="hidden" name="hiddenFieldId" value="<%= hiddenFieldId %>" />
<input type="hidden" name="displayFieldId" value="<%= displayFieldId %>" />
<input type="hidden" name="catName" value=""/>
<input type="hidden" name="finalsubmit" value="false"/>
<input type="hidden" name="cleanTrails" value=""/>
<% if (request.getParameter("catMaster") != null) { %>
	<input type="hidden" name="catMaster" value="<%= request.getParameter("catMaster") %>"/>
<% } else { %>
	<input type="hidden" name="catMaster" value=""/>
<% } %>
<table width="100%" border="0">
	<tr>
		<td>
			<%= CategoryTypeList.getHtmlSelect("listFilter1", ProductCategoryListInfo.getFilterKey("listFilter1")) %>
		</td>
		<td>
			<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="ProductCategoryListInfo" showHiddenParams="true" enableJScript="true" form="categoryListView"/>
		</td>
	</tr>
</table>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="product.childCategory.text">Clicking on a Category name displays a list of its child categories.</dhv:label></td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th align="center" width="8">
			&nbsp;
		</th>
		<th>
			<strong><dhv:label name="product.CategoryName">Category Name</dhv:label></strong>
		</th>
	</tr>
	<%
		Iterator j = CategoryList.iterator();
		if (j.hasNext()) {
			int rowid = 0;
			int count = 0;
			while (j.hasNext()) {
				count++;
				rowid = (rowid != 1 ? 1 : 2);
				ProductCategory thisCategory = (ProductCategory) j.next();
				String categoryId = String.valueOf(thisCategory.getId());
	%>
				<tr class="row<%= rowid %>">
					<td align="center" nowrap width="8">
						<a href="javascript:finalSubmit('<%= thisCategory.getId() %>', '<%= thisCategory.getName() %>', '<%= hiddenFieldId %>', '<%= displayFieldId %>');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>
					</td>
					<td nowrap>
						<a href="javascript:submitForm('<%= thisCategory.getId() %>','<%= thisCategory.getName() %>')"><%= toHtml(thisCategory.getName()) %>
					</td>
				</tr>
	<%
			}
		} else {
  %>
			<tr>
      <td class="containerBody" colspan="4">
        <dhv:label name="product.noCategoriesMatchedQuery">No categories matched query.</dhv:label>
      </td>
    </tr>
	<%
		}
	%>
</table>
</form>
<%} else {%>
<%-- The final submit --%>
	<body onLoad="javascript:window.close();"></body>
<%
	}
%>
