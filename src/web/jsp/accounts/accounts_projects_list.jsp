<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
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
<jsp:useBean id="AccountProjectInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="projectList" class="com.zeroio.iteam.base.ProjectList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_projects_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<dhv:evaluate if="<%= !isPopup(request) %>">
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select_<%= SKIN %>');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<dhv:label name="dependency.projects">Projects</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="projects" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountProjectInfo"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
<dhv:evaluate if="<%= !isPopup(request) %>">
      <th width="8">&nbsp;</th>
</dhv:evaluate>
      <th nowrap><a href="<%= AccountProjectInfo.getLink() %>&column=p.entered<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="project.startDate">Start Date</dhv:label></a><%= AccountProjectInfo.getSortIcon("p.entered") %></th>
      <th width="100%" nowrap><a href="<%= AccountProjectInfo.getLink() %>&column=title<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="project.projectTitle">Project Title</dhv:label></a><%= AccountProjectInfo.getSortIcon("title") %></th>
      <th nowrap><dhv:label name="project.overallProgress">Overall Progress</dhv:label></th>
      <%--
      <th width="118">Category</th>
      --%>
    </tr>
  <%
    if (projectList.size() == 0) {
  %>
    <tr class="row2">
      <td colspan="<%= !isPopup(request)?"3":"4" %>"><dhv:label name="project.noProjectsToDisplay">No projects to display.</dhv:label></td>
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
<dhv:evaluate if="<%= !isPopup(request) %>">
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
        <dhv:evaluate if="<%= thisProject.getHasAccess() %>">
          <a href="ProjectManagement.do?command=ProjectCenter&pid=<%= thisProject.getId() %>"><%= toHtml(thisProject.getTitle()) %></a>
        </dhv:evaluate>
        <dhv:evaluate if="<%= !thisProject.getHasAccess() %>">
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
        <table cellpadding="1" cellspacing="1" class="empty">
          <td>Progress:</td>
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
        </table>
        <dhv:evaluate if="<%= requirements.getPlanActivityCount() == 0 %>">
          (0 activities)
        </dhv:evaluate>
        <dhv:evaluate if="<%= requirements.getPlanActivityCount() > 0 %>">
          (<%= requirements.getPlanClosedCount() %> of <%= requirements.getPlanActivityCount() %>
          activit<%= (requirements.getPlanActivityCount() == 1?"y":"ies") %>
          <%= (requirements.getPlanClosedCount() == 1?"is":"are") %> complete)
        </dhv:evaluate>
      </td>
    </tr>
  <%
    }
  %>
  </table>
</dhv:container>
