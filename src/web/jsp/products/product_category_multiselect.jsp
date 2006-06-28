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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page import="org.aspcfs.modules.products.base.*"%>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request" />
<jsp:useBean id="categoryHierarchy" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request" />
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request" />
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request" />
<jsp:useBean id="action" class="java.lang.String" scope="request" />
<jsp:useBean id="parentId" class="java.lang.String" scope="request" />
<jsp:useBean id="returnAction" class="java.lang.String" scope="request" />
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/categorytree.js"></SCRIPT>
<link type="text/css" rel="stylesheet" href="css/categorytree.css">

<%@ include file="../initPage.jsp"%>
<%ProductCategoryList list = productCatalog.getCategoryList();%>
<form name="categoryTree" action="ProductCatalogs.do?command=SaveMove&popup=true" onsubmit="javascript:return true;" method="post">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
    <tr>
      <td>
        <dhv:label name="product.category.selectCategories">Select a category for product</dhv:label>
        <br>
        <strong><%=toHtml(productCatalog.getName())%></strong>:
      </td>
    </tr>
  </table>
  <%String path = "ProductCatalogs.do?command=Move&productId="+productCatalog.getId()+"&moduleId="+permissionCategory.getId()+"&categoryId="+productCatalog.getCategoryId();%>
  <dhv:productCategoryTree items="categoryHierarchy" checked="checkedList" parentItemProperty="parentId" path="<%=path%>" home="true"></dhv:productCategoryTree>
  <input type="hidden" name="id" value="<%= productCatalog.getId() %>" />
  <input type="hidden" name="returnAction" value="<%= returnAction%>" />

  &nbsp;
  <br>
  <input type="submit" value="<dhv:label name="button.Done">Save</dhv:label>">
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
</form>
