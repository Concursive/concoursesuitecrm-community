<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="IssueCategory" class="com.zeroio.iteam.base.IssueCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    //Check required fields
    if (form.subject.value == "") {
      messageText += "- Forum name is a required field\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The message could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementIssueCategories.do?command=Save&auto-populate=true" onSubmit="return checkForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_data-explorer-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=<%= Project.getId() %>">Forums</a> >
      <%= IssueCategory.getId() == -1 ? "Add" : "Modify" %>
    </td>
  </tr>
</table>
<br>
  <input type="submit" value=" Save ">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=<%= Project.getId() %>';"><br>
  <%= showError(request, "actionError") %>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong><%= IssueCategory.getId() == -1 ? "Add" : "Modify" %> Forum</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Name</td>
      <td>
        <input type="text" name="subject" size="57" maxlength="255" value="<%= toHtmlValue(IssueCategory.getSubject()) %>"><font color=red>*</font>
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
  </table>
  &nbsp;<br>
  <input type="submit" value=" Save ">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=<%= Project.getId() %>';">
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="id" value="<%= IssueCategory.getId() %>">
  <input type="hidden" name="projectId" value="<%= Project.getId() %>">
  <input type="hidden" name="modified" value="<%= IssueCategory.getModified() %>">
  <input type="hidden" name="dosubmit" value="true">
</form>  
</body>
