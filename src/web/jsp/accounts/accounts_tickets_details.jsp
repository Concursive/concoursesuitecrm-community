<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.troubletickets.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<%@ include file="../initPage.jsp" %>
<form name="details" action="AccountTickets.do?command=ModifyTicket&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a> >
Ticket Details
</td>
</tr>
</table>
<%-- End Trails --%>
<%-- Begin container --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + TicketDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="tickets" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
  	<td class="containerBack">
      <%-- Begin container content --%>
        <%@ include file="accounts_ticket_header_include.jsp" %>
        <% String param2 = "id=" + TicketDetails.getId(); %>
        [ <dhv:container name="accountstickets" selected="details" param="<%= param2 %>"/> ]
      <br>
      <br>
       <% if (TicketDetails.getClosed() != null) { %>
              <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Reopen" onClick="javascript:this.form.action='AccountTickets.do?command=ReopenTicket&id=<%=TicketDetails.getId()%>';submit();"> </dhv:permission>
        <%} else {%>
              <dhv:permission name="accounts-accounts-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='AccountTickets.do?command=ModifyTicket&id=<%=TicketDetails.getId()%>';submit();"></dhv:permission>
              <dhv:permission name="accounts-accounts-tickets-delete"><input type="button" value="Delete" onClick="javascript:popURL('AccountTickets.do?command=ConfirmDelete&orgId=<%= TicketDetails.getOrgId() %>&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','320','200','yes','no');"></dhv:permission>
        <%}%>
        <dhv:permission name="accounts-accounts-tickets-edit,accounts-accounts-tickets-delete"><br>&nbsp;</dhv:permission>
        <%-- Ticket Information --%>
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
              <td nowrap class="formLabel">
                Service Contract Number
              </td>
              <td>
                <%= toHtml(TicketDetails.getServiceContractNumber()) %>
              </td>
            </tr>
            <tr class="containerBody">
              <td nowrap class="formLabel">
                Asset Serial Number
              </td>
              <td>
                <%= toHtml(TicketDetails.getAssetSerialNumber()) %>
              </td>
            </tr>
            <dhv:evaluate if="<%= TicketDetails.getProductId() != -1 %>">
            <tr class="containerBody">
              <td nowrap class="formLabel">
                Labor Category
              </td>
              <td>
                <%= toHtml(product.getSku()) %>:&nbsp;<%= toHtml(product.getName()) %>
                <%
                    if(!"".equals(product.getShortDescription()) && (product.getShortDescription() != null)){
                %>
                                / <%= toHtml(product.getShortDescription()) %>
                <%
                    }
                %>
              </td>
            </tr>
            </dhv:evaluate>
            <dhv:evaluate if="<%= TicketDetails.getCustomerProductId() != -1 %>">
            <tr class="containerBody">
              <td nowrap class="formLabel">
                Customer Product
              </td>
              <td>
                <%= toHtml(customerProduct.getDescription()) %> <input type="button" value="Display" onClick="javascript:popURL('Publish.do?command=DisplayCustomerProduct&adId=<%= customerProduct.getId() %>&ticketId=<%= TicketDetails.getId() %>','Customer Product','500','200','yes','yes');"/>
              </td>
            </tr>
            </dhv:evaluate>
            <tr class="containerBody">
              <td valign="top" class="formLabel">
                <dhv:label name="ticket.issue">Issue</dhv:label>
              </td>
            <td>
              <%= toHtml(TicketDetails.getProblem()) %>
              <input type="hidden" name="problem" value="<%= toHtml(TicketDetails.getProblem()) %>">
              <input type="hidden" name="orgId" value="<%= TicketDetails.getOrgId() %>">
              <input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              Location
            </td>
            <td>
              <%= toHtml(TicketDetails.getLocation()) %>
            </td>
          </tr>
        <dhv:include name="ticket.catCode" none="true">
          <tr class="containerBody">
            <td class="formLabel">
              Category
            </td>
            <td>
              <%=toHtml(TicketDetails.getCategoryName())%>
            </td>
          </tr>
        </dhv:include>
        <dhv:include name="ticket.severity" none="true">
          <tr class="containerBody">
            <td class="formLabel">
              Severity
            </td>
            <td>
              <%=toHtml(TicketDetails.getSeverityName())%>
            </td>
          </tr>
        </dhv:include>
          <tr class="containerBody">
            <td class="formLabel">
              Entered
            </td>
            <td>
              <dhv:username id="<%= TicketDetails.getEnteredBy() %>"/>
              -
              <dhv:tz timestamp="<%= TicketDetails.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              Modified
            </td>
            <td>
              <dhv:username id="<%= TicketDetails.getModifiedBy() %>"/>
              -
              <dhv:tz timestamp="<%= TicketDetails.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
            </td>
          </tr>
        </table>
        &nbsp;
        <%-- Primary Contact --%>
        <%
          if (TicketDetails.getThisContact() != null ) {
        %>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="2">
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
        <%-- Assignment --%>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="2">
              <strong>Assignment</strong>
            </th>
          </tr>
          <dhv:include name="ticket.priority" none="true">
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
              <%= toHtml(TicketDetails.getDepartmentCode() > 0 ? TicketDetails.getDepartmentName() : "-- unassigned --") %>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              Resource Assigned
            </td>
            <td>
              <dhv:username id="<%= TicketDetails.getAssignedTo() %>" default="-- unassigned --"/>
              <dhv:evaluate if="<%= !(TicketDetails.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
            </td>
          </tr>
          <tr class="containerBody">
            <td nowrap class="formLabel">
              Assignment Date
            </td>
            <td>
              <dhv:tz timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              Estimated Resolution Date
            </td>
            <td>
              <dhv:tz timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
            </td>
          </tr>
        </table>
        &nbsp;
        <%-- Resolution --%>
        <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
          <tr>
            <th colspan="2">
              <strong>Resolution</strong>
            </th>     
          </tr>
          <tr class="containerBody">
            <td class="formLabel" valign="top">
              Cause
            </td>
            <td>
              <%= toHtml(TicketDetails.getCause()) %>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel" valign="top">
              Resolution
            </td>
            <td>
              <%= toHtml(TicketDetails.getSolution()) %>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              Resolution Date
            </td>
            <td>
              <dhv:tz timestamp="<%= TicketDetails.getResolutionDate() %>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
            </td>
          </tr>
          <tr class="containerBody">
            <td class="formLabel">
              Have our services met or exceeded your expectations?
            </td>
            <td>
              <dhv:evaluate if="<%= TicketDetails.getExpectation() == 1 %>">
                Yes
              </dhv:evaluate>
              <dhv:evaluate if="<%= TicketDetails.getExpectation() == 0 %>">
                No
              </dhv:evaluate>
              <dhv:evaluate if="<%= TicketDetails.getExpectation() == -1 %>">
                Undecided
              </dhv:evaluate>
            </td>
          </tr>
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
