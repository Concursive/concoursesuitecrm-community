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
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_center_issues_categories_menu.jsp" %>
<%-- Preload image rollovers --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_data-explorer-16.gif" align="absmiddle">
      Forums
    </td>
  </tr>
</table>
<br>
<zeroio:permission name="project-discussion-forums-add">
<img border="0" src="images/icons/stock_new-callouts2-16.gif" align="absmiddle">
<a href="ProjectManagementIssueCategories.do?command=Add&pid=<%= Project.getId() %>">New Forum</a><br>
</zeroio:permission>
<dhv:pagedListStatus label="Forums" title="<%= showError(request, "actionError") %>" object="projectIssueCategoryInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" nowrap><strong>Action</strong></th>
    <th width="100%"><strong>Forum</strong></th>
    <th align="center" nowrap><strong>Topics</strong></th>
    <th align="center" nowrap><strong>Posts</strong></th>
    <th align="center" nowrap><strong>Last Post</strong></th>
  </tr>
<%
	IssueCategoryList issueCategoryList = Project.getIssueCategories();
  if (issueCategoryList.size() == 0) {
%>
  <tr class="row2">
    <td colspan="5">No forums to display.</td>
  </tr>
<%
  }
  int count = 0;
  int rowid = 0;
  Iterator i = issueCategoryList.iterator();
  while (i.hasNext()) {
    ++count;
    rowid = (rowid != 1?1:2);
    IssueCategory thisCategory = (IssueCategory) i.next();
%>    
  <tr>
    <td class="row<%= rowid %>" valign="top" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>', 'menuItem', <%= thisCategory.getId() %>);"
         onMouseOver="over(0, <%= count %>)"
         onmouseout="out(0, <%= count %>); hideMenu('menuItem');"><img 
         src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td class="row<%= rowid %>" valign="top" width="100%">
      <img border="0" src="images/icons/stock_draw-callouts2-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Issues&pid=<%= thisCategory.getProjectId() %>&cid=<%= thisCategory.getId() %>&resetList=true"><%= toHtml(thisCategory.getSubject()) %></a>
    </td>
    <td class="row<%= rowid %>" valign="top" align="center" nowrap>
      <%= ((thisCategory.getTopicsCount()==0) ? "0" : "" + thisCategory.getTopicsCount()) %>
    </td>
    <td class="row<%= rowid %>" valign="top" align="center" nowrap>
      <%= ((thisCategory.getPostsCount()==0) ? "0" : "" + thisCategory.getPostsCount()) %>
    </td>
    <td class="row<%= rowid %>" valign="top" align="center" nowrap>
      <dhv:evaluate if="<%= thisCategory.getPostsCount() > 0 %>">
      <zeroio:tz timestamp="<%= thisCategory.getLastPostDate() %>"/><br />
      </dhv:evaluate>
			<dhv:username id="<%= thisCategory.getModifiedBy() %>"/>
    </td>
  </tr>
<%
  }
%>
</table>
<br>
<dhv:pagedListControl object="projectIssueCategoryInfo"/>
