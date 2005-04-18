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
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></SCRIPT>
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (checkNullString(form.subject.value)) {
      messageText += label("Subject.required", "- Subject is required\r\n");
      formTest = false;
    }
    if (form.id<%= Campaign.getId() %>.value.length < 5) {
      messageText += label("file.required", "- File is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("File.not.submitted", "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      if (form.upload.value != label("button.pleasewait","Please Wait...")) {
        form.upload.value=label("button.pleasewait","Please Wait...");
        return true;
      } else {
        return false;
      }
    }
  }
</script>
<body onLoad="document.inputForm.subject.focus();">
<form method="post" name="inputForm" action="CampaignDocuments.do?command=Upload" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do"><dhv:label name="communications.campaign.Communications">Communications</dhv:label></a> >
<a href="CampaignManager.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<a href="CampaignManager.do?command=Details&id=<%= Campaign.getId() %>"><dhv:label name="campaign.campaignDetails">Campaign Details</dhv:label></a> >
<a href="CampaignDocuments.do?command=View&id=<%= Campaign.getId() %>"><dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label></a> >
<dhv:label name="accounts.accounts_documents_upload.UploadDocument">Upload Document</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="communications" selected="documents" object="Campaign" param="<%= "id=" + Campaign.getId() %>">
  <dhv:formMessage showSpace="false"/>
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
	  <tr>
	    <th colspan="2">
	      <img border="0" src="images/file.gif" align="absmiddle"><b><dhv:label name="accounts.accounts_documents_upload.UploadNewDocument">Upload a New Document</dhv:label></b>
	    </th>
	  </tr>
	  <tr class="containerBody">
	    <td class="formLabel">
        <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
	    </td>
	    <td>
	      <input type="hidden" name="folderId" value="<%= (String)request.getAttribute("folderId") %>">
	      <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue((String)request.getAttribute("subject")) %>"><font color="red">*</font>
        <%= showAttribute(request,"subjectError") %>
	    </td>
	  </tr>
	  <tr class="containerBody">
	    <td class="formLabel">
	      <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
	    </td>
	    <td>
	      <input type="file" name="id<%= Campaign.getId() %>" size="45">
        <%= showAttribute(request,"filenameError") %>
	    </td>
	  </tr>
	</table>
  <p align="center">
    <dhv:label name="product.largeFileUploadStatement" param="break=<br />">* Large files may take a while to upload.<br />Wait for file completion message when upload is complete.</dhv:label>
  </p>
  <input type="submit" value=" <dhv:label name="global.button.Upload">Upload</dhv:label> " name="upload">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='CampaignDocuments.do?command=View&id=<%= Campaign.getId() %>';">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="id" value="<%= Campaign.getId() %>">
</dhv:container>
</form>
</body>
