<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="fileFolder" class="com.zeroio.iteam.base.FileFolder" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += "- Name is required\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The form could not be submitted.          \r\nPlease verify the following:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    }
    return true;
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementFileFolders.do?command=Save&auto-populate=true" onSubmit="return checkForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <zeroio:folderHierarchy/>
    </td>
  </tr>
</table>
<br>
  <input type="submit" value=" Save " name="save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=File_Library';"><br>
  <%= showError(request, "actionError") %><br>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong><%= (fileFolder.getId() > -1 ? "Rename" : "New" ) %> Folder</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Name</td>
      <td>
        <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue(fileFolder.getSubject()) %>">
        <font color="red">*</font> <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Display Type</td>
      <td>
        Icon Layout
        <select size="1" name="display">
          <option value="-1" <%= fileFolder.getDisplay() == -1 ? "selected" : "" %>>List View</option>
          <option value="1" <%= fileFolder.getDisplay() == 1 ? "selected" : "" %>>Image View</option>
          <option value="2" <%= fileFolder.getDisplay() == 2 ? "selected" : "" %>>Slideshow View</option>
        </select>
      </td>
    </tr>
  </table>
  <br>
  <input type="hidden" name="modified" value="<%= fileFolder.getModifiedString() %>">
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="id" value="<%= fileFolder.getId() %>">
  <input type="hidden" name="parentId" value="<%= fileFolder.getParentId() %>">
  <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
  <input type="hidden" name="dosubmit" value="true">
  <input type="submit" value=" Save " name="save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=File_Library';"><br>
</form>
</body>
