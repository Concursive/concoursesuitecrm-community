<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.admin.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="AccessLog" class="org.aspcfs.modules.admin.base.AccessLogList" scope="request"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
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
      <zeroio:tz timestamp="<%= thisLog.getEntered() %>" />
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
