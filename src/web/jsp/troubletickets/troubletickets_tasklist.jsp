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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.tasks.base.*, org.aspcfs.modules.base.EmailAddress, org.aspcfs.modules.base.PhoneNumber" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="TaskList" class="org.aspcfs.modules.tasks.base.TaskList" scope="request"/>
<jsp:useBean id="TicketTaskListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="troubletickets_tasklist_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
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
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="TroubleTickets.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View Tickets</dhv:label></a> >
<%}%>
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>"><dhv:label name="tickets.details">Ticket Details</dhv:label></a> >
<dhv:label name="myitems.tasks">Tasks</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="tasks" object="TicketDetails" param="<%= param1 %>">
  <%@ include file="ticket_header_include.jsp" %>
  <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>">
    <dhv:permission name="tickets-tickets-tasks-add">
      <a href="javascript:popURL('TroubleTicketTasks.do?command=Add&ticketId=<%= TicketDetails.getId() %>&popup=true','Task','600','425','yes','yes');"><dhv:label name="quickactions.addTask">Add a Task</dhv:label></a><br><br>
    </dhv:permission>
  </dhv:evaluate>
  <%-- include the tasks created --%>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th align="center" nowrap>
        &nbsp;
      </th>
      <th align="center" nowrap>
        <strong><a href="TroubleTicketTasks.do?command=List&ticketId=<%= TicketDetails.getId() %>&column=t.priority"><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></a></strong>
        <%= TicketTaskListInfo.getSortIcon("t.priority") %>
      </th>
      <th width="100%" nowrap>
        <strong><a href="TroubleTicketTasks.do?command=List&ticketId=<%= TicketDetails.getId() %>&column=t.description"><dhv:label name="ticket.task">Task</dhv:label></a></strong>
        <%= TicketTaskListInfo.getSortIcon("t.description") %>
      </th>
      <th align="center" nowrap>
        <strong><dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label></strong>
      </th>
      <th align="center" nowrap>
        <strong><a href="TroubleTicketTasks.do?command=List&ticketId=<%= TicketDetails.getId() %>&column=t.duedate"><dhv:label name="accounts.accounts_calls_list.DueDate">Due Date</dhv:label></a></strong>
        <%= TicketTaskListInfo.getSortIcon("t.duedate") %>
      </th>
      <th align="center" nowrap>
        <strong><a href="TroubleTicketTasks.do?command=List&ticketId=<%= TicketDetails.getId() %>&column=t.completedate"><dhv:label name="ticket.task.completeDate">Complete Date</dhv:label></a></strong>
        <%= TicketTaskListInfo.getSortIcon("t.completedate") %>
      </th>
      <th align="center" nowrap>
        <strong><a href="TroubleTicketTasks.do?command=List&ticketId=<%= TicketDetails.getId() %>&column=t.entered"><dhv:label name="ticket.age">Age</dhv:label></a></strong>
        <%= TicketTaskListInfo.getSortIcon("t.entered") %>
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
        <%-- <a href="javascript:window.location.href='MyTasksForward.do?command=ForwardMessage&forwardType=<%= Constants.TASKS %>&id=<%=thisTask.getId()%>&return=' + escape('MyTasks.do?command=ListTasks') + '&sendUrl='+ escape('MyTasksForward.do?command=SendMessage');">Fwd</a>|--%>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= count %>','menuTask', '<%= TicketDetails.getId() %>', '<%= thisTask.getId() %>','<%= TicketDetails.isTrashed() %>');"
       onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuTask');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
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
        <dhv:permission name="tickets-tickets-tasks-edit">
          <td>
        <% boolean hasAuthority = false; %>
        <dhv:hasAuthority owner="<%= thisTask.getOwner() %>">
        <% hasAuthority = true; %>
        </dhv:hasAuthority>
        <%
          if (thisTask.getComplete()) {
        %>
          <dhv:evaluate if="<%= hasAuthority %>">
            <a href="javascript:changeCurrentImages('image<%= count %>','MyTasks.do?command=ProcessImage&return=list&id=box.gif|gif|'+<%= thisTask.getId() %>+'|0','MyTasks.do?command=ProcessImage&return=list&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;">
            <img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change"></a>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !hasAuthority %>">
            <a href="javascript:alert(label('status.change.requirement','Status can be changed only by the user who the task is assigned to'));" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;">
            <img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change"></a>
          </dhv:evaluate>
        <%
           } else {
        %>
          <dhv:evaluate if="<%= hasAuthority %>">
            <a href="javascript:changeCurrentImages('image<%= count %>','MyTasks.do?command=ProcessImage&return=list&id=box.gif|gif|'+<%= thisTask.getId() %>+'|0','MyTasks.do?command=ProcessImage&return=list&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;">
            <img src="images/box.gif" name="image<%= count %>" id="0" border="0" title="Click to change"></a>
          </dhv:evaluate>
          <dhv:evaluate if="<%= !hasAuthority %>">
            <a href="javascript:alert(label('status.change.requirement','Status can be changed only by the user who the task is assigned to'));" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box.gif" name="image<%= count %>" id="0" border="0" title="Click to change"></a>
          </dhv:evaluate>
          <%
            }
          %>
          </td>
          </dhv:permission>
          <td valign="top">
  <%
        if (thisTask.getHasLinks()) {
  %>
            <a href="javascript:changeImages('detailsimage<%= count %>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle('taskdetails<%=count%>','style','span');"><img src="images/arrowright.gif" name="detailsimage<%=count%>" id="1" border="0" title="Click To View Details"></a>
  <%
        }
  %>
          </td>
          <td valign="top">
            <a href="javascript:popURL('TroubleTicketTasks.do?command=Details&ticketId=<%= TicketDetails.getId() %>&id=<%= thisTask.getId() %>&popup=true','CRM_Task','600','425','yes','yes');"><%= toHtml(thisTask.getDescription()) %></a>&nbsp;
