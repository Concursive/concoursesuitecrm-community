<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.webutils.LookupElement,org.aspcfs.modules.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="category" class="org.aspcfs.webutils.LookupElement" scope="request"/>
<jsp:useBean id="outlineList" class="org.aspcfs.modules.TaskList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border='0' width='100%'  bgcolor='#000000' cellspacing='0' cellpadding='0'>
  <tr>
    <td width='100%' bgcolor='#39686e'>
      <font color='#FFFFFF'>&nbsp;<b>Lists: <%= toHtml(category.getDescription()) %></b></font>
    </td>
  </tr>
</table>
   
<table border='0' width='100%' cellpadding='0' cellspacing='0'>
  <tr bgcolor="#808080">
    <td width="5" nowrap>&nbsp;</td>
    <td nowrap><font color='#FFFFFF'>&lt;Action&gt;</font></td>
    <td nowrap><font color='#FFFFFF'>&lt;Pri&gt;</font></td>
    <td width="100%"><font color='#FFFFFF'>&lt;Item&gt;</font></td>
    <td nowrap><font color='#FFFFFF'>&lt;Posted By&gt;</font></td>
    <td nowrap><font color='#FFFFFF'>&lt;Date&gt;</font></td>
  </tr>
<%    
  String bgColorVar = " bgColor=\"#E4E4E4\"";
  Iterator i = outlineList.iterator();
  while (i.hasNext()) {
    Task thisTask = (Task)i.next();
%>    
  <tr<%= bgColorVar %>>
    <td width="5" valign="top" nowrap>&nbsp;</td>
    <td valign="top" align="left" nowrap><a href="ProjectManagementLists.do?command=Add&pid=<%= Project.getId() %>&cid=<%= category.getId() %>&id=<%= thisTask.getId() %>">Edit</a>|Del</td>
    <td valign="top" align="center" nowrap><%= thisTask.getPriority() %></td>
    <td width="100%" valign="top" align="left"<%= thisTask.getComplete()?" class=\"strike\"":"" %>>
      <%= toHtml(thisTask.getDescription()) %>
    </td>
    <%-- <td width="100%" valign="top" align="left"><a href="ProjectManagementIssues.do?command=Details&pid=<%= Project.getId() %>&tid=<%= thisTask.getId() %>&cid=<%= category.getId() %>"><%= toHtml(thisTask.getDescription()) %></a></td> --%>
    <td valign="top" align="left" nowrap>&nbsp; <dhv:username id="<%= thisTask.getEnteredBy() %>"/> &nbsp;</td>
    <td valign="top" align="left" nowrap><%= thisTask.getEnteredString() %></td>
  </tr>
<%    
    if (bgColorVar.equals(" bgColor=\"#E4E4E4\"")) {
      bgColorVar = "";
    } else {
      bgColorVar = " bgColor=\"#E4E4E4\"";
    }
  }
%>
</table>
&nbsp;<br>
<hr color='#000000' width='100%' noshade size='1'>
<br>
[<a href="ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=<%= Project.getId() %>">Back to Categories</a>]
[<a href="ProjectManagementLists.do?command=Add&pid=<%= Project.getId() %>&cid=<%= category.getId() %>">Add an Item to this Category</a>]
