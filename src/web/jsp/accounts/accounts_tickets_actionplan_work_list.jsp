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
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.troubletickets.base.Ticket"%>
<jsp:useBean id="actionPlanWorkList" class="org.aspcfs.modules.actionplans.base.ActionPlanWorkList" scope="request"/>
<jsp:useBean id="accountTicketPlanWorkListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ticket" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_tickets_actionplan_work_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  
  function reassignPlan(userId, actionPlanWork) {
    window.location.href = "AccountTicketActionPlans.do?command=Reassign&ticketId=<%= ticket.getId() %>&actionPlanId=" + actionPlanWork + "&userId=" + userId + "&return=list";
  }
</SCRIPT>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.tickets.tickets">Tickets</dhv:label></a> >
<% if (request.getParameter("return") == null) {%>
<a href="AccountTickets.do?command=TicketDetails&id=<%=ticket.getId()%>"><dhv:label name="accounts.tickets.details">Ticket Details</dhv:label></a> >
<%}%>
<dhv:label name="sales.actionPlans">Action Plans</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<% String param1 = "id=" + ticket.getId(); %>
<dhv:container name="accounts" selected="tickets" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <dhv:container name="accountstickets" selected="actionplans" object="ticket" param="<%= "id=" + ticket.getId() %>">
  <%@ include file="accounts_ticket_header_include.jsp" %>
  <dhv:evaluate if="<%= ticket.getClosed() != null %>">
    <font color="red"><dhv:label name="tickets.alert.closed">This ticket has been closed:</dhv:label>
    <zeroio:tz timestamp="<%= ticket.getClosed() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </font><br />
  </dhv:evaluate>
