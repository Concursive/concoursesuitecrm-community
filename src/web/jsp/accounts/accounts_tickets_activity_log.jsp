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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="activityList" class="org.aspcfs.modules.troubletickets.base.TicketActivityLogList" scope="request"/>
<jsp:useBean id="TMListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="ticket_activity_log_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=ticketDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=ticketDetails.getOrgId()%>"><dhv:label name="accounts.tickets.tickets">Tickets</dhv:label></a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=ticketDetails.getId()%>"><dhv:label name="accounts.tickets.details">Ticket Details</dhv:label></a> >
<dhv:label name="tickets.activitylog.long_html">Activity Log</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="tickets" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <dhv:container name="accountstickets" object="ticketDetails" selected="activitylog" param="<%= "id=" + ticketDetails.getId() %>">
    <%@ include file="accounts_ticket_header_include.jsp" %>
<dhv:permission name="accounts-accounts-tickets-activity-log-add,tickets-activity-log-view" all="false">
<table cellpadding="4" cellspacing="0" border="0" width="100%" >
  <tr>
    <td>
      <dhv:permission name="accounts-accounts-tickets-activity-log-add"><a href="AccountTicketActivityLog.do?command=Add&id=<%=ticketDetails.getId()%>"><dhv:label name="account.addActivities">Add activities</dhv:label></a><br /></dhv:permission>
    </td>
  </tr>
</table>
</dhv:permission>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="TMListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      &nbsp;
    </th>
    <th width="12%">
      <strong><dhv:label name="account.firstActivityDate">First Activity Date</dhv:label></strong>
    </th>
    <th width="12%">
      <strong><dhv:label name="account.lastActivityDate">Last Activity Date</dhv:label></strong>
    </th>
    <th width="12%">
      <strong><dhv:label name="account.folowUp.question">Follow Up?</dhv:label></strong>
    </th>
    <th width="10%" nowrap>
      <strong><dhv:label name="accounts.accounts_add.AlertDate">Alert Date</dhv:label></strong>
    </th>
    <th width="44%" nowrap>
      <strong><dhv:label name="account.followUpDescription">Follow Up Description</dhv:label></strong>
    </th>
    <th width="10%" nowrap>
      <strong><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></strong>
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
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTicketForm');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
    <td width="12%" >
    <zeroio:tz timestamp="<%=thisMaintenance.getFirstActivityDate()%>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
		</td>
		<td width="12%" >
    <zeroio:tz timestamp="<%=thisMaintenance.getLastActivityDate()%>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
		</td>
		<td width="12%" >
    <%if (thisMaintenance.getFollowUpRequired()) { %>
      <dhv:label name="account.yes">Yes</dhv:label>
    <%}else{%>
      <dhv:label name="account.no">No</dhv:label>
    <%}%>
		</td>
		<td width="10%">
      <% if(!User.getTimeZone().equals(thisMaintenance.getAlertDateTimeZone())){%>
      <zeroio:tz timestamp="<%= thisMaintenance.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisMaintenance.getAlertDate() %>" dateOnly="true" timeZone="<%= thisMaintenance.getAlertDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
		</td>
		<td width="48%" >
    <%if (thisMaintenance.getFollowUpRequired() == false) { %>
      <dhv:label name="account.na">N/A</dhv:label>
    <%}else{%>
    <%= toHtml(thisMaintenance.getFollowUpDescription())%>
    <%}%>
		</td>
		<td width="10%" >
      <zeroio:tz timestamp="<%=thisMaintenance.getModified()%>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
		</td>
   </tr>
    <%  
      }
    }else{
    %>
    <tr>
      <td colspan="7" class="containerBody">
        <dhv:label name="accounts.accounts_calls_list.NoActivitiesFound">No activities found.</dhv:label>
      </td>
    </tr>
    <%
    }
    %></table>
    <br>
    <dhv:pagedListControl object="TMListInfo"/>
  </dhv:container>
</dhv:container>