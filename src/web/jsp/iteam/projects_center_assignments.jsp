<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<%@ include file="initPage.jsp" %>
<%
  String bgColorVar = "";
  int firstRun = 1;
  
  String previousActivityDescription = ""; 
%>
  <table border='0' width='100%'  bgcolor='#000000' cellspacing='0' cellpadding='0'>
  <tr>
    <td width='100%' bgcolor='#008000'>
      <font color='#FFFFFF'>&nbsp;<b>Activities</b></font>
    </td>
  </tr>
  </table>
  <table border='0' width='100%' cellspacing='0' cellpadding='0'>
<%    
  AssignmentList assignments = Project.getAssignments();
  Iterator i = assignments.iterator();
  while (i.hasNext()) {
  
    Assignment thisAssignment = (Assignment)i.next();

    //Draw the activity category
    if (!previousActivityDescription.equals(thisAssignment.getActivity())) {
      if (firstRun == 0) {
%>
      <tr>
        <td colspan="5">
          &nbsp;
        </td>
      </tr>
<%    
      }
      previousActivityDescription = thisAssignment.getActivity();
%>      
      <tr bgcolor="#808080">
        <td width='19' valign='middle'>&nbsp;</td>
        <td valign='middle'>
          <b><font color='#FFFFFF'><%=toHtml(thisAssignment.getActivity())%></font></b>
        </td>
        <td width='85' valign='middle' nowrap><font color='#FFFFFF'>&lt;Assigned&gt;</font></td>
        <td width='85' valign='middle' nowrap><font color='#FFFFFF'>&lt;Due&gt;</font></td>
        <td width='110' valign='middle' nowrap><font color='#FFFFFF'>&lt;Assigned To&gt;</font></td>
        <td width='111' valign='middle' nowrap><font color='#FFFFFF'>&lt;Status&gt;</font></td>
      </tr>
<%      
      bgColorVar = " bgColor='#E4E4E4'";
      firstRun = 0;
    }  
%>    
    <tr<%= bgColorVar %>>
      <td width='19' valign='top'> <%= thisAssignment.getStatusGraphicTag() %></td>
      <td valign='top'>
        <a href="ProjectManagementAssignments.do?command=Modify&aid=<%= thisAssignment.getId() %>&pid=<%= Project.getId() %>"><%= toHtml(thisAssignment.getRole()) %></a><br>
        <font color='#808080'><%= toHtml(thisAssignment.getTechnology()) %></font>
      </td>
      <td width='85' nowrap valign='top'><%= thisAssignment.getAssignDateString() %></td>
      <td width='85' nowrap valign='top'><%= thisAssignment.getRelativeDueDateString() %></td>
      <td width='110' nowrap valign='top'><%= thisAssignment.getUserAssigned() %><br>
      LOE: <%= thisAssignment.getEstimatedLoeString() %></td>
      <td width='111' valign='top' nowrap>
        <%= toHtml(thisAssignment.getStatus()) %><br>
        <%= thisAssignment.getStatusDateString() %>
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
&nbsp;<br>
<hr color='#000000' width='100%' noshade size='1'>
<table border='0' width='100%' bgColor="#FFFFFF">
  <tr>
    <td width='100%'>
      <img border='0' src='images/box.gif' alt='Incomplete'>
      Assignment incomplete<br>
      <img border='0' src='images/box-checked.gif' alt='Completed'>
      Assignment has been completed<br>
      <img border='0' src='images/box-closed.gif' alt='Closed'>
      Assignment has been closed<br>
      <img border='0' src='images/box-hold.gif' alt='On Hold'>
      Assignment on hold
    </td>
  </tr>
</table>
  
