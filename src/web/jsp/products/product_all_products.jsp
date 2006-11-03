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
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="productCatalogListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="product_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ProductCatalogEditor.do?command=List"><dhv:label name="product.products">Products</dhv:label></a> >
<dhv:label name="product.laborCategoryEditor">Labor Category Editor</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="product-catalog-product-add"><a href="ProductsCatalog.do?command=AddProduct&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.addItem">Add Item</dhv:label></a></dhv:permission>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="productCatalogListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th>
      &nbsp;
    </th>
    <th width="20%"> 
      <strong><dhv:label name="product.code">Code</dhv:label></strong>
    </th>
    <th width="80%"> 
      <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
    </th>
  </tr>
 <%
    Iterator itr = productList.iterator();
    if (itr.hasNext()){
		  int rowid = 0;
    	int i = 0;
      while (itr.hasNext()) {
        i++;
	      rowid = (rowid != 1?1:2);
    	  ProductCatalog thisProduct = (ProductCatalog)itr.next();
  %>
       <tr class="row<%= rowid %>">
        <td>
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
           <a href="javascript:displayMenu('select<%= i %>','menuProduct', '<%= thisProduct.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuProduct');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
        </td>
        <td>
          <a href="ProductsCatalog.do?command=ViewProductDetails&productId=<%=thisProduct.getId()%>&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(thisProduct.getSku()) %></a>
        </td>
        <td>
          <%= toHtml(thisProduct.getName()) %>
        </td>
        </tr>
     <%}%>
   <%}else{%>
       <tr class="row2">
        <td colspan="3"><dhv:label name="calendar.noProductsFound">No products found.</dhv:label></td>
       </tr>
   <%}%>
  </table>
<br>
<dhv:pagedListControl object="productCatalogListInfo"/>

       
