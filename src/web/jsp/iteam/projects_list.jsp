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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="projectList" class="com.zeroio.iteam.base.ProjectList" scope="request"/>
<jsp:useBean id="projectListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="projectCategoryList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_list_menu.jsp" %>
<%-- Preload image rollovers --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<%-- Temp. fix for Weblogic --%>
<%
String actionError = showError(request, "actionError");
%>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ProjectManagement.do"><dhv:label name="Projects" mainMenuItem="true">Projects</dhv:label></a> >
<dhv:label name="project.list">List</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="projects-projects-add">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<a href="ProjectManagement.do?command=AddProject"><dhv:label name="project.startAProject">Start a Project</dhv:label></a>
</td></tr></table>
<br />
</dhv:permission>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="ProjectManagement.do?command=ProjectList">
    <td align="left" valign="bottom">
      <img src="images/icons/stock_filter-data-by-criteria-16.gif" border="0" align="absmiddle" />
      <select size="1" name="listView" onChange="javascript:document.forms['listView'].submit();">
        <option <%= projectListInfo.getOptionValue("open") %>><dhv:label name="project.allOpenProjects">All Open Projects</dhv:label></option>
        <option <%= projectListInfo.getOptionValue("closed") %>><dhv:label name="project.allClosedProjects">All Closed Projects</dhv:label></option>
        <option <%= projectListInfo.getOptionValue("recent") %>><dhv:label name="project.recentlyAccessedProjects">Recently Accessed Projects</dhv:label></option>
      </select>
      <dhv:evaluate if="<%= projectCategoryList.size() > 1 %>">
        <% projectCategoryList.setJsEvent("onChange=\"javascript:document.forms['listView'].submit();\""); %>
        <%= projectCategoryList.getHtml("listFilter1", projectListInfo.getFilterValue("listFilter1")) %>
      </dhv:evaluate>
      <%--
      <select size="1" name="listFilter1" onChange="javascript:document.forms['listView'].submit();">
        <option <%= projectListInfo.getFilterOption("listFilter1", "all") %>>All Categories</option>
      </select>
      --%>
    </td>
    <td>
      <dhv:pagedListStatus label="Projects" title="<%= actionError %>" object="projectListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8">&nbsp;</th>
    <th nowrap><a href="<%= projectListInfo.getLink() %>&column=p.entered"><dhv:label name="documents.details.startDate">Start Date</dhv:label></a><%= projectListInfo.getSortIcon("p.entered") %></th>
    <th width="100%" nowrap><a href="<%= projectListInfo.getLink() %>&column=title"><dhv:label name="project.projectTitle">Project Title</dhv:label></a><%= projectListInfo.getSortIcon("title") %></th>
      <th nowrap><dhv:label name="project.overallProgress">Overall Progress</dhv:label></th>
    <%--
    <th width="118">Category</th>
    --%>
  </tr>
<%
  if (projectList.size() == 0) {
%>
  <tr class="row2">
    <td colspan="4"><dhv:label name="project.noProjectsToDisplay">There are currently no projects to display in this view.</dhv:label></td>
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
%>
  <tr class="row<%= rowid %>">
    <td valign="top" align="center" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>', 'menuItem', <%= thisProject.getId() %>, 0<dhv:evaluate if="<%= thisProject.getShowNews() %>">1</dhv:evaluate>, 0<dhv:evaluate if="<%= thisProject.getShowDiscussion() %>">1</dhv:evaluate>, 0<dhv:evaluate if="<%= thisProject.getShowDocuments() %>">1</dhv:evaluate>, 0<dhv:evaluate if="<%= thisProject.getShowLists() %>">1</dhv:evaluate>, 0<dhv:evaluate if="<%= thisProject.getShowPlan() %>">1</dhv:evaluate>, 0<dhv:evaluate if="<%= thisProject.getShowTickets() %>">1</dhv:evaluate>, 0<dhv:evaluate if="<%= thisProject.getShowTeam() %>">1</dhv:evaluate>, 0<dhv:evaluate if="<%= thisProject.getShowDetails() %>">1</dhv:evaluate>, 0);"
         onMouseOver="over(0, <%= count %>)"
         onmouseout="out(0, <%= count %>); hideMenu('menuItem');"><img
         src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= thisProject.getRequestDate() %>" dateOnly="true" default="&nbsp;" />
    </td>
    <td valign="top">
      <a href="ProjectManagement.do?command=ProjectCenter&pid=<%= thisProject.getId() %>"><%= toHtml(thisProject.getTitle()) %></a>
      <%--
      <dhv:evaluate if="<%= thisProject.getAllowGuests() %>">
        <img src="images/public.gif" border="0" alt="" align="absmiddle" />
      </dhv:evaluate>
      --%>
      <dhv:evaluate if="<%= thisProject.getApprovalDate() == null %>">
        <img src="images/unapproved.gif" border="0" alt="" align="absmiddle" />
      </dhv:evaluate>
      <br />
      <dhv:evaluate if="<%= thisProject.getCategoryId() > -1 %>">
        <i><%= toHtml(projectCategoryList.getValueFromId(thisProject.getCategoryId())) %></i>
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
          <dhv:label name="projects.oneOfOneActivityComplete.text" param="<%= "closedCount="+requirements.getPlanClosedCount()+"activityCount="+requirements.getPlanActivityCount() %>">(<%= requirements.getPlanClosedCount() %> of <%= requirements.getPlanActivityCount() %>activity is complete)</dhv:label>
          <%} else {%>
            <% if (requirements.getPlanClosedCount() == 1) { %>
          <dhv:label name="projects.oneOfSeveralActivitiesComplete.text" param="<%= "closedCount="+requirements.getPlanClosedCount()+"activityCount="+requirements.getPlanActivityCount() %>">(<%= requirements.getPlanClosedCount() %> of <%= requirements.getPlanActivityCount() %>activities is complete)</dhv:label>
            <%} else {%>
          <dhv:label name="projects.numberofSeveralActivitiesComplete.text" param="<%= "closedCount="+requirements.getPlanClosedCount()+"activityCount="+requirements.getPlanActivityCount() %>">(<%= requirements.getPlanClosedCount() %> of <%= requirements.getPlanActivityCount() %>activities are complete)</dhv:label>
          <%}%>
          <%}%>
        </dhv:evaluate>
    </td>
  </tr>
<%
  }
%>
</table>
