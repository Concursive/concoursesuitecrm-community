<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="IssueCategory" class="com.zeroio.iteam.base.IssueCategory" scope="request"/>
<jsp:useBean id="Issue" class="com.zeroio.iteam.base.Issue" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body bgcolor="#FFFFFF" onLoad="document.inputForm.subject.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
  
    //Check required fields
    if ((form.subject.value == "") || (form.body.value == "")) {    
      form.dosubmit.value = "true";
      alert("Subject and Description are required, please verify then try submitting your information again.");
      formTest = false;
    }
  
    if (formTest == false) {
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementIssues.do?command=Update&auto-populate=true" onSubmit="return checkForm(this);">
  <% if (request.getAttribute("actionError") != null) { %>
    <%= showError(request, "actionError") %>
  <%}%>
  <table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2' bgcolor='#808080' rowspan='2'>
        <font color='#FFFFFF'><b>&nbsp;Update Issue</b></font>
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2' valign='center'>
        &nbsp;<br>
        <%--= issueByLine --%>
        &nbsp;Issue Category: <%= CategoryList.getHtmlSelect("categoryId", Issue.getCategoryId()) %><font color=red>*</font> <%= showAttribute(request, "categoryIdError") %><br>
        &nbsp;
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2'>
        &nbsp;Issue
        Subject:<br>
        &nbsp;&nbsp;<input type="text" name="subject" size="57" maxlength="50" value="<%= toHtmlValue(Issue.getSubject()) %>"><font color=red>*</font> <%= showAttribute(request, "subjectError") %><br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2'>
        &nbsp;Issue
        Description:<br>
        &nbsp;
        <textarea rows="8" name="body" cols="70"><%= toString(Issue.getBody()) %></textarea><font color=red>*</font> <%= showAttribute(request, "bodyError") %><br>
        &nbsp;
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='50%' bgcolor='#808080' height='30'>
        <p align='right'>
          &nbsp;
          <input type="submit" value=" Update ">&nbsp;&nbsp;
        </p>
      </td>
      <td width='50%' bgcolor='#808080' height='30'>
        <p align='left'>
          &nbsp;&nbsp;
          <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= Project.getId() %>';">
        </p>
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
  </table>
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="dosubmit" value="true">
</form>  
</body>
