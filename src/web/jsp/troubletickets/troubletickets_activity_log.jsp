<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.* " %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="activityList" class="org.aspcfs.modules.troubletickets.base.TicketActivityLogList" scope="request"/>
<jsp:useBean id="TMListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="activity_log_menu.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do?"><dhv:label name="tickets.helpdesk"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></dhv:label></a> > 
    <% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
      <a href="TroubleTickets.do?command=SearchTicketsForm">Search Form</a> >
      <a href="TroubleTickets.do?command=SearchTickets">Search Results</a> >
    <%}else{%> 
      <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View Tickets</dhv:label></a> >
    <%}%>
<a href="TroubleTickets.do?command=Details&id=<%= ticketDetails.getId() %>"><dhv:label name="tickets.details">Ticket Details</dhv:label></a> >
Activity Log
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="ticket_header_include.jsp" %>
<% String param1 = "id=" + ticketDetails.getId(); %>
<dhv:container name="tickets" selected="activitylog" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<table cellspacing="0" border="0" width="100%">
  <tr>
    <td>
      <dhv:permission name="tickets-activity-log-add">
        <a href="TroubleTicketActivityLog.do?command=Add&id=<%=ticketDetails.getId()%>">Add activities</a>
      </dhv:permission>
      <dhv:permission name="tickets-activity-log-add" none="true"></dhv:permission>
    </td>
  </tr>
</table>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="TMListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="12%">
      <strong>First Activity Date</strong>
    </th>
    <th width="12%">
      <strong>Last Activity Date</strong>
    </th>
    <th width="12%">
      <strong>Follow Up?</strong>
    </th>
    <th width="10%" nowrap>
      <strong>Alert Date</strong>
    </th>
    <th width="36%" nowrap>
      <strong>Follow Up Description</strong>
    </th>
    <th width="10%" nowrap>
      <strong>Modified</strong>
    </th>
  </tr>
  <% 
    Iterator itr = activityList.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        TicketActivityLog thisMaintenance = (TicketActivityLog)itr.next();
    %>    
  <tr valign="top" class="row<%=rowid%>">
    <td width=8 valign="center"  nowrap >
        <% int status = -1;%>
        <% status = thisMaintenance.getEnabled() ? 1 : 0; %>
      	<%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuTicketForm', '<%=ticketDetails.getId() %>', '<%= thisMaintenance.getId() %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTicketForm');">
         <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
    <td width="12%" >
      <zeroio:tz timestamp="<%=thisMaintenance.getFirstActivityDate()%>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/>
		</td>
		<td width="12%" >
      <zeroio:tz timestamp="<%=thisMaintenance.getLastActivityDate()%>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/>
		</td>
		<td width="12%" >
    <%if (thisMaintenance.getFollowUpRequired()) { %>
    Yes
    <%}else{%>
    No
    <%}%>
		</td>
		<td width="10%" >
      <% if(!User.getTimeZone().equals(thisMaintenance.getAlertDateTimeZone())){%>
      <zeroio:tz timestamp="<%= thisMaintenance.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisMaintenance.getAlertDate() %>" dateOnly="true" timeZone="<%= thisMaintenance.getAlertDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
		</td>
		<td width="48%" >
    <%if (thisMaintenance.getFollowUpRequired() == false) { %>
    N/A
    <%}else{%>
    <%= toHtml(thisMaintenance.getFollowUpDescription())%>
    <%}%>
		</td>
		<td width="10%" >
      <zeroio:tz timestamp="<%=thisMaintenance.getModified()%>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/>
		</td>
   </tr>
    <%  
      }
    }else{
    %>
    <tr>
      <td colspan="7" class="containerBody">
        No activities found.
      </td>
    </tr>
    <%
    }
    %></table>
<br>
<dhv:pagedListControl object="TMListInfo"/>
 </td>
</tr>
</table>
