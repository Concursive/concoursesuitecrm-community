<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.troubletickets.base.*, org.aspcfs.modules.tasks.base.*,org.aspcfs.modules.base.EmailAddress, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="TaskList" class="org.aspcfs.modules.tasks.base.TaskList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<%@ include file="../initPage.jsp" %>
<form name="details" action="AccountTickets.do?command=ModifyTicket&auto-populate=true" method="post">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a> >
Ticket Details<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%=toHtml(TicketDetails.getCompanyName())%> - Ticket # <%=TicketDetails.getPaddedId()%></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + TicketDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="tickets" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
  	<td class="containerBack">
<% if (TicketDetails.getClosed() != null) { %>
      <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Reopen" onClick="javascript:this.form.action='AccountTickets.do?command=ReopenTicket&id=<%=TicketDetails.getId()%>';submit();"> <font color="red">This ticket was closed on <%=toHtml(TicketDetails.getClosedString())%></font></dhv:permission>
<%} else {%>
      <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='AccountTickets.do?command=ModifyTicket&id=<%=TicketDetails.getId()%>';submit();"></dhv:permission>
      <dhv:permission name="accounts-accounts-tickets-delete"><input type="button" value="Delete" onClick="javascript:popURL('AccountTickets.do?command=ConfirmDelete&orgId=<%= TicketDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','320','200','yes','no');"></dhv:permission>
<%}%>
<dhv:permission name="accounts-accounts-tickets-edit,accounts-accounts-tickets-delete"><br>&nbsp;</dhv:permission>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
		<td colspan="2">
      <strong>Ticket Information</strong>
		</td>     
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Ticket Source
		</td>
		<td>
      <%= toHtml(TicketDetails.getSourceName()) %>
		</td>
  </tr>
	<tr class="containerBody">
		<td valign="top" class="formLabel">
      <dhv:label name="tickets-problem">Issue</dhv:label>
    </td>
		<td>
      <%= toHtml(TicketDetails.getProblem()) %>
      <input type="hidden" name="problem" value="<%= toHtml(TicketDetails.getProblem()) %>">
      <input type="hidden" name="orgId" value="<%= TicketDetails.getOrgId() %>">
      <input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
		</td>
  </tr>
<dhv:include name="tickets-code" none="true">
	<tr class="containerBody">
		<td class="formLabel">
      Category
		</td>
		<td>
      <%=toHtml(TicketDetails.getCategoryName())%>
		</td>
  </tr>
</dhv:include>
<dhv:include name="tickets-severity" none="true">
	<tr class="containerBody">
		<td class="formLabel">
      Severity
    </td>
		<td>
      <%=toHtml(TicketDetails.getSeverityName())%>
		</td>
  </tr>
</dhv:include>
<dhv:include name="tickets-priority" none="true">
  <tr class="containerBody">
		<td class="formLabel">
      Priority
    </td>
		<td>
      <%=toHtml(TicketDetails.getPriorityName())%>
		</td>
  </tr>
</dhv:include>
	<tr class="containerBody">
		<td class="formLabel">
      Department
		</td>
		<td>
      <%= toHtml(TicketDetails.getDepartmentName()) %>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Assigned To
		</td>
		<td>
      <%= toHtml(TicketDetails.getOwnerName()) %>
      <dhv:evaluate exp="<%= !(TicketDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Solution
		</td>
		<td>
      <%= toHtml(TicketDetails.getSolution()) %>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Entered
    </td>
		<td>
      <%=toHtml(TicketDetails.getEnteredByName())%> - <%=TicketDetails.getEnteredString()%>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Modified
    </td>
		<td>
      <%=toHtml(TicketDetails.getModifiedByName())%> - <%=TicketDetails.getModifiedString()%>
		</td>
  </tr>
</table>
&nbsp;
<%
  if (TicketDetails.getThisContact() != null ) {
%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="4">
      <strong>Primary Contact</strong>
		</td>     
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Name
		</td>
		<td>
      <%= toHtml(TicketDetails.getThisContact().getNameLastFirst()) %>
		</td>
  </tr>
	<tr class="containerBody">
		<td class="formLabel">
      Title
		</td>
		<td>
      <%=toHtml(TicketDetails.getThisContact().getTitle())%>
		</td>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Email
		</td>
		<td>
      <%= TicketDetails.getThisContact().getEmailAddressTag("Business", toHtml(TicketDetails.getThisContact().getEmailAddress("Business")), "&nbsp;") %>
		</td>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Phone
		</td>
		<td>
      <%= toHtml(TicketDetails.getThisContact().getPhoneNumber("Business")) %>
		</td>
  </tr>
</table>
&nbsp;
<%}%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
		<td colspan="4">
      <strong>Ticket Log History</strong>
		</td>     
  </tr>
<%  
		Iterator hist = TicketDetails.getHistory().iterator();
		if (hist.hasNext()) {
			while (hist.hasNext()) {
				TicketLog thisEntry = (TicketLog)hist.next();
%>    
<% if (thisEntry.getSystemMessage() == true) {%>
  <tr bgColor="#F1F0E0">
<% } else { %>
  <tr class="containerBody">
<%}%>
    <td nowrap valign="top" class="formLabel">
      <%= toHtml(thisEntry.getEnteredByName()) %>
    </td>
    <td nowrap valign="top">
      <%= thisEntry.getEnteredString() %>
    </td>
    <td valign="top" width="100%">
			<%= toHtml(thisEntry.getEntryText()) %>
    </td>
  </tr>
<%    
    }
  } else {
%>
  <tr class="containerBody">
    <td>
      <font color="#9E9E9E" colspan="3">No Log Entries.</font>
    </td>
  </tr>
<%}%>
</table>
&nbsp;

