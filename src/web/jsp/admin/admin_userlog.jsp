<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.admin.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="AccessLog" class="org.aspcfs.modules.admin.base.AccessLogList" scope="request"/>
<a href="Admin.do">Setup</a> >
<a href="Users.do?command=ListUsers">View Users</a> >
<a href="Users.do?command=UserDetails&id=<%=UserRecord.getId()%>">User Details</a> >
Login History<br>
<hr color="#BFBFBB" noshade>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(UserRecord.getUsername()) %> (<%= toHtml(UserRecord.getContact().getNameLastFirst()) %>)</strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + UserRecord.getId(); %>      
      <dhv:container name="users" selected="history" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
    
<dhv:pagedListStatus object="AccessLogInfo"/>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td><b>Username</b></td>
    <td><b>Login IP Address</b></td>
    <td><b>Date/Time</b></td>
  </tr>
  
  <%
	Iterator k = AccessLog.iterator();
	
	if ( k.hasNext() ) {
		int rowid = 0;
		while (k.hasNext()) {
		if (rowid != 1) {
			rowid = 1;
		} else {
			rowid = 2;
		}
	
		AccessLog thisLog = (AccessLog)k.next();
%>   
	<tr>
		<td valign="top" nowrap class="row<%= rowid %>">
			<%=toHtml(thisLog.getUsername())%>
		</td>
		<td valign="top" nowrap class="row<%= rowid %>">
			<%=toHtml(thisLog.getIp())%>
		</td>
		<td nowrap valign="top" class="row<%= rowid %>">
    <%=toHtml(thisLog.getEnteredString())%>
		</td>
	</tr> 
  
  <%}%>
  
<%} else {%>
  <tr bgcolor="white"><td colspan=3 valign=center>No login history found.</td></tr>
<%}%>
  
  </table>
  <br>
  <dhv:pagedListControl object="AccessLogInfo"/>
</td>
</tr>
</table>  
