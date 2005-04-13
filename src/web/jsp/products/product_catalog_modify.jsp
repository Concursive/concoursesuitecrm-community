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
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.addCatalog.name.focus();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
			<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
			<a href="Admin.do?command=ConfigDetails&moduleId=<%= permissionCategory.getId() %>"><%= toHtml(permissionCategory.getCategory()) %></a> >
			<dhv:label name="product.editor">Editor</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<% String param1 = "productId=" + productCatalog.getId(); %>
<% String param2 = "moduleId=" + permissionCategory.getId(); %>
<% String param3 = "categoryId=" + productCategory.getId(); %>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="abovetab">
    <td>
      <% String link = "ProductCatalogEditor.do?command=List&moduleId=" + permissionCategory.getId(); %>
      <dhv:productCategoryHierarchy link="<%= link %>" showLastLink="true"/> > 
  <% if (request.getParameter("return") != null) { %>
    <% if (!"list".equals(request.getParameter("return")))  {%>
      <a href="ProductCatalogs.do?command=Details&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.productDetails">Product Details</dhv:label></a> >
    <% } %>  
  <% } else { %>
      <a href="ProductCatalogs.do?command=Details&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.productDetails">Product Details</dhv:label></a> >
  <% } %>
      <dhv:label name="product.modifyProduct">Modify Product</dhv:label>
    </td>
  </tr>
</table>
<br />
<dhv:container name="productcatalogs" selected="details" object="productCatalog" param="<%= param1 + "|" + param2 + "|" + param3 %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td>
      <form name="addCatalog" action="ProductCatalogs.do?command=Update&auto-populate=true" onSubmit="return doCheck(this);" method="post">
			<input type="hidden" name="moduleId" value="<%= toHtmlValue(permissionCategory.getId()) %>"/>
			<input type="hidden" name="id" value="<%= productCatalog.getId() %>"/>
			<input type="hidden" name="productId" value="<%= productCatalog.getId() %>"/>
			<input type="hidden" name="categoryId" value="<%= productCategory.getId() %>"/>
      <dhv:evaluate if="<%= (request.getParameter("return") != null) %>">
        <input type="hidden" name="return" value="<%= request.getParameter("return") %>"/>
      </dhv:evaluate>  
      <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
			<% if (request.getParameter("return") != null) { %>
        <% if (request.getParameter("return").equals("list")) { %>
            <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogEditor.do?command=List&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>';this.form.dosubmit.value='false';">
        <% } %>
      <% } else {%>
            <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogs.do?command=Details&moduleId=<%= permissionCategory.getId() %>&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>';this.form.dosubmit.value='false';">
      <% } %>
			&nbsp;<br>
      <dhv:formMessage />
      <%@ include file="product_catalog_include.jsp" %>
			&nbsp;<br />
			<input type="submit" value="<dhv:label name="button.update">Update</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
    <% if (request.getParameter("return") != null) { %>
    <% if (request.getParameter("return").equals("list")) { %>
        <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogEditor.do?command=List&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>';this.form.dosubmit.value='false';">
    <% } %>
    <% } else {%>
        <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogs.do?command=Details&moduleId=<%= permissionCategory.getId() %>&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>';this.form.dosubmit.value='false';">
        <% } %>
			<input type="hidden" name="dosubmit" value="true" />
			</form>
      </td>
    </tr>
</table>
</dhv:container>
</body>