<%-- include the tasks created --%>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
   <tr class="title">
   <td  colspan="7">
      <strong>Tasks created for the Ticket</strong> [<a  href="javascript:popURL('AccountTicketTasks.do?command=Add&orgId=<%= TicketDetails.getOrgId() %>&ticketId=<%= TicketDetails.getId() %>&popup=true','Task','600','425','yes','yes');">Add a Task</a>]
   </td>
   </tr>
   <tr class="title">
    <td align="center" nowrap>
      <strong>Action</strong>
    </td>
    <td align="center" nowrap>
      <strong>Priority</strong>
    </td>
     <td width="100%" nowrap>
      <strong>Task</strong>
    </td>
    <td align="center" nowrap>
      <strong>Assigned To</strong>
    </td>
    <td align="center" nowrap>
      <strong>Due Date</strong>
    </td>
    <td align="center" nowrap>
      <strong>Complete Date</strong>
    </td>
    <td align="center" nowrap>
      <strong>Age</strong>
    </td>
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
      <%-- <a href="javascript:window.location.href='MyTasksForward.do?command=ForwardMessage&forwardType=<%= Constants.TASKS %>&id=<%=thisTask.getId()%>&return=' + escape('MyTasks.do?command=ListTasks') + '&sendUrl='+ escape('MyTasksForward.do?command=SendMessage');">Fwd</a>|--%><a href="javascript:popURL('AccountTicketTasks.do?command=ConfirmDelete&id=<%= thisTask.getId() %>&popup=true', 'Delete_task','320','200','yes','no');">Del</a>
    </td>
    <td nowrap align="center" valign="top">
      <%= thisTask.getPriority() == -1 ? "-NA-" : (new Integer(thisTask.getPriority())).toString() %>
    </td>
    <td>
    <table cellpadding="0" cellspacing="0">
      <tr <%= thisTask.getComplete()?"class=\"strike\"":"class=\"\""%> id="complete<%=count%>">
        <td>
<% 
      if (thisTask.getComplete()) {
%>
          <a href="javascript:changeImages('image<%= count %>','MyTasks.do?command=ProcessImage&id=box.gif|gif|'+<%= thisTask.getId() %>+'|0','MyTasks.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');" onMouseOver="this.style.color='blue';window.status='View Details';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/box-checked.gif" name="image<%= count %>" id="1" border="0" title="Click to change"></a>
<% 
      } else {
%>
          <a href="javascript:changeImages('image<%= count %>','MyTasks.do?command=ProcessImage&id=box.gif|gif|'+<%= thisTask.getId() %>+'|1','MyTasks.do?command=ProcessImage&id=box-checked.gif|gif|'+<%= thisTask.getId() %>+'|1');javascript:switchClass('complete<%=count%>');"><img src="images/box.gif" name="image<%= count %>" id="0" border="0" title="Click to change"></a>
<%
      }
%>
        </td>
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
          <a href="javascript:popURL('AccountTicketTasks.do?command=Modify&orgId=<%= TicketDetails.getOrgId() %>&ticketId=<%= TicketDetails.getId() %>&id=<%= thisTask.getId() %>&popup=true','CFS_Task','600','425','yes','yes');"><%= thisTask.getDescription()!=null?thisTask.getDescription():"" %></a>&nbsp; <%=(thisTask.getContactId()==-1)?"":"[<a href=\"ExternalContacts.do?command=ContactDetails&id="+ thisTask.getContact().getId() +"\" title=\""+ thisTask.getContact().getNameLastFirst() +"\"><font color=\"green\">C</font></a>]"%>
        </td>
      </tr>
      <% if(thisTask.getHasLinks()){ %>
      <tr style="display:none">
        <td></td>
        <td></td>
        <td>
          <span style="visibility:hidden" id="taskdetails<%= count %>">
            <table>
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
      <%= thisTask.getDueDateString().equals("")?"-NA-":thisTask.getDueDateString() %>
    </td>
      <td nowrap align="center" valign="top">
        <%= thisTask.getCompleteDateString().equals("")?"-NA-":thisTask.getCompleteDateString() %>
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
</table>

&nbsp;
<dhv:permission name="accounts-accounts-tickets-edit,accounts-accounts-tickets-delete"><br></dhv:permission>
<% if (TicketDetails.getClosed() != null) { %>
      <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Reopen" onClick="javascript:this.form.action='AccountTickets.do?command=ReopenTicket&id=<%=TicketDetails.getId()%>';submit();"></dhv:permission>
<%} else {%>
      <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='AccountTickets.do?command=ModifyTicket&id=<%=TicketDetails.getId()%>';submit();"></dhv:permission>
      <dhv:permission name="accounts-accounts-tickets-delete"><input type="button" value="Delete" onClick="javascript:popURL('AccountTickets.do?command=ConfirmDelete&orgId=<%=TicketDetails.getOrgId()%>&id=<%=TicketDetails.getId()%>&popup=true', 'Delete_ticket','320','200','yes','no');"></dhv:permission>
<%}%>
</td>
</tr>
</table>
</form>
