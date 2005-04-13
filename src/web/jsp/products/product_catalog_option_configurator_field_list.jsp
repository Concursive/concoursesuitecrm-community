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
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*, org.aspcfs.modules.products.configurator.*" %>
<jsp:useBean id="permissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="productCatalog" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="productCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="productOption" class="org.aspcfs.modules.products.base.ProductOption" scope="request"/>
<jsp:useBean id="PropertyList" class="org.aspcfs.modules.products.configurator.OptionPropertyList" scope="request"/>
<jsp:useBean id="configId" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.paramForm.text_label.focus();">
<dhv:evaluate if="<%= productOption.getId() != -1 %>">
  <form name="paramForm" action="ProductCatalogOptions.do?command=UpdateOption&auto-populate=true" method="post">
  <input type="hidden" name="id" value=<%= productOption.getId() %>>
  <input type="hidden" name="optionId" value=<%= productOption.getId() %>>
  <input type="hidden" name="action" value=<%= request.getParameter("action") %>>
</dhv:evaluate>
<dhv:evaluate if="<%= productOption.getId() == -1 %>">
  <form name="paramForm" action="ProductCatalogOptions.do?command=InsertOption&auto-populate=true" method="post">
</dhv:evaluate>
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
      <a href="ProductCatalogs.do?command=Details&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.productDetails">Product Details</dhv:label></a> >
      <a href="ProductCatalogOptions.do?command=List&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.options">Options</dhv:label></a> >
      <dhv:evaluate if="<%= productOption.getId() == -1 %>">
        <dhv:label name="product.addOption">Add Option</dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if="<%= productOption.getId() != -1 %>">
        <% if (request.getParameter("return") == null)  { %>
          <a href="ProductCatalogOptions.do?command=Details&optionId=<%= productOption.getId() %>&productId=<%= productCatalog.getId() %>&moduleId=<%= permissionCategory.getId() %>&categoryId=<%= productCategory.getId() %>"><dhv:label name="product.optionDetails">Option Details</dhv:label></a> >
        <% } %>
        <dhv:label name="product.modifyOption">Modify Option</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
</table>
<br />
<dhv:container name="productcatalogs" selected="options" object="productCatalog" param="<%= param1 + "|" + param2 + "|" + param3 %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td>
      <dhv:evaluate if="<%= productOption.getId() != -1%>">
        <strong><%= toHtml(productOption.getLabel()) %></strong><br/><br/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= productOption.getId() != -1%>">
        <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>"/>
      </dhv:evaluate>
			<dhv:evaluate if="<%= productOption.getId() == -1%>">
        <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
      </dhv:evaluate>
      <% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) { %>
        <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductCatalogOptions.do?command=List&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>'"/>
      <% } else { %>
			  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductCatalogOptions.do?command=Details&optionId=<%= productOption.getId() %>&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>'"/>
      <% } %>
      <input type="hidden" name="configId" value="<%= configId %>"/>
      <input type="hidden" name="moduleId" value="<%= permissionCategory.getId() %>"/>
      <input type="hidden" name="categoryId" value="<%= productCategory.getId()  %>"/>
      <input type="hidden" name="productId" value="<%= productCatalog.getId() %>"/>
      <dhv:evaluate if="<%= (request.getParameter("return") != null && !"".equals(request.getParameter("return").trim())) %>">
        <input type="hidden" name="return" value="<%= request.getParameter("return") %>"/>
      </dhv:evaluate>
      &nbsp;<br />
			<%@ include file="product_option_configurator_field_include.jsp" %>
			&nbsp;<br />
      <dhv:evaluate if="<%= productOption.getId() != -1%>">
        <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>"/>
      </dhv:evaluate>
			<dhv:evaluate if="<%= productOption.getId() == -1%>">
        <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
      </dhv:evaluate>
      <% if (request.getParameter("return") != null && "list".equals(request.getParameter("return"))) { %>
        <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductCatalogOptions.do?command=List&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>'"/>
      <% } else { %>
			  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='ProductCatalogOptions.do?command=Details&optionId=<%= productOption.getId() %>&productId=<%= productCatalog.getId() %>&categoryId=<%= productCategory.getId() %>&moduleId=<%= permissionCategory.getId() %>'"/>
      <% } %>
		</td>
	</tr>
</table>
</dhv:container>
</form>
</body>