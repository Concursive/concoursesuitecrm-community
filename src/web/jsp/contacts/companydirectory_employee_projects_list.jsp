<%--
  - Copyright(c) 2005 Concursive Corporation (http://www.concursive.com/) All
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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.troubletickets.base.Ticket,com.zeroio.iteam.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="CompanyDirectoryProjectsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="projectList" class="com.zeroio.iteam.base.ProjectList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="companydirectory_employee_projects_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select_<%= SKIN %>');
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CompanyDirectory.do?command=ListEmployees"><dhv:label name="employees.employees">Employees</dhv:label></a> >
<a href="CompanyDirectory.do?command=ListEmployees"><dhv:label name="employees.view">View Employees</dhv:label></a> >
<dhv:label name="dependency.projects">Projects</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<%
  int colspanDecrement = 0;
  if (isPopup(request)) {
    colspanDecrement = 1;
  }
%>
<dhv:container name="employees" selected="projects" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="CompanyDirectoryProjectsInfo"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <dhv:evaluate if="<%= colspanDecrement != 1 %>">
      <th width="8">&nbsp;</th>
      </dhv:evaluate>
      <th nowrap><a href="<%= CompanyDirectoryProjectsInfo.getLink() %>&column=p.entered<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="documents.details.startDate">Start Date</dhv:label></a><%= CompanyDirectoryProjectsInfo.getSortIcon("p.entered") %></th>
      <th width="100%" nowrap><a href="<%= CompanyDirectoryProjectsInfo.getLink() %>&column=title<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="project.projectName">Project Name</dhv:label></a><%= CompanyDirectoryProjectsInfo.getSortIcon("title") %></th>
      <th nowrap><dhv:label name="project.overallProgress">Overall Progress</dhv:label></th>
      <%--
      <th width="118">Category</th>
      --%>
    </tr>
  <%
    if (projectList.size() == 0) {
  %>
    <tr class="row2">
      <td colspan="<%= 4-colspanDecrement %>"><dhv:label name="project.noProjectsToDisplay">There are currently no projects to display in this view.</dhv:label></td>
    </tr>
  <%
    }
    int rowid = 0;
    int count = 0;
    Iterator i = projectList.iterator();
    while (i.hasNext()) {
      rowid = (rowid != 1?1:2);
      ++count;
      Project thisProject = (Project) i.next();
      RequirementList requirements = thisProject.getRequirements();
      boolean hasAccess = false;
  %>
  <dhv:permission name="projects-view">
    <dhv:evaluate if="<%= thisProject.getHasAccess() %>">
      <% hasAccess = true; %>
    </dhv:evaluate>
  </dhv:permission>
    <tr class="row<%= rowid %>">
      <dhv:evaluate if="<%= colspanDecrement != 1 %>">
      <td valign="top" align="center" nowrap>
        <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>','menuItem','<%= thisProject.getId() %>','<%= hasAccess %>');"
           onMouseOver="over(0, <%= count %>)"
           onmouseout="out(0, <%= count %>); hideMenu('menuItem');"><img
           src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
      </td>
      </dhv:evaluate>
      <td valign="top" align="center" nowrap>
        <zeroio:tz timestamp="<%= thisProject.getRequestDate() %>" dateOnly="true" default="&nbsp;" />
      </td>
      <td valign="top">
        <dhv:evaluate if="<%= thisProject.getHasAccess() && !isPopup(request) %>">
          <a href="ProjectManagement.do?command=ProjectCenter&pid=<%= thisProject.getId() %>"><%= toHtml(thisProject.getTitle()) %></a>
        </dhv:evaluate>
        <dhv:evaluate if="<%= !thisProject.getHasAccess() || isPopup(request) %>">
          <%= toHtml(thisProject.getTitle()) %>
        </dhv:evaluate>
        <%--
        <dhv:evaluate if="<%= thisProject.getAllowGuests() %>">
          <img src="images/public.gif" border="0" alt="" align="absmiddle" />
        </dhv:evaluate>
        --%>
        <dhv:evaluate if="<%= thisProject.getApprovalDate() == null %>">
          <img src="images/unapproved.gif" border="0" alt="" align="absmiddle" />
        </dhv:evaluate>
      </td>
      <td valign="top" align="right" nowrap>
        <table cellpadding="1" cellspacing="1" class="empty"><tr>
          <td><dhv:label name="project.progress.colon">Progress:</dhv:label></td>
          <dhv:evaluate if="<%= requirements.getPlanActivityCount() == 0 %>">
            <td width="<%= requirements.getPercentClosed() %>" bgColor="#CCCCCC" nowrap class="progressCell"></td>
          </dhv:evaluate>
          <dhv:evaluate if="<%= requirements.getPlanActivityCount() > 0 %>">
            <dhv:evaluate if="<%= requirements.getPercentClosed() > 0 %>">
              <td width="<%= requirements.getPercentClosed()  %>" bgColor="green" nowrap class="progressCell"></td>
            </dhv:evaluate>
            <dhv:evaluate if="<%= requirements.getPercentUpcoming() > 0 %>">
              <td width="<%= requirements.getPercentUpcoming() %>" bgColor="#99CC66" nowrap class="progressCell"></td>
            </dhv:evaluate>
            <dhv:evaluate if="<%= requirements.getPercentOverdue() > 0 %>">
              <td width="<%= requirements.getPercentOverdue() %>" bgColor="red" nowrap class="progressCell"></td>
            </dhv:evaluate>
          </dhv:evaluate>
        </tr></table>
        <dhv:evaluate if="<%= requirements.getPlanActivityCount() == 0 %>">
          <dhv:label name="project.zeroActivities">(0 activities)</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= requirements.getPlanActivityCount() > 0 %>">
          <% if (requirements.getPlanActivityCount() == 1) { %>
          <dhv:label name="project.oneOfOneActivityComplete.text" param='<%= "closedCount="+requirements.getPlanClosedCount()+"|activityCount="+requirements.getPlanActivityCount() %>'>(<%= requirements.getPlanClosedCount() %> of <%= requirements.getPlanActivityCount() %> activity is complete)</dhv:label>
          <%} else {%>
            <% if (requirements.getPlanClosedCount() == 1) { %>
          <dhv:label name="project.oneOfSeveralActivitiesComplete.text" param='<%= "closedCount="+requirements.getPlanClosedCount()+"|activityCount="+requirements.getPlanActivityCount() %>'>(<%= requirements.getPlanClosedCount() %> of <%= requirements.getPlanActivityCount() %> activities is complete)</dhv:label>
            <%} else {%>
          <dhv:label name="project.numberofSeveralActivitiesComplete.text" param='<%= "closedCount="+requirements.getPlanClosedCount()+"|activityCount="+requirements.getPlanActivityCount() %>'>(<%= requirements.getPlanClosedCount() %> of <%= requirements.getPlanActivityCount() %> activities are complete)</dhv:label>
            <%}%>
          <%}%>
        </dhv:evaluate>
      </td>
    </tr>
  <% } %>
  </table><br />
</dhv:container>

