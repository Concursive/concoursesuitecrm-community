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
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.addCatalog.name.focus();"> 
<dhv:evaluate if='<%= (request.getParameter("actionReq") == null) || (request.getParameter("actionReq") != null && "".equals(request.getParameter("actionReq"))) %>'>
  <form name="addCatalog" action="ProductCatalogs.do?command=Insert&auto-populate=true" onSubmit="return doCheck(this);" method="post">
</dhv:evaluate>
<dhv:evaluate if='<%= (request.getParameter("actionReq") != null && "clone".equals(request.getParameter("actionReq"))) %>'>
  <form name="addCatalog" action="ProductCatalogs.do?command=SaveClone&auto-populate=true" onSubmit="return doCheck(this);" method="post">
</dhv:evaluate>
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
          <% String link = "ProductCatalogEditor.do?command=List&moduleId=" + permissionCategory.getId(); %>
          <dhv:productCategoryHierarchy link="<%= link %>" showLastLink="true"/> >
          <dhv:label name="product.addProduct">Add Product</dhv:label>
        </td>
      </tr>
    </table>
    <br />
    <input type="hidden" name="moduleId" value="<%= toHtmlValue(permissionCategory.getId()) %>"/>
    <input type="hidden" name="categoryId" value="<%= toHtmlValue(parentCategory.getId()) %>"/>
    <input type="hidden" name="productId" value="<%= toHtmlValue(request.getParameter("productId")) %>"/>
    <input type="hidden" name="actionReq" value="<%= toHtmlValue(request.getParameter("actionReq")) %>">
    <input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
    <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogEditor.do?command=List&moduleId=<%= permissionCategory.getId() %>';this.form.dosubmit.value='false';">
    &nbsp;<br>
    <dhv:formMessage />
    <%@ include file="product_catalog_include.jsp" %>
    <br /> 
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="product.addPrice">Add Price</dhv:label></strong>
        </th>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="product.msrp">MSRP</dhv:label>
        </td>
        <td>
          <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
          <input type="text" name="activePrice_msrpAmount" size="15" value="<zeroio:number value="<%= productCatalog.getActivePrice().getMsrpAmount() %>" locale="<%= User.getLocale() %>" />">
          <%= showAttribute(request, "msrpAmountError") %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="quotes.Price">Price</dhv:label>
        </td>
        <td>
          <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
          <input type="text" name="activePrice_priceAmount" size="15" value="<zeroio:number value="<%= productCatalog.getActivePrice().getPriceAmount() %>" locale="<%= User.getLocale() %>" />">
          <%= showAttribute(request, "priceAmountError") %>
        </td>
      </tr>
      <tr class="containerBody">
        <td class="formLabel">
          <dhv:label name="product.cost">Cost</dhv:label>
        </td>
        <td>
          <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
          <input type="text" name="activePrice_costAmount" size="15" value="<zeroio:number value="<%= productCatalog.getActivePrice().getCostAmount() %>" locale="<%= User.getLocale() %>" />">
          <%= showAttribute(request, "costAmountError") %>
        </td>
      </tr>
    </table>
    <br />
    <input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
    <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogEditor.do?command=List&moduleId=<%= permissionCategory.getId() %>';this.form.dosubmit.value='false';">
    <input type="hidden" name="dosubmit" value="true" />
</form>
</body>
<script type="text/javascript">
  refreshCategories();
</script>