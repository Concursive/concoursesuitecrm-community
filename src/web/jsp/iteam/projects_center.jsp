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
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="currentMember" class="com.zeroio.iteam.base.TeamMember" scope="request"/>
<jsp:useBean id="projectView" class="java.lang.String" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ProjectManagement.do"><dhv:label name="Projects" mainMenuItem="true">Projects</dhv:label></a> >
<a href="ProjectManagement.do?command=ProjectList"><dhv:label name="project.list">List</dhv:label></a> >
<dhv:label name="projects.projectCenter">Project Center</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%
  if (Project.getId() == -1) {
%>
<font color="red"><dhv:label name="project.doesNotBelongToYou.text">This project does not belong to you, or does not exist!</dhv:label>
<%
  } else {
    String section = (String) request.getAttribute("IncludeSection");
    String includeSection = "projects_center_" + section + ".jsp";
%>
<table border="0" width="100%">
  <tr>
    <td>
      <img src="images/icons/stock_navigator-open-toolbar-16.gif" border="0" align="absmiddle" />
    </td>
    <td width="100%">
      <strong><%= toHtml(Project.getTitle()) %></strong>
      <dhv:evaluate if="<%= Project.getPortal() %>">
        <img src="images/portal.gif" border="0" alt="" align="absmiddle" />
      </dhv:evaluate>
      <dhv:evaluate if="<%= Project.getAllowGuests() %>">
        <img src="images/public.gif" border="0" alt="" align="absmiddle" />
      </dhv:evaluate>
      <dhv:evaluate if="<%= Project.getApprovalDate() == null %>">
        <img src="images/unapproved.gif" border="0" alt="" align="absmiddle" />
      </dhv:evaluate>
    </td>
    <td align="right" nowrap>
      (<dhv:label name="project.role.colon">Role:</dhv:label> <zeroio:role id="<%= currentMember.getUserLevel() %>"/>)
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2">
      <%= toHtml(Project.getShortDescription()) %>
    </td>
  </tr>
</table>
<div class="tabs" id="toptabs">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <zeroio:permission name="project-news-view">
      <dhv:evaluate if="<%= Project.getShowNews() %>">
        <zeroio:tabbedMenu text='<%= Project.getLabel("News") %>' display="News" type="project.news" key="home,news" value="<%= section %>" url='<%= "ProjectManagement.do?command=ProjectCenter&section=News&pid=" + Project.getId() %>'/>
      </dhv:evaluate>
    </zeroio:permission>
<%--
    <dhv:evaluate if="<%= Project.getShowCalendar() %>">
      <zeroio:tabbedMenu text="<%= calendarLabel %>" key="calendar" value="<%= section %>" url="<%= calendarUrl %>" />
    </dhv:evaluate>
--%>
    <zeroio:permission name="project-discussion-forums-view">
      <dhv:evaluate if="<%= Project.getShowDiscussion() %>">
        <zeroio:tabbedMenu text='<%= Project.getLabel("Discussion") %>' display="Discussion" type="project.discussion" key="issues" value="<%= section %>" url='<%= "ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=" + Project.getId() %>'/>
      </dhv:evaluate>
    </zeroio:permission>
    <zeroio:permission name="project-documents-view">
      <dhv:evaluate if="<%= Project.getShowDocuments() %>">
        <zeroio:tabbedMenu text='<%= Project.getLabel("Documents") %>' display="Documents" type="project.documents" key="file" value="<%= section %>" url='<%= "ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=" + Project.getId() + "&folderId=-1" %>'/>
      </dhv:evaluate>
    </zeroio:permission>
    <zeroio:permission name="project-lists-view">
      <dhv:evaluate if="<%= Project.getShowLists() %>">
        <zeroio:tabbedMenu text='<%= Project.getLabel("Lists") %>' display="Lists" type="project.lists" key="lists" value="<%= section %>" url='<%= "ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=" + Project.getId() %>'/>
      </dhv:evaluate>
    </zeroio:permission>
    <zeroio:permission name="project-plan-view">
      <dhv:evaluate if="<%= Project.getShowPlan() %>">
        <zeroio:tabbedMenu text='<%= Project.getLabel("Plan") %>' display="Plan" type="project.plan" key="requirements,assignments" value="<%= section %>" url='<%= "ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=" + Project.getId() %>'/>
      </dhv:evaluate>
    </zeroio:permission>
    <zeroio:permission name="project-tickets-view">
      <dhv:evaluate if="<%= Project.getShowTickets() %>">
        <zeroio:tabbedMenu text='<%= Project.getLabel("Tickets") %>' display="Tickets" type="project.tickets" key="tickets" value="<%= section %>" url='<%= "ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=" + Project.getId() %>'/>
      </dhv:evaluate>
    </zeroio:permission>
    <zeroio:permission name="project-accounts-view">
      <dhv:evaluate if="<%= Project.getShowAccounts() %>">
        <zeroio:tabbedMenu text='<%= Project.getLabel("Accounts") %>' display="Accounts" type="project.accounts" key="accounts" value="<%= section %>" url='<%= "ProjectManagementAccounts.do?command=List&pid=" + Project.getId() %>'/>
      </dhv:evaluate>
    </zeroio:permission>
    <zeroio:permission name="project-team-view">
      <dhv:evaluate if="<%= Project.getShowTeam() %>">
        <zeroio:tabbedMenu text='<%= Project.getLabel("Team") %>' display="Team" type="project.team" key="team" value="<%= section %>" url='<%= "ProjectManagement.do?command=ProjectCenter&section=Team&pid=" + Project.getId() %>'/>
      </dhv:evaluate>
    </zeroio:permission>
    <zeroio:permission name="project-details-view">
      <dhv:evaluate if="<%= Project.getShowDetails() %>">
        <zeroio:tabbedMenu text='<%= Project.getLabel("Details") %>' display="Details" type="project.details" key="details,modifyproject" value="<%= section %>" url='<%= "ProjectManagement.do?command=ProjectCenter&section=Details&pid=" + Project.getId() %>'/>
      </dhv:evaluate>
    </zeroio:permission>
    <dhv:evaluate if="<%= !Project.isTrashed() %>" >
      <dhv:evaluate if="<%= currentMember.getRoleId() <= TeamMember.PROJECT_LEAD %>">
        <zeroio:tabbedMenu text="Setup" display="Setup" type="project.setup" key="setup" value="<%= section %>" url='<%= "ProjectManagement.do?command=ProjectCenter&section=Setup&pid=" + Project.getId() %>'/>
      </dhv:evaluate>
    </dhv:evaluate>
    <td width="100%" style="background-image: none; background-color: transparent; border: 0px; border-bottom: 1px solid #666; cursor: default;">&nbsp;</td>
  </tr>
</table>
</div>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <jsp:include page="<%= includeSection %>" flush="true"/>
      <br>
    </td>
  </tr>
</table>
<%
  }
%>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
