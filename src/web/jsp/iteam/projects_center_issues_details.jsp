<%@ page import="java.util.*,org.aspcfs.modules.*,com.zeroio.iteam.base.*,org.aspcfs.webutils.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="IssueCategory" class="com.zeroio.iteam.base.IssueCategory" scope="request"/>
<jsp:useBean id="Issue" class="com.zeroio.iteam.base.Issue" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border='0' width='100%' bgcolor='#000000' cellspacing='0' cellpadding='0'>
<tr>
  <td width='100%' bgcolor='#003399'>
    <font color='#FFFFFF'>&nbsp;<b>Issues: <%= toHtml(IssueCategory.getDescription()) %></b></font>
  </td>
</tr>
</table>
<br>
  
<table border='0' width='100%' bgcolor='#808080' cellpadding='0' cellspacing='0'>
  <tr>
    <td width='5'>&nbsp;</td>
    <td valign='top' align='left'>
      <font color='#FFFFFF'>
        <b><%= toHtml(Issue.getSubject()) %></b> (<%= toHtml(Issue.getCategory()) %>)
      </font>
    </td>
  </tr>
  <tr>
    <td width='5'>&nbsp;</td>
    <td valign='top' align='left'>
      <font color='#FFFFFF'>
        <i>Posted by <%= toHtml(Issue.getUser()) %> on <%= Issue.getEnteredDateTimeString() %></i>
      </font>
    </td>
  </tr>
</table>
  
<table border='0' width='100%' bgcolor='#808080' cellpadding='1' cellspacing='1'>
  <tr>
    <td bgcolor='#FFFFFF'><%= toHtml(Issue.getBody()) %></td>
  </tr>
</table>
  
&nbsp;<hr color='#000000' width='100%' noshade size='1'>
  
<b>There <%= Issue.getReplyCountString() %> to this issue.</b><br>
<%
  IssueReplyList replyList = Issue.getReplyList();
  Iterator i = replyList.iterator();
  
  while (i.hasNext()) {
    IssueReply thisReply = (IssueReply)i.next();
%>    
<br>
<table border='0' width='100%' bgcolor='#E4E4E4' cellpadding='1' cellspacing='1'>
  <tr>
    <td valign='top'>
      <font color='#000000'>
        <i>Reply by <%= toHtml(thisReply.getUser()) %> on <%= thisReply.getEnteredDateTimeString() %></i>
      </font>
    </td>
  </tr>
  <tr>
    <td bgcolor='#FFFFFF'>
      <%= toHtml(thisReply.getBody()) %>
    </td>
  </tr>
</table>
<%
  }
%>  
<br>
<font color='#000000'>
<%
  if (request.getParameter("popup") != null) {
%>
  [<a href="javascript:window.close();">Close Window</a>]
<%  
  } else {
%>
  [<a href="ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= Project.getId() %>&cid=<%= IssueCategory.getId() %>">Back to Issues</a>]
  [<a href="ProjectManagementIssues.do?command=Reply&pid=<%= Project.getId() %>&iid=<%= Issue.getId() %>&cid=<%= IssueCategory.getId() %>">Add Reply</a>]
<%  
  }
%>
</font>
<br>
