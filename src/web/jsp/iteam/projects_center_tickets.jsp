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
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="ticketList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="projectTicketsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_center_tickets_menu.jsp" %>
<%-- Preload image rollovers --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-organizer-16.gif" border="0" align="absmiddle">
      Tickets
    </td>
  </tr>
</table>
<br>
<zeroio:permission name="project-tickets-add">
<a href="ProjectManagementTickets.do?command=Add&pid=<%= Project.getId() %>">New Ticket</a><br>
<br>
</zeroio:permission>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <form name="ticketView" method="post" action="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>">
    <td align="left">
      <img alt="" src="images/icons/stock_filter-data-by-criteria-16.gif" align="absmiddle">
      <select name="listView" onChange="javascript:document.forms['ticketView'].submit();">
        <option <%= projectTicketsInfo.getOptionValue("open") %>>Open Tickets</option>
        <option <%= projectTicketsInfo.getOptionValue("closed") %>>Closed Tickets</option>
        <option <%= projectTicketsInfo.getOptionValue("all") %>>All Tickets</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus label="Tickets" title="<%= showError(request, "actionError") %>" object="projectTicketsInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th align="center">
      <strong>Id</strong>
    </th>
    <th align="center">
      <strong>Status</strong>
    </th>
    <th width="100%" nowrap>
      <strong><a href="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>&column=problem">Issue</a></strong>
      <%= projectTicketsInfo.getSortIcon("problem") %>
    </th>
    <th align="center" nowrap>
      <strong><a href="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>&column=pri_code">Priority</a></strong>
      <%= projectTicketsInfo.getSortIcon("pri_code") %>
    </th>
    <th nowrap>
      <strong>Assigned To</strong>
    </th>
    <th align="center" nowrap>
      <strong><a href="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>&column=entered">Age</a></strong>
      <%= projectTicketsInfo.getSortIcon("entered") %>
    </th>
    <th align="center" nowrap>
      <strong><a href="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>&column=modified">Last Modified</a></strong>
      <%= projectTicketsInfo.getSortIcon("modified") %>
    </th>
  </tr>
<dhv:evaluate if="<%= ticketList.size() == 0 %>">
  <tr class="row2">
    <td colspan="8">No tickets to display.</td>
  </tr>
</dhv:evaluate>
<%
  Iterator i = ticketList.iterator();
  int count = 0;
  int rowid = 0;
  int offset = (projectTicketsInfo.getCurrentOffset() - 1);
  while (i.hasNext()) {
    ++count;
    ++offset;
    rowid = (rowid != 1?1:2);
    Ticket thisTicket = (Ticket) i.next();
%>    
  <tr class="row<%= rowid %>">
    <td width="8" valign="top" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>', 'menuItem', <%= thisTicket.getId() %>);"
         onMouseOver="over(0, <%= count %>)"
         onmouseout="out(0, <%= count %>); hideMenu('menuItem');"><img 
         src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td align="center" nowrap valign="top">
      <%= thisTicket.getProjectTicketCount() %>
    </td>
    <td align="center" nowrap valign="top">
<% if (thisTicket.getClosed() == null) { %>
      <font color="green">open</font>
<%} else {%>
      <font color="red">closed</font>
<%}%>
    </td>
    <td valign="top">
      <a href="ProjectManagementTickets.do?command=Details&pid=<%= Project.getId() %>&id=<%= thisTicket.getId() %>"><%= toHtml(thisTicket.getProblemHeader()) %></a>
	<% if (thisTicket.getCategoryName() != null) { %>
      [<%= toHtml(thisTicket.getCategoryName()) %>]
	<%}%>
    </td>
    <td valign="top" align="center" nowrap>
      <%= toHtml(thisTicket.getPriorityName()) %>
    </td>
    <td valign="top">
      <dhv:username id="<%= thisTicket.getAssignedTo() %>"/>
    </td>
    <td valign="top" align="right" nowrap>
      <%= thisTicket.getAgeOf() %>
    </td>
    <td valign="top" align="center" nowrap>
<% if (thisTicket.getClosed() == null) { %>
      <zeroio:tz timestamp="<%= thisTicket.getModified() %>"/>
<%} else {%>
      <zeroio:tz timestamp="<%= thisTicket.getClosed() %>"/>
<%}%>
    </td>
	</tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="projectTicketsInfo"/>
