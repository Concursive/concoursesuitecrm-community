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
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*" %>
<%@ page import="org.aspcfs.modules.actionlist.base.*, org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="UserSelectList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SourceUser" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="SourceAccounts" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="SourceAllContacts" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="SourcePublicContacts" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="SourceHierarchyContacts" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="SourceAccountContacts" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="SourceLeads" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="SourceUsers" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SourceOpenTickets" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="IncompleteTicketTasks" class="org.aspcfs.modules.tasks.base.TaskList" scope="request"/>
<jsp:useBean id="SourceRevenue" class="org.aspcfs.modules.accounts.base.RevenueList" scope="request"/>
<jsp:useBean id="ManagingOpenOpportunities" class="org.aspcfs.modules.pipeline.base.OpportunityList" scope="request"/>
<jsp:useBean id="SourceOpportunities" class="org.aspcfs.modules.pipeline.base.OpportunityList" scope="request"/>
<jsp:useBean id="SourceOpenOpportunities" class="org.aspcfs.modules.pipeline.base.OpportunityList" scope="request"/>
<jsp:useBean id="SourceAssignments" class="com.zeroio.iteam.base.AssignmentList" scope="request"/>
<jsp:useBean id="SourceDocumentStores" class="org.aspcfs.modules.documents.base.DocumentStoreTeamMemberList" scope="request"/>
<jsp:useBean id="SourcePendingActivities" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="SourceInProgressActionLists" class="org.aspcfs.modules.actionlist.base.ActionLists" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<script language="JavaScript">
  function doCheck(form) {
    formTest = true;
    message = "";
    if (form.userId.value == "-1") {
      formTest = false;
      message = label("select.user.toreassign","- User to re-assign from must be selected");
    }
    if (!formTest) {
      alert(label("alert.reassignments","Re-assignments could not be made, please check the following:\r\n\r\n") + message);
    }
    return formTest;
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<dhv:label name="myitems.Re-assignments">Re-assignments</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:formMessage showSpace="false" />
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="Reassignments.do?command=Reassign">
    <td align="left" valign="center">
    <% UserSelectList.setJsEvent("onChange=\"javascript:document.forms['listView'].submit();\""); %>
    <dhv:label name="calendar.userToReassignDataFrom.colon">User to re-assign data from:</dhv:label>&nbsp;<%= UserSelectList.getHtmlSelect("userId", SourceUser.getId()) %>
    <font color="red">*</font>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
<form name="adminReassign" action="Reassignments.do?command=DoReassign" method="post" onSubmit="return doCheck(this);">
  <tr>
    <th nowrap>
      <strong><dhv:label name="calendar.reassign">Re-assign</dhv:label></strong>
    </th>
    <th width="150" nowrap>
      <strong><dhv:label name="calendar.fromUser">From User</dhv:label></strong>
    </th>
    <th width="150" nowrap>
      <strong><dhv:label name="calendar.toUser">To User</dhv:label></strong>
    </th>
  </tr>
<% int rowid = 0; %>
<%-- Accounts --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="calendar.Accounts">Accounts</dhv:label> (<%= SourceAccounts.size() %>)
		</td>
    <td valign="top" nowrap>
    <% if (SourceUser.getId() > -1) { %>
      <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		 <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <% UserList.setExcludeDisabledIfUnselected(true); %>
      <%= UserList.getHtmlSelect("ownerToAccounts") %>
    </td>
  </tr>
<%-- All Contacts --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="actionList.allContacts">All Contacts</dhv:label>
      (<%= SourceAllContacts.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToAllContacts") %>
    </td>
  </tr>
<%-- Public Contacts --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="contacts.generalContacts.brackets.public">General Contacts (Public)</dhv:label>
      (<%= SourcePublicContacts.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToPublicContacts") %>
    </td>
  </tr>
<%-- Controlled Hierarchy Contacts --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="contacts.generalContacts.brackets.controlledHierarchy">General Contacts (Controlled Hierarchy)</dhv:label>
      (<%= SourceHierarchyContacts.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToHierarchyContacts") %>
    </td>
  </tr>
<%-- Account Contacts --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="documents.team.accountContacts">Account Contacts</dhv:label>
      (<%= SourceAccountContacts.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToAccountContacts") %>
    </td>
  </tr>
<%-- Leads --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="sales.leads">Leads</dhv:label>
      (<%= SourceLeads.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToLeads") %>
    </td>
  </tr>
<%-- Opportunities being Managed(Open) --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="calendar.opportunitiesBeingManaged.openOnly.brackets">Opportunities being Managed (Open Only)</dhv:label> (<%= ManagingOpenOpportunities.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("managerToOpenOpps") %>
    </td>
  </tr>
<%-- Opportunities (Open) --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="calendar.opportunities.openOnly.brackets">Opportunities (Open Only)</dhv:label> (<%= SourceOpenOpportunities.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToOpenOpps") %>
    </td>
  </tr>
<%-- Opportunities (Open & Closed) --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="calendar.opportunities.openAndClosed.brackets" param="amp=&amp;">Opportunities (Open &amp; Closed)</dhv:label> (<%= SourceOpportunities.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToOpenClosedOpps") %>
    </td>
  </tr>
<%-- Project Activities --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="calendar.projectActivities.incomplete.brackets">Project Activities (Incomplete)</dhv:label> (<%= SourceAssignments.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToActivities") %>
    </td>
  </tr>
<%-- Revenue --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="calendar.accountRevenue">Account Revenue</dhv:label> (<%= SourceRevenue.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToRevenue") %>
    </td>
  </tr>
<%-- Tickets (Open) --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="calendar.tickets.open.brackets">Tickets (Open)</dhv:label> (<%= SourceOpenTickets.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToOpenTickets") %>
    </td>
  </tr>
<%-- Incomplete Ticket Tasks --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="ticket.ticketTask.incomplete.bracket">Ticket Tasks (Incomplete)</dhv:label> (<%= IncompleteTicketTasks.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToIncompleteTicketTasks") %>
    </td>
  </tr>
<%-- Document Stores (Open) --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <%--<dhv:label name="tickets.tickets">Document Stores</dhv:label> (Open) (<%= SourceDocumentStores.size() %>)--%>
      <dhv:label name="calendar.documentStores">Document Stores</dhv:label> (<%= SourceDocumentStores.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToOpenDocumentStores") %>
    </td>
  </tr>
<%-- Users --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="calendar.users.reportingTo">Users Reporting-to</dhv:label> (<%= SourceUsers.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <table cellpadding="0" cellspacing="0" border="0" width="100%" class="empty">
        <tr><td align="left"><%= UserList.getHtmlSelect("ownerToUsers") %></td>
        <td><%= showAttribute(request, "managerIdError") %></td></tr>
      </table>
    </td>
  </tr>  
<%-- Activities (Pending) --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="accounts.accounts_contacts_calls_list.PendingActivities">Pending Activities</dhv:label> (<%= SourcePendingActivities.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToPendingActivities") %>
    </td>
  </tr>
<%-- In Progress Action Lists --%>
<% rowid = (rowid != 1?1:2); %>
  <tr class="row<%= rowid %>">
		<td valign="top">
      <dhv:label name="actionlists.actionListsInProgress">Action Lists (In Progress)</dhv:label> (<%= SourceInProgressActionLists.size() %>)
		</td>
    <td valign="top" nowrap>
		<% if (SourceUser.getId() > -1) { %>
		  <%= SourceUser.getContact().getNameLastFirst() %>
    <%} else {%>
		  <dhv:label name="accounts.accounts_add.NoneSelected">None Selected</dhv:label>
    <%}%>
    </td>
		<td valign="top" nowrap>
      <%= UserList.getHtmlSelect("ownerToActionLists") %>
    </td>
  </tr>
</table>
&nbsp;<br>
<dhv:permission name="myhomepage-reassign-edit">
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" />
<input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='MyCFS.do?command=Home'" />
<input type="hidden" name="userId" value="<%= SourceUser.getId() %>" />
</dhv:permission>
</form>
</body>
