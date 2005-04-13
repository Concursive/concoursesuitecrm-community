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
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.addCatalog.name.focus();"> 
<dhv:evaluate if="<%= (request.getParameter("actionReq") == null) || (request.getParameter("actionReq") != null && "".equals(request.getParameter("actionReq"))) %>">
  <form name="addCatalog" action="ProductCatalogs.do?command=Insert&auto-populate=true" onSubmit="return doCheck(this);" method="post">
</dhv:evaluate>
<dhv:evaluate if="<%= (request.getParameter("actionReq") != null && "clone".equals(request.getParameter("actionReq"))) %>">
  <form name="addCatalog" action="ProductCatalogs.do?command=SaveClone&auto-populate=true" onSubmit="return doCheck(this);" method="post">
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
    <input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
    <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogEditor.do?command=List&moduleId=<%= permissionCategory.getId() %>';this.form.dosubmit.value='false';">
    <input type="hidden" name="dosubmit" value="true" />
</form>
</body>