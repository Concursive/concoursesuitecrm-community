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
  - Version: $Id: contacts_documents_popup_list.jsp 17754 2006-12-11 vadim.vishnevsky@corratech.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="fileFolderList" class="com.zeroio.iteam.base.FileFolderList" scope="request"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="fileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript">
  function checkFileForm(form) {
    var formTest = true;
    var messageText = "";
    if (checkNullString(form.subject.value)) {
      messageText += label("Subject.required", "- Subject is required\r\n");
      formTest = false;
    }
    if (form.id<%= ContactDetails.getId() %>.value.length < 5) {
      messageText += label("file.required", "- File is required");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("File.not.submitted", "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
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
<dhv:evaluate if="<%= fileItem.getId() != -1 %>">
  <body onLoad="javascript:document.inputForm.subject.focus();">
</dhv:evaluate>
<dhv:evaluate if="<%= fileItem.getId() == -1 %>">
  <body onLoad="document.inputForm.subject.focus();">
</dhv:evaluate>
<dhv:container name="contacts" selected="documents" object="ContactDetails" hideContainer="<%= "true".equals(request.getParameter("actionplan")) %>" param="<%= "id=" + ContactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>">
  <%
   String permission_doc_folders_add ="contacts-external_contacts-documents-add";
    String permission_doc_files_upload = "contacts-external_contacts-documents-add";
    String permission_doc_folders_edit = "contacts-external_contacts-documents-edit";
    String documentFolderAdd ="ExternalContactsDocumentsFolders.do?command=Add&contactId="+ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
    String documentFileAdd = "ExternalContactsDocuments.do?command=Add&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
    String documentFolderModify = "ExternalContactsDocumentsFolders.do?command=Modify&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
    String documentFolderList = "ExternalContactsDocuments.do?command=View&contactId="+ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
    String documentFileDetails = "ExternalContactsDocuments.do?command=Details&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
    String documentModule = "Contacts";
    String specialID = ""+ContactDetails.getId();
    boolean hasPermission = true;
   %>
<%-- START Document folder trails--%>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <zeroio:folderHierarchy module="<%= documentModule %>" link="<%= documentFolderList %>"/>
    </td>
  </tr>
</table>
<%-- END Document folder trails--%>
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="100%"><strong><dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="accounts.accounts_add.Type">Type</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="accounts.accounts_documents_details.Size">Size</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="documents.documents.dateModified">Date Modified</dhv:label></strong></th>
  </tr>
<dhv:evaluate if="<%= fileItemList.size() == 0 && fileFolderList.size() == 0 %>">
  <tr class="row2">
    <td colspan="5"><dhv:label name="project.noFilesToDisplay">No files to display.</dhv:label></td>
  </tr>
</dhv:evaluate>
<%
  int rowid = 0;
  if ( fileFolderList.size() > 0 ) {
    Iterator folders = fileFolderList.iterator();
    while (folders.hasNext()) {
      FileFolder thisFolder = (FileFolder) folders.next();
      rowid = (rowid != 1?1:2);
%>
  <tr>
    <td class="row<%= rowid %>" width="100%">
      <img src="images/stock_folder-23.gif" border="0" align="absmiddle">
      <a href="<%= documentFolderList %>&folderId=<%= thisFolder.getId() %><%= thisFolder.getDisplay() == 2?"&details=true":"" %>"><%= toHtml(thisFolder.getSubject()) %></a>
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <dhv:label name="project.folder.lowercase">folder</dhv:label>
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <%= thisFolder.getItemCount() %> 
      <% if (thisFolder.getItemCount() == 1) {%>
        <dhv:label name="project.item.lowercase">item</dhv:label>
      <%} else {%>
        <dhv:label name="project.items.lowercase">items</dhv:label>
      <%}%>
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      --
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <zeroio:tz timestamp="<%= thisFolder.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" /><br />
      <dhv:username id="<%= thisFolder.getModifiedBy() %>"/>
    </td>
  </tr>
<%
    }
  }
  if ( fileItemList.size() > 0) {
    Iterator iterator = fileItemList.iterator();
    while (iterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      FileItem thisFile = (FileItem)iterator.next();
%>    
  <tr>
    <td class="row<%= rowid %>" width="100%">
      <%= thisFile.getImageTag("-23") %>
      <a href="ExternalContactsDocuments.do?command=Download&contactId=<%= ContactDetails.getId() %>&folderId=<%= thisFile.getFolderId() %>&fid=<%= thisFile.getId() %>"><%= toHtml(thisFile.getSubject()) %></a>
    </td>
    <td class="row<%= rowid %>" align="center" nowrap><%= toHtml(thisFile.getExtension()) %></td>
    <td class="row<%= rowid %>" align="right" nowrap>
      <%= thisFile.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <%= thisFile.getVersion() %>&nbsp;
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <zeroio:tz timestamp="<%= thisFile.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" /><br />
      <dhv:username id="<%= thisFile.getModifiedBy() %>"/>
    </td>
  </tr>
<% }} %>
</table>
&nbsp;<br/>
<form method="post" name="inputForm" action="ExternalContactsDocuments.do?command=Upload&popup=true" onSubmit="return checkFileForm(this);" enctype="multipart/form-data">
<input type="hidden" name="id" value="<%= ContactDetails.getId() %>" />
<input type="hidden" name="folderId" value="<%= fileItemList.getFolderId() %>" />
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
      <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue((String)request.getAttribute("subject")) %>"><font color="red">*</font>
      <%= showAttribute(request, "subjectError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td width="100%" colspan="2">
      <center>
        <input type="file" name="id<%= ContactDetails.getId() %>" size="45"><br>
        <dhv:label name="product.largeFileUploadStatement" param="break=<br />">* Large files may take a while to upload.<br />Wait for file completion message when upload is complete.</dhv:label><br />
        <input type="submit" value="<dhv:label name="documents.documents.uploadFile">Upload File</dhv:label>" name="upload">
        <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();" />
      </center>
    </td>
  </tr>
</table>
</form>
</dhv:container>
