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
<jsp:useBean id="Issue" class="com.zeroio.iteam.base.Issue" scope="request"/>
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
      messageText += "- Subject is a required field\r\n";
      formTest = false;
    }
    if (form.body.value == "") {    
      messageText += "- Message is a required field\r\n";
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
<form method="POST" name="inputForm" action="ProjectManagementIssues.do?command=Save&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&auto-populate=true" onSubmit="return checkForm(this);">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_data-explorer-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=<%= Project.getId() %>">Forums</a> >
      <img border="0" src="images/icons/stock_draw-callouts2-16.gif" align="absmiddle">
      <%= toHtml(IssueCategory.getSubject()) %>
    </td>
  </tr>
</table>
<br>
  <input type="submit" value=" Save ">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= Project.getId() %>&cid=<%= IssueCategory.getId() %>';"><br>
  <%= showError(request, "actionError") %>
  <input type="hidden" name="categoryId" value="<%= IssueCategory.getId() %>">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th colspan="2">
        <strong>Topic</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">Subject</td>
      <td>
        <input type="text" name="subject" size="57" maxlength="50" value="<%= toHtmlValue(Issue.getSubject()) %>"><font color=red>*</font>
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">Message</td>
      <td>
        <table border="0" cellpadding="0" cellspacing="0" class="empty">
          <tr>
            <td>
              <textarea rows="10" name="body" cols="70"><%= Issue.getBody() %></textarea>
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
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= Project.getId() %>&cid=<%= IssueCategory.getId() %>';"><br>
  <input type="hidden" name="projectId" value="<%= Project.getId() %>">
  <input type="hidden" name="id" value="<%= Issue.getId() %>">
  <input type="hidden" name="modified" value="<%= Issue.getModified() %>">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="return" value="<%= request.getParameter("return") %>">
</form>  
</body>
