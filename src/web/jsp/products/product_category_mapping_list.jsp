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
<%@ page import="java.util.*, org.aspcfs.modules.products.base.*, org.aspcfs.utils.web.*" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="ProductCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="MappingList" class="org.aspcfs.modules.products.base.ProductCategoryList" scope="request"/>
<jsp:useBean id="CategoryTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="product_category_mapping_list_menu.jsp" %> 
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
			<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
			<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
			<a href="ProductCatalogEditor.do?command=Options&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.editor">Editor</dhv:label></a> >
			<a href="ProductCategories.do?command=SearchForm&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="product.searchCategories">Search Categories</dhv:label></a> >
      <a href="ProductCategories.do?command=Search&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
			<a href="ProductCategories.do?command=Details&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.categoryDetails">Category Details</dhv:label></a> >
			<dhv:label name="product.categoryLinks">Category Links</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<%@ include file="product_category_header_include.jsp" %>
<% String param1 = "categoryId=" + ProductCategory.getId(); %>
<% String param2 = "moduleId=" + PermissionCategory.getId(); %>
<dhv:container name="productcategories" selected="categorylinks" object="ProductCategory" param="<%= param1 + "|" + param2 %>">
  <a href="ProductCategories.do?command=AddMapping&moduleId=<%= PermissionCategory.getId() %>&categoryId=<%= ProductCategory.getId() %>"><dhv:label name="product.addCategoryLink">Add Category Link</dhv:label></a>
  <br>&nbsp;
  <%
    Iterator items = CategoryTypeList.iterator();
    boolean exists = false;
    boolean noCategoryTypeExists = false;
    if (items.hasNext()){
      while (items.hasNext()) {
        LookupElement thisElement = (LookupElement) items.next();
        if (MappingList.hasMappingsWithType(thisElement.getCode())) {
          exists = true;
    %>
        <table align="center" width="100%" cellpadding="4" cellspacing="0" border="0">
          <tr>
            <th nowrap valign="bottom" align="left" class="pagedListTab"><%= toHtml(thisElement.getDescription()) %></th>
            <td nowrap width="100%" valign="bottom" align="left">&nbsp;</td>
          </tr>
        </table>
        <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
          <tr>
            <th width="8">&nbsp;</th>
            <th><dhv:label name="product.CategoryName">Category Name</dhv:label></th>
          </tr>
          <%
            Iterator j = MappingList.iterator();
            int rowid = 0;
            int i = 0;
            while (j.hasNext()) {
              i++;
              rowid = (rowid != 1 ? 1 : 2);
              ProductCategory thisCategory = (ProductCategory) j.next();
              if (thisElement.getCode() == thisCategory.getTypeId()) {
          %>
          <tr class="containerBody">
            <td width="8" valign="center" nowrap class="row<%= rowid %>">
              <% int status = -1;%>
              <%-- <% status = thisCategory.getEnabled() %> --%>
              <%-- Use the unique id for opening the menu, and toggling the graphics --%>
              <a href="javascript:displayMenu('select<%= i %>','menuMapping', '<%= thisCategory.getId() %>', '<%= status %>');"
              onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuMapping');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
            </td>
            <td class="row<%= rowid %>"><%= toHtml(thisCategory.getName()) %></td>
          </tr>
          <%
            }
          }
          %>
          </table>
          &nbsp;<br />
      <%
         }
       }
     }
      Iterator j = MappingList.iterator();
      while (j.hasNext()) {
        ProductCategory thisCategory = (ProductCategory) j.next();
        System.out.println(thisCategory.getName() + " ----- " + thisCategory.getTypeId());
        if (thisCategory.getTypeId() == -1) {
          noCategoryTypeExists = true;
          break;
        }
       }
       if (noCategoryTypeExists){
     %>
        <table align="center" width="100%" cellpadding="4" cellspacing="0" border="0">
          <tr>
            <th nowrap valign="bottom" align="left" class="pagedListTab"><dhv:label name="product.undefinedCategoryType">Undefined Category Type</dhv:label></th>
            <td nowrap width="100%" valign="bottom" align="left">&nbsp;</td>
          </tr>
        </table>
        <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
          <tr>
            <th width="8">&nbsp;</th>
            <th><dhv:label name="product.CategoryName">Category Name</dhv:label></th>
          </tr>
          <%
            j = MappingList.iterator();
            int rowid = 0;
            int i = 0;
            while (j.hasNext()) {
              i++;
              ProductCategory thisCategory = (ProductCategory) j.next();
              if (thisCategory.getTypeId() == -1) {
              rowid = (rowid != 1 ? 1 : 2);
          %>
          <tr class="containerBody">
            <td width="8" valign="center" nowrap class="row<%= rowid %>">
              <% int status = -1;%>
              <%-- <% status = thisCategory.getEnabled() %> --%>
              <%-- Use the unique id for opening the menu, and toggling the graphics --%>
              <a href="javascript:displayMenu('select<%= i %>','menuMapping', '<%= thisCategory.getId() %>', '<%= status %>');"
              onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuMapping');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
            </td>
            <td class="row<%= rowid %>"><%= toHtml(thisCategory.getName()) %></td>
          </tr>
    <%
          }
        }
    %>
        </table>
    <%
      }
     if ((!exists)  && (!noCategoryTypeExists)){
    %>
    <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
      <tr>
        <th width="8">&nbsp;</th>
        <th><dhv:label name="product.CategoryName">Category Name</dhv:label></th>
      </tr>
      <tr class="containerBody">
        <td colspan="2"><dhv:label name="product.noMapingsExist">No Mappings exist</dhv:label></td>
      </tr>
    </table>
    <%
     }
    %>
</dhv:container>