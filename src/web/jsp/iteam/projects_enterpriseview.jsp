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
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="projectList" class="com.zeroio.iteam.base.ProjectList" scope="request"/>
<jsp:useBean id="projectEnterpriseInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="ProjectManagement.do?command=EnterpriseView">
    <td align="left" valign="bottom">
      <img src="images/icons/stock_filter-data-by-criteria-16.gif" border="0" align="absmiddle" />
      <select size="1" name="listView" onChange="javascript:document.forms['listView'].submit();">
        <option <%= projectEnterpriseInfo.getOptionValue("open") %>><dhv:label name="project.openProjects">All Open Projects</dhv:label></option>
        <option <%= projectEnterpriseInfo.getOptionValue("closed") %>><dhv:label name="project.closedProjects">All Closed Projects</dhv:label></option>
      </select>
      <select size="1" name="listFilter3" onChange="javascript:document.forms['listView'].submit();">
        <option <%= projectEnterpriseInfo.getFilterOption("listFilter3", "hide") %>><dhv:label name="project.hideNews">Hide News</dhv:label></option>
        <option <%= projectEnterpriseInfo.getFilterOption("listFilter3", "last3") %>><dhv:label name="project.lastThreeArticles">Last 3 Articles</dhv:label></option>
        <option <%= projectEnterpriseInfo.getFilterOption("listFilter3", "last6") %>><dhv:label name="project.lastSixArticles">Last 6 Articles</dhv:label></option>
      </select>
      <select size="1" name="listFilter1" onChange="javascript:document.forms['listView'].submit();">
        <option <%= projectEnterpriseInfo.getFilterOption("listFilter1", "hide") %>><dhv:label name="project.hideAssignments">Hide Assignments</dhv:label></option>
        <option <%= projectEnterpriseInfo.getFilterOption("listFilter1", "my") %>><dhv:label name="project.myAssignments">My Assignments</dhv:label></option>
        <option <%= projectEnterpriseInfo.getFilterOption("listFilter1", "all") %>><dhv:label name="project.allAssignments">All Assignments</dhv:label></option>
      </select>
      <select size="1" name="listFilter2" onChange="javascript:document.forms['listView'].submit();">
        <option <%= projectEnterpriseInfo.getFilterOption("listFilter2", "hide") %>><dhv:label name="project.hideTopics">Hide Topics</dhv:label></option>
        <option <%= projectEnterpriseInfo.getFilterOption("listFilter2", "last3") %>><dhv:label name="project.lastThreeTopics">Last 3 Topics</dhv:label></option>
        <option <%= projectEnterpriseInfo.getFilterOption("listFilter2", "last6") %>><dhv:label name="project.lastSixTopics">Last 6 Topics</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus label="Projects" title='<%= showError(request, "actionError") %>' object="projectEnterpriseInfo"/>
    </td>
    </form>
  </tr>
</table>
<dhv:evaluate if="<%= projectList.hasUserProjects() %>">
<table class="note" cellspacing="0">
<tr>
  <th>
    <img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />
  </th>
  <td>
    <dhv:label name="project.youAreMemberOfProjects.text">You are a member of the following projects.  Choose a project to view additional details.</dhv:label>
  </td>
</tr>
</table>
</dhv:evaluate>
<%-- Show the projects --%>
<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
<%
  ArrayList publicProjects = new ArrayList();
  Iterator i = projectList.iterator();
  String tableHighlight1 = "#000000";
  String tableHighlight2 = "#0000FF";
  String currentHighlight = tableHighlight2;
  while (i.hasNext()) {
    Project thisProject = (Project) i.next();
    if (thisProject.getAllowGuests()) {
      publicProjects.add(thisProject);
    } else {
      if (currentHighlight.equals(tableHighlight1)) {
        currentHighlight = tableHighlight2;
      } else {
        currentHighlight = tableHighlight1;
      }
%>
<%@ include file="projects_enterpriseview_include.jsp" %>
<% 
    }
  }
%>
<%-- Show the public projects --%>
<dhv:evaluate if="<%= publicProjects.size() > 0 %>">
</table>
<br />
<table class="note" cellspacing="0">
  <tr>
    <th nowrap>
      <img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />&nbsp;
    </th>
    <td>
      You have access to the following public projects.  Choose a project to view additional details.
    </td>
  </tr>
</table>
<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
<%
  Iterator j = publicProjects.iterator();
  tableHighlight1 = "#000000";
  tableHighlight2 = "#0000FF";
  currentHighlight = tableHighlight2;
  while (j.hasNext()) {
    Project thisProject = (Project) j.next();
    if (currentHighlight.equals(tableHighlight1)) {
      currentHighlight = tableHighlight2;
    } else {
      currentHighlight = tableHighlight1;
    }
%>
<%@ include file="projects_enterpriseview_include.jsp" %>
<%      
  }
%>
</dhv:evaluate>
<%-- No projects to display --%>
<dhv:evaluate if="<%= projectList.size() == 0 %>">
  <tr>
    <td colspan="5">
      <font color="red">There are currently no projects to display in this view.</font>
    </td>
  </tr>
  <tr>
    <td colspan="5">
      &nbsp;
    </td>
  </tr>
</dhv:evaluate>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0"><tr><td>
<a href="ProjectManagement.do?command=AddProject"><img src="images/buttons/start_project-green.gif" border="0" /></a>
</td></tr></table>
