<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*,com.darkhorseventures.webutils.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="Issue" class="com.zeroio.iteam.base.Issue" scope="request"/>
<jsp:useBean id="CategoryList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<body bgcolor='#FFFFFF' onLoad="document.inputForm.subject.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    
    //Check required fields
    if (form.categoryId.selectedIndex == 0) {
      messageText += "- Category is a required field\r\n";
      formTest = false;
    }
    if (form.subject.value == "") {    
      messageText += "- Subject is a required field\r\n";
      formTest = false;
    }
    if (form.body.value == "") {    
      messageText += "- Description is a required field\r\n";
      formTest = false;
    }
  
    if (formTest == false) {
      messageText = "The issue form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementIssues.do?command=Insert&auto-populate=true" onSubmit="return checkForm(this);">
  <table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2' bgcolor='#808080' rowspan='2'>
        <font color='#FFFFFF'><b>&nbsp;New Issue</b></font>
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
<%
  String categoryId = request.getParameter("cid");
  if (categoryId != null) Issue.setCategoryId(Integer.parseInt(categoryId));
%>
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
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2'>
        &nbsp;Issue
        Description:<br>
        &nbsp;
        <textarea rows="8" name="body" cols="70"><%= Issue.getBody() %></textarea><font color=red>*</font> <%= showAttribute(request, "bodyError") %><br>
        &nbsp;
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='50%' bgcolor='#808080' height='30'>
        <p align='right'>
          &nbsp;
          <input type="submit" value=" Save ">&nbsp;&nbsp;
        </p>
      </td>
      <td width='50%' bgcolor='#808080' height='30'>
        <p align='left'>
          &nbsp;&nbsp;
          <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Issues<%= ((Issue.getCategoryId() == -1)?"_Categories":"") %>&pid=<%= Project.getId() %>&cid=<%= Issue.getCategoryId() %>';">
        </p>
      </td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
  </table>
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="dosubmit" value="true">
</form>  
</body>
