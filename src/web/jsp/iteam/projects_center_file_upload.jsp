<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%-- Note: When cancel is submitted, the encoding of the form has to be processed differently
     or the id can be added to the action --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
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
      messageText += "- Subject is required\r\n";
      formTest = false;
    }
    if (form.id<%= Project.getId() %>.value.length < 5) {
      messageText += "- File is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The file could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      if (form.upload.value != 'Please Wait...') {
        form.upload.value='Please Wait...';
        return true;
      } else {
        return false;
      }
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementFiles.do?command=Upload&pid=<%= request.getParameter("pid") %>&folderId=<%= request.getParameter("folderId") %>" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <zeroio:folderHierarchy/>
    </td>
  </tr>
</table>
<br />
  <input type="submit" value=" Upload " name="upload">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=<%= Project.getId() %>&folderId=<%= request.getParameter("folderId") %>';"><br />
  <br />
<%--
<dhv:evaluate if="<%= User.getAccountSize() > -1 %>">
<table class="note" cellspacing="0"> 
<tr>
  <th>
    <img src="images/icons/stock_about-16.gif" border="0" align="absmiddle" />
  </th>
  <td>
    Maintain your files by deleting older versions of the same file, and by deleting
    outdated or unused files.<br />
    This user account is limited to <%= User.getAccountSize() %> MB.<br />
    This account is currently using <%= User.getCurrentAccountSizeInMB() %> MB.
  </td>
</tr>
</table>
<br />
</dhv:evaluate>
--%>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2" align="left">
        <dhv:evaluate if="<%= FileItem.getId() == -1 %>">
          <strong>Upload File</strong>
        </dhv:evaluate>
        <dhv:evaluate if="<%= FileItem.getId() > 0 %>">
          <strong>Add Revision to File</strong>
        </dhv:evaluate>
      </th>
    </tr>
  <dhv:evaluate if="<%= FileItem.getId() > 0 %>">
    <tr class="containerBody">
      <td nowrap class="formLabel">File</td>
      <td>
        <%= FileItem.getSubject() %>
      </td>
    </tr>
  </dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel">Subject</td>
      <td>
        <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue(FileItem.getSubject()) %>">
      </td>
    </tr>
  <dhv:evaluate if="<%= FileItem.getId() > 0 %>">
    <tr class="containerBody" valign="top">
      <td nowrap class="formLabel">Version</td>
      <td>
        Current Version: <strong><%= FileItem.getVersion() %></strong><br />
        &nbsp;<br />
        <input type="radio" value="<%= FileItem.getVersionNextMajor() %>" checked name="versionId">Major Update <%= FileItem.getVersionNextMajor() %>
        <input type="radio" value="<%= FileItem.getVersionNextMinor() %>" name="versionId">Minor Update <%= FileItem.getVersionNextMinor() %>
        <input type="radio" value="<%= FileItem.getVersionNextChanges() %>" name="versionId">Changes <%= FileItem.getVersionNextChanges() %>
      </td>
    </tr>
  </dhv:evaluate>
    <tr class="containerBody">
      <td nowrap class="formLabel">File</td>
      <td>
        <input type="file" name="id<%= Project.getId() %>" size="45">
      </td>
    </tr>
  </table>
  <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
  <p align="center">* Large files may take awhile to upload.<br />
     Wait for file completion message when upload finishes.
  </p>
  <input type="submit" value=" Upload " name="upload">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=<%= Project.getId() %>&folderId=<%= request.getParameter("folderId") %>';">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="fid" value="<%= FileItem.getId() %>">
</form>
</body>