<% if(thisTask.getContactId()!=-1) {%>
  <% if(!thisTask.getContact().getEmployee()) {%>
  [<a href="ExternalContacts.do?command=ContactDetails&id=<%= thisTask.getContact().getId() %>" title="<%= thisTask.getContact().getNameFull() %>"><font color="green"><dhv:label name="admin.contact.abbreviation">Contact</dhv:label></font></a>]
  <%} else {%>
  [<a href="CompanyDirectory.do?command=EmployeeDetails&empid=<%= thisTask.getContact().getId() %>" title="<%= thisTask.getContact().getNameFull() %>"><font color="green"><dhv:label name="admin.employee.abbreviation">Employee</dhv:label></font></a>]
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
                    <table cellpadding="4" cellspacing="0" class="empty"><tr><td valign="top"><li>&nbsp;<dhv:label name="account.name.colon">Name:</dhv:label></li></td>
                    <td align="left">&nbsp;<%= thisTask.getContact().getNameFull() %></td></tr></table>
                  </td>
                </tr>
                <tr>
                  <td><table cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
                    <li>&nbsp;<dhv:label name="account.emails.colon">Email(s):</dhv:label></li>
                    </td><td><table cellpadding="0" cellspacing="0" class="empty">
  <%
                    Iterator i = thisTask.getContact().getEmailAddressList().iterator();
                    while (i.hasNext()) {
                      EmailAddress thisAddress = (EmailAddress)i.next(); %>
                      <tr><td>
                        &nbsp;<%= toHtml(thisAddress.getEmail()) %>(<%= thisAddress.getTypeName() %>)&nbsp;&nbsp;
                      </td></tr>
                    <%}%>
                    </table>
                    </td></tr></table>
                  </td>
                </tr>
                <tr>
                  <td><table cellpadding="0" cellspacing="0" class="empty"><tr><td valign="top">
                    <li>&nbsp;<dhv:label name="account.phones.colon">Phone(s):</dhv:label></li>
                    </td><td><table cellpadding="0" cellspacing="0" class="empty">
                    <%
                      i = thisTask.getContact().getPhoneNumberList().iterator();
                      while (i.hasNext()) {
                        PhoneNumber phoneNumber = (PhoneNumber)i.next(); %>
                        <tr><td>
                        &nbsp;<%= phoneNumber.getPhoneNumber()%>(<%=phoneNumber.getTypeName()%>)<br />
                        </td></tr>
                    <%}%>
                     </table></td></tr></table>
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
        <% if(!User.getTimeZone().equals(thisTask.getDueDateTimeZone())){%>
        <zeroio:tz timestamp="<%= thisTask.getDueDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } else { %>
        <zeroio:tz timestamp="<%= thisTask.getDueDate() %>" dateOnly="true" timeZone="<%= thisTask.getDueDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
        <td nowrap align="center" valign="top">
          <zeroio:tz timestamp="<%= thisTask.getCompleteDate() %>" dateOnly="true" default="-NA-" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
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
  <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
</dhv:container>
