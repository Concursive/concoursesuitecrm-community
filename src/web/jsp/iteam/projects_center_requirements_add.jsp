<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="Requirement" class="com.zeroio.iteam.base.Requirement" scope="request"/>
<jsp:useBean id="LoeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<body bgcolor="#FFFFFF" onLoad="document.inputForm.shortDescription.focus();">
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    //Required fields
    if (form.shortDescription.value == "") {    
      messageText += "- Title is a required field\r\n";
      formTest = false;
    }
    if (form.description.value == "") {    
      messageText += "- Description is a required field\r\n";
      formTest = false;
    }
    
    //Check LOE number field
    var valid = "0123456789.";
    var ok = true;
    if (form.estimatedLoe.value != "") {
      for (var i=0; i<form.estimatedLoe.value.length; i++) {
        temp = "" + form.estimatedLoe.value.substring(i, i+1);
        if (valid.indexOf(temp) == "-1") {
          ok = false;
        }
      }
      if (ok == false) {
        messageText += "- Only numbers are allowed in the LOE field\r\n";
        formTest = false;
      }
    }
    
    //Check date fields
    if ((form.deadline.value != "") && (!checkDate(form.deadline.value))) {
      messageText += "- Requirements due date was not properly entered\r\n";
      formTest = false;
    }
    
    if (formTest == false) {
      messageText = "The requirements form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementRequirements.do?command=Insert&auto-populate=true" onSubmit="return checkForm(this);">
  <table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="100%" colspan="2" bgcolor="#000000" rowspan="2">
        <font color="#FFFFFF"><b>&nbsp;New Requirements</b></font>
      </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="100%" colspan="2">&nbsp;<br>
        &nbsp;Requirements Title:<br>
        &nbsp;
        <input type="text" name="shortDescription" size="57" maxlength="255" value="<%= toHtmlValue(Requirement.getShortDescription()) %>"><font color=red>*</font> <%= showAttribute(request, "shortDescriptionError") %><br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="50%">&nbsp;Requested By:<br>
        &nbsp;
        <input type="text" name="submittedBy" size="24" maxlength="50" value="<%= toHtmlValue(Requirement.getSubmittedBy()) %>"><br>
        &nbsp;
      </td>
      <td width="50%">&nbsp;Department or Company:<br>
        &nbsp;
        <input type="text" name="departmentBy" size="24" maxlength="50" value="<%= toHtmlValue(Requirement.getDepartmentBy()) %>"><br>
        &nbsp;</td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="50%">&nbsp;Estimated Level of Effort:<br>
        &nbsp;
        <input type="text" name="estimatedLoe" size="4" value="<%= Requirement.getEstimatedLoeValue() %>">
        <%= LoeList.getHtmlSelect("estimatedLoeTypeId", Requirement.getEstimatedLoeTypeId()) %>
        <br>
        &nbsp;
      </td>
      <td width="50%">
        &nbsp;Requirements Due:<br>
        &nbsp;
        <input type="text" name="deadline" size="20" onChange="checkDate(this.value)" value="<%= toHtmlValue(Requirement.getDeadlineValue()) %>">
        <a href="javascript:popCalendar('inputForm', 'deadline');">Date</a><br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="50%">&nbsp;
        <input type="checkbox" name="approved" value="ON" <%= (Requirement.getApproved()?"checked":"") %>>
        Requirements Approved
      </td>
      <td width="50%">&nbsp;</td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="50%">&nbsp;
        <input type="checkbox" name="closed" value="ON" <%= (Requirement.getClosed()?"checked":"") %>>
        Requirements Closed<br>
        &nbsp;
      </td>
      <td width="50%">&nbsp;</td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="100%" colspan="2">&nbsp;Requirements:<br>
        &nbsp;
        <textarea rows="8" name="description" cols="80"><%= toString(Requirement.getDescription()) %></textarea><font color=red>*</font> <%= showAttribute(request, "descriptionError") %><br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="50%" bgcolor="#000000" height="30">
        <p align="right">&nbsp;
        <input type="submit" value=" Save ">&nbsp;&nbsp;</p>
      </td>
      <td width="50%" bgcolor="#000000" height="30">
        <p align="left">
          &nbsp;&nbsp;
          <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>';">
        </p>
      </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
  </table>
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="dosubmit" value="true">
</form>
</body>
