<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<%@ include file="initPage.jsp" %>
<table border='0' width='100%'  bgcolor='#000000' cellspacing='0' cellpadding='0'>
  <tr>
    <td width='100%' bgcolor='#FF9900'>
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
  String statusGraphic = "&nbsp;";
  String bgColorVar = " bgColor='#E4E4E4'";
  RequirementList requirements = Project.getRequirements();
  Iterator i = requirements.iterator();
  
  while (i.hasNext()) {
    Requirement thisRequirement = (Requirement)i.next();
%>    
  <tr<%= bgColorVar %>>
    <td width='19' valign='top'>
      <%= statusGraphic %>
    </td>
    <td width='86' valign='top'>
      <%= thisRequirement.getEnteredString() %>
    </td>
    <td width='412' valign='top'>
      <a href="/ProjectManagementRequirements.do?command=Modify&rid=<%= thisRequirement.getId() %>&pid=<%= Project.getId() %>"><%= toHtml(thisRequirement.getShortDescription()) %></a><br>
      <i>Requested By <%= thisRequirement.getSubmittedBy() %></i>
    </td>
    <td width='118' valign='top'>
      Due: <%= thisRequirement.getDeadlineString() %><br>
      LOE: <%= thisRequirement.getEstimatedLoeString() %>
    </td>
  </tr>
<%    
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
      <img border='0' src='/images/box.gif' alt='Incomplete'> Requirement is incomplete<br>
      <img border='0' src='/images/box-checked.gif' alt='Completed'> Requirement has been completed (or closed)<br>
      <img border='0' src='/images/box-hold.gif' alt='On Hold'> Requirement has not been approved
    </td>
  </tr>
</table>
  
