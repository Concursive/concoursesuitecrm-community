<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="projectRequirementsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>

<table border='0' width='100%'  bgcolor='#FFFFFF' cellspacing='0' cellpadding='0'>
  <tr>
    <form name="listView" method="post" action="ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= projectRequirementsInfo.getOptionValue("open") %>>Open Requirements</option>
        <option <%= projectRequirementsInfo.getOptionValue("closed") %>>Closed Requirements</option>
        <option <%= projectRequirementsInfo.getOptionValue("all") %>>All Requirements</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="projectRequirementsInfo"/>
    </td>
    </form>
  </tr>
</table>

<table border='0' width='100%'  bgcolor='#000000' cellspacing='0' cellpadding='0'>  
  <tr>
    <td width="100%" bgcolor="#FF9900" colspan="2">
      <b><font color='#FFFFFF'>&nbsp;Requirements</font></b>
    </td>
  </tr>
</table>
   
<table border='0' width='100%' cellpadding='0' cellspacing='0'>
  <tr>
    <td width='19' bgcolor='#808080'>&nbsp;</td>
    <td width='86' bgcolor='#808080'><font color='#FFFFFF'>&lt;Submitted&gt;</font></td>
    <td width='412' bgcolor='#808080'><font color='#FFFFFF'>&lt;Description&gt;</font></td>
    <td width='118' bgcolor='#808080'><font color='#FFFFFF'>&lt;Effort&gt;</font></td>
  </tr>
<%    
  String bgColorVar = " bgColor='#E4E4E4'";
  RequirementList requirements = Project.getRequirements();
  Iterator i = requirements.iterator();
  
  while (i.hasNext()) {
    Requirement thisRequirement = (Requirement)i.next();
%>    
  <tr<%= bgColorVar %>>
    <td width='19' valign='top'>
      <%= thisRequirement.getStatusGraphicTag() %>
    </td>
    <td width='86' valign='top'>
      <%= thisRequirement.getEnteredString() %>
    </td>
    <td width='412' valign='top'>
      <%= thisRequirement.getAssignmentTag("ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=" + Project.getId() + "&" + (thisRequirement.isTreeOpen()?"contract":"expand") + "=" + thisRequirement.getId()) %>
      <a href="ProjectManagementRequirements.do?command=Modify&rid=<%= thisRequirement.getId() %>&pid=<%= Project.getId() %>"><%= toHtml(thisRequirement.getShortDescription()) %></a>
      (<%= thisRequirement.getAssignments().size() %> activit<%= (thisRequirement.getAssignments().size() == 1?"y":"ies") %>)<br>
      <%= ("".equals(thisRequirement.getSubmittedBy())?"":"<i>Requested By " + thisRequirement.getSubmittedBy() + "</i>") %>
    </td>
    <td width='118' valign='top'>
      Due: <%= thisRequirement.getDeadlineString() %><br>
      LOE: <%= thisRequirement.getEstimatedLoeString() %>
    </td>
  </tr>
<%    
    if (thisRequirement.isTreeOpen() && thisRequirement.getAssignments().size() > 0) {
      Iterator assignments = thisRequirement.getAssignments().iterator();
      while (assignments.hasNext()) {
        Assignment thisAssignment = (Assignment)assignments.next();
%>
  <tr<%= bgColorVar %>>
    <td valign="top" colspan="2">
      &nbsp;
    </td>
    <td valign="top">
      &nbsp;<%= thisAssignment.getStatusGraphicTag() %>
      <a href="javascript:popURL('ProjectManagementAssignments.do?command=Modify&pid=<%= Project.getId() %>&aid=<%= thisAssignment.getId() %>&popup=true&return=ProjectRequirements&param=<%= Project.getId() %>','CRM_Assignment','600','325','yes','no');" style="text-decoration:none;color:black;" onMouseOver="this.style.color='blue';window.status='Update this assignment';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisAssignment.getRole()) %></a>
      (<dhv:username id="<%= thisAssignment.getUserAssignedId() %>"/>)
    </td>
    <td valign="top" nowrap>
      Due: <%= thisAssignment.getRelativeDueDateString() %><br>
      LOE: <%= thisAssignment.getEstimatedLoeString() %>
    </td>
  </tr>
<%
      }
    }
    if (bgColorVar.equals(" bgColor='#E4E4E4'")) {
      bgColorVar = "";
    } else {
      bgColorVar = " bgColor='#E4E4E4'";
    }
  }
%>
</table>
&nbsp;<br><hr color='#000000' width='100%' noshade size='1'>
<table border='0' width='100%'>
  <tr>
    <td width='100%'>
      <img border='0' src='images/box.gif' alt='Incomplete'> Requirement is incomplete<br>
      <img border='0' src='images/box-checked.gif' alt='Completed'> Requirement has been completed (or closed)<br>
      <img border='0' src='images/box-hold.gif' alt='On Hold'> Requirement has not been approved
    </td>
  </tr>
</table>
  
