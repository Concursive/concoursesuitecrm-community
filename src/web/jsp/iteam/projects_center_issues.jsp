<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="IssueCategory" class="com.zeroio.iteam.base.IssueCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_center_issues_menu.jsp" %>
<%-- Preload image rollovers --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_data-explorer-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Issues_Categories&pid=<%= Project.getId() %>">Forums</a> >
      <img border="0" src="images/icons/stock_draw-callouts2-16.gif" align="absmiddle">
      <%= toHtml(IssueCategory.getSubject()) %>
    </td>
  </tr>
</table>
<br>
<zeroio:permission name="project-discussion-topics-add">
<img border="0" src="images/icons/stock_new-callouts-16.gif" align="absmiddle">
<a href="ProjectManagementIssues.do?command=Add&pid=<%= Project.getId() %>&cid=<%= IssueCategory.getId() %>">New Topic</a><br>
</zeroio:permission>
<dhv:pagedListStatus label="Topics" title="<%= showError(request, "actionError") %>" object="projectIssuesInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" nowrap><strong>Action</strong></th>
    <th width="100%"><strong>Topic</strong></th>
    <th align="center" nowrap><strong>Author</strong></th>
    <th align="center" nowrap><strong>Replies</strong></th>
    <th align="center" nowrap><strong>Last Post</strong></th>
  </tr>
<%    
  IssueList issues = Project.getIssues();
  if (issues.size() == 0) {
%>
  <tr class="row2">
    <td colspan="5">No messages to display.</td>
  </tr>
<%
  }
  int count = 0;
  int rowid = 0;
  Iterator i = issues.iterator();
  while (i.hasNext()) {
    ++count;
    rowid = (rowid != 1?1:2);
    Issue thisIssue = (Issue) i.next();
%>    
  <tr>
    <td class="row<%= rowid %>" valign="top" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>', 'menuItem', <%= thisIssue.getId() %>, <%= IssueCategory.getId() %>);"
         onMouseOver="over(0, <%= count %>)"
         onmouseout="out(0, <%= count %>); hideMenu('menuItem');"><img 
         src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td class="row<%= rowid %>" valign="top" width="100%">
      <img border="0" src="images/icons/stock_draw-callouts-16.gif" align="absmiddle">
      <a href="ProjectManagementIssues.do?command=Details&pid=<%= thisIssue.getProjectId() %>&iid=<%= thisIssue.getId() %>&cid=<%= IssueCategory.getId() %>&resetList=true"><%= toHtml(thisIssue.getSubject()) %></a>
    </td>
    <td class="row<%= rowid %>" valign="top" align="center" nowrap>
      <dhv:username id="<%= thisIssue.getEnteredBy() %>"/>
    </td>
    <td class="row<%= rowid %>" valign="top" align="center" nowrap>
      <%= ((thisIssue.getReplyCount()==0)?"0":""+thisIssue.getReplyCount()) %>
    </td>
    <td class="row<%= rowid %>" valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= thisIssue.getReplyDate() %>"/><br />
      <%= ((thisIssue.getReplyCount()==0)?"":"by") %>
      <dhv:username id="<%= thisIssue.getReplyBy() %>"/>
    </td>
  </tr>
<%
  }
%>
</table>
<br>
<dhv:pagedListControl object="projectIssuesInfo"/>
