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
 --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*" %>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="categoryHierarchy" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="action" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
<tr>
  <td>
    <dhv:label name="product.category.selectToMove">Select a category to move the item to:</dhv:label><br>
    <% if ("moveProduct".equals(action)) { %>
       <strong><%= toHtml(productCatalog.getName()) %></strong>
    <% } else if ("moveCategory".equals(action)) { %>
      <img src="images/icons/stock_folder-16-19.gif" border="0" align="absmiddle">
      <%= toHtml(productCategory.getName()) %>
    <% } %>
  </td>
</tr>
</table>
<br />
<table cellpadding="0" cellspacing="0" width="100%" border="1" rules="cols">
  <tr class="section">
    <td valign="top" width="100%">
      <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
      <img alt="" src="images/icons/stock_open-16-19.gif" border="0" align="absmiddle" height="16" width="19"/>
    <% if ("moveProduct".equals(action)) { %>
      <dhv:evaluate if="<%= productCategory.getId() != -1 %>">
        <a href="ProductCatalogs.do?command=SaveMove&id=<%= productCatalog.getId() %>&popup=true&categoryId=-1&return=ProductCatalogEditor&param=<%= permissionCategory.getId() %>&param1=<%= productCategory.getId() %>"><dhv:label name="accounts.accounts_documents_file_move.Home">Home</dhv:label></a>
      </dhv:evaluate>
      <dhv:evaluate if="<%= productCategory.getId() == -1 %>">
        <dhv:label name="documents.documents.home">Home</dhv:label>
        (<dhv:label name="product.category.currentCategory">current category</dhv:label>)
      </dhv:evaluate>
    <% } else if ("moveCategory".equals(action)) { %>
      <dhv:evaluate if="<%= productCategory.getParentId() != -1 %>">
        <a href="ProductCategories.do?command=SaveMove&id=<%= productCategory.getId() %>&popup=true&categoryId=-1&return=ProductCatalogEditor&param=<%= permissionCategory.getId() %>&param1=<%= productCategory.getParentId() %>"><dhv:label name="accounts.accounts_documents_file_move.Home">Home</dhv:label></a>
      </dhv:evaluate>
      <dhv:evaluate if="<%= productCategory.getParentId() == -1 %>">
        <dhv:label name="documents.documents.home">Home</dhv:label>
        (<dhv:label name="product.category.currentCategory">current category</dhv:label>)
      </dhv:evaluate>
    <% } %> 
    </td>
  </tr>
<%
  int rowid = 0;
  Iterator i = categoryHierarchy.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    ProductCategory thisCategory = (ProductCategory) i.next();
%>    
 <tr class="row<%= rowid %>">
    <td valign="top">
      <% for (int j=1; j<thisCategory.getLevel(); j++) { %>
          <img border="0" src="images/treespace.gif" align="absmiddle" height="16" width="19">&nbsp;
      <%}%>
          <img border="0" src="images/treespace.gif" align="absmiddle" height="16" width="19">
          <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
          <img border="0" src="images/icons/stock_open-16-19.gif" align="absmiddle">
      <% if ("moveProduct".equals(action)) { %>
        <% if (productCategory.getId() != thisCategory.getId()) {  %>
            <a href="ProductCatalogs.do?command=SaveMove&id=<%= productCatalog.getId() %>&popup=true&categoryId=<%= thisCategory.getId() %>&return=ProductCatalogEditor&param=<%= permissionCategory.getId() %>&param1=<%= productCategory.getId() %>"><%= toHtml(thisCategory.getName()) %></a>
        <% } else { %>
            <%= toHtml(thisCategory.getName()) %>
            (<dhv:label name="product.category.currentCategory">current category</dhv:label>)
        <% } %>
      <% } else if ("moveCategory".equals(action)) { %>
        <% if (productCategory.getParentId() != thisCategory.getId() && productCategory.getId() != thisCategory.getId()) {  %>
            <a href="ProductCategories.do?command=SaveMove&id=<%= productCategory.getId() %>&popup=true&categoryId=<%= thisCategory.getId() %>&return=ProductCatalogEditor&param=<%= permissionCategory.getId() %>&param1=<%= productCategory.getParentId() %>"><%= toHtml(thisCategory.getName()) %></a>
        <% } else if (productCategory.getId() == thisCategory.getId()) { %>
            <%= toHtml(thisCategory.getName()) %>
        <% } else {%>
          <%= toHtml(thisCategory.getName()) %>
          (<dhv:label name="product.category.currentCategory">current category</dhv:label>)
        <% } %>
      <% } %>
    </td>
  </tr>
<%
  }
%>
</table>
&nbsp;<br>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
