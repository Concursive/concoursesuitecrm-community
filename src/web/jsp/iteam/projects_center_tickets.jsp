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
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="ticketList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="projectTicketsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
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
      <dhv:label name="dependency.tickets">Tickets</dhv:label>
    </td>
  </tr>
</table>
<br>
<dhv:evaluate if="<%= !Project.isTrashed() %>" >
  <zeroio:permission name="project-tickets-add">
  <a href="ProjectManagementTickets.do?command=Add&pid=<%= Project.getId() %>"><dhv:label name="project.newTicket">New Ticket</dhv:label></a><br>
  <br />
  </zeroio:permission>
</dhv:evaluate>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <form name="ticketView" method="post" action="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>">
    <td align="left">
      <img alt="" src="images/icons/stock_filter-data-by-criteria-16.gif" align="absmiddle">
      <select name="listView" onChange="javascript:document.forms['ticketView'].submit();">
        <option <%= projectTicketsInfo.getOptionValue("open") %>><dhv:label name="project.openTickets">Open Tickets</dhv:label></option>
        <option <%= projectTicketsInfo.getOptionValue("closed") %>><dhv:label name="project.closedTickets">Closed Tickets</dhv:label></option>
        <option <%= projectTicketsInfo.getOptionValue("all") %>><dhv:label name="project.allTickets">All Tickets</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus label="Tickets" title='<%= showError(request, "actionError") %>' object="projectTicketsInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8">&nbsp;</th>
    <th align="center">
      <dhv:label name="project.id">Id</dhv:label>
    </th>
    <th align="center">
      <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
    </th>
    <th width="100%" nowrap>
      <dhv:label name="accounts.accounts_asset_history.Issue">Issue</dhv:label>
      <%= projectTicketsInfo.getSortIcon("problem") %>
    </th>
    <th align="center" nowrap>
      <a href="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>&column=pri_code"><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></a>
      <%= projectTicketsInfo.getSortIcon("pri_code") %>
    </th>
    <th nowrap>
      <dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label>
    </th>
    <th align="center" nowrap>
      <a href="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>&column=t.entered">Age</a>
      <%= projectTicketsInfo.getSortIcon("t.entered") %>
    </th>
    <th align="center" nowrap>
      <a href="ProjectManagement.do?command=ProjectCenter&section=Tickets&pid=<%= Project.getId() %>&column=t.modified"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.LastModified">Last Modified</dhv:label></a>
      <%= projectTicketsInfo.getSortIcon("t.modified") %>
    </th>
  </tr>
<dhv:evaluate if="<%= ticketList.size() == 0 %>">
  <tr class="row2">
    <td colspan="8"><dhv:label name="project.noTicketsToDisplay">No tickets to display.</dhv:label></td>
  </tr>
</dhv:evaluate>
<%
  Iterator i = ticketList.iterator();
  int count = 0;
  int rowid = 0;
  int offset = (projectTicketsInfo.getCurrentOffset() -1);
  while (i.hasNext()) {
    ++count;
    ++offset;
    rowid = (rowid != 1?1:2);
    Ticket thisTicket = (Ticket) i.next();
%>    
  <tr class="row<%= rowid %>">
    <td width="8" valign="top" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>','menuItem',<%= thisTicket.getId() %>,<%= offset %>,'<%= Project.isTrashed() %>');"
         onMouseOver="over(0, <%= count %>)"
         onmouseout="out(0, <%= count %>); hideMenu('menuItem');"><img 
         src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td align="center" nowrap valign="top">
      <a href="ProjectManagementTickets.do?command=Details&pid=<%= Project.getId() %>&id=<%= thisTicket.getId() %>&offset=<%= offset %>"><%= thisTicket.getPaddedProjectTicketCount() %></a>
    </td>
    <td align="center" nowrap valign="top">
<% if (thisTicket.getClosed() == null) { %>
      <font color="green"><dhv:label name="project.open.lowercase">open</dhv:label></font>
<%} else {%>
      <font color="red"><dhv:label name="project.closed.lowercase">closed</dhv:label></font>
<%}%>
    </td>
    <td valign="top">
      <%= toHtml(thisTicket.getProblemHeader()) %>
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
      <zeroio:tz timestamp="<%= thisTicket.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
<%} else {%>
      <zeroio:tz timestamp="<%= thisTicket.getClosed() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
<%}%>
    </td>
	</tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="projectTicketsInfo"/>
