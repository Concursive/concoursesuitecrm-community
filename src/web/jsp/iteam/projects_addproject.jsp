<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="ProjectList" class="com.zeroio.iteam.base.ProjectList" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body bgcolor="#FFFFFF" onLoad="document.inputForm.title.focus()">
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    
    //Check required fields
    if (form.title.value == "") {
      messageText += "- Title is a required field\r\n";
      formTest = false;
    }
    if (form.shortDescription.value == "") {    
      messageText += "- Description is a required field\r\n";
      formTest = false;
    }
    if (form.requestDate.value == "") {    
      messageText += "- Start Date is a required field\r\n";
      formTest = false;
    }
  
    if (formTest == false) {
      messageText = "The form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="post" name="inputForm" action="ProjectManagement.do?command=InsertProject&auto-populate=true" onSubmit="return checkForm(this);">
  <center>
  <% if (request.getAttribute("actionError") != null) { %>
    <%= showError(request, "actionError") %>
  <%}%>
  <table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
      <td width="100%" colspan="2" bgcolor="#808080" rowspan="2">
        <font color="#FFFFFF">
        &nbsp;<img border="0" src="images/task.gif">
        <b>New Project Information</b>
        </font>
      </td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
      <td width="100%" colspan="2">
        &nbsp;<br>
				&nbsp;Import Requirements, Assignments, and Team from an Existing Project<br>
        &nbsp;
				<%= ProjectList.getHtmlSelect("templateId", 0) %>
      </td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
      <td width="100%" colspan="2">
        &nbsp;<br>
        &nbsp;Project Title:<br>
        &nbsp;
        <input type="text" name="title" size="57" maxlength="100" value="<%= toHtmlValue(Project.getTitle()) %>"><font color=red>*</font> <%= showAttribute(request, "titleError") %><br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
      <td width="100%" colspan="2">
        &nbsp;Project Short
        Description:<br>
        &nbsp;
        <input type="text" name="shortDescription" size="57" maxlength="200" value="<%= toHtmlValue(Project.getShortDescription()) %>"><font color=red>*</font> <%= showAttribute(request, "shortDescriptionError") %><br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
      <td width="50%">
        &nbsp;Project Requested By:<br>
        &nbsp;
        <input type="text" name="requestedBy" size="24" maxlength="50" value="<%= toHtmlValue(Project.getRequestedBy()) %>"><br>
        &nbsp;
      </td>
      <td width="50%">
        &nbsp;Department:<br>
        &nbsp;
        <input type="text" name="requestedByDept" size="24" maxlength="50" value="<%= toHtmlValue(Project.getRequestedByDept()) %>"><br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
    
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
      <td width="50%">
        &nbsp;Start Date:<br>
        &nbsp;
        <input type="text" name="requestDate" size="10" value="<%= Project.getRequestDateString() %>">
        <a href="javascript:popCalendar('inputForm', 'requestDate');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle" height="16" width="16"/></a>
        <font color=red>*</font> <%= showAttribute(request, "requestDateError") %><br>
        &nbsp;
      </td>
      <td width="50%">
        &nbsp;Category:<br>
        &nbsp;
        <%= DepartmentList.getHtmlSelect("departmentId", -1) %>
        <br>&nbsp;
      </td>    
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
<%
  String projectApprovedCheck = "";
  String projectClosedCheck = "";
%>
      <td width="100%" colspan="2">
        &nbsp;<input type="checkbox" name="approved" value="ON"<%=projectApprovedCheck%>>
        Project Approved <%= Project.getApprovedDateString() %>
      </td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
      <td width="100%" colspan="2">
        &nbsp;<input type="checkbox" name="closed" value="ON"<%=projectClosedCheck%>>
        Project Closed <%= Project.getCloseDateString() %><br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
      <td width="50%" bgcolor="#808080" height="30">
        <p align="right">
          &nbsp;<input type="hidden" name="dosubmit" value="true">
          <input type="submit" value=" Save ">
          &nbsp;&nbsp;
        </p>
    </td>
      <td width="50%" bgcolor="#808080" height="30">
        <p align="left">
          &nbsp;&nbsp;<input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do'">
        </p>
    </td>
    <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
  </table>
  </center>
</form>
</body>