<dhv:permission name="accounts-action-plans-add"><a href="AccountTicketActionPlans.do?command=Add&ticketId=<%= ticket.getId() %>"><dhv:label name="actionPlan.addActionPlan">Add Action Plan</dhv:label></a><br /></dhv:permission>
<form name="actionPlanListView" method="post" action="AccountTicketActionPlans.do?command=List&ticketId=<%= ticket.getId() %>">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
      <select size="1" name="listFilter1" onChange="javascript:document.actionPlanListView.submit();">
        <option value="all" <%= accountTicketPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("all")?" selected":"" %>><dhv:label name="actionPlan.allActionPlans">All Action Plans</dhv:label></option>
        <option value="my" <%= accountTicketPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("my")?" selected":"" %>><dhv:label name="actionPlan.myActionPlans">My Action Plans</dhv:label></option>
        <option value="mymanaged" <%= accountTicketPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("mymanaged")?" selected":"" %>><dhv:label name="actionPlan.myManagedActionPlans">Action Plans Managed By Me</dhv:label></option>
        <option value="mywaiting" <%= accountTicketPlanWorkListInfo.getFilterValue("listFilter1").equalsIgnoreCase("mywaiting")?" selected":"" %>><dhv:label name="actionPlan.myWaitingActionPlans">Action Plans Waiting on Me</dhv:label></option>
      </select>
      <select size="1" name="listFilter2" onChange="javascript:document.actionPlanListView.submit();">
        <option value="true" <%= accountTicketPlanWorkListInfo.getFilterValue("listFilter2").equalsIgnoreCase("true")?" selected":"" %>><dhv:label name="actionPlan.activePlans">Active Plans</dhv:label></option>
        <option value="false" <%= accountTicketPlanWorkListInfo.getFilterValue("listFilter2").equalsIgnoreCase("false")?" selected":"" %>><dhv:label name="actionPlan.inactivePlans">Inactive Plans</dhv:label></option>
        <option value="all" <%= accountTicketPlanWorkListInfo.getFilterValue("listFilter2").equalsIgnoreCase("all")?" selected":"" %>><dhv:label name="actionPlan.anyStatusPlans">Any Status Plans</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="accountTicketPlanWorkListInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th valign="middle">
      &nbsp;
    </th>
    <th valign="middle">
      <strong><dhv:label name="sales.assigned">Assigned</dhv:label></strong>
    </th>
    <th valign="middle" width="25%">
      <strong><dhv:label name="dynamicForm.name">Name</dhv:label></strong>
    </th>
    <th valign="middle" nowrap>
      <strong><dhv:label name="actionPlan.actionRequired">Action Required</dhv:label></strong>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.weeklyPotential">Weekly Potential</dhv:label></strong>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.currentPhase">Current Phase</dhv:label></strong>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.daysInPhase">Days in Phase</dhv:label></strong>
    </th>
    <th align="center" nowrap>
      <strong><dhv:label name="actionPlan.daysActive">Days Active</dhv:label></strong>
    </th>
    <th valign="middle" nowrap>
      <strong><a href="AccountTicketActionPlans.do?command=List&ticketId=<%= ticket.getId() %>&column=apw.modified"><dhv:label name="actionList.lastUpdated">Last Updated</dhv:label></a></strong>
      <%= accountTicketPlanWorkListInfo.getSortIcon("apw.modified") %>
    </th>
  </tr>
 <%
  Iterator j = actionPlanWorkList.iterator();
  if ( j.hasNext() ) {
  int rowid = 0;
  int i =0;
  while (j.hasNext()) {
      i++;
      rowid = (rowid != 1 ? 1 : 2);
      ActionPlanWork thisWork = (ActionPlanWork) j.next();
%>
  <tr
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null && thisWork.getCurrentPhase().requiresUserAttention(User.getUserRecord().getId(), request) %>">
        class="highlightRed"
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() == null || !thisWork.getCurrentPhase().requiresUserAttention(User.getUserRecord().getId(), request) %>">
        class="row<%= rowid %>"
      </dhv:evaluate>
      >
    <td>
     <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="javascript:displayMenu('select<%= i %>','menuActionPlan','<%= thisWork.getId() %>','<%= thisWork.getManagerId() %>','<%= thisWork.getEnabled() ? 1 : 0 %>', <%= thisWork.getPlanSiteId() %>);"
          onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuActionPlan');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td>
      <a href="AccountTicketActionPlans.do?command=Details&actionPlanId=<%= thisWork.getId() %>&ticketId=<%= ticket.getId() %>"><dhv:username id="<%= thisWork.getAssignedTo() %>"/></a>
    </td>
    <td width="25%"><%= toHtml(thisWork.getPlanName()) %></td>
    </td>
    <td align="center" nowrap>
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null %>">
        <dhv:evaluate if="<%= thisWork.getCurrentPhase().requiresUserAttention(User.getUserRecord().getId(), request) %>">
          <dhv:label name="account.yes">Yes</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= !thisWork.getCurrentPhase().requiresUserAttention(User.getUserRecord().getId(), request) %>">
          <dhv:label name="account.no">No</dhv:label>
        </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() == null %>">
        <dhv:label name="account.no">No</dhv:label>
      </dhv:evaluate>
    </td>
    <td align="center">
      <dhv:evaluate if="<%= thisWork.getOrganization() != null %>">
        <zeroio:currency value="<%= thisWork.getOrganization().getPotential() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getOrganization() == null && thisWork.getContact() != null %>">
        <zeroio:currency value="<%= thisWork.getContact().getPotential() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
      </dhv:evaluate>
    </td>
    <td align="center">
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null %>">
        <%= toHtml(thisWork.getCurrentPhase().getPhaseName()) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() == null %>">
        <dhv:label name="sales.actionPlan">Action Plan</dhv:label>
      </dhv:evaluate>
    </td>
    <td align="center">
      &nbsp;
      <dhv:evaluate if="<%= thisWork.getCurrentPhase() != null %>">
        <%= thisWork.getCurrentPhase().getDaysInPhase() %>
      </dhv:evaluate>
    </td>
    <td align="center">
      <%= thisWork.getDaysActive() %>
    </td>
    <td align="center">
      <zeroio:tz timestamp="<%= thisWork.getModified() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>"/>
    </td>
  </tr>
<%}
}else{%>
      <tr>
        <td class="containerBody" colspan="9" valign="center">
          <dhv:label name="actionPlan.noActionPlansFound">No Action Plans found in this view.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
</form>
&nbsp;<br>
<dhv:pagedListControl object="accountTicketPlanWorkListInfo"/>
<input type="hidden" name="id" value="<%= ticket.getId() %>">
</dhv:container>
</dhv:container>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

