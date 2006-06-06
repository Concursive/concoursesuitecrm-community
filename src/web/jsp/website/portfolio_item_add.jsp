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
<jsp:useBean id="item" class="org.aspcfs.modules.website.base.PortfolioItem" scope="request"/>
<jsp:useBean id="category" class="org.aspcfs.modules.website.base.PortfolioCategory" scope="request"/>
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script type="text/javascript">

  function reopen(extension) {
    window.location.href='PortfolioItemEditor.do?command=AddItem&categoryId=<%= item.getCategoryId() %>'+extension;
  }

  function checkForm(form) {
    formTest = true;
    message = "";
    if (checkNullString(form.name.value)) {
      message += label("name.required","- Check that the name is entered\r\n");
      formTest = false;
    }
    if (form.description.value != "" && checkNullString(form.description.value)) {
      message += label("check.valid.description","- Please enter a valid description.\r\n");
      formTest = false;
    }
    if (form.caption.value != "" && checkNullString(form.caption.value)) {
      message += label("check.valid.caption","- Please enter a valid caption.\r\n");
      formTest = false;
    }
    if (extract(form.imageId1.value) != '') {
      form.fileText.value= extract(form.imageId1.value);
    }
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      hideSpan('saveButton');
      showSpan('savingInfo');
      return true;
    }
    return true;
  }
function extract(what) {
  var answer = '';
    if (what.indexOf('/') > -1) {
        answer = what.substring(what.lastIndexOf('/')+1,what.length);
    } else {
        answer = what.substring(what.lastIndexOf('\\')+1,what.length);
    }
   return answer;
}
</script>
<form name="addItem" action="PortfolioItemEditor.do?command=SaveItem" method="post" enctype="multipart/form-data" onSubmit="return checkForm(this);">
<input type="hidden" name="id" value="<%= item.getId() %>"/>
<input type="hidden" name="categoryId" value="<%= item.getCategoryId() %>"/>
<input type="hidden" name="imageId" value="<%= fileItem.getId() %>">
<input type="hidden" name="positionId" value="<%= item.getPositionId() %>"/>
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="fileText" value="" />
<input type="hidden" name="return" value="<%= (request.getAttribute("return") != null ? request.getAttribute("return"):"")%>"/>
<input type="hidden" value="<%= fileItem.getVersionNextMajor() %>" name="versionId" />
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Website.do"><dhv:label name="website.website">Website</dhv:label></a> >
      <a href="PortfolioEditor.do?command=List&categoryId=<%= category.getId() %>"><dhv:label name="product.categoryDetails">Category Details</dhv:label></a> >
<dhv:evaluate if="<%= item.getId() == -1 %>">
      <dhv:label name="product.addItem">Add Item</dhv:label>
</dhv:evaluate>
<dhv:evaluate if="<%= item.getId() > -1 %>">
      <a href="PortfolioItemEditor.do?command=ItemDetails&categoryId=<%= category.getId() %>&itemId=<%= item.getId() %>"><dhv:label name="product.categoryDetails">Item Details</dhv:label></a> >
      <dhv:label name="">Modify Item</dhv:label>
</dhv:evaluate>
    </td>
  </tr>
</table>
<%-- End Trails --%>
<%-- Category Trails --%>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td>
      <dhv:productCategoryHierarchy link="PortfolioEditor.do?command=List" showLastLink="true" /> >
      <dhv:label name="">Item</dhv:label>
    </td>
  </tr>
</table>
<%-- End Category Trails --%>
<%--<input type="submit" name="save1" value="<dhv:label name="button.save">Save</dhv:label>" />
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="window.location.href='PortfolioEditor.do?command=List&categoryId=<%= item.getCategoryId() %>';"/>
<br /> --%>
<dhv:formMessage showSpace="true"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr><th colspan="2"><strong>
  <dhv:evaluate if="<%= item.getId() == -1 %>">
    <dhv:label name="product.addItem">Add Item</dhv:label>
  </dhv:evaluate><dhv:evaluate if="<%= item.getId() != -1 %>">
    <dhv:label name="product.modifyItem">Modify Item</dhv:label>
  </dhv:evaluate>
  </strong></th>
  <tr class="containerBody">
    <td class="formLabel" valign="top"><dhv:label name="quotes.productName">Name</dhv:label></td>
    <td><input type="text" name="name" value="<%= toHtmlValue(item.getName()) %>" size="58" maxlength="300"/><font color="red">*</font>
        <%= showAttribute(request, "nameError") %><input type="hidden" name="refresh" value="-1">
    </td>
  </tr>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="product.description">Description</dhv:label></td>
    <td><textarea name="description" id="description" cols="45" rows="10" ><%= toString(item.getDescription()) %></textarea></td>
  </tr>
  <tr>
    <td class="formLabel" valign="top"><dhv:label name="documents.details.approved">Enabled</dhv:label></td>
    <td><dhv:checkbox name="enabled" value="true" checked="<%= item.getEnabled() %>"/></td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top"><dhv:label name="website.portfolio.caption">Caption</dhv:label></td>
    <td><input type="text" name="caption" value="<%= toHtmlValue(item.getCaption()) %>" size="58" maxlength="300"/> <%= showAttribute(request, "subjectError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
    </td>
    <td>
      <input type="file" name="imageId1" size="45" ERROR="<%= showAttribute(request, "imageId1Error") %>">
    </td>
  </tr>
</table>
  <p align="center">
    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label><br>
    <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
  </p>
<table border="0">
<tr id="saveButton"><td>
<input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
  <dhv:evaluate if="<%= item.getId() == -1 %>">
    <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="window.location.href='PortfolioEditor.do?command=List&categoryId=<%= item.getCategoryId() %>';"/>
  </dhv:evaluate><dhv:evaluate if="<%= item.getId() != -1 %>">
    <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="window.location.href='PortfolioItemEditor.do?command=ItemDetails&categoryId=<%= item.getCategoryId() %>&itemId=<%= item.getId() %>';"/>
  </dhv:evaluate>
</td>
</tr>
<tr id="savingInfo"><td><dhv:label name="">Saving the Information...</dhv:label></td></tr>
</table>
</form>
<%-- Set the default cursor location in the form --%>
<script type="text/javascript">
  showSpan('saveButton');
  hideSpan('savingInfo');
  document.addItem.name.focus();
</script>

