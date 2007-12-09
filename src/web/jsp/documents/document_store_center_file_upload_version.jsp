<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>
<%-- Note: When cancel is submitted, the encoding of the form has to be processed differently
     or the id can be added to the action --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="folderId" class="java.lang.String"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<script language="JavaScript">
  function checkFileForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += label("subject.required","- Subject is required\r\n");
      formTest = false;
    }
    if (form.id<%= documentStore.getId() %>.value.length < 5) {
      messageText += label("file.required","- File is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("File.not.submitted","The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n") + messageText;
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
<form method="POST" name="inputForm" action="DocumentStoreManagementFiles.do?command=Upload&documentStoreId=<%= request.getParameter("documentStoreId") %>&folderId=<%= request.getParameter("folderId") %>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="documentStoreId" value="<%= documentStore.getId() %>">
<input type="hidden" name="fid" value="<%= FileItem.getId() %>">
<input type="hidden" name="folderId" value='<%= request.getParameter("folderId") %>'>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <% String documentLink = "DocumentManagement.do?command=DocumentStoreCenter&section=File_Library&documentStoreId="+documentStore.getId(); %>
      <zeroio:folderHierarchy module="Documents" link="<%= documentLink %>" showLastLink="true"/> >
      <%= FileItem.getSubject() %>
    </td>
  </tr>
</table>
<dhv:formMessage showSpace="false" />
<br />
  <input type="submit" value="<dhv:label name="documents.documents.upload">Upload</dhv:label>" name="upload" />
  <input type="submit" value="<dhv:label name="documents.documents.cancelUpload">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='DocumentManagement.do?command=DocumentStoreCenter&section=File_Library&documentStoreId=<%= documentStore.getId() %>&folderId=<%= request.getParameter("folderId") %>';" />
  <br /><br />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2" align="left">
        <dhv:evaluate if="<%= FileItem.getId() == -1 %>">
          <strong><dhv:label name="documents.documents.uploadFile">Upload File</dhv:label></strong>
        </dhv:evaluate>
        <dhv:evaluate if="<%= FileItem.getId() > 0 %>">
          <strong><dhv:label name="documents.documents.addRevisionToFile">Add Revision to File</dhv:label></strong>
        </dhv:evaluate>
      </th>
    </tr>
  <dhv:evaluate if="<%= FileItem.getId() > 0 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="documents.documents.file">File</dhv:label></td>
      <td>
        <%= FileItem.getSubject() %>
      </td>
    </tr>
  </dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="documents.documents.subject">Subject</dhv:label></td>
      <td>
        <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue((FileItem.getId() > 0 ? FileItem.getSubject() : (String)request.getAttribute("subject") )) %>"><font color="red">*</font>
        <%= showAttribute(request, "subjectError") %>
     </td>
    </tr>
  <dhv:evaluate if="<%= FileItem.getId() > 0 %>">
    <tr class="containerBody" valign="top">
      <td nowrap class="formLabel"><dhv:label name="documents.documents.version">Version</dhv:label></td>
      <td>
        <dhv:label name="documents.documents.currentVersion"  param="break=<br />">Current Version: </dhv:label><strong><%= FileItem.getVersion() %></strong>
        &nbsp;<br />
        <input type="radio" value="<%= FileItem.getVersionNextMajor() %>" checked name="versionId"><dhv:label name="documents.documents.majorUpdate">Major Update</dhv:label> <%= FileItem.getVersionNextMajor() %>
        <input type="radio" value="<%= FileItem.getVersionNextMinor() %>" name="versionId"><dhv:label name="documents.documents.minorUpdate">Minor Update</dhv:label> <%= FileItem.getVersionNextMinor() %>
        <input type="radio" value="<%= FileItem.getVersionNextChanges() %>" name="versionId"><dhv:label name="documents.documents.changes">Changes</dhv:label> <%= FileItem.getVersionNextChanges() %>
      </td>
    </tr>
  </dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel"><dhv:label name="documents.documents.file">File</dhv:label></td>
      <td>
        <input type="file" name="id<%= documentStore.getId() %>" size="45">
<%--<%= showAttribute(request, "actionError") %> --%>
      </td>
    </tr>
  </table>
  <p align="center"><dhv:label name="documents.documents.largeFileUploadMessage" param="break=<br />">* Large files may take awhile to upload.<br />
     Wait for file completion message when upload finishes.</dhv:label>
  </p>
  <input type="submit" value=" <dhv:label name="documents.documents.upload">Upload</dhv:label> " name="upload" />
  <input type="submit" value="<dhv:label name="documents.documents.cancelUpload">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='DocumentManagement.do?command=DocumentStoreCenter&section=File_Library&documentStoreId=<%= documentStore.getId() %>&folderId=<%= request.getParameter("folderId") %>';" />
</form>
</body>
