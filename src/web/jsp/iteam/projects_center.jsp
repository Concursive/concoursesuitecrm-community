<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<%@ include file="initPage.jsp" %>
<%
  if (Project.getId() == -1) {
%>
<BR><font color='red'>This project does not belong to you, or does not exist!
<%
  } else {
%>
<table border='0' width='100%' cellspacing='0' cellpadding='0'>
  <tr>
  <td bgcolor='#E4E4E4' valign='top' nowrap>
    <font color='#000000'>
      <b><img border="0" src="images/project.gif" align="absbottom">&nbsp;Project Center</b>
    </font>
  </td>
  <td bgcolor='#E4E4E4' valign='top'>
    <p align='right'>
    <font color='#000000'>

<!--  if ((accessAdmin.equals(trueChar)) || (projectManager == 1)) {-->
    &nbsp; [<a href="ProjectManagementRequirements.do?command=Add&pid=<%= Project.getId() %>" style="text-decoration:none;color:black;" onMouseOver="this.style.color = 'blue';" onMouseOut="this.style.color = 'black';">New Requirement</a>]

<!--  if ((accessAdmin.equals(trueChar)) || (projectManager == 1)) {-->
    &nbsp; [<a href="ProjectManagementTeam.do?command=Modify&pid=<%= Project.getId() %>" style="text-decoration:none;color:black;" onMouseOver="this.style.color = 'blue';" onMouseOut="this.style.color = 'black';">Modify Team</a>]

<!--  if ((accessAdmin.equals(trueChar)) || (projectManager == 1)) {-->
    &nbsp; [<a href="ProjectManagementAssignments.do?command=Add&pid=<%= Project.getId() %><%= ("Requirements".equals(request.getParameter("section"))?"&return=Requirements":"") %>" style="text-decoration:none;color:black;" onMouseOver="this.style.color = 'blue';" onMouseOut="this.style.color = 'black';">New Activity</a>]

    &nbsp; [<a href="ProjectManagementIssues.do?command=Add&pid=<%= Project.getId() %>" style="text-decoration:none;color:black;" onMouseOver="this.style.color = 'blue';" onMouseOut="this.style.color = 'black';">New Issue</a>]

  <!--  if ((accessAdmin.equals(trueChar)) || (projectManager == 1)) {-->
    <br>[<a href="ProjectManagement.do?command=ModifyProject&pid=<%= Project.getId() %>&return=ProjectCenter" style="text-decoration:none;color:black;" onMouseOver="this.style.color = 'blue';" onMouseOut="this.style.color = 'black';">Update Project</a>]

  </font>
  </td>
  </tr> 
</table>

<table border='0' width='100%' cellspacing='0' cellpadding='0'>
  <tr>
  <td width='100%'>
    <img border='0' src='images/graybar_main.gif' width='100%' height='11'>
<%
    String approvalResponse = "";
    if (Project.getApprovalDate() == null) {
      approvalResponse = ", <font color='red'>Under Review</font>";
    } else {
      approvalResponse = ", <font color='darkgreen'>Approved</font>";
    }
    
    String requestedByResponse = "";
    if ((Project.getRequestedBy().equals(""))) {
      requestedByResponse = "unknown request source";
    } else {
      requestedByResponse = Project.getRequestedBy();
    }

    String requestedDeptResponse = "";
    if (Project.getRequestedByDept().equals("")) {
     requestedDeptResponse = "";
    } else {
      requestedDeptResponse = "from " + Project.getRequestedByDept();
    }
%>
    <table border='0' width='100%' cellspacing='0' cellpadding='0'>
    <tr>
      <td width='15' bgcolor='#E6E9CC' align='left' valign='top'>
        <br>
      </td>
      <td width='200' bgcolor='#E6E9CC' align='left' valign='top'>
        <b><%= toHtml(Project.getTitle()) %></b><br>
        <%= Project.getRequestDateString() %><%= approvalResponse %>
      </td>
      <td bgcolor='#E6E9CC' align='left' valign='top'>
        <%= toHtml(Project.getShortDescription()) %><br>Requested by <%= requestedByResponse %> <%= requestedDeptResponse %>
      </td>
      <td bgcolor='#E6E9CC' align='right' valign='top'>
        &nbsp;
      </td>
    </tr>
    </table>

    <table border='0' width='100%' cellspacing='0' cellpadding='0'>
    <tr>
      <td width='15' bgcolor='#E6E9CC' align='left' valign='top'>&nbsp;</td>
      <td bgcolor='#E6E9CC' align='right' valign='center'>
        <a href="ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>" onMouseOver="window.status='Requirements'; return true;" onMouseOut="window.status=''; return true;"><img border='0' src='images/pc-requirements.gif' width='89' height='20'></a>
        <a href="ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>" onMouseOver="window.status='Team Members'; return true;" onMouseOut="window.status=''; return true;"><img border='0' src='images/pc-team.gif'></a>
        <a href="ProjectManagement.do?command=ProjectCenter&section=Assignments&pid=<%= Project.getId() %>" onMouseOver="window.status='Activities'; return true;" onMouseOut="window.status=''; return true;"><img border='0' src='images/pc-activities.gif' width='63' height='20'></a>
        <a href="ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=<%= Project.getId() %>" onMouseOver="window.status='Issues'; return true;" onMouseOut="window.status=''; return true;"><img border='0' src='images/pc-issues.gif' width='48' height='20'></a>
        <a href="ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=<%= Project.getId() %>&folderId=<%= Project.getFiles().getFolderId() %>" onMouseOver="window.status='File Sharing'; return true;" onMouseOut="window.status=''; return true;"><img border='0' src='images/pc-filesharing.gif' width='76' height='20'></a>
        <table border="0" width="100%" cellspacing="7" bgcolor="#FFFFFF">
          <tr>
            <td>
<% String includeSection = "projects_center_" + (String)request.getAttribute("IncludeSection") + ".jsp"; %>
<jsp:include page="<%= includeSection %>" flush="true"/>
            </td>
          </tr>
        </table>
      </td>
      <td width='15' bgcolor='#E6E9CC' align='right' valign='top'>&nbsp;</td>
    </tr>
  </table>
    
  <table border='0' width='100%' cellspacing='0' cellpadding='0'>
    <tr>
      <td width='100%' bgcolor='#E6E9CC' align='left' valign='top'>&nbsp;</td>
    </tr>
  </table>
</table>

<%
  }
%>
