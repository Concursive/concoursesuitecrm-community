<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*,com.darkhorseventures.webutils.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="IssueCategory" class="com.zeroio.iteam.base.IssueCategory" scope="request"/>
<jsp:useBean id="Issue" class="com.zeroio.iteam.base.Issue" scope="request"/>
<jsp:useBean id="IssueReply" class="com.zeroio.iteam.base.IssueReply" scope="request"/>
<%@ include file="initPage.jsp" %>
<body bgcolor='#FFFFFF' onLoad="document.inputForm.body.focus();">
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
  
    //Check required fields
    if ((form.subject.value == "") || (form.body.value == "")) {    
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
<form method="POST" name="inputForm" action="ProjectManagementIssueReply.do?command=InsertReply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>&auto-populate=true" onSubmit="return checkForm(this);">
  <table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2' bgcolor='#808080' rowspan='2'>
        <font color='#FFFFFF'><b>&nbsp;Reply to Issue</b></font>
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2'>
        &nbsp;Subject:<br>
        &nbsp;&nbsp;<input type='text' name='subject' size='57' maxlength='50' value='<%= toHtmlValue(IssueReply.getSubject()) %>'><font color=red>*</font> <%= showAttribute(request, "subjectError") %><br>
        &nbsp;
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='100%' colspan='2'>
        &nbsp;Description:<br>
        &nbsp;
        <textarea rows='8' name='body' cols='70'><%= IssueReply.getBody() %></textarea><font color=red>*</font> <%= showAttribute(request, "bodyError") %><br>
        &nbsp;
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
    <tr>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
      <td width='50%' bgcolor='#808080' height='30'>
        <p align='right'>
          &nbsp;
          <input type="submit" value=" Save " onClick="javascript:this.form.dosubmit.value='true';">&nbsp;&nbsp;
        </p>
      </td>
      <td width='50%' bgcolor='#808080' height='30'>
        <p align='left'>
          &nbsp;&nbsp;
          <input type='submit' value='Cancel' onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagementIssues.do?command=Details&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>';">
        </p>
      </td>
      <td width='2' bgcolor='#808080'>&nbsp;</td>
    </tr>
  </table>
  <input type='hidden' name='projectId' value='<%= Project.getId() %>'>
  <input type='hidden' name='issueId' value='<%= Issue.getId() %>'>
  <input type="hidden" name="dosubmit" value="false">
</form>
<br>
Original Issue:
<table border='0' width='100%' bgcolor='#808080' cellpadding='0' cellspacing='0'>
  <tr>
    <td width='5'>&nbsp;</td>
    <td valign='top' align='left'>
      <font color='#FFFFFF'>
        <b><%= toHtml(Issue.getSubject()) %></b> (<%= toHtml(Issue.getCategory()) %>)
      </font>
    </td>
  </tr>
  <tr>
    <td width='5'>&nbsp;</td>
    <td valign='top' align='left'>
      <font color='#FFFFFF'>
        <i>Posted by <%= toHtml(Issue.getUser()) %> on <%= Issue.getEnteredDateTimeString() %></i>
      </font>
    </td>
  </tr>
</table>

<table border='0' width='100%' bgcolor='#808080' cellpadding='1' cellspacing='1'>
  <tr>
    <td bgcolor='#FFFFFF'><%= toHtml(Issue.getBody()) %></td>
  </tr>
</table>
  
&nbsp;<hr color='#000000' width='100%' noshade size='1'>
  
<b>There <%= Issue.getReplyCountString() %> to this issue.</b><br>
</body>
