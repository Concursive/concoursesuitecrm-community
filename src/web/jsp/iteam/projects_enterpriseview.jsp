<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ProjectList" class="com.zeroio.iteam.base.ProjectList" scope="request"/>
<jsp:useBean id="projectListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<table class="note" cellspacing="0">
<tr>
  <th>
    <img src="images/icons/stock_form-open-in-design-mode-16.gif" border="0" align="absmiddle" />
  </th>
  <td>
    You are a member of the following projects.  Choose a project to view additional details.
  </td>
</tr>
</table>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="ProjectManagement.do?command=EnterpriseView">
    <td align="left" valign="bottom">
      <img src="images/icons/stock_filter-data-by-criteria-16.gif" border="0" align="absmiddle" />
      <select size="1" name="listView" onChange="javascript:document.forms['listView'].submit();">
        <option <%= projectListInfo.getOptionValue("open") %>>Open Projects</option>
        <option <%= projectListInfo.getOptionValue("closed") %>>Closed Projects</option>
      </select>
      <select size="1" name="listFilter3" onChange="javascript:document.forms['listView'].submit();">
        <option <%= projectListInfo.getFilterOption("listFilter3", "hide") %>>Hide News</option>
        <option <%= projectListInfo.getFilterOption("listFilter3", "last3") %>>Last 3 Articles</option>
        <option <%= projectListInfo.getFilterOption("listFilter3", "last6") %>>Last 6 Articles</option>
      </select>
      <select size="1" name="listFilter1" onChange="javascript:document.forms['listView'].submit();">
        <option <%= projectListInfo.getFilterOption("listFilter1", "hide") %>>Hide Assignments</option>
        <option <%= projectListInfo.getFilterOption("listFilter1", "my") %>>My Assignments</option>
        <option <%= projectListInfo.getFilterOption("listFilter1", "all") %>>All Assignments</option>
      </select>
      <select size="1" name="listFilter2" onChange="javascript:document.forms['listView'].submit();">
        <option <%= projectListInfo.getFilterOption("listFilter2", "hide") %>>Hide Topics</option>
        <option <%= projectListInfo.getFilterOption("listFilter2", "last3") %>>Last 3 Topics</option>
        <option <%= projectListInfo.getFilterOption("listFilter2", "last6") %>>Last 6 Topics</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus label="Projects" title="<%= showError(request, "actionError") %>" object="projectListInfo"/>
    </td>
    </form>
  </tr>
