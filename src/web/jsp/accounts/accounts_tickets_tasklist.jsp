<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.troubletickets.base.*, org.aspcfs.modules.tasks.base.*,org.aspcfs.modules.base.EmailAddress, org.aspcfs.modules.base.Constants" %>
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
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getId()%>">Ticket Details</a> >
Tasks
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="tickets" param="<%= "orgId=" + TicketDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
  	<td class="containerBack">
        <%@ include file="accounts_ticket_header_include.jsp" %>
        <% String param2 = "id=" + TicketDetails.getId(); %>
        [ <dhv:container name="accountstickets" selected="tasks" param="<%= param2 %>"/> ]
        <br>
        <dhv:permission name="accounts-accounts-tickets-tasks-add">
        <br>
        <a  href="javascript:popURL('AccountTicketTasks.do?command=Add&orgId=<%= TicketDetails.getOrgId() %>&ticketId=<%= TicketDetails.getId() %>&popup=true','Task','600','425','yes','yes');">Add a Task</a><br>
        </dhv:permission>
        &nbsp;<br>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
          <tr>
          <th align="center" nowrap>
            <strong>Action</strong>
          </th>
          <th align="center" nowrap>
            <strong>Priority</strong>
          </th>
          <th width="100%" nowrap>
            <strong>Task</strong>
          </th>
          <th align="center" nowrap>
            <strong>Assigned To</strong>
          </th>
          <th align="center" nowrap>
            <strong>Due Date</strong>
          </th>
          <th align="center" nowrap>
            <strong>Complete Date</strong>
          </th>
          <th align="center" nowrap>
            <strong>Age</strong>
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
            <%= thisTask.getPriority() == -1 ? "-NA-" : (new Integer(thisTask.getPriority())).toString() %>
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
        <%
           if (thisTask.getComplete()) {
        %>
              <dhv:evaluate if="<%= hasAuthority %>">
                <a href="javascript:changeImages('image<%= count %>','MyTasks.do?command=ProcessImage&id=box.gif|gif|'+<%= thisTask.getId() %>+'|0','MyTasks.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;">
                <img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change"></a>
              </dhv:evaluate>
              <dhv:evaluate if="<%= !hasAuthority %>">
                <a href="javascript:alert('Status can be changed only by the user who the task is assigned to');" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;">
                <img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change"></a>
              </dhv:evaluate>
        <% 
            } else {
        %>
                <dhv:evaluate if="<%= hasAuthority %>">
                    <a href="javascript:changeImages('image<%= count %>','MyTasks.do?command=ProcessImage&id=box.gif|gif|'+<%= thisTask.getId() %>+'|1','MyTasks.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box.gif" name="image<%= count %>" id="0" border="0" title="Click to change">
                    </a>
                </dhv:evaluate>
                <dhv:evaluate if="<%= !hasAuthority %>">
                  <a href="javascript:alert('Status can be changed only by the user who the task is assigned to');" onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box.gif" name="image<%= count %>" id="0" border="0" title="Click to change"></a>
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
                <a href="javascript:popURL('AccountTicketTasks.do?command=Details&orgId=<%= TicketDetails.getOrgId() %>&ticketId=<%= TicketDetails.getId() %>&id=<%= thisTask.getId() %>&popup=true','CRM_Task','600','425','yes','yes');"><%= toHtml(thisTask.getDescription()) %></a>&nbsp; <%=(thisTask.getContactId()==-1)?"":"[<a href=\"ExternalContacts.do?command=ContactDetails&id="+ thisTask.getContact().getId() +"\" title=\""+ thisTask.getContact().getNameLastFirst() +"\"><font color=\"green\">C</font></a>]"%>
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
                      <td>Contact Information: </td>
                    </tr>
                    <ul>
                    <tr>
                      <td>
                        <li>&nbsp;Name: &nbsp;<%= thisTask.getContact().getNameLastFirst() %></li>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <li>&nbsp;Email(s):
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
                        <li>&nbsp;Phone(s): 
                          &nbsp;<%= thisTask.getContact().getPhoneNumber("Business").equals("")?"":thisTask.getContact().getPhoneNumber("Business")+" (W)<br>"%><%= thisTask.getContact().getPhoneNumber("Home").equals("")?"":thisTask.getContact().getPhoneNumber("Home")+" (H)<br>"%><%=thisTask.getContact().getPhoneNumber("Mobile").equals("")?"":thisTask.getContact().getPhoneNumber("Mobile")+" (M)<br>"%>
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
              -NA-
            <%}%>
          </td>
          <td nowrap align="center" valign="top">
            <zeroio:tz timestamp="<%= thisTask.getDueDate() %>" dateOnly="true" default="-NA-"/>
          </td>
            <td nowrap align="center" valign="top">
              <zeroio:tz timestamp="<%= thisTask.getCompleteDate() %>" dateOnly="true" default="-NA-"/>
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
                   <font color="#9E9E9E">No Task Entries.</font>
              </td>
            </tr>
        <%}%>
         <%-- ticket container end --%>
      </table>
    </td>
  </tr>
</table>
