<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="DepartmentList" class="com.darkhorseventures.webutils.LookupList" scope="request"/>
<%@ include file="initPage.jsp" %>
<body bgcolor='#FFFFFF' onLoad="document.inputForm.title.focus()">
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popCalendar.js"></script>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <form method="POST" name="inputForm" action="ProjectManagement.do?command=UpdateProject&auto-populate=true">
    <input type="hidden" name="id" value="<%= Project.getId() %>">
    <input type="hidden" name="modified" value="<%= Project.getModified() %>">
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
      <td width="100%" colspan="2" bgcolor="#808080" rowspan="2">
        <font color="#FFFFFF">
          &nbsp;<img border='0' src='images/task.gif'>
          <b>Update Existing Project Information</b>
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
        <a href="javascript:popCalendar('inputForm', 'requestDate');">Date</a>
        <font color=red>*</font> <%= showAttribute(request, "requestDateError") %><br>
        &nbsp;
      </td>
      <td width="50%">
        &nbsp;Category:<br>
        &nbsp;
        <%= DepartmentList.getHtmlSelect("departmentId", Project.getDepartmentId()) %>
        <br>&nbsp;
      </td>    
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
    <tr>
      <td width="2" bgcolor="#808080">&nbsp;</td>
<%
  String projectApprovedCheck = "";
  String projectClosedCheck = "";
  
  if (Project.getApprovalDate() != null) {
    projectApprovedCheck = " checked";
  }
  
  if (Project.getCloseDate() != null) {
    projectClosedCheck = " checked";
  }
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
          &nbsp;
          <input type='submit' value=' Update '>
          &nbsp;&nbsp;
        </p>
      </td>
      <td width="50%" bgcolor="#808080" height="30">
        <p align="left">
          &nbsp;&nbsp;<input type="submit" value="Cancel" onClick="javascript:this.form.action='ProjectManagement.do?command=ProjectCenter&pid=<%= Project.getId() %>'">
        </p>
      </td>
      <td width="2" bgcolor="#808080">&nbsp;</td>
    </tr>
  </form>
</table>
</body>
