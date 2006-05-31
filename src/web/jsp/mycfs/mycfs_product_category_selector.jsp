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
<jsp:useBean id="ParentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="TrailIds" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="TrailValues" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="CategoryTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Filters" class="org.aspcfs.modules.base.FilterList" scope="request"/>
<jsp:useBean id="SelectedCategories" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="IgnoredCategories" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="FinalCategories" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="ProductCategoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></script>
<script language="javascript">
  function submitForm(catId, catName) {
		document.categoryListView.action = "ProductCategorySelector.do?command=ListProductCategories&categoryId=" + catId; 
		document.categoryListView.catName.value = catName;
		document.categoryListView.submit();
	}
</script>
<%
  Iterator p = SelectedCategories.iterator();
  String previousSelection = "";
  while (p.hasNext()) {
    String catId = (String) p.next();
    if (!"".equals(previousSelection)) {
      previousSelection = previousSelection + "|";
    }
    previousSelection = previousSelection + catId;
  }
  
  Iterator q = IgnoredCategories.iterator();
  String ignoreIds = "";
  while (q.hasNext()) {
    String catId = (String) q.next();
    if (!"".equals(ignoreIds)) {
      ignoreIds = ignoreIds + "|";
    }
    ignoreIds = ignoreIds + catId;
  }
%>
<form name="categoryListView" method="post" action="ProductCategorySelector.do?command=ListProductCategories">
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
						<a href="javascript:document.categoryListView.action='ProductCategorySelector.do?command=ListProductCategories&categoryId=<%= id %>';document.categoryListView.cleanTrails.value='true';document.categoryListView.catName.value='<%= value %>';document.categoryListView.submit();"><%= value %></a> >
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
			<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ProductCategoryListInfo" showHiddenParams="true" enableJScript="true" form="categoryListView"/>
		</td>
	</tr>
</table>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><strong><dhv:label name="product.childCategory.text">Clicking on a Category name displays a list of its child categories.</dhv:label></strong></td>
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
				<tr class="row<%= rowid+(SelectedCategories.indexOf(categoryId) != -1 ? "hl" : "") %>">
          <td align="center" nowrap width="8">
<%
  if ("list".equals(request.getParameter("listType"))) {
%>
         <input type="checkbox" name="category<%= count %>" value="<%= thisCategory.getId() %>" <%= (SelectedCategories.indexOf(categoryId) != -1 ? " checked" : "") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
<%} else {%>
         <a href="javascript:document.categoryListView.finalsubmit.value = 'true';setFieldSubmit('rowcount','<%= count %>','categoryListView');">Select</a>
<%} %>
            <input type="hidden" name="hiddenCategoryId<%= count %>" value="<%= thisCategory.getId() %>">
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
  <input type="hidden" name="catalogId" value="<%= request.getParameter("catalogId") %>">
  <input type="hidden" name="moduleId" value="<%= request.getParameter("moduleId") %>">
  <input type="hidden" name="rowcount" value="0">
  <input type="hidden" name="listType" value="<%= toHtmlValue(request.getParameter("listType")) %>">
  <input type="hidden" name="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
  <input type="hidden" name="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>">
  <input type="hidden" name="setParentList" value="<%= toHtmlValue(request.getParameter("setParentList")) %>"/>
  <input type="hidden" name="catName" value=""/>
  <input type="hidden" name="finalsubmit" value="false"/>
  <input type="hidden" name="cleanTrails" value=""/>
  <input type="hidden" name="previousSelection" value="<%= previousSelection %>"/>
  <input type="hidden" name="ignoreIds" value="<%= ignoreIds %>"/>
</table>
<% if("list".equals(request.getParameter("listType"))){ %>
  <input type="button" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:setFieldSubmit('finalsubmit','true','categoryListView');">
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
  <a href="javascript:SetChecked(1,'category','categoryListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>
  <a href="javascript:SetChecked(0,'category','categoryListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>
<%}else{%>
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
<%}%>
</form>
<%} else {%>
<%-- The final submit --%>
<%
  if (!"true".equals(request.getParameter("setParentList"))) {
%>
  <%-- Parent need not be set. Just call the requested action and close the window --%>
  <body onLoad="javascript:opener.window.location.href='ProductCatalogs.do?command=AddCategoryMappings&catalogId=<%= request.getParameter("catalogId") %>&moduleId=<%= request.getParameter("moduleId") %>&finalElements=' + finalElements;window.close(); ">
  <script>finalElements = new Array();</script>
  <%
  Iterator m = FinalCategories.iterator();
  int cnt = -1;
  while (m.hasNext()) {
    cnt++;
    ProductCategory thisCategory = (ProductCategory) m.next();
%>
  <script>
    finalElements[<%= cnt %>] = "<%= thisCategory.getId() %>";
  </script>
<%	
  }
%>
  </body>
<%  
  } else {
%>
  <body onLoad="javascript:setParentList(categoryIds, categoryNames, '<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= User.getBrowserId() %>');window.close()">
  <script>categoryIds = new Array();categoryNames = new Array();</script>
<%
  Iterator i = FinalCategories.iterator();
  int count = -1;
  while (i.hasNext()) {
    count++;
    ProductCategory thisCategory = (ProductCategory) i.next();
%>
  <script>
    categoryIds[<%= count %>] = "<%= thisCategory.getId() %>";
    categoryNames[<%= count %>] = "<%= toJavaScript(thisCategory.getName()) %>";
  </script>
<%	
  }
%>
  </body>
<%
  }
}
%>
