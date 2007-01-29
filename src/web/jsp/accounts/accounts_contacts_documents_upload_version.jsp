<%-- 
  - Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: accounts_contacts_documents_upload_version.jsp 28.12.2006 17:00:20 zhenya.zhidok $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="folderId" class="java.lang.String"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/tasks.js"></script>
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
    if (form.id<%= ContactDetails.getId() %>.value.length < 5) {
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
<form method="post" name="inputForm" action="AccountsContactsDocuments.do?command=UploadVersion<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="id" value="<%= ContactDetails.getId() %>">
<input type="hidden" name="fid" value="<%= FileItem.getId() %>">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%= ContactDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=ContactDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="AccountsContactsDocuments.do?command=View&contactId=<%= ContactDetails.getId() %>"><dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label></a> >
<dhv:label name="accounts.accounts_documents_upload_version.UploadNewVersion">Upload New Version</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <dhv:container name="accountscontacts" selected="documents" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>" hideContainer="<%= !ContactDetails.getEnabled() || ContactDetails.isTrashed() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	  <tr class="subtab">
	    <td>
	      <% String documentLink = "AccountsContactsDocuments.do?command=View&contactId="+ContactDetails.getId()+ addLinkParams(request, "popup|popupType|actionId|actionplan"); %>
	      <zeroio:folderHierarchy module="Accounts" link="<%= documentLink %>" showLastLink="true" /> >
	      <%= FileItem.getSubject() %>
	    </td>
	  </tr>
	</table>
	  <dhv:formMessage />
	  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	    <tr>
	      <th colspan="2">
	        <img border="0" src="images/file.gif" align="absmiddle"><b><dhv:label name="accounts.accounts_documents_upload_version.UploadNewVersionDocument">Upload a New Version of Document</dhv:label></b>
	      </th>
	    </tr>
	    <tr class="containerBody">
	      <td class="formLabel">
	        <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
	      </td>
	      <td>
	        <input type="hidden" name="folderId" value="<%= (String)request.getAttribute("folderId") %>">
	        <input type="text" name="subject" size="59" maxlength="255" value="<%= FileItem.getSubject() %>"><font color="red">*</font>
	      </td>
	    </tr>
	    <tr class="containerBody">
	      <td class="formLabel">
	        <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
	      </td>
	      <td>
	        <dhv:label name="accounts.accounts_documents_upload_version.CurrentVersion">Current Version</dhv:label>:<br>
	         &nbsp;&nbsp;
	         <strong><%= FileItem.getVersion() %></strong><br>
	         <dhv:label name="accounts.accounts_documents_upload_version.NewVersion">New Version</dhv:label>: <br>
	         &nbsp;&nbsp;
	         <strong><%= FileItem.getVersionNextMajor() %></strong><br>
	         <input type="hidden" value="<%= FileItem.getVersionNextMajor() %>" name="versionId">
	      </td>
	    </tr>
	    <% if (User.getRoleType() == Constants.ROLETYPE_CUSTOMER){ %>
	    <input type="hidden" name="allowPortalAccess" value="1"></input>
	    <%} else { %>
	    <tr>
	    <td class="formLabel">
	      <dhv:label name="accounts.accounts_document_portal_include.ShareWithPortalUser">Share With Portal User?</dhv:label>
	    </td>
	    <td>
	    <input type="checkbox" name="chk1" value="on" onclick="javascript:setField('allowPortalAccess', document.inputForm.chk1.checked, 'inputForm');" <%= FileItem.getAllowPortalAccess() ? "checked":""%>/>
	    <input type="hidden" name="allowPortalAccess" value="<%= FileItem.getAllowPortalAccess() ? "1":"0"%>"></input>
	    </td>
	    </tr>
	 		<%}%>
	    <tr class="containerBody">
	      <td class="formLabel">
	        <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
	      </td>
	      <td>
	        <input type="file" name="id<%= ContactDetails.getId() %>" size="45">
	      </td>
	    </tr>
	  </table>
	  <p align="center">
	    * <dhv:label name="accounts.accounts_documents_upload.LargeFilesUpload">Large files may take a while to upload.</dhv:label><br>
	    <dhv:label name="accounts.accounts_documents_upload.WaitForUpload">Wait for file completion message when upload is complete.</dhv:label>
	  </p>
	  <input type="submit" value="<dhv:label name="global.button.Upload">Upload</dhv:label>" name="upload" />
	  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='AccountsContactsDocuments.do?command=View&contactId=<%= ContactDetails.getId() %>&folderId=<%= (String)request.getAttribute("folderId") %><%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';" />
  </dhv:container>
</dhv:container>
</form>
</body>
  