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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="invitedProjectList" class="com.zeroio.iteam.base.ProjectList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<dhv:evaluate if="<%= invitedProjectList.size() > 0 %>">
<table class="note" cellspacing="0">
<tr>
  <th>
    <img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />
  </th>
  <td>
    You have been invited to the following projects and have been requested to
    either <b>ACCEPT</b> or <b>REJECT</b> each project.<br />
    Accepting will simply add the project to your list and allow you to access it.
  </td>
</tr>
</table>
<table border="0" width="100%" cellspacing="0" cellpadding="4">
<%
  Iterator vi = invitedProjectList.iterator();
  String viHighlight1 = "#000000";
  String viHighlight2 = "#0000FF";
  String viHighlight = viHighlight2;
  while (vi.hasNext()) {
    if (viHighlight.equals(viHighlight1)) {
      viHighlight = viHighlight2;
    } else {
      viHighlight = viHighlight1;
    }
    Project thisProject = (Project) vi.next();
%>
<tr class="newline">
  <td nowrap valign="top">
    <a href="ProjectManagement.do?command=AcceptProject&pid=<%= thisProject.getId() %>"><img src="images/buttons/accept.gif" border="0" align="absmiddle" /></a>
    <a href="ProjectManagement.do?command=RejectProject&pid=<%= thisProject.getId() %>"><img src="images/buttons/reject.gif" border="0" align="absmiddle" /></a>
  </td>
  <td valign="top" width="100%">
    <b><%= toHtml(thisProject.getTitle()) %></b>,
    created by <dhv:username id="<%= thisProject.getEnteredBy() %>"/>
    on <zeroio:tz timestamp="<%= thisProject.getEntered() %>" default="&nbsp;"/><br />
    <%= toHtml(thisProject.getShortDescription()) %>
  </td>
</tr>
<%
  }
%>
</table>
</dhv:evaluate>
<dhv:evaluate if="<%= invitedProjectList.size() == 0 %>">
<table class="note" cellspacing="0">
<tr>
  <th>
    <img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />
  </th>
  <td>
    Invitations to join a project are listed here.
  </td>
</tr>
</table>
There are currently no projects awaiting your reply.<br />
You can view the projects that you belong to by navigating to the 
<a href="ProjectManagement.do?command=EnterpriseView">Project List</a>.
</dhv:evaluate>
