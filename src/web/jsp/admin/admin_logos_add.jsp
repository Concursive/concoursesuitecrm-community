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
  - Version: $Id: 
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.PermissionCategory, java.util.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="subject" class="java.lang.String" scope="request" />
<body onLoad="document.inputForm.subject.focus();">
<form method="post" name="inputForm" action="AdminLogos.do?command=UploadLogo" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> > 
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
<a href="AdminLogos.do?command=View&moduleId=<%= PermissionCategory.getId() %>"><dhv:label name="admin.logos">Logos</dhv:label></a> >
<dhv:label name="admin.uploadLogo">Upload Logo</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += label("Subject.required", "- Subject is required\r\n");
      formTest = false;
    }
    if (form.id1.value.length < 5) {
      messageText += label("file.required", "- File is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("File.not.submitted", "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      if (form.upload.value != label('button.pleasewait','Please Wait...')) {
        form.upload.value=label('button.pleasewait','Please Wait...');
        return true;
      } else {
        return false;
      }
    }
  }
	
	function setField(formField,thisValue,thisForm) {
		var frm = document.forms[thisForm];
		var len = document.forms[thisForm].elements.length;
		var i=0;
		for( i=0 ; i<len ; i++) {
			if (frm.elements[i].name.indexOf(formField)!=-1) {
				if(thisValue){
					frm.elements[i].value = "1";
				}	else {
					frm.elements[i].value = "0";
				}
			}
		}
	}
</script>
<dhv:formMessage showSpace="false"/>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="admin.logos.uploadNote.text">NOTE: The logo file being uploaded should be in one of the following formats. *.gif *.jpg *.jpeg *.tiff *.png *.bmp *.wmf</dhv:label></td></tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <img border="0" src="images/file.gif" align="absmiddle"><b><dhv:label name="admin.uploadAnewLogo">Upload a New Logo</dhv:label></b>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Name</dhv:label>
    </td>
    <td>
      <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue((String)request.getAttribute("subject")) %>"><font color="red">*</font>
      <%= showAttribute(request, "subjectError") %>
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
    </td>
    <td>
      <input type="file" name="id1" size="45">
    </td>
  </tr>
	<tr>
    <td class="formLabel">
      <dhv:label name="admin.defaultLogo">Default Logo</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="chk1" value="on" onclick="javascript:setField('defaultLogo', document.inputForm.chk1.checked, 'inputForm');" /> <dhv:label name="admin.markFileAsDefaultLogo.text">Mark this file as the default logo</dhv:label>
			<input type="hidden" name="defaultLogo" value="">
    </td>
  </tr>
</table>
<p align="center">
	* <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label><br>
	<dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
</p>
<input type="submit" value="<dhv:label name="global.button.Upload">Upload</dhv:label> " name="upload" />
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='AdminLogos.do?command=View&moduleId=<%= PermissionCategory.getId() %>';" />
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="id" value="1" />
<input type="hidden" name="moduleId" value="<%= toHtmlValue(PermissionCategory.getId()) %>" />
</form>
</body>
