<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*,com.darkhorseventures.webutils.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="Assignment" class="com.zeroio.iteam.base.Assignment" scope="request"/>
<jsp:useBean id="RequirementList" class="com.zeroio.iteam.base.RequirementList" scope="request"/>
<jsp:useBean id="ActivityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="StatusList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<jsp:useBean id="LoeList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<body bgcolor='#FFFFFF' onLoad="document.inputForm.role.focus();">
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    
    //Check required field
    if (form.role.value == "") {    
      messageText += "- Description field is required\r\n";
      formTest = false;
    }
    if (form.userAssignedId.selectedIndex == 0) {
      messageText += "- Assigned To is a required field\r\n";
      formTest = false;
    }
    if (form.activityId.selectedIndex == 0) {
      messageText += "- Activity Type is a required field\r\n";
      formTest = false;
    }
    if (form.statusId.selectedIndex == 0) {
      messageText += "- Current Status is a required field\r\n";
      formTest = false;
    }
  
    //Check number field
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

    //Check date field
    if ((form.dueDate.value != "") && (!checkDate(form.dueDate.value))) {
      messageText += "- Due date was not properly entered\r\n";
      formTest = false;
    }
      
    if (formTest == false) {
      messageText = "The activity form could not be submitted.          \r\nPlease verify the following items:\r\n\r\n" + messageText;
      alert(messageText);
      return false;
    } else {
      return true;
    }
  }
</script>
<form method="POST" name="inputForm" action="ProjectManagementAssignments.do?command=Insert&auto-populate=true" onSubmit="return checkForm(this);">
  <table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="100%" colspan="4" bgcolor="#000000" rowspan="2">
      <font color="#FFFFFF">&nbsp;<b>New Activity</b></font>
    </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">
        &nbsp;
      </td>
      <td width="100%" colspan="4">
        &nbsp;<br>
        &nbsp;Activity Description:<br>
        &nbsp;
        <input type='text' name='role' size='57' maxlength='150' value='<%= toHtmlValue(Assignment.getRole()) %>'><font color=red>*</font> <%= showAttribute(request, "roleError") %>
        <br>
      </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">
        &nbsp;
      </td>
      <td width="100%" colspan="4">
        &nbsp;<br>
        &nbsp;Link to Requirement:<br>
        &nbsp;
        <%= RequirementList.getHtmlSelect("requirementId", Assignment.getRequirementId()) %><font color=red>*</font> <%= showAttribute(request, "requirementIdError") %>
        <br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="34%" nowrap>&nbsp;Assigned To:<br>
        &nbsp; 
<% 
    TeamMemberList thisTeam = Project.getTeam();
    HtmlSelect team = new HtmlSelect();
    team.addItem(0, "-- Select User --");
    Iterator iTeam = thisTeam.iterator();
    while (iTeam.hasNext()) {
      TeamMember thisMember = (TeamMember)iTeam.next();
      team.addItem(thisMember.getUserId(), 
           ((Contact)thisMember.getContact()).getNameLast() + ", " +
           ((Contact)thisMember.getContact()).getNameFirst());
    }
%>
        <%= team.getHtml("userAssignedId", Assignment.getUserAssignedId()) %><font color=red>*</font> <%= showAttribute(request, "userAssignedIdError") %>
        <br>&nbsp;
      </td>
      <td width="33%" colspan="2" nowrap>&nbsp;Activity
        Type:<br>
        &nbsp;
        <%= ActivityList.getHtmlSelect("activityId", Assignment.getActivityId()) %><font color=red>*</font> <%= showAttribute(request, "activityIdError") %>
        <br>
        &nbsp;
      </td>
      <td width="33%">&nbsp;Priority:<br>
        &nbsp;
        <%= PriorityList.getHtmlSelect("priorityId", Assignment.getPriorityId()) %>
        <br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="50%" colspan="2">&nbsp;Current
        Status:<br>
        &nbsp;<%--= Assignment.getStatusGraphic() --%><%= StatusList.getHtmlSelect("statusId", Assignment.getStatusId()) %><font color=red>*</font> <%= showAttribute(request, "statusIdError") %>
        <br>
        &nbsp;
      </td>
      <td width="50%" colspan="2">&nbsp;Assigned
        Technology:<br>
        &nbsp;
        <input type="text" name="technology" size="24" maxlength="50" value="<%= toHtmlValue(Assignment.getTechnology()) %>">
        <br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="50%" colspan="2">&nbsp;Level of Effort:<br>
        &nbsp;
        <input type="text" name="estimatedLoe" size="4" value="<%= Assignment.getEstimatedLoeValue() %>">
        <%= LoeList.getHtmlSelect("estimatedLoeTypeId", Assignment.getEstimatedLoeTypeId()) %>
        <br>
        &nbsp;
      </td>
      <td width="50%" colspan="2">&nbsp;Activity Due:<br>
        &nbsp;
        <input type="text" name="dueDate" size="20" value="<%= Assignment.getDueDateValue() %>">
        <a href="javascript:popCalendar('inputForm', 'dueDate');">Date</a>
        <br>
        &nbsp;
      </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#000000">&nbsp;</td>
      <td width="100%" bgcolor="#000000" height="30" colspan="4">
        <p align="center">
          &nbsp;
          <input type='submit' value=' Save ' onClick="javascript:this.form.dosubmit.value='true';">&nbsp;&nbsp;
          &nbsp;&nbsp;
          <input type='submit' value='Cancel' onClick="javascript:this.form.dosubmit.value='false';this.form.action='ProjectManagement.do?command=ProjectCenter&section=Assignments&pid=<%= Project.getId() %>';">
          &nbsp;&nbsp;
        </p>
      </td>
      <td width="2" bgcolor="#000000">&nbsp;</td>
    </tr>
  </table>
  <input type="hidden" name="pid" value="<%= Project.getId() %>">
  <input type="hidden" name="dosubmit" value="false">
</form>
</body>
