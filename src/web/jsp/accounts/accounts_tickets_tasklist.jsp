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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.troubletickets.base.*, org.aspcfs.modules.tasks.base.*,org.aspcfs.modules.base.EmailAddress, org.aspcfs.modules.base.Constants, org.aspcfs.modules.base.PhoneNumber" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="TaskList" class="org.aspcfs.modules.tasks.base.TaskList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_tickets_tasklist_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

  function changeCurrentImages(id, one, two) {
    var img = document[id];
    if (img.id == '1') {
      img.id = "0";
      window.frames['server_commands'].location.href = one+'&imageId='+id;
    } else {
      img.id = "1";
      window.frames['server_commands'].location.href = two+'&imageId='+id;
    }
  }

</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="accounts.tickets.tickets">Tickets</dhv:label></a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getId()%>"><dhv:label name="accounts.tickets.details">Ticket Details</dhv:label></a> >
<dhv:label name="myitems.tasks">Tasks</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="tickets" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>">
  <dhv:container name="accountstickets" selected="tasks" object="TicketDetails" param="<%= "id=" + TicketDetails.getId() %>">
      <%@ include file="accounts_ticket_header_include.jsp" %>
      <dhv:permission name="accounts-accounts-tickets-tasks-add">
        <a  href="javascript:popURL('AccountTicketTasks.do?command=Add&orgId=<%= TicketDetails.getOrgId() %>&ticketId=<%= TicketDetails.getId() %>&popup=true','Task','600','425','yes','yes');"><dhv:label name="quickactions.addTask">Add a Task</dhv:label></a><br>
        <br />
      </dhv:permission>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
        <th align="center" nowrap>
          &nbsp;
        </th>
        <th align="center" nowrap>
          <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label>
        </th>
        <th width="100%" nowrap>
          <dhv:label name="ticket.task">Task</dhv:label>
        </th>
        <th align="center" nowrap>
          <dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label>
        </th>
        <th align="center" nowrap>
          <dhv:label name="accounts.accounts_calls_list.DueDate">Due Date</dhv:label>
        </th>
        <th align="center" nowrap>
          <dhv:label name="ticket.task.completeDate">Complete Date</dhv:label>
        </th>
        <th align="center" nowrap>
          <dhv:label name="ticket.age">Age</dhv:label>
        </th>
       </tr>
     <%
      Iterator j = TaskList.iterator();
      if ( j.hasNext() ) {
        int rowid = 0;
        int count = 0;
        while (j.hasNext()) {
          count++;
          rowid = (rowid != 1 ? 1 : 2);
          Task thisTask = (Task) j.next();
    %>
      <tr class="row<%= rowid %>">
        <td align="center" valign="top">
          <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <%-- To display the menu, pass the actionId, accountId and the contactId--%>
          <a href="javascript:displayMenu('select<%= count %>','menuTask',<%= OrgDetails.getId() %>,<%= TicketDetails.getId() %>,'<%= thisTask.getId() %>')" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuTask');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
        </td>
        <td nowrap align="center" valign="top">
          <% if(thisTask.getPriority() != -1) {%>
            <%= toHtml((new Integer(thisTask.getPriority())).toString()) %>
          <%} else {%>
            <dhv:label name="account.na">N/A</dhv:label>
          <%}%>
        </td>
        <td>
        <table cellpadding="0" cellspacing="0" class="empty">
          <tr <%= thisTask.getComplete()?"class=\"strike\"":"class=\"\""%> id="complete<%=count%>">
          <dhv:permission name="accounts-accounts-tickets-tasks-edit">
            <td>
              <% boolean hasAuthority = false; %>
              <dhv:hasAuthority owner="<%= thisTask.getOwner() %>">
                <% hasAuthority = true; %>
              </dhv:hasAuthority>
              <% if (thisTask.getComplete()) { %>
                    <dhv:evaluate if="<%= hasAuthority %>">
                      <a href="javascript:changeCurrentImages('image<%= count %>','MyTasks.do?command=ProcessImage&return=list&id=box.gif|gif|'+<%= thisTask.getId() %>+'|0','MyTasks.do?command=ProcessImage&return=list&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;">
                      <img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change"></a>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !hasAuthority %>">
                      <a href="javascript:alert(label('status.change.requirement','Status can be changed only by the user who the task is assigned to'));" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;">
                      <img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change"></a>
                    </dhv:evaluate>
              <% } else { %>
                      <dhv:evaluate if="<%= hasAuthority %>">
                          <a href="javascript:changeCurrentImages('image<%= count %>','MyTasks.do?command=ProcessImage&return=list&id=box.gif|gif|'+<%= thisTask.getId() %>+'|0','MyTasks.do?command=ProcessImage&return=list&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;">
                <img src="images/box.gif" name="image<%= count %>" id="0" border="0" title="Click to change">
                          </a>
                      </dhv:evaluate>
                      <dhv:evaluate if="<%= !hasAuthority %>">
                        <a href="javascript:alert(label('status.change.requirement','Status can be changed only by the user who the task is assigned to'));" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box.gif" name="image<%= count %>" id="0" border="0" title="Click to change"></a>
                      </dhv:evaluate>
                <% } %>
            </td>
          </dhv:permission>
          <td valign="top">
            <% if (thisTask.getHasLinks()) { %>
              <a href="javascript:changeImages('detailsimage<%= count %>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle('taskdetails<%=count%>','style','span');"><img src="images/arrowright.gif" name="detailsimage<%=count%>" id="1" border="0" title="Click To View Details"></a>
            <% } %>
          </td>
          <td valign="top">
            <a href="javascript:popURL('AccountTicketTasks.do?command=Details&orgId=<%= TicketDetails.getOrgId() %>&ticketId=<%= TicketDetails.getId() %>&id=<%= thisTask.getId() %>&popup=true','CRM_Task','600','425','yes','yes');"><%= toHtml(thisTask.getDescription()) %></a>&nbsp;
            <% if(thisTask.getContactId()!=-1) {%>
              <% if(!thisTask.getContact().getEmployee()) {%>
                [<a href="ExternalContacts.do?command=ContactDetails&id=<%= thisTask.getContact().getId() %>" title="<%= thisTask.getContact().getNameLastFirst() %>"><font color="green"><dhv:label name="admin.contact.abbreviation">C</dhv:label></font></a>]
              <%} else {%>
                [<a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisTask.getContact().getId() %>" title="<%= thisTask.getContact().getNameLastFirst() %>"><font color="green"><dhv:label name="admin.employee.abbreviation">E</dhv:label></font></a>]
              <%}%>
            <%}%>
          </td>
        </tr>
        <% if(thisTask.getHasLinks()){ %>
          <tr style="display:none">
            <td></td>
            <td></td>
            <td>
              <span style="visibility:hidden" id="taskdetails<%= count %>">
                <table class="empty">
                  <tr>
                    <td><dhv:label name="account.contactInformation.colon">Contact Information:</dhv:label> </td>
                  </tr>
                  <ul>
                  <tr>
                    <td>
                      <li>&nbsp;<dhv:label name="account.name.colon">Name:</dhv:label> &nbsp;<%= thisTask.getContact().getNameLastFirst() %></li>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <li>&nbsp;<dhv:label name="account.emails.colon">Email(s):</dhv:label>
                     <%
                      Iterator i = thisTask.getContact().getEmailAddressList().iterator();
                      while (i.hasNext()) {
                        EmailAddress thisAddress = (EmailAddress)i.next(); %>
                        &nbsp;<%=thisAddress.getEmail()%>(<%= thisAddress.getTypeName() %>)&nbsp;&nbsp;
                     <%}%>
                      </li>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <li>&nbsp;<dhv:label name="account.phones.colon">Phone(s):</dhv:label>
                        &nbsp;<% if(!thisTask.getContact().getPhoneNumber("Business").equals("")) {%>
                        <dhv:label name="task.business.abbreviation" param="<%= "number="+thisTask.getContact().getPhoneNumber("Business") %>"><%= thisTask.getContact().getPhoneNumber("Business") %> (W)</dhv:label><br />
                        <%}%>
                        <% if(!thisTask.getContact().getPhoneNumber("Home").equals("")) {%>
                        <dhv:label name="task.home.abbreviation" param="<%= "number="+thisTask.getContact().getPhoneNumber("Home") %>"><%= thisTask.getContact().getPhoneNumber("Home") %> (H)</dhv:label><br />
                        <%}%>
                        <% if(!thisTask.getContact().getPhoneNumber("Mobile").equals("")) {%>
                        <dhv:label name="task.home.abbreviation" param="<%= "number="+thisTask.getContact().getPhoneNumber("Mobile") %>"><%= thisTask.getContact().getPhoneNumber("Mobile") %> (M)</dhv:label><br />
                        <%}%>
                      </li>
                    </td>
                  </tr>
                  </ul>
                </table>
              </span>
            </td>
         </tr>
        <%}%>
        </table>
        </td>
        <td nowrap align="center" valign="top">
          <%if(thisTask.getOwner() > 0){ %>
            <dhv:username id="<%= thisTask.getOwner() %>"/>
          <% }else{ %>
            <dhv:label name="account.na">N/A</dhv:label>
          <%}%>
        </td>
        <td nowrap align="center" valign="top">
          <zeroio:tz timestamp="<%= thisTask.getDueDate() %>" dateOnly="true" default="N/A"/>
        </td>
          <td nowrap align="center" valign="top">
            <zeroio:tz timestamp="<%= thisTask.getCompleteDate() %>" dateOnly="true" default="N/A"/>
          </td>
          <td nowrap align="center" valign="top">
            <%= thisTask.getAgeString() %>
          </td>
        </tr>
        <%
          }
        } else {
       %>
          <tr>
            <td class="containerBody" colspan="7" valign="center">
                 <font color="#9E9E9E"><dhv:label name="ticket.task.noTaskEntries">No Task Entries.</dhv:label></font>
            </td>
          </tr>
      <%}%>
    </table>
  </dhv:container>
</dhv:container>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

