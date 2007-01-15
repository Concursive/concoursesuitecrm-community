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
      <a href="ProjectManagement.do?command=ProjectCenter&section=Lists_Categories&pid=<%= Project.getId() %>"><dhv:label name="project.lists">Lists</dhv:label></a> >
      <%= toHtml(category.getDescription()) %>
    </td>
  </tr>
</table>
<br>
<dhv:evaluate if="<%= !Project.isTrashed() %>" >
  <zeroio:permission name="project-lists-modify">
    <a href="ProjectManagementLists.do?command=Add&pid=<%= Project.getId() %>&cid=<%= category.getId() %>"><dhv:label name="project.addAnItemtothisList">Add an Item to this List</dhv:label></a><br>
    <br />
  </zeroio:permission>
</dhv:evaluate>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <form name="pagedListView" method="post" action="ProjectManagement.do?command=ProjectCenter&section=Lists&pid=<%= Project.getId() %>&cid=<%= category.getId() %>">
    <td align="left">
      <img alt="" src="images/icons/stock_filter-data-by-criteria-16.gif" align="absmiddle">
      <select name="listView" onChange="javascript:document.forms['pagedListView'].submit();">
        <option <%= projectListsInfo.getOptionValue("open") %>><dhv:label name="project.incompleteItems">Incomplete Items</dhv:label></option>
        <option <%= projectListsInfo.getOptionValue("closed") %>><dhv:label name="project.completedItems">Completed Items</dhv:label></option>
        <option <%= projectListsInfo.getOptionValue("all") %>><dhv:label name="project.allItems">All Items</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus label="Items" title='<%= showError(request, "actionError") %>' object="projectListsInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" nowrap>&nbsp;</th>
    <th align="center" nowrap><strong>#</strong></th>
    <th align="center" nowrap><strong><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></strong></th>
    <th width="100%" nowrap><strong><dhv:label name="accounts.accounts_documents_details.Item">Item</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="accounts.accounts_fields_list.ModifiedBy">Modified By</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></strong></th>
  </tr>
<%
  if (outlineList.size() == 0) {
%>
  <tr class="row2">
    <td colspan="6"><dhv:label name="project.noItemstoDisplay">No items to display.</dhv:label></td>
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
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>', 'menuListItem', <%= thisTask.getId() %>,'<%= Project.isTrashed() %>');"
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
      <table border="0" cellspacing="0" cellpadding="0" width="100%" class="empty">
        <tr>
          <dhv:evaluate if="<%= !Project.isTrashed() %>" >
            <td valign="top" nowrap>
              <zeroio:permission name="project-lists-modify"><a href="javascript:changeImages('task<%= count %>', 'ProjectManagementLists.do?command=MarkItem&pid=<%= Project.getId() %>&id=<%= thisTask.getId() %>&check=off', 'ProjectManagementLists.do?command=MarkItem&pid=<%= Project.getId() %>&id=<%= thisTask.getId() %>&check=on')"></zeroio:permission><img name="task<%= count %>" border="0" src="images/box<%= thisTask.getComplete()?"-checked":"" %>.gif" alt="" align="absmiddle" id="<%= thisTask.getComplete()?"1":"0" %>"><zeroio:permission name="project-lists-modify"></a></zeroio:permission>&nbsp;
            </td>
          </dhv:evaluate>
          <td valign="top" width="100%">
            <%= toHtml(thisTask.getDescription()) %>
            <dhv:evaluate if="<%= hasText(thisTask.getNotes()) %>">
            <a href="javascript:popURL('ProjectManagementLists.do?command=Details&pid=<%= Project.getId() %>&cid=<%= category.getId() %>&id=<%= thisTask.getId() %>&popup=true','List_Details','650','375','yes','yes');"><img src="images/icons/stock_insert-note-16.gif" border="0" align="absmiddle"/></a>
            </dhv:evaluate>
          </td>
        </tr>
      </table>
    </td>
    <td align="center" valign="top" nowrap>
      <dhv:username id="<%= thisTask.getModifiedBy() %>"/>
    </td>
    <td align="center" valign="top" nowrap>
      <zeroio:tz timestamp="<%= thisTask.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
<%
  }
%>
</table>
