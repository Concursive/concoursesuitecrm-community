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
<%@ page import="java.util.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.web.LookupElement" %>
<%@ page import="org.aspcfs.modules.tasks.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="category" class="org.aspcfs.utils.web.LookupElement" scope="request"/>
<jsp:useBean id="outlineList" class="org.aspcfs.modules.tasks.base.TaskList" scope="request"/>
<jsp:useBean id="projectListsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_center_lists_menu.jsp" %>
<%-- Preload image rollovers --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<%-- dynamic checkboxes --%>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_list_enum2-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=<%= Project.getId() %>">Lists</a> >
      <%= toHtml(category.getDescription()) %>
    </td>
  </tr>
</table>
<br>
<zeroio:permission name="project-lists-modify">
<a href="ProjectManagementLists.do?command=Add&pid=<%= Project.getId() %>&cid=<%= category.getId() %>">Add an Item to this List</a><br>
<br>
</zeroio:permission>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <form name="pagedListView" method="post" action="ProjectManagement.do?command=ProjectCenter&section=Lists&pid=<%= Project.getId() %>&cid=<%= category.getId() %>">
    <td align="left">
      <img alt="" src="images/icons/stock_filter-data-by-criteria-16.gif" align="absmiddle">
      <select name="listView" onChange="javascript:document.forms['pagedListView'].submit();">
        <option <%= projectListsInfo.getOptionValue("open") %>>Incomplete Items</option>
        <option <%= projectListsInfo.getOptionValue("closed") %>>Completed Items</option>
        <option <%= projectListsInfo.getOptionValue("all") %>>All Items</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus label="Items" title="<%= showError(request, "actionError") %>" object="projectListsInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8"><strong>Action</strong></th>
    <th align="center"><strong>#</strong></th>
    <th align="center"><strong>Priority</strong></th>
    <th width="100%"><strong>Item</strong></th>
    <th align="center" nowrap><strong>Modified By</strong></th>
    <th align="center"><strong>Modified</strong></th>
  </tr>
<%
  if (outlineList.size() == 0) {
%>  
  <tr class="row2">
    <td colspan="6">No items to display.</td>
  </tr>
<%
  }
  int count = 0;
  int rowid = 0;
  Iterator i = outlineList.iterator();
  while (i.hasNext()) {
    ++count;
    rowid = (rowid != 1?1:2);
    Task thisTask = (Task)i.next();
%>    
  <tr class="row<%= rowid %>">
    <td valign="top" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>', 'menuListItem', <%= thisTask.getId() %>);"
         onMouseOver="over(0, <%= count %>)"
         onmouseout="out(0, <%= count %>); hideMenu('menuListItem');"><img 
         src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td align="center" valign="top" nowrap>
      <table border="0" cellpadding="0" cellspacing="0" width="100%" class="empty">
        <tr>
          <td valign="middle" align="right">
            <%= count %>.&nbsp;
          </td>
        </tr>
      </table>
    </td>
    <td align="center" valign="top" nowrap>
      <%= thisTask.getPriority() %>
    </td>
    <td width="100%" valign="top" align="left"<%= thisTask.getComplete()?" class=\"ghost\"":"" %>>
      <zeroio:permission name="project-lists-modify"><a href="javascript:changeImages('task<%= count %>', 'ProjectManagementLists.do?command=MarkItem&pid=<%= Project.getId() %>&id=<%= thisTask.getId() %>&check=off', 'ProjectManagementLists.do?command=MarkItem&pid=<%= Project.getId() %>&id=<%= thisTask.getId() %>&check=on')"></zeroio:permission><img name="task<%= count %>" border="0" src="images/box<%= thisTask.getComplete()?"-checked":"" %>.gif" alt="" align="absmiddle" id="<%= thisTask.getComplete()?"1":"0" %>"><zeroio:permission name="project-lists-modify"></a></zeroio:permission>
      <%= toHtml(thisTask.getDescription()) %>
      <dhv:evaluate if="<%= hasText(thisTask.getNotes()) %>">
      <a href="javascript:popURL('ProjectManagementLists.do?command=Details&pid=<%= Project.getId() %>&cid=<%= category.getId() %>&id=<%= thisTask.getId() %>&popup=true','List_Details','650','375','yes','yes');"><img src="images/icons/stock_insert-note-16.gif" border="0" align="absmiddle"/></a>
      </dhv:evaluate>
    </td>
    <td align="center" valign="top" nowrap>
      <dhv:username id="<%= thisTask.getModifiedBy() %>"/>
    </td>
    <td align="center" valign="top" nowrap>
      <zeroio:tz timestamp="<%= thisTask.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/>
    </td>
  </tr>
<%    
  }
%>
</table>