</table>
<%-- Show the projects --%>
<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
<%
  Iterator i = ProjectList.iterator();
  if (i.hasNext()) {
    String tableHighlight1 = "#000000";
    String tableHighlight2 = "#0000FF";
    String currentHighlight = tableHighlight2;
    while (i.hasNext()) {
      if (currentHighlight.equals(tableHighlight1)) {
        currentHighlight = tableHighlight2;
      } else {
        currentHighlight = tableHighlight1;
      }
      Project thisProject = (Project) i.next();
%>
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">
        &nbsp;
      </td>
      <td width="743" valign="top" colspan="4" class="title">
        &nbsp;<img src="images/icons/stock_macro-objects-16.gif" border="0" align="absmiddle" />
        <a href="ProjectManagement.do?command=ProjectCenter&pid=<%= thisProject.getId() %>"><b><%= toHtml(thisProject.getTitle()) %>:</b></a>
        <%= toHtml(thisProject.getShortDescription()) %>
      </td>
    </tr>
<%-- Show news articles --%>
  <dhv:evaluate if="<%= !"hide".equals(projectListInfo.getFilterValue("listFilter3")) %>">
    <tr bgcolor="#EFF0EA">
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="2"><b>&nbsp;News</b></td>
      <td width="88" align="left"><b>Posted</b></td>
      <td width="109"><b>From</b></td>
    </tr>
    <dhv:evaluate if="<%= thisProject.getNews().isEmpty() %>">
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="4">
        &nbsp;&nbsp;&nbsp;No News found.
      </td>
    </tr>
    </dhv:evaluate>
  </dhv:evaluate>
<%
      if (thisProject.getNews().size() > 0) {
        Iterator newsList = thisProject.getNews().iterator();
        while (newsList.hasNext()) {
          NewsArticle thisArticle = (NewsArticle) newsList.next();
%>      
    <tr>
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="430" colspan="2">
        &nbsp;&nbsp;&nbsp;<img src="images/icons/stock_announcement-16.gif" border="0" align="absmiddle" />&nbsp;
        <%= toHtml(thisArticle.getSubject()) %>
        <%--<a href="javascript:popURL('ProjectManagementIssues.do?command=Details&pid=<%= thisProject.getId() %>&iid=<%= thisIssue.getId() %>&popup=true','CFS_Issue','600','300','yes','yes');" style="text-decoration:none;color:black;" onMouseOver="this.style.color='blue';window.status='Review this issue';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisIssue.getSubject()) %></a>--%>
      </td>
      <td width="88" align="left">&nbsp;<zeroio:tz timestamp="<%= thisArticle.getStartDate() %>" dateOnly="true" default="&nbsp;"/></td>
      <td width="109">&nbsp;<dhv:username id="<%= thisArticle.getEnteredBy() %>"/></td>
    </tr>
<%
        }
      }
%>
<%-- Show the assignments --%>
  <dhv:evaluate if="<%= !"hide".equals(projectListInfo.getFilterValue("listFilter1")) %>">
    <tr bgcolor="#EFF0EA">
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="430"><b>&nbsp;Assignments</b></td>
      <td width="116" align="left"><b>Status</b></td>
      <td width="88"><b>Due Date</b></td>
      <td width="109" nowrap><b>Assigned To</b></td>
    </tr>
    <dhv:evaluate if="<%= thisProject.getAssignments().isEmpty() %>">
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="4">
        &nbsp;&nbsp;&nbsp;No Assignments found.
      </td>
    </tr>
    </dhv:evaluate>
  </dhv:evaluate>
<%
      if (thisProject.getAssignments().size() > 0) {
        Iterator assignmentList = thisProject.getAssignments().iterator();
        while (assignmentList.hasNext()) {
          Assignment thisAssignment = (Assignment) assignmentList.next();
%>
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="430">
        &nbsp;&nbsp;&nbsp;<%= thisAssignment.getStatusGraphicTag() %>&nbsp;
        <a href="javascript:popURL('ProjectManagementAssignments.do?command=Modify&pid=<%= thisProject.getId() %>&aid=<%= thisAssignment.getId() %>&popup=true&return=ProjectEnterpriseView','Assignment','650','375','yes','yes');" style="text-decoration:none;color:black;" onMouseOver="this.style.color='blue';window.status='Update this assignment';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisAssignment.getRole()) %></a>
      </td>
      <td width="116" align="left">&nbsp;<%= toHtml(thisAssignment.getStatus()) %></td>
      <td width="88">&nbsp;<%= thisAssignment.getRelativeDueDateString(User.getTimeZone(), User.getLocale()) %></td>
      <td width="109" nowrap>&nbsp;<dhv:username id="<%= thisAssignment.getUserAssignedId() %>"/></td>
    </tr>
<%
        }
      }
%>
<%-- Show discussion topics --%>
  <dhv:evaluate if="<%= !"hide".equals(projectListInfo.getFilterValue("listFilter2")) %>">
    <tr bgcolor="#EFF0EA">
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="2"><b>&nbsp;Topics</b></td>
      <td width="88" align="left"><b>Posted</b></td>
      <td width="109"><b>From</b></td>
    </tr>
    <dhv:evaluate if="<%= thisProject.getIssues().isEmpty() %>">
    <tr>
      <td width="2" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td colspan="4">
        &nbsp;&nbsp;&nbsp;No Topics found.
      </td>
    </tr>
    </dhv:evaluate>
  </dhv:evaluate>
<%
      if (thisProject.getIssues().size() > 0) {
        Iterator issueList = thisProject.getIssues().iterator();
        while (issueList.hasNext()) {
          Issue thisIssue = (Issue)issueList.next();
%>      
    <tr>
      <td width="2" align="center" valign="top" bgcolor="<%= currentHighlight %>">&nbsp;</td>
      <td width="430" colspan="2">
        &nbsp;&nbsp;&nbsp;<img src="images/icons/stock_draw-callouts-16.gif" border="0" align="absmiddle" />&nbsp;
        <%= toHtml(thisIssue.getSubject()) %>
        <%--<a href="javascript:popURL('ProjectManagementIssues.do?command=Details&pid=<%= thisProject.getId() %>&iid=<%= thisIssue.getId() %>&popup=true','CFS_Issue','600','300','yes','yes');" style="text-decoration:none;color:black;" onMouseOver="this.style.color='blue';window.status='Review this issue';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisIssue.getSubject()) %></a>--%>
      </td>
      <td width="88" align="left">&nbsp;<zeroio:tz timestamp="<%= thisIssue.getReplyDate() %>" dateOnly="true" default="&nbsp;"/></td>
      <td width="109">&nbsp;<dhv:username id="<%= thisIssue.getModifiedBy() %>" /></td>
    </tr>
<%
        }
      }
      if (i.hasNext()) {
%>      
  <dhv:evaluate if="<%= !"hide".equals(projectListInfo.getFilterValue("listFilter3")) || !"hide".equals(projectListInfo.getFilterValue("listFilter2")) || !"hide".equals(projectListInfo.getFilterValue("listFilter1"))%>">
  <tr>
    <td colspan="5">
      &nbsp;
    </td>
  </tr>
  </dhv:evaluate>
<%      
      }
    }
  } else {
%>
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
<%  
  }
%>
</table>
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0"><tr><td>
<a href="ProjectManagement.do?command=AddProject"><img src="images/buttons/start_project-green.gif" border="0" /></a>
</td></tr></table>
