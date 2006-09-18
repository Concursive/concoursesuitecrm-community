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
<jsp:useBean id="CategoryTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ProductCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.products.base.ProductCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProductCategories.js"></script> 
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript">
  function doCheck(form) {
		if (form.dosubmit.value == "false") {
			return true;
		} else {
			return (checkForm(form));
		}
	}
	
	function checkForm(form) {
		formTest = true;
		message = "";
		if (checkNullString(form.name.value)) {
			message += label("check.product.categoryName","- Please specify the category name\r\n");
			formTest = false;
		}
		if (formTest == false) {
			alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
			return false;
		} else {
			return true;
		}
	}
</script>
<body onLoad="javascript:document.addCategory.name.focus();">
<form name="addCategory" action="ProductCategories.do?command=Insert&auto-populate=true" onSubmit="return doCheck(this);" method="post">
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
          <dhv:label name="product.addNewCategory">Add Category</dhv:label>
        </td>
      </tr>
    </table>
    <br />
    <input type="hidden" name="moduleId" value="<%= toHtmlValue(permissionCategory.getId()) %>"/>
    <input type="hidden" name="parentId" value="<%= toHtmlValue(parentCategory.getId()) %>"/>
    <input type="hidden" name="categoryId" value="<%= toHtmlValue(parentCategory.getId()) %>"/>
    <input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
    <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogEditor.do?command=List&moduleId=<%= permissionCategory.getId() %>';this.form.dosubmit.value='false';">
    <br>
    <%= showError(request, "actionError") %><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="product.addProductCategory">Add Product Category</dhv:label></strong>
        </th>
      </tr>
      <tr>
        <td nowrap class="formLabel">
          <dhv:label name="product.CategoryName">Category Name</dhv:label>
        </td>
        <td class="containerBody">
          <input type="text" size="35" maxlength="80" name="name" value="<%= toHtmlValue(ProductCategory.getName()) %>"><font color="red">*</font> <%= showAttribute(request, "nameError") %>
        </td>
      </tr>
      <%--
      <tr>
        <td nowrap class="formLabel" valign="top">
          <dhv:label name="product.parentCategory">Parent Category</dhv:label>
        </td>
        <td>
          <table border="0" cellspacing="0" cellpadding="0" class="empty">
            <tr>
              <td valign="top" nowrap>
                <div id="changeparent">
                  <% if(ProductCategory.getParentId() != -1) {%>
                    <%= toHtml(ProductCategory.getParentName()) %>
                  <%} else {%>
                    <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
                  <%}%>
                </div>
              </td>
              <td valign="top" width="100%" nowrap>
                &nbsp;[<a href="javascript:popProductCategoriesListSingle('parentLink', 'changeparent', 'filters=top&listType=single&setParentList=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
                &nbsp;[<a href="javascript:document.addCategory.parentId.value=''; changeDivContent('changeparent', label('none.selected','None Selected'))"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
                <input type="hidden" name="parentId" id="parentLink" value="<%= toHtmlValue(ProductCategory.getParentId()) %>"
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel">
          <dhv:label name="product.catalogType">Catalog Type</dhv:label>
        </td>
        <td>
          <%= CategoryTypeList.getHtmlSelect("typeId", ProductCategory.getTypeId()) %>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel">
          <dhv:label name="literal.abbreviation.name">Abbreviation</dhv:label>
        </td>
        <td>
          <input type="text" size="10" maxlength="10" name="abbreviation" value="<%= toHtmlValue(ProductCategory.getAbbreviation()) %>">
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel">
          <dhv:label name="documents.details.startDate">Start Date</dhv:label>
        </td>
        <td>
          <zeroio:dateSelect form="addCategory" field="startDate" timestamp="<%= ProductCategory.getStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
          <%= showError(request, "startDateError") %>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel">
          <dhv:label name="accounts.accountasset_include.ExpirationDate">Expiration Date</dhv:label>
        </td>
        <td>
          <zeroio:dateSelect form="addCategory" field="expirationDate" timestamp="<%= ProductCategory.getExpirationDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
        </td>
      </tr>
    </table>
    <br>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <tr>
        <th colspan="2">
          <strong><dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
        </th>
      </tr>
      <tr>
        <td nowrap class="formLabel">
          <dhv:label name="documents.details.shortDescription">Short Description</dhv:label>
        </td>
        <td>
          <input type="text" size="35" maxlength="80" name="shortDescription" value="<%= toHtmlValue(ProductCategory.getShortDescription()) %>">
        </td>
      </tr>
      <tr>
        <td valign="top" nowrap class="formLabel"><dhv:label name="documents.details.longDescription">Long Description</dhv:label></td>
        <td>
          <TEXTAREA NAME="longDescription" ROWS="3" COLS="50"><%= toString(ProductCategory.getLongDescription()) %></TEXTAREA>
        </td>
      </tr>
      --%>
    </table>
    <br />
    <input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="this.form.dosubmit.value='true';">
    <input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='ProductCatalogEditor.do?command=List&moduleId=<%= permissionCategory.getId() %>';this.form.dosubmit.value='false';">
    <input type="hidden" name="dosubmit" value="true" />
</form>
</body>
