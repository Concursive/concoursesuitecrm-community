<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
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
      <a href="ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>"><dhv:label name="documents.team.long_html">Team</dhv:label></a> >
      <a href="ProjectManagementTeam.do?command=Modify&pid=<%= Project.getId() %>"><dhv:label name="button.modify">Modify</dhv:label></a> >
      <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
    </td>
  </tr>
</table>
<br>
<dhv:label name="project.invitationResults">Invitation results</dhv:label>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <dhv:label name="contacts.name">Name</dhv:label>
    </th>
    <th>
      <dhv:label name="documents.team.emailAddress">Email Address</dhv:label>
    </th>
    <th>
      <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
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
      <dhv:evaluate if="<%= thisInvitation.getSentMail() %>"><font color="green"><dhv:label name="project.invitationSent">Invitation Sent</dhv:label></font></dhv:evaluate>
      <dhv:evaluate if="<%= !thisInvitation.getSentMail() %>"><font color="red"><dhv:label name="project.emailError">Email Error</dhv:label></font></dhv:evaluate>
    </td>
  </tr>
<% } %>
</table>
<br>
<input type="button" value="<dhv:label name="button.ok">OK</dhv:label>" onclick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>'"/>
</form>
