<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.troubletickets.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<%@ include file="../initPage.jsp" %>
<form name="details" action="AccountTickets.do?command=ModifyTicket&auto-populate=true" method="post">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a> >
Ticket Details<br>
<hr color="#BFBFBB" noshade>
<%-- Begin container --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + TicketDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="tickets" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
  	<td class="containerBack">
      <%-- Begin container content --%>
        <% String param2 = "id=" + TicketDetails.getId(); %>
        <strong>Ticket # <%= TicketDetails.getPaddedId() %>:</strong>
        [ <dhv:container name="accountstickets" selected="details" param="<%= param2 %>"/> ]
        <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
          <br>
          <font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font>
        </dhv:evaluate>
      <br>
      <br>
       <% if (TicketDetails.getClosed() != null) { %>
              <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Reopen" onClick="javascript:this.form.action='AccountTickets.do?command=ReopenTicket&id=<%=TicketDetails.getId()%>';submit();"> </dhv:permission>
        <%} else {%>
              <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='AccountTickets.do?command=ModifyTicket&id=<%=TicketDetails.getId()%>';submit();"></dhv:permission>
              <dhv:permission name="accounts-accounts-tickets-delete"><input type="button" value="Delete" onClick="javascript:popURL('AccountTickets.do?command=ConfirmDelete&orgId=<%= TicketDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','320','200','yes','no');"></dhv:permission>
        <%}%>
        <dhv:permission name="accounts-accounts-tickets-edit,accounts-accounts-tickets-delete"><br>&nbsp;</dhv:permission>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
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
              <dhv:username id="<%= TicketDetails.getAssignedTo() %>" default="-- unassigned --"/>
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
              <dhv:username id="<%= TicketDetails.getEnteredBy() %>"/>
              -
              <%= TicketDetails.getEnteredString() %>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              Modified
            </td>
            <td>
              <dhv:username id="<%= TicketDetails.getModifiedBy() %>"/>
              -
              <%= TicketDetails.getModifiedString() %>
            </td>
          </tr>
        </table>
        &nbsp;
        <%
          if (TicketDetails.getThisContact() != null ) {
        %>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="4">
              <strong>Primary Contact</strong>
            </th>     
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



