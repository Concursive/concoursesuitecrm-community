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
  - Version: $Id: 
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.actionplans.base.*, org.aspcfs.modules.base.Constants"%>
<jsp:useBean id="actionPlanList" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="actionPlanWork" class="org.aspcfs.modules.actionplans.base.ActionPlanWork" scope="request"/>
<jsp:useBean id="ticket" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<script language="JavaScript" TYPE="text/javascript" src="javascript/popContacts.js?v=20070827"></script>
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
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<%if(defectCheck != null && !"".equals(defectCheck.trim())) {%>
  <a href="TroubleTicketDefects.do?command=View"><dhv:label name="tickets.defects.viewDefects">View Defects</dhv:label></a> >
  <a href="TroubleTicketDefects.do?command=Details&defectId=<%= defectCheck %>"><dhv:label name="tickets.defects.defectDetails">Defect Details</dhv:label></a> >
  <a href="TroubleTickets.do?command=Details&id=<%= ticket.getId() %>&defectCheck=<%= defectCheck %>"><dhv:label name="tickets.details">Ticket Details</dhv:label></a> >
<%}else{%>
<% if (("list".equals((String)request.getParameter("return"))) ||
      ("searchResults".equals((String)request.getParameter("return")))) {%>
    <% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
      <a href="TroubleTickets.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
      <a href="TroubleTickets.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
    <%}else{%> 
      <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View Tickets</dhv:label></a> >
    <%}%>
<%} else {%>
  <% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
    <a href="TroubleTickets.do?command=SearchTickets"><dhv:label name="tickets.search">Search Tickets</dhv:label></a> >
  <%}else{%> 
    <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View Tickets</dhv:label></a> >
  <%}%>
    <a href="TroubleTickets.do?command=Details&id=<%= ticket.getId() %>"><dhv:label name="tickets.details">Ticket Details</dhv:label></a> >
<%}%>
<%}%>
<a href="TroubleTicketActionPlans.do?command=List&ticketId=<%= ticket.getId() %>"><dhv:label name="sales.actionPlans">Action Plans</dhv:label></a> >
<dhv:label name="actionPlan.addActionPlan">Add Action Plan</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<% String param1 = "id=" + ticket.getId(); %>
<dhv:container name="tickets" selected="actionplans" object="ticket" param="<%= param1 %>" hideContainer='<%= (isPopup(request) || (defectCheck != null && !"".equals(defectCheck.trim()))) %>'>
  <%@ include file="ticket_header_include.jsp" %>
  <dhv:evaluate if="<%= ticket.getClosed() != null %>">
    <font color="red"><dhv:label name="tickets.alert.closed">This ticket has been closed:</dhv:label>
    <zeroio:tz timestamp="<%= ticket.getClosed() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </font><br />
  </dhv:evaluate>
<form name="addActionPlan" method="post" action="TroubleTicketActionPlans.do?command=Insert&auto-populate=true" onSubmit="return checkForm(this);">
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="return checkForm(this.form)">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='TroubleTicketActionPlans.do?command=List&ticketId=<%= ticket.getId() %>'"/>
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
                <dhv:username id="<%= User.getUserId() %>" lastFirst="true"/>
              <%  } %>
              </div>
            </td>
            <td>
              <%  if (actionPlanWork.getAssignedTo() > 0) { %>
							<% System.out.println("ASSIGNED TO");%>
	              <input type="hidden" name="assignedTo" id="assignedTo" value="<%= actionPlanWork.getAssignedTo() %>">
              <%  } else { %>
							<% System.out.println("USER" + User.getUserId());%>
	              <input type="hidden" name="assignedTo" id="assignedTo" value="<%= User.getUserId() %>">
              <%  } %>
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
              &nbsp;[<a href="javascript:popContactsListSingle('managerId','changeplanmanager', 'listView=employees&siteId=<%= ticket.getSiteId() %>&usersOnly=true&hierarchy=<%= User.getUserId() %>&reset=true&searchcodePermission=tickets-action-plans-view,accounts-action-plans-view,myhomepage-action-plans-view');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
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
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='TroubleTicketActionPlans.do?command=List&ticketId=<%= ticket.getId() %>'"/>
  <input type="hidden" name="ticketId" value="<%= ticket.getId() %>"/>
</form>
</dhv:container>
