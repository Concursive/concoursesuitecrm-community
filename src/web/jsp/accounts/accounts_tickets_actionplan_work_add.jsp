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
  - Version: $Id: 
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="actionPlanList" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="actionPlanWork" class="org.aspcfs.modules.actionplans.base.ActionPlanWork" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ticket" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript">
  function checkForm(form) {
    formTest = true;
    message = "";
    if (form.managerId.value == -1) {
      message += label("check.actionplan.managerId", "- Plan Manager is a required field\r\n");
      formTest = false;
    }
    if (form.assignedTo.value == -1) {
      message += label("check.actionplan.assignedTo", "- Plan Assignee is a required field\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</script>
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
  <a href="AccountTicketActionPlans.do?command=List&ticketId=<%= ticket.getId() %>"><dhv:label name="sales.actionPlans">Action Plans</dhv:label></a> >
  <dhv:label name="actionPlan.addActionPlan">Add Action Plan</dhv:label>
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
<form name="addActionPlan" method="post" action="AccountTicketActionPlans.do?command=Insert&auto-populate=true" onSubmit="return checkForm(this);">
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="return checkForm(this.form)">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AccountTicketActionPlans.do?command=List&ticketId=<%= ticket.getId() %>'"/>
  &nbsp;<br /><br />
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="actionPlan.addActionPlan">Add Action Plan</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="sales.actionPlan">Action Plan</dhv:label>
      </td>
      <td>
        <%= actionPlanList.getHtmlSelect("actionPlan", actionPlanWork.getActionPlanId()) %> <font color="red">*</font> <%= showAttribute(request, "actionPlanError") %>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="actionList.assignTo">Assign To</dhv:label>
      </td>
      <td>
        <table class="empty">
          <tr class="containerBody">
            <td>
              <div id="changeowner">
              <%  if (actionPlanWork.getAssignedTo() > 0) { %>
                <dhv:username id="<%= actionPlanWork.getAssignedTo() %>" lastFirst="true"/>
              <%  } else { %>
                 <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
              <%  } %>
              </div>
            </td>
            <td>
              <input type="hidden" name="assignedTo" id="assignedTo" value="<%= actionPlanWork.getAssignedTo() %>">
              &nbsp;[<a href="javascript:popContactsListSingle('assignedTo','changeowner', 'listView=employees&siteId=<%= ticket.getSiteId() %>&usersOnly=true&searchcodePermission=tickets-action-plans-view,accounts-action-plans-view,myhomepage-action-plans-view&reset=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
              &nbsp; [<a href="javascript:changeDivContent('changeowner',label('none.selected','None Selected'));javascript:resetNumericFieldValue('assignedTo');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
              <font color="red">*</font> <%= showAttribute(request, "assignedToError") %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel" valign="top">
        <dhv:label name="actionPlan.planManager">Plan Manager</dhv:label>
      </td>
      <td>
        <table class="empty">
          <tr class="containerBody">
            <td>
              <div id="changeplanmanager">
              <% if (actionPlanWork.getManagerId() > 0) { %>
                <dhv:username id="<%= actionPlanWork.getManagerId() %>" lastFirst="true"/>
              <% } else { %>
                 <dhv:username id="<%= User.getUserRecord().getId() %>" lastFirst="true"/>
              <% } %>
              </div>
            </td>
            <td>
              <input type="hidden" name="managerId" id="managerId" value="<%= ((actionPlanWork.getManagerId() > 0) ? actionPlanWork.getManagerId() : User.getUserRecord().getId()) %>">
              &nbsp;[<a href="javascript:popContactsListSingle('managerId','changeplanmanager', 'listView=employees&siteId=<%= ticket.getSiteId() %>&usersOnly=true&hierarchy=<%= User.getUserId() %>&searchcodePermission=tickets-action-plans-view,accounts-action-plans-view,myhomepage-action-plans-view&reset=true');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
              &nbsp; [<a href="javascript:changeDivContent('changeplanmanager',label('none.selected','None Selected'));javascript:resetNumericFieldValue('managerId');"><dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label></a>]
              <font color="red">*</font> <%= showAttribute(request, "managerIdError") %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  &nbsp;<br />
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="return checkForm(this.form)">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AccountTicketActionPlans.do?command=List&ticketId=<%= ticket.getId() %>'"/>
  <input type="hidden" name="ticketId" value="<%= ticket.getId() %>"/>
</form>
</dhv:container>
</dhv:container>
