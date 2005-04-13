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
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>

<dhv:evaluate if="<%= ticket.getId() > -1 %>">
<input type="hidden" name="projectTicketCount" value="<%=ticket.getProjectTicketCount()%>" />
  <br>
  <strong><dhv:label name="tickets.symbol.number" param="<%= "number="+ticket.getProjectTicketCount() %>">Ticket #<%= ticket.getProjectTicketCount() %></dhv:label></strong>
  <dhv:evaluate if="<%= ticket.getClosed() != null %>">
    (<font color="red"><dhv:label name="project.ticketClosedOn" param="<%= "time="+toHtml(ticket.getClosedString()) %>">This ticket was closed on <%= toHtml(ticket.getClosedString()) %></dhv:label></font>)
  </dhv:evaluate>
  <dhv:evaluate if="<%= ticket.getClosed() == null %>">
    (<font color="green"><dhv:label name="quotes.open">Open</dhv:label></font>)
  </dhv:evaluate>
  <br>
</dhv:evaluate>
&nbsp;<br>
<%--
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
		<strong>Ticket Information</strong>
		</th>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Ticket Source
		</td>
		<td>
      <%= SourceList.getHtmlSelect("sourceCode",  ticket.getSourceCode()) %>
		</td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Organization">Organization</dhv:label>
    </td>
    <td>
      <%= toHtml(ticket.getCompanyName()) %>
    </td>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Contact
		</td>
    <td>
      if ( ticket.getThisContact() == null ) {
         ContactList.getHtmlSelect("contactId", 0 ) 
      } else {
        ContactList.getHtmlSelect("contactId", ticket.getContactId() ) 
      }
      <font color="red">*</font> <%= showAttribute(request, "contactIdError") %>
		</td>
  </tr>
</table>
<br>
--%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong><dhv:label name="accounts.accounts_add.Classification">Classification</dhv:label></strong>
		</th>
  </tr>
  <tr class="containerBody">
		<td class="formLabel" valign="top">
      <dhv:label name="accounts.accounts_asset_history.Issue">Issue</dhv:label>
    </td>
		<td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"><%= toString(ticket.getProblem()) %></textarea>
          </td>
          <td valign="top">
            <font color="red">*</font> <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
		</td>
  </tr>
<%--
	<tr class="containerBody">
		<td class="formLabel">
      Category
		</td>
		<td>
      <%= CategoryList.getHtmlSelect("catCode", ticket.getCatCode()) %>
<% if (ticket.getCatCode() == 0) { %>
			<input type="checkbox" name="newCat0chk" onClick="javascript:showSpan('new0')">add new<span name="new0" ID="new0" style="display:none">&nbsp;<input type="text" size="25" name="newCat0"></span>
<%}%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      <dhv:label name="account.ticket.subLevel1">Sub-level 1</dhv:label>
		</td>
    <td>
      <%= SubList1.getHtmlSelect("subCat1", ticket.getSubCat1()) %>
<% if (ticket.getCatCode() != 0 && ticket.getSubCat1() == 0) { %>
			<input type="checkbox" name="newCat1chk" onClick="javascript:showSpan('new1')">add new<span name="new1" ID="new1" style="display:none">&nbsp;<input type="text" size="25" name="newCat1"></span>
<%}%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      <dhv:label name="account.ticket.subLevel2">Sub-level 2</dhv:label>
		</td>
		<td>
      <%= SubList2.getHtmlSelect("subCat2", ticket.getSubCat2()) %>
<% if (ticket.getSubCat1() != 0 && ticket.getCatCode() != 0 && ticket.getSubCat2() == 0) { %>
			<input type="checkbox" name="newCat2chk" onClick="javascript:showSpan('new2')">add new<span name="new2" ID="new2" style="display:none">&nbsp;<input type="text" size="25" name="newCat2"></span>
<%}%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      <dhv:label name="account.ticket.subLevel3">Sub-level 3</dhv:label>
		</td>
		<td>
      <%= SubList3.getHtmlSelect("subCat3", ticket.getSubCat3()) %>
