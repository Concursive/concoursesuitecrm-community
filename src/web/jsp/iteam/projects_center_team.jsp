<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<%@ include file="initPage.jsp" %>
<table border='0' width='100%'  bgcolor='#000000' cellspacing='0' cellpadding='0'>
  <tr>
    <td width='100%' bgcolor='#0033CC'>
      <b><font color='#FFFFFF'>&nbsp;Team</font></b>
    </td>
  </tr>
</table>
   
<table border='0' width='100%' bgcolor='#FFFFFF' cellpadding='0' cellspacing='0'>
  <tr>
    <td width='150' bgcolor='#808080' align='left' valign='bottom'><font color='#FFFFFF'>&lt;Name&gt;</font></td>
    <td width='220' bgcolor='#808080' align='left' valign='bottom'><font color='#FFFFFF'>&lt;Company&gt;</font></td>
    <td width='150' bgcolor='#808080' align='left' valign='bottom'><font color='#FFFFFF'>&lt;Email&gt;</font></td>
    <td width='100' bgcolor='#808080' align='left' valign='bottom'><font color='#FFFFFF'>&lt;Added&gt;</font></td>
    <td width='30' bgcolor='#808080' align='left' valign='bottom'><font color='#FFFFFF'>&lt;Project Manager&gt;</font></td>
  </tr>
<%    
  String bgColorVar = " bgColor='#E4E4E4'";
  TeamMemberList team = Project.getTeam();
  Iterator i = team.iterator();
  while (i.hasNext()) {
    TeamMember thisMember = (TeamMember)i.next();
    Contact thisContact = (Contact)thisMember.getContact();
    if (thisContact == null) thisContact = new Contact();
%>    
  <tr>
    <td align='left'<%= bgColorVar %>><b><font color='black'><%= toHtml(thisContact.getNameFull()) %></font></b></td>
    <td align='left'<%= bgColorVar %>><font color='black'><%= toHtml(thisContact.getOrgName()) %><br><%= toHtml(thisContact.getDepartmentName()) %></font></td>
    <td align='left'<%= bgColorVar %>><font color='black'><a href='mailto:<%= thisContact.getEmailAddress("Business") %>'><%= thisContact.getEmailAddress("Business") %></a>&nbsp;</font></td>
    <td align='left'<%= bgColorVar %>><font color='black'><%= thisMember.getEnteredString() %></font></td>
    <td align='center'<%= bgColorVar %>><font color='black'><%= thisMember.getUserLevel() %></font></td>
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
&nbsp;<br>
<hr color='#000000' width='100%' noshade size='1'>
  
