<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.admin.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="AccessLog" class="org.aspcfs.modules.admin.base.AccessLogList" scope="request"/>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<a href="Users.do?command=ListUsers">View Users</a> >
<a href="Users.do?command=UserDetails&id=<%=UserRecord.getId()%>">User Details</a> >
Login History
</td>
</tr>
</table>
<%-- End Trails --%>
<strong><%= toHtml(UserRecord.getUsername()) %> (<%= toHtml(UserRecord.getContact().getNameLastFirst()) %>)</strong>
<% String param1 = "id=" + UserRecord.getId(); %>      
<dhv:container name="users" selected="history" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <dhv:pagedListStatus object="AccessLogInfo"/>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
          <tr>
            <th><b>Username</b></th>
            <th nowrap><b>Login IP Address</b></th>
            <th nowrap><b>Date/Time</b></th>
          </tr>
<%
	Iterator k = AccessLog.iterator();
	if ( k.hasNext() ) {
		int rowid = 0;
		while (k.hasNext()) {
		  rowid = (rowid != 1?1:2);
      AccessLog thisLog = (AccessLog)k.next();
%>   
	<tr>
		<td valign="top" nowrap class="row<%= rowid %>">
			<%= toHtml(thisLog.getUsername()) %>
		</td>
		<td valign="top" nowrap class="row<%= rowid %>">
			<%= toHtml(thisLog.getIp()) %>
		</td>
		<td nowrap valign="top" class="row<%= rowid %>">
      <dhv:tz timestamp="<%= thisLog.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
		</td>
	</tr>
  <%}%>
<%} else {%>
  <tr class="containerBody"><td colspan="3">No login history found.</td></tr>
<%}%>
  </table>
  <br>
  <dhv:pagedListControl object="AccessLogInfo"/>
</td>
</tr>
</table>
