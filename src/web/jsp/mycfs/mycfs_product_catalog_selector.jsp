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
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="ProductList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="ProductListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SelectedProducts" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="FinalProducts" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCatalogs.js"></script>
<script language="javascript">
  function init() {
  <% 
		String productName = request.getParameter("productName") ;
		String productSku = request.getParameter("productSku");
		if (productName == null || "".equals(productName.trim())){
	%>
			document.productListView.productName.value = "Product Name";
	<%
		}
		if (productSku == null || "".equals(productSku.trim())){
	%>
		  document.productListView.productSku.value = "Product SKU";
	<%
		}
	%>
  }
  
  function clearSearchFields(clear, field) {
		if (clear) {
			// Clear the search fields since clear button was clicked
			document.productListView.productName.value = "Product Name";
			document.productListView.productSku.value = "Product SKU";
		} else {
			// The search fields recieved focus
			if (field.value == "Product Name" || field.value == "Product SKU") {
				field.value = "" ;
			}
		}
	}
</script>
<%
  Iterator p = SelectedProducts.iterator();
  String previousSelection = "";
  while (p.hasNext()) {
    String catId = (String) p.next();
    if (!"".equals(previousSelection)) {
      previousSelection = previousSelection + "|";
    }
    previousSelection = previousSelection + catId;
  }
%>
<%
  if (!"true".equals(request.getParameter("finalsubmit"))) {
%>
<%-- Navigating the contact list, not the final submit --%>
<body onload="javascript:init();">
<form name="productListView" method="post" action="ProductCatalogSelector.do?command=ListProductCatalogs">
  <table cellpadding="6" cellspacing="0" width="100%" border="0">
		<tr>
			<td align="center" valign="center" bgcolor="#d3d1d1">
				<strong><dhv:label name="button.search">Search</dhv:label></strong>
				<input type="text" name="productName" onFocus="clearSearchFields(false, this)" value="<%= toHtmlValue(request.getParameter("productName")) %>">
				<input type="text" name="productSku" onFocus="clearSearchFields(false, this)" value="<%= toHtmlValue(request.getParameter("productSku")) %>">
				<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
				<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="clearSearchFields(true, '')">
			</td>
		</tr>
	</table>
	&nbsp;<br>	
  <input type="hidden" name="letter">
  <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ProductListInfo" showHiddenParams="true" enableJScript="true" form="productListView"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th align="center" width="8">
        &nbsp;
      </th>
      <th>
        <strong><dhv:label name="contacts.name">Name</dhv:label></strong>
      </th>
      <th>
        <strong><dhv:label name="quotes.sku">SKU</dhv:label></strong>
      </th>
    </tr>
<%
	Iterator j = ProductList.iterator();
	if (j.hasNext()) {
		int rowid = 0;
		int count = 0;
    while (j.hasNext()) {
			count++;
      rowid = (rowid != 1 ? 1 : 2);
      ProductCatalog thisProduct = (ProductCatalog) j.next();
      String productId = String.valueOf(thisProduct.getId());
%>
    <tr class="row<%= rowid+(SelectedProducts.indexOf(productId) != -1 ? "hl" : "") %>">
      <td align="center" nowrap width="8">
<% 
  if ("list".equals(request.getParameter("listType"))) { 
%>
     <input type="checkbox" name="product<%= count %>" value="<%= thisProduct.getId() %>" <%= (SelectedProducts.indexOf(productId) != -1 ? " checked" : "") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
<%} else {%>
     <a href="javascript:document.productListView.finalsubmit.value = 'true';setFieldSubmit('rowcount','<%= count %>','productListView');">Select</a>
<%}%>
        <input type="hidden" name="hiddenProductId<%= count %>" value="<%= thisProduct.getId() %>">
      </td>
      <td nowrap>
          <%= toHtml(thisProduct.getName()) %>
      </td>
      <td nowrap>
          <%= toHtml(thisProduct.getSku()) %>
      </td>
    </tr>
<%
    }
  } else {
%>
    <tr>
      <td class="containerBody" colspan="4">
        <dhv:label name="calendar.noProductsMatchedQuery">No products matched query.</dhv:label>
      </td>
    </tr>
<%}%>
    <input type="hidden" name="productId" value="<%= request.getParameter("productId") %>">
    <input type="hidden" name="categoryId" value="<%= request.getParameter("categoryId") %>">
    <input type="hidden" name="moduleId" value="<%= request.getParameter("moduleId") %>">
    <input type="hidden" name="finalsubmit" value="false">
    <input type="hidden" name="rowcount" value="0">
    <input type="hidden" name="setParentList" value="<%= toHtmlValue(request.getParameter("setParentList")) %>">
    <input type="hidden" name="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
    <input type="hidden" name="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>">
    <input type="hidden" name="listType" value="<%= toHtmlValue(request.getParameter("listType")) %>">
    <input type="hidden" name="previousSelection" value="<%= previousSelection %>"/>
  </table>
<% if("list".equals(request.getParameter("listType"))){ %>
  <input type="button" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:setFieldSubmit('finalsubmit','true','productListView');">
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
  <a href="javascript:SetChecked(1,'product','productListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>
  <a href="javascript:SetChecked(0,'product','productListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>
<%}else{%>
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
<%}%>
</form>
<%} else { %>
<%-- The final submit --%>
<%
  if (!"true".equals(request.getParameter("setParentList"))) {
%>
  <%-- Parent need not be set. Just call the requested action and close the window --%>
  <body onLoad="javascript:opener.window.location.href='ProductCategoryProducts.do?command=AddCategoryMappings&moduleId=<%= request.getParameter("moduleId") %>&categoryId=<%= request.getParameter("categoryId") %>&finalElements=' + finalElements;window.close(); ">
  <script>finalElements = new Array();</script>
  <%
  Iterator m = FinalProducts.iterator();
  int cnt = -1;
  while (m.hasNext()) {
    cnt++;
    ProductCatalog thisProduct = (ProductCatalog) m.next();
%>
  <script>
    finalElements[<%= cnt %>] = "<%= thisProduct.getId() %>";
  </script>
<%	
  }
%>
  </body>
<%  
  } else {
%>
  <body onLoad="javascript:setParentList(productIds, productNames, '<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= User.getBrowserId() %>');window.close()">
  <script>productIds = new Array();productNames = new Array();</script>
<%
  Iterator i = FinalProducts.iterator();
  int count = -1;
  while (i.hasNext()) {
    count++;
    ProductCatalog thisProduct = (ProductCatalog) i.next();
%>
  <script>
    productIds[<%= count %>] = "<%= thisProduct.getId() %>";
    productNames[<%= count %>] = "<%= toJavaScript(thisProduct.getName()) %>";
  </script>
<%	
  }
%>
  </body>
<%
  }
}
%>