<% if (ticket.getSubCat2() != 0 && ticket.getCatCode() != 0 && ticket.getSubCat1() != 0) { %>
			<input type="checkbox" name="newCat3chk" onClick="javascript:showSpan('new3')">add new<span name="new3" ID="new3" style="display:none">&nbsp;<input type="text" size="25" name="newCat3"></span>
<%}%>
		</td>
  </tr>
--%>
  <tr class="containerBody">
		<td class="formLabel">
      <dhv:label name="project.severity">Severity</dhv:label>
		</td>
		<td>
      <%= SeverityList.getHtmlSelect("severityCode", ticket.getSeverityCode()) %>
		</td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong><dhv:label name="project.assignment">Assignment</dhv:label></strong>
		</th>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label>
		</td>
		<td>
      <%= PriorityList.getHtmlSelect("priorityCode", ticket.getPriorityCode()) %>
		</td>
  </tr>
<%--
	<tr class="containerBody">
		<td class="formLabel">
      Department
		</td>
		<td>
      <%= DepartmentList.getHtmlSelect("departmentCode", ticket.getDepartmentCode()) %>
		</td>
  </tr>
--%>
<zeroio:permission name="project-tickets-assign">
	<tr class="containerBody">
		<td nowrap class="formLabel">
      <dhv:evaluate if="<%= ticket.getId() == -1 %>"><dhv:label name="actionList.assignTo">Assign To</dhv:label></dhv:evaluate>
      <dhv:evaluate if="<%= ticket.getId() > -1 %>"><dhv:label name="accounts.accounts_revenue_modify.ReassignTo">Reassign To</dhv:label></dhv:evaluate>
		</td>
		<td valign="center">
<%
    TeamMemberList thisTeam = Project.getTeam();
    HtmlSelect team = new HtmlSelect();
    team.addItem(-1, "-- None Selected --");
    Iterator iTeam = thisTeam.iterator();
    while (iTeam.hasNext()) {
      TeamMember thisMember = (TeamMember) iTeam.next();
      User thisUser = (User)thisMember.getUser();
      if (thisUser.getEnabled() && !(thisUser.getExpires() != null && thisUser.getExpires().before(new Timestamp(Calendar.getInstance().getTimeInMillis())))) {
        team.addItem(thisMember.getUserId(), 
           ((User) thisMember.getUser()).getContact().getNameLastFirst());
      }
    }
%>
      <%= team.getHtml("assignedTo", ticket.getAssignedTo()) %>
		</td>
  </tr>
</zeroio:permission>
<zeroio:permission name="project-tickets-assign" if="none">
  <input type="hidden" name="assignedTo" value="<%= ticket.getAssignedTo() %>" />
</zeroio:permission>
	<tr class="containerBody">
		<td class="formLabel" valign="top">
      Estimated Resolution Date
    </td>
		<td>
      <zeroio:dateSelect form="ticketForm" field="estimatedResolutionDate" timestamp="<%= ticket.getEstimatedResolutionDate() %>" />
      <dhv:label name="project.at">at</dhv:label>
      <zeroio:timeSelect baseName="estimatedResolutionDate" value="<%= ticket.getEstimatedResolutionDate() %>" timeZone="<%= ticket.getResolutionDateTimeZone() %>" showTimeZone="true" />
      <%= showAttribute(request, "estimatedResolutionDateError") %>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel" valign="top">
      <dhv:label name="project.userComments">User Comments</dhv:label>
    </td>
		<td>
      <textarea name="comment" cols="55" rows="5"><%= toString(ticket.getComment()) %></textarea>
		</td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th colspan="2">
      <strong>Resolution</strong>
		</th>
  </tr>
	<tr class="containerBody">
		<td class="formLabel" valign="top">
      <dhv:label name="project.ticketSolution">Solution</dhv:label>
		</td>
		<td>
      <textarea name="solution" cols="55" rows="8"><%= toString(ticket.getSolution()) %></textarea><%= showAttribute(request, "solutionError") %>
      <br>
      <input type="checkbox" name="closeNow" <%=((ticket.getClosed() != null)? "checked":"")%>>Close ticket <%= showAttribute(request, "closeNowError") %>
<%--
        <br><input type="checkbox" name="kbase">Add this solution to Knowledge Base &nbsp;
--%>
      </td>
		</tr>
</table>

