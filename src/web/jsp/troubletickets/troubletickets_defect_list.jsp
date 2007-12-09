<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*,java.text.DateFormat" %>
<jsp:useBean id="defects" class="org.aspcfs.modules.troubletickets.base.TicketDefectList" scope="request"/>
<jsp:useBean id="defectListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="troubletickets_defect_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<dhv:label name="tickets.defects.viewDefects">View Defects</dhv:label> 
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="tickets-defects-add">
<a href="TroubleTicketDefects.do?command=Add"><dhv:label name="tickets.addDefect">Add a Defect</dhv:label></a><br />
</dhv:permission>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="defectListInfo"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th width="8" nowrap>&nbsp;</th>
      <th nowrap>
        <strong><a href="TroubleTicketDefects.do?command=View&column=title"><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></a></strong>
        <%= defectListInfo.getSortIcon("title") %>
      </th>
      <th nowrap>
        <strong><a href="TroubleTicketDefects.do?command=View&column=start_date"><dhv:label name="product.startDate">Start Date</dhv:label></a></strong>
        <%= defectListInfo.getSortIcon("start_date") %>
      </th>
      <th nowrap>
        <strong><a href="TroubleTicketDefects.do?command=View&column=end_date"><dhv:label name="reports.parameter.endDate">End Date</dhv:label></a></strong>
        <%= defectListInfo.getSortIcon("end_date") %>
      </th>
      <th nowrap>
        <strong><dhv:label name="tickets.defects.active">Active</dhv:label></strong>
      </th>
      <th nowrap>
        <strong><dhv:label name="accounts.site">Site</dhv:label></strong>
      </th>
      <th nowrap>
        <strong><dhv:label name="ticket.age">Age</dhv:label></strong>
      </th>
      <th nowrap>
        <strong><dhv:label name="accounts.tickets.tickets">Tickets</dhv:label></strong>
      </th>
    </tr>
<%
	Iterator j = defects.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
		  rowid = (rowid != 1?1:2);
      TicketDefect thisDefect = (TicketDefect) j.next();
      boolean canEdit = true;
      boolean canDelete = true;
      if (User.getSiteId() != -1){
        if (thisDefect.getSiteId() != User.getSiteId()){
          canEdit = false;
          canDelete = false;
        }
      }
%>
		<tr class="row<%= rowid %>">
      <td valign="top">
        <a href="javascript:displayMenu('select<%= i %>','menuTicketDefect', '<%= thisDefect.getId() %>',<%=canEdit%>,<%=canDelete%>);"
        onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTicketDefect');">
        <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0" /></a>
      </td>
      <td valign="top" width="100%" nowrap>
        <a href="TroubleTicketDefects.do?command=Details&defectId=<%= thisDefect.getId() %>"><%= toHtml(thisDefect.getTitle()) %></a>
      </td>
      <td valign="top"><%-- Start Date --%>
        <zeroio:tz timestamp="<%= thisDefect.getStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" default="&nbsp;" />
      </td>
      <td valign="top"><%-- End Date --%>
        <zeroio:tz timestamp="<%= thisDefect.getEndDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" default="&nbsp;" />
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= !thisDefect.isDisabled() %>">
          <dhv:label name="account.yes">Yes</dhv:label>
        </dhv:evaluate><dhv:evaluate if="<%= thisDefect.isDisabled() %>">
          <dhv:label name="account.no">No</dhv:label>
        </dhv:evaluate>
      </td>
      <td valign="top" nowrap>
        <%= SiteList.getSelectedValue(thisDefect.getSiteId()) %>
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if='<%= "".equals(thisDefect.getAgeOf()) %>'>&nbsp;</dhv:evaluate>
        <dhv:evaluate if='<%= !"".equals(thisDefect.getAgeOf()) %>'>
          <dhv:label name="tickets.defects.ageOf" param='<%= "hours="+thisDefect.getAgeHours()+"|days="+thisDefect.getAgeDays() %>'><%= thisDefect.getAgeOf() %></dhv:label>
        </dhv:evaluate>
      </td>
      <td valign="top" nowrap>
        <%= thisDefect.getTickets().size() %>
      </td>
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan="8">
        <dhv:label name="tickets.defects.noDefectsAvailable.text">No defects available</dhv:label>
      </td>
    </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="defectListInfo"/>

