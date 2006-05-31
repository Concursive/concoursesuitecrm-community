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
  - Version: $Id: $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*,org.aspcfs.modules.admin.base.*" %>
<jsp:useBean id="category" class="org.aspcfs.modules.website.base.PortfolioCategory" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script type="text/javascript">

  function reopen(extension) {
    window.location.href='PortfolioEditor.do?command=AddCategory&parentId=<%= category.getParentId() %>'+extension;
  }

  function checkForm(form) {
    formTest = true;
    message = "";
    if (checkNullString(form.name.value)) {
      message += label("name.required","- Check that the name is entered\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</script>
<form name="addCategory" action="PortfolioEditor.do?command=SaveCategory&auto-populate=true" method="post" onSubmit="return checkForm(this);">
<input type="hidden" name="id" value="<%= category.getId() %>"/>
<input type="hidden" name="parentId" value="<%= category.getParentId() %>"/>
<input type="hidden" name="return" value="<%= (request.getAttribute("return") != null ? request.getAttribute("return"):"")%>"/>
<input type="hidden" name="positionId" value="<%= category.getPositionId() %>"/>
<input type="hidden" name="dosubmit" value="true"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Website.do"><dhv:label name="website.website">Website</dhv:label></a> >
      <dhv:label name="product.addNewCategory">Add Category</dhv:label>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%-- Category Trails --%>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td>
      <dhv:productCategoryHierarchy link="PortfolioEditor.do?command=List" showLastLink="true"/>
    </td>
  </tr>
</table>
<%-- End Category Trails --%>
<dhv:formMessage showSpace="true"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr><th colspan="2"><strong>
  <dhv:evaluate if="<%= category.getId() == -1 %>">
    <dhv:label name="category.addCategory">Add Category</dhv:label>
  </dhv:evaluate><dhv:evaluate if="<%= category.getId() != -1 %>">
    <dhv:label name="product.modifyCategory">Modify Category</dhv:label>
  </dhv:evaluate>
  </strong></th>
  <tr class="containerBody">
    <td class="formLabel" valign="top"><dhv:label name="quotes.productName">Name</dhv:label></td>
    <td><input type="text" name="name" value="<%= toHtmlValue(category.getName()) %>" size="58" maxlength="300"/><font color="red">*</font>
        <%= showAttribute(request, "nameError") %><input type="hidden" name="refresh" value="-1">
    </td>
  </tr>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="product.description">Description</dhv:label></td>
    <td><textarea name="description" id="description" cols="45" rows="10" ><%= toString(category.getDescription()) %></textarea></td>
  </tr>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="documents.details.approved">Enabled</dhv:label></td>
    <td><dhv:checkbox name="enabled" value="true" checked="<%= category.getEnabled() %>"/></td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="window.location.href='PortfolioEditor.do?command=List&categoryId=<%= category.getParentId() %>';"/>
<br />
</form>
<%-- Set the default cursor location in the form --%>
<script type="text/javascript">
  document.addCategory.name.focus();
</script>

