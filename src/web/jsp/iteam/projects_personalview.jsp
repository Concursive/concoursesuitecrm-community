<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ProjectList" class="com.zeroio.iteam.base.ProjectList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%
  Iterator i = ProjectList.iterator();
  
  if (i.hasNext()) {
    String tableHighlight1 = "#000000";
    String tableHighlight2 = "#0000FF";
    String currentHighlight = tableHighlight2;
%>
  <table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
    <tr bgcolor="#E4E4E4">
      <td colspan="4">
        &nbsp;<b>Personal View:</b> My Recent Assignments
      </td>
    </tr>
    <tr bgcolor="#E4E4E4">
      <td colspan="4">
        &nbsp;<b>Projects:</b> <%= ProjectList.size() %>
      </td>
    </tr>
    <tr>
      <td colspan="4">
        <img border="0" src="images/graybar_main.gif" width="100%" height="11"><br>
        &nbsp;
      </td>
    </tr>    
<%
    while (i.hasNext()) {
      if (currentHighlight.equals(tableHighlight1)) {
        currentHighlight = tableHighlight2;
      } else {
        currentHighlight = tableHighlight1;
      }
      Project thisProject = (Project)i.next();
      String previousActivity = "-->NONE";
%>      
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>" align="center" valign="top">&nbsp;</td>
      <td width="658" bgcolor="#E4EBFD" align="left" valign="top" colspan="3">
        &nbsp;<a href="ProjectManagement.do?command=ProjectCenter&pid=<%= thisProject.getId() %>"><b><%= toHtml(thisProject.getTitle()) %>:</b></a>
        <%= toHtml(thisProject.getShortDescription()) %>
      </td>
    </tr>
    <tr>
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="418" bgcolor="#EFF0EA"><b>&nbsp;Assignments</b></td>
      <td width="140" bgcolor="#EFF0EA" align="left"><b>Status</b></td>
      <td width="100" bgcolor="#EFF0EA"><b>Due Date</b></td>
    </tr>
<%
    if (thisProject.getAssignments().size() > 0) {
      Iterator assignmentList = thisProject.getAssignments().iterator();
      while (assignmentList.hasNext()) {
        Assignment thisAssignment = (Assignment)assignmentList.next();
        if (!thisAssignment.getActivity().equals(previousActivity)) {
          previousActivity = thisAssignment.getActivity();  
%>  
    <tr>
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="418"><font color="#000066"><b>&nbsp;&nbsp;<%= thisAssignment.getActivity() %></b></font></td>
      <td width="140">&nbsp;</td>
      <td width="100">&nbsp;</td>
    </tr>
<%  
        }
%>        
    <tr>
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="418">
        &nbsp;&nbsp;&nbsp;<%= thisAssignment.getStatusGraphicTag() %>&nbsp;
        <a href="javascript:popURL('ProjectManagementAssignments.do?command=Modify&pid=<%= thisProject.getId() %>&aid=<%= thisAssignment.getId() %>&popup=true&return=ProjectPersonalView','CRM_Assignment','600','325','yes','no');" style="text-decoration:none;color:black;" onMouseOver="this.style.color='blue';window.status='Update this assignment';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisAssignment.getRole()) %></a>
      </td>
      <td width="140" align="left">&nbsp;<%= toHtml(thisAssignment.getStatus()) %></td>
      <td width="100">&nbsp;<%= thisAssignment.getRelativeDueDateString() %></td>
    </tr>
<%        
      }
    } else {
%>        
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="3">
        &nbsp;&nbsp;&nbsp;No Assignments found.
      </td>
    </tr>
<%
    }
%>
    <tr>
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="418" bgcolor="#EFF0EA"><b>&nbsp;Issues</b></td>
      <td width="140" bgcolor="#EFF0EA" align="left"><b>Posted</b></td>
      <td width="100" bgcolor="#EFF0EA"><b>From</b></td>
    </tr>
<%
    if (thisProject.getIssues().size() > 0) {
      Iterator issueList = thisProject.getIssues().iterator();
      while (issueList.hasNext()) {
        Issue thisIssue = (Issue)issueList.next();
%>      
    <tr>
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="418">
        &nbsp;&nbsp;&nbsp;#&nbsp;
        <a href="javascript:popURL('ProjectManagementIssues.do?command=Details&pid=<%= thisProject.getId() %>&iid=<%= thisIssue.getId() %>&popup=true','CRM_Issue','600','300','yes','yes');" style="text-decoration:none;color:black;" onMouseOver="this.style.color='blue';window.status='Review this issue';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisIssue.getSubject()) %></a>
      </td>
      <td width="140" align="left">&nbsp;<%= toHtml(thisIssue.getReplyDateTimeString()) %></td>
      <td width="100">&nbsp;<dhv:username id="<%= thisIssue.getModifiedBy() %>" /></td>
    </tr>
<%
      }
    } else {
%>
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="3">
        &nbsp;&nbsp;&nbsp;No Issues found.
      </td>
    </tr>
<%
    }
%>      
  <tr>
    <td colspan="4">
      &nbsp;
    </td>
  </tr>
<%      
    }
%>  
  <tr>
    <td colspan="4">
      <img border='0' src='images/graybar_invert.gif' width='100%' height='11'>
    </td>
  </tr>
  </table>
<%    
  } else {
%>  
<font color="red">There are currently no projects to display in this view.</font>
<%  
  }
%>
