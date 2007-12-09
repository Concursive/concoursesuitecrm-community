<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
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
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Users.do?command=ListUsers"><dhv:label name="admin.viewUsers">View Users</dhv:label></a> >
<a href="Users.do?command=UserDetails&id=<%=UserRecord.getId()%>"><dhv:label name="admin.userDetails">User Details</dhv:label></a> >
<dhv:label name="users.history.long_html">Login History</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="users" selected="history" object="UserRecord" param='<%= "id=" + UserRecord.getId() %>'>
  <dhv:pagedListStatus object="AccessLogInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th><b><dhv:label name="accounts.Username">Username</dhv:label></b></th>
      <th nowrap><b><dhv:label name="admin.loginIpAddress">Login IP Address</dhv:label></b></th>
      <th nowrap><b><dhv:label name="admin.dateTime">Date/Time</dhv:label></b></th>
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
    <tr class="containerBody"><td colspan="3"><dhv:label name="admin.noLoginHistory">No login history found.</dhv:label></td></tr>
<%}%>
  </table>
  <br>
  <dhv:pagedListControl object="AccessLogInfo"/>
</dhv:container>
