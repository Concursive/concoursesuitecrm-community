<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*, org.aspcfs.modules.quotes.base.*, org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="product" class="org.aspcfs.modules.products.base.ProductCatalog" scope="request"/>
<jsp:useBean id="customerProduct" class="org.aspcfs.modules.products.base.CustomerProduct" scope="request"/>
<jsp:useBean id="quoteList" class="org.aspcfs.modules.quotes.base.QuoteList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<%@ include file="../initPage.jsp" %>
<form name="details" action="TroubleTickets.do?command=Modify&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> >
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm">Search Form</a> >
  <a href="TroubleTickets.do?command=SearchTickets">Search Results</a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View Tickets</dhv:label></a> >
<%}%>
<dhv:label name="tickets.details">Ticket Details</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="ticket_header_include.jsp" %>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="details" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
		<td class="containerBack">
    <% if (TicketDetails.getClosed() != null) { %>
      <dhv:permission name="tickets-tickets-edit"><input type="button" value="Reopen" onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&id=<%= TicketDetails.getId()%>';submit();"></dhv:permission>
    <%} else {%>
      <dhv:permission name="tickets-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='TroubleTickets.do?command=Modify&auto-populate=true';submit();"></dhv:permission>
      <dhv:permission name="quotes-view">
        <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
          <input type="button" value="Generate Quote" onClick="javascript:this.form.action='Quotes.do?command=Display&productId=<%= thisTicket.getProductId() %>&ticketId=<%= thisTicket.getId() %>';submit();"/>
        </dhv:evaluate>
      </dhv:permission>
      <dhv:permission name="tickets-tickets-delete">
      <% if ("searchResults".equals(request.getParameter("return"))){ %>
        <input type="button" value="Delete" onClick="javascript:popURL('TroubleTickets.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
      <%}else{%>
        <input type="button" value="Delete" onClick="javascript:popURL('TroubleTickets.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
      <%}%>
      </dhv:permission>
      <%}%>
<dhv:permission name="tickets-tickets-edit,tickets-tickets-delete"><br />&nbsp;<br /></dhv:permission>
<%-- Ticket Information --%>
<%-- Primary Contact --%>
<dhv:evaluate if="<%= TicketDetails.getThisContact() != null %>">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
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
      <dhv:permission name="accounts-accounts-contacts-view">
        <a href="Contacts.do?command=Details&id=<%= TicketDetails.getContactId() %>"><%= toHtml(TicketDetails.getThisContact().getNameLastFirst()) %></a>
      </dhv:permission>
      <dhv:permission name="accounts-accounts-contacts-view" none="true">
        <%= toHtml(TicketDetails.getThisContact().getNameLastFirst()) %>
      </dhv:permission>
    </td>
  </tr>
	<tr class="containerBody">
    <td class="formLabel">
      Title
    </td>
    <td>
      <%= toHtml(TicketDetails.getThisContact().getTitle()) %>
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
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="tickets.information">Ticket Information</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
		<td nowrap class="formLabel">
      <dhv:label name="tickets.source">Ticket Source</dhv:label>
		</td>
		<td>
      <%= toHtml(TicketDetails.getSourceName()) %>
		</td>
  </tr>
  <dhv:include name="ticket.contractNumber" none="true">
  <tr class="containerBody">
		<td nowrap class="formLabel">
      Service Contract Number
		</td>
		<td>
      <%= toHtml(TicketDetails.getServiceContractNumber()) %>
		</td>
  </tr>
  </dhv:include>
  <dhv:include name="ticket.asset" none="true">
  <tr class="containerBody">
		<td nowrap class="formLabel">
      Asset Serial Number
		</td>
		<td>
      <%= toHtml(TicketDetails.getAssetSerialNumber()) %>
		</td>
  </tr>
  </dhv:include>
  <dhv:include name="ticket.labor" none="true">
<dhv:evaluate if="<%= TicketDetails.getProductId() != -1 %>">
  <tr class="containerBody">
		<td nowrap class="formLabel">
      Labor Category
		</td>
		<td>
      <%= toHtml(product.getSku()) %>:
      <%= toHtml(product.getName()) %>
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
</dhv:include>
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
<%
  if (quoteList.size() > 0) {
%>
  <tr class="containerBody">
		<td nowrap class="formLabel">
<%
    if( quoteList.size() > 1 ){
%>
      Related Quotes
<%
    } else {
%>
      Related Quote
<%
    }
%>
		</td>
		<td>
<%
    Iterator quotes = (Iterator) quoteList.iterator();
    int quoteCounter = 0;
    while(quotes.hasNext()){
      Quote quote = (Quote) quotes.next();
      if(quoteCounter++ == 0 ){
%>
        <a href="Quotes.do?command=Details&quoteId=<%= quote.getId() %>"> Quote #<%= quote.getId() %></a>
<%
      } else {
%>
        , <a href="Quotes.do?command=Details&quoteId=<%= quote.getId() %>"> Quote #<%= quote.getId() %></a>
<%
      }
    }
%>
		</td>
  </tr>
<%
}
%>

  <tr class="containerBody">
    <td class="formLabel" valign="top">
      <dhv:label name="ticket.issue">Issue</dhv:label>
    </td>
    <td valign="top">
