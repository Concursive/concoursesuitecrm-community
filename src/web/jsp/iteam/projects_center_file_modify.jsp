<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
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
    if ((form.clientFilename.value) == "") {
      messageText += "- Filename is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      form.dosubmit.value = "true";
      messageText = "The file information could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementFiles.do?command=Update" onSubmit="return checkFileForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <zeroio:folderHierarchy showLastLink="true"/> >
      <%= FileItem.getSubject() %>
    </td>
  </tr>
</table>
<br>
<input type="submit" value=" Update " name="update">
<input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=<%= Project.getId() %>';"><br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="7">
      <strong>Modify File Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Subject</td>
    <td>
      <input type="text" name="subject" size="59" maxlength="255" value="<%= FileItem.getSubject() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">File Name</td>
    <td>
      <input type="text" name="clientFilename" size="59" maxlength="255" value="<%= FileItem.getClientFilename() %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Current<br>Version</td>
    <td valign="top">
      <%= FileItem.getVersion() %>
    </td>
  </tr>
</table>
<br>
<input type="hidden" name="folderId" value="<%= FileItem.getFolderId() %>">
<input type="submit" value=" Update " name="update">
<input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=<%= Project.getId() %>';">
<input type="hidden" name="dosubmit" value="true">
<input type="hidden" name="pid" value="<%= Project.getId() %>">
<input type="hidden" name="fid" value="<%= FileItem.getId() %>">
</form>
</body>
