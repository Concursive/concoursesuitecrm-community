<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="invitationList" class="com.zeroio.iteam.base.InvitationList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_new-bcard-16.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>">Team</a> >
      <a href="ProjectManagementTeam.do?command=Modify&pid=<%= Project.getId() %>">Modify</a> >
      Status
    </td>
  </tr>
</table>
<br>
Invitation results
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      Name
    </th>
    <th>
      Email Address
    </th>
    <th>
      Status
    </th>
  </tr>
<%
  int rowId = 0;
  int count = 0;
  Iterator i = invitationList.iterator();
  while (i.hasNext()) {
    rowId = (rowId != 1?1:2);
    ++count;
    Invitation thisInvitation = (Invitation) i.next();
%>
  <tr class="row<%= rowId %>">
    <td>
      <%= toHtml(thisInvitation.getFirstName()) %>
      <%= toHtml(thisInvitation.getLastName()) %>
    </td>
    <td nowrap>
      <%= toHtml(thisInvitation.getEmail()) %>
    </td>
    <td nowrap>
      <dhv:evaluate if="<%= thisInvitation.getSentMail() %>"><font color="green">Invitation Sent</font></dhv:evaluate>
      <dhv:evaluate if="<%= !thisInvitation.getSentMail() %>"><font color="red">Email Error</font></dhv:evaluate>
    </td>
  </tr>
<% } %>
</table>
<br>
<input type="button" value="OK" onclick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>'"/>
</form>
