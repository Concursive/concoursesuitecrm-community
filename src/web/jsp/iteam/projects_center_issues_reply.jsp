<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="IssueCategory" class="com.zeroio.iteam.base.IssueCategory" scope="request"/>
<jsp:useBean id="Issue" class="com.zeroio.iteam.base.Issue" scope="request"/>
<jsp:useBean id="IssueReply" class="com.zeroio.iteam.base.IssueReply" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.body.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    //Check required fields
    if (form.subject.value == "") {    
      messageText += "- Subject is a required field\r\n";
      formTest = false;
    }
    if (form.body.value == "") {    
      messageText += "- Message is a required field\r\n";
      formTest = false;
    }
    if (formTest == false) {
      messageText = "The form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      alert(messageText);
      form.dosubmit.value = "true";
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementIssueReply.do?command=SaveReply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&auto-populate=true" onSubmit="return checkForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_data-explorer-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=<%= Project.getId() %>">Forums</a> >
      <img border="0" src="images/icons/stock_draw-callouts2-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= Project.getId() %>&cid=<%= IssueCategory.getId() %>"><%= toHtml(IssueCategory.getSubject()) %></a> >
      <img border="0" src="images/icons/stock_draw-callouts-16.gif" align="absmiddle">
      <a href="ProjectManagementIssues.do?command=Details&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>"><%= toHtml(Issue.getSubject()) %></a> >
      Reply
    </td>
  </tr>
</table>
<br>
  <input type="submit" value=" Save ">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagementIssues.do?command=Details&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>';"><br>
  <dhv:formMessage />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong>Reply to Message</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Subject</td>
      <td>
        <input type="text" name="subject" size="57" maxlength="50" value="<%= toHtmlValue(IssueReply.getSubject()) %>"><font color=red>*</font> <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">Message</td>
      <td>
        <table border="0" cellpadding="0" cellspacing="0" class="empty">
          <tr>
            <td>
              <textarea rows="10" name="body" cols="70"><%= IssueReply.getBody() %></textarea>
            </td>
            <td valign="top">
              <font color=red>*</font>
            </td>
          </tr>
        </table>
        <%= showAttribute(request, "bodyError") %>
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value=" Save ">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagementIssues.do?command=Details&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>';">
  <input type="hidden" name="projectId" value="<%= Project.getId() %>">
  <input type="hidden" name="issueId" value="<%= Issue.getId() %>">
  <input type="hidden" name="categoryId" value="<%= IssueCategory.getId() %>">
  <input type="hidden" name="id" value="<%= IssueReply.getId() %>">
  <input type="hidden" name="modified" value="<%= IssueReply.getModified() %>">
  <input type="hidden" name="dosubmit" value="true">
</form>
</body>
