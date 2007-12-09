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
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="SearchProductCategoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
  var thisCode = -1;
  var menu_init = false;
  var moduleId=<%=PermissionCategory.getId()%>
  var catalogId=<%=productCatalog.getId()%>
  //Set the action parameters for clicked item
  function displayMenu(loc, id, code) {
    thisCode = code;
    if (!menu_init) {
      menu_init = true;
      new ypSlideOutMenu("menuCategories", "down", 0, 0, 170, getHeight("menuCategoriesTable"));
    }
    return ypSlideOutMenu.displayDropMenu(id, loc);
  }
  function removeCategory(id) {
  var url = 'ProductCatalogs.do?command=RemoveCategoryMapping&categoryId='+id+'&moduleId='+moduleId+'&productId='+catalogId;
    window.location.href = url;
  }
</script>
<div id="menuCategoriesContainer" class="menu">
  <div id="menuCategoriesContent">
    <table id="menuCategoriesTable" class="pulldown" width="170" cellspacing="0">
      <tr onmouseover="cmOver(this)" onmouseout="cmOut(this)"
          onclick="removeCategory(thisCode);">
        <th valign="top">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        </th>
        <td width="100%">
          <dhv:label name="button.remove">Remove</dhv:label>
        </td>
      </tr>
    </table>
  </div>
</div>
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
			<dhv:label name="product.editor">Editor</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="abovetab">
    <td>
      <% String link = "ProductCatalogEditor.do?command=List&moduleId=" + PermissionCategory.getId(); %>
      <dhv:productCategoryHierarchy link="<%= link %>" showLastLink="true"/> > 
      <a href="ProductCatalogs.do?command=Details&productId=<%= productCatalog.getId() %>&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= productCatalog.getCategoryId() %>"><dhv:label name="product.productDetails">Product Details</dhv:label></a> >
      <dhv:label name="product.Categories">Categories</dhv:label>
    </td>
  </tr>
</table>
<br />
    <% int columnCount = 0; %>
<% String param1 = "productId=" + productCatalog.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<% String param3 = "categoryId=" + productCatalog.getCategoryId(); %>
<dhv:container name="productcatalogs" selected="categories" object="productCatalog" param='<%= param1 + "|" + param2 + "|" + param3 %>'>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
<tr>
<td>
<a  href="javascript: popURL('ProductCatalogs.do?command=Move&productId=' + <%=productCatalog.getId()%> + '&categoryId=' + <%=productCatalog.getCategoryId()%> + '&moduleId=<%= PermissionCategory.getId() %>&return=ProductCatalogEditor.do?command=List', 'Products', '400', '375', 'yes', 'yes');" ><dhv:label name="product.manageCategoryAssociations">Manage category associations</dhv:label></a>
&nbsp;<br/>
   <dhv:formMessage />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th width="8" <% ++columnCount; %>>
          &nbsp;
        </th>
        <th nowrap width="100%" <% ++columnCount; %>>
          <strong><dhv:label name="product.CategoryName">Category Name</dhv:label></strong>
      <%= SearchProductCategoryListInfo.getSortIcon("pctgy.category_name") %>
        </th>
		
        <th nowrap <% ++columnCount; %>>
      <strong><dhv:label name="product.productCount">Products Count</dhv:label></strong>
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
				<a href="javascript:displayMenu('select<%= i %>', 'menuCategories', <%= thisCategory.getId() %>);"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuCategories');">
       <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
          </td>
          <td class="row<%= rowid %>">
          <%if(thisCategory.getFullPath()!=null){
          ProductCategoryList fullPath = thisCategory.getFullPath();
          Iterator iter = (Iterator) fullPath.iterator();
          while (iter.hasNext()) {
           ProductCategory pc = (ProductCategory)iter.next();
          %>
          <a href="ProductCatalogEditor.do?command=List&moduleId=<%= PermissionCategory.getId()%>&categoryId=<%=pc.getId()%>"><%=pc.getName()%></a><%if(iter.hasNext()){%> > <%}%>
    	  <%}
        }%>
          </td>
			<td class="row<%= rowid %>" nowrap>
				<dhv:evaluate if="<%= thisCategory.getProductList() != null %>">
          <%= thisCategory.getProductList().size()%>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisCategory.getProductList() == null%>">
          &nbsp;
        </dhv:evaluate>
          </td>
        </tr>
    <%
        }
      } else {
    %>
	<tr>
        <td colspan="<%= columnCount %>">
			<dhv:label name="product.noProductCategoriesFound">No Product Categories found.</dhv:label><br />
        </td>
      </tr>
    <%
    }
    %>
    </table>
</td></tr></table>
</dhv:container>
<br />
</form>