<%
  //Show audio files so that they can be streamed
  Iterator files = TicketDetails.getFiles().iterator();
  while (files.hasNext()) {
    FileItem thisFile = (FileItem)files.next();
    if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
%>
  <a href="TroubleTicketsDocuments.do?command=Download&stream=true&tId=<%= TicketDetails.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom">Play Audio Message</a><br />
<%
    }
  }
%>
      <%= toHtml(TicketDetails.getProblem()) %>
      <input type="hidden" name="problem" value="<%=toHtml(TicketDetails.getProblem())%>">
      <input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
      <input type="hidden" name="id" value="<%=TicketDetails.getId()%>">
    </td>
  </tr>
  <dhv:include name="ticket.location" none="true">
  <tr class="containerBody">
		<td class="formLabel">
      Location
		</td>
		<td>
      <%= toHtml(TicketDetails.getLocation()) %>
		</td>
  </tr>
  </dhv:include>
<dhv:include name="ticket.catCode" none="true">
  <tr class="containerBody">
		<td class="formLabel">
      Category
		</td>
		<td>
      <%= toHtml(TicketDetails.getCategoryName()) %>
		</td>
  </tr>
</dhv:include>
<dhv:include name="ticket.severity" none="true">
  <tr class="containerBody">
		<td class="formLabel">
      Severity
    </td>
		<td>
      <%= toHtml(TicketDetails.getSeverityName()) %>
		</td>
  </tr>
</dhv:include>
  <tr class="containerBody">
    <td class="formLabel">
      Entered
    </td>
		<td>
      <dhv:username id="<%= TicketDetails.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= TicketDetails.getEntered() %>" />
		</td>
  </tr>
  <tr class="containerBody">
		<td class="formLabel">
      Modified
    </td>
		<td>
      <dhv:username id="<%= TicketDetails.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= TicketDetails.getModified() %>" />
		</td>
  </tr>
</table>
&nbsp;
<%-- Assignment --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
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
      <%= toHtml(TicketDetails.getPriorityName()) %>
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
      <zeroio:tz timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true" timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% if (!User.getTimeZone().equals(TicketDetails.getAssignedDateTimeZone())) { %>
      <br />
      <zeroio:tz timestamp="<%= TicketDetails.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="ticket.estimatedResolutionDate">Estimated Resolution Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" dateOnly="true" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>" showTimeZone="yes"  default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(TicketDetails.getEstimatedResolutionDateTimeZone())){%>
      <br>
      <zeroio:tz timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"  default="&nbsp;" />
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="ticket.issueNotes">Issue Notes</dhv:label>
    </td>
    <td>
      <font color="red"><dhv:label name="accounts.tickets.ticket.previousTicket">(Previous notes for this ticket are listed under the history tab.)</dhv:label></font>
    </td>
  </tr>
</table>
  <br />
<%-- Resolution --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong>Resolution</strong>
    </th>
  </tr>
  <dhv:include name="ticket.cause" none="true">
  <tr class="containerBody">
		<td class="formLabel" valign="top">
      Cause
		</td>
		<td>
      <%= toHtml(TicketDetails.getCause()) %>
		</td>
  </tr>
  </dhv:include>
  <tr class="containerBody">
		<td class="formLabel" valign="top">
      <dhv:label name="ticket.resolution">Resolution</dhv:label>
		</td>
		<td>
      <%= toHtml(TicketDetails.getSolution()) %>
		</td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="ticket.resolutionDate">Resolution Date</dhv:label>
    </td>
    <td>
      <zeroio:tz timestamp="<%= TicketDetails.getResolutionDate() %>" dateOnly="true" timeZone="<%= TicketDetails.getResolutionDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% if (!User.getTimeZone().equals(TicketDetails.getResolutionDateTimeZone())) { %>
      <br />
      <zeroio:tz timestamp="<%= TicketDetails.getResolutionDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <dhv:include name="ticket.resolution" none="true">
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
  </dhv:include>
</table>
&nbsp;
<br />
<% if (TicketDetails.getClosed() != null) { %>
  <dhv:permission name="tickets-tickets-edit">
    <input type="button" value="Reopen" onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&id=<%= TicketDetails.getId()%>';submit();">
  </dhv:permission>
<%} else {%>
  <dhv:permission name="tickets-tickets-edit"><input type="button" value="Modify" onClick="javascript:this.form.action='TroubleTickets.do?command=Modify&auto-populate=true';submit();"></dhv:permission>
  <dhv:permission name="quotes-view">
    <dhv:evaluate if="<%= TicketDetails.getProductId() > 0 %>">
      <input type="button" value="Generate Quote" onClick="javascript:this.form.action='Quotes.do?command=Display&productId=<%= thisTicket.getProductId() %>&ticketId=<%= thisTicket.getId() %>';submit();"/>
    </dhv:evaluate>
  </dhv:permission>
  <dhv:permission name="tickets-tickets-delete">
    <% if ("searchResults".equals(request.getParameter("return"))){ %>
      <input type="button" value="Delete" onClick="javascript:popURL('TroubleTickets.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&return=searchResults&popup=true', 'Delete_ticket','320','200','yes','no');">
    <%}else{%>
      <input type="button" value="Delete" onClick="javascript:popURL('TroubleTickets.do?command=ConfirmDelete&id=<%= TicketDetails.getId() %>&popup=true', 'Delete_ticket','320','200','yes','no');">
    <%}%>
  </dhv:permission>
<%}%>
	</td>
  </tr>
</table>
</form>
