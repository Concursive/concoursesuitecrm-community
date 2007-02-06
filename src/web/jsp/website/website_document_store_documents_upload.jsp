<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: accounts_documents_upload.jsp 13128 2005-10-21 11:22:55 -0400 (Fri, 21 Oct 2005) partha $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="folderId" class="java.lang.String"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="documentStore"
	class="org.aspcfs.modules.documents.base.DocumentStore" scope="request" />
<%@ include file="../initPage.jsp" %>
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
    if (form.id<%= Constants.DOCUMENTS_WEBSITE %>.value.length < 5) {
      messageText += label("file.required", "- File is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText += label("File.not.submitted", "The file could not be submitted.          \r\nPlease verify the following items.\r\n\r\n");
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
<form method="post" name="inputForm" action="WebsiteDocuments.do?command=Upload<%=addLinkParams(request, "popup|row|siteId|forEmail")%>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="id" value="<%= Constants.DOCUMENTS_WEBSITE %>" />
<input type="hidden" name="folderId" value="<%= (String)request.getAttribute("folderId") %>" />
<input type="hidden" name="documentStoreId"
						value="<%=documentStore.getId()%>">
<table class="trails" cellspacing="0">
		<tr>
			<td>
				<a
					href="WebsiteDocuments.do?command=View<%=addLinkParams(request, "popup|row|siteId|forEmail")%>"><dhv:label
						name="Documents" mainMenuItem="true">Documents</dhv:label> </a> >
				<dhv:label name="documents.documentStoreDetails">Document Store Details</dhv:label>
			</td>
		</tr>
	</table>
	<table border="0" width="100%">
		<tr>
			<td>
				<img src="images/icons/stock_navigator-open-toolbar-16.gif"
					border="0" align="absmiddle">
			</td>
			<td width="100%">
				<strong><%=toHtml(documentStore.getTitle())%> </strong>
			</td>
		</tr>
	</table>
<%-- End Trails --%>
	<table cellpadding="4" cellspacing="0" border="0" width="100%">
			<tr>
				<td width="100%"
					style="background-image: none; background-color: transparent; border: 0px; border-bottom: 1px solid #666; cursor: default;">
					&nbsp;
				</td>
			</tr>
		</table>
		<table cellpadding="4" cellspacing="0" border="0" width="100%">
			<tr>
				<td class="containerBack">
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <% String documentLink = "WebsiteDocuments.do?command=DocumentStoreCenter" + addLinkParams(request,"popup|row|siteId|forEmail"); %>
      <zeroio:folderHierarchy module="Website" link="<%= documentLink %>" />
    </td>
  </tr>
</table>
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
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
        <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue((String)request.getAttribute("subject")) %>"><font color="red">*</font>
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
      </td>
      <td>
        <input type="file" name="id<%= Constants.DOCUMENTS_WEBSITE %>" size="45">
      </td>
    </tr>
  </table>
  </td>
  </tr>
  </table>
  <p align="center">
    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label><br>
    <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
  </p>
  <input type="submit" value="<dhv:label name="global.button.Upload">Upload</dhv:label> " name="upload" />
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='WebsiteDocuments.do?command=DocumentStoreCenter&documentStoreId=<%= documentStore.getId() %>&folderId=<%= (String)request.getAttribute("folderId") %><%=addLinkParams(request, "popup|row|siteId|forEmail")%>';" />
</form>
</body>
