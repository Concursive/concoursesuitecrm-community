<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.* " %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="maintenanceList" class="org.aspcfs.modules.troubletickets.base.TicketMaintenanceNoteList" scope="request"/>
<jsp:useBean id="SunListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="ticket_maintenancenote_menu.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/tasks.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=ticketDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=ticketDetails.getOrgId()%>"><dhv:label name="accounts.tickets.tickets">Tickets</dhv:label></a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=ticketDetails.getId()%>"><dhv:label name="accounts.tickets.details">Ticket Details</dhv:label></a> >
Maintenance Notes
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="tickets" param="<%= "orgId=" + ticketDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%@ include file="accounts_ticket_header_include.jsp" %>
      <% String param2 = "id=" + ticketDetails.getId(); %>
      [ <dhv:container name="accountstickets" selected="maintenancenotes" param="<%= param2 %>"/> ]
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<br /><br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" >
  <tr class="overlineSection">
    <td width="20%" nowrap>
      <b>Serial No.:</b>
      <%= toHtml(ticketDetails.getAssetSerialNumber()) %>
    </td>
    <td width="20%" nowrap>
      <b>Vendor:</b>
      <%= toHtml(ticketDetails.getAssetVendor()) %>
    </td>
    <td width="20%" nowrap>
      <b>Model:</b>
      <%= toHtml(ticketDetails.getAssetModelVersion()) %>
    </td>
    <td width="20%" nowrap>
      <b>Location:</b>
      <%= (ticketDetails.getAssetLocation().trim().equals(""))? "Not Specified" : toHtml(ticketDetails.getAssetLocation()) %> 
    </td>
  </tr>
  <tr valign="top" class="underlineSection">
    <td width="20%" nowrap>
      <b>Service Contract No.:</b>
      <%= toHtml(ticketDetails.getServiceContractNumber()) %>
    </td>
    <td width="20%" nowrap>
      <b>Onsite Service Model:</b>
      <%= toHtml((ticketDetails.getAssetOnsiteResponseModel() == -1) ? onsiteModelList.getSelectedValue(ticketDetails.getContractOnsiteResponseModel()) : onsiteModelList.getSelectedValue(ticketDetails.getAssetOnsiteResponseModel()))%>
    </td>
    <td width="20%" nowrap>
      <b>Start Date:</b>
      <zeroio:tz timestamp="<%= ticketDetails.getContractStartDate() %>" dateOnly="true" default="&nbsp;"/>
    </td>
    <td nowrap>
      <b>End Date:</b>
      <zeroio:tz timestamp="<%= ticketDetails.getContractEndDate() %>" dateOnly="true" default="&nbsp;"/>
    </td>
  </tr>
</table>
<dhv:permission name="accounts-accounts-tickets-maintenance-report-add,tickets-maintenance-report-view" all="false">
<table cellpadding="4" cellspacing="0" border="0" width="100%" >
  <tr>
    <td>
      <dhv:permission name="accounts-accounts-tickets-maintenance-report-add">
          <a href="AccountTicketMaintenanceNotes.do?command=Add&id=<%=ticketDetails.getId()%>">Add maintenance note</a>
      </dhv:permission>
    </td>
  </tr>
</table>
</dhv:permission>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SunListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="15%" nowrap>
      <strong>Entered Date</strong>
    </th>
    <th width="85%" nowrap>
      <strong>Description</strong>
    </th>
  </tr>
  <% 
    Iterator itr = maintenanceList.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        TicketMaintenanceNote thisSun = (TicketMaintenanceNote)itr.next();
    %>    
  <tr valign="top" class="row<%=rowid%>">
    <td width="8" valign="center" nowrap >
        <% int status = -1;%>
        <% status = thisSun.getEnabled() ? 1 : 0; %>
      	<%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuTicketForm', '<%=ticketDetails.getId() %>', '<%= thisSun.getId() %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTicketForm');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
		<td width="15%" nowrap>
      <a href="AccountTicketMaintenanceNotes.do?command=View&id=<%=ticketDetails.getId()%>&formId=<%= thisSun.getId()%>"><zeroio:tz timestamp="<%= thisSun.getEntered() %>" dateOnly="true" default="&nbsp;"/></a>
		</td>
		<td width="15%" >
      <%= toHtml(thisSun.getDescriptionOfService()) %>
		</td>
   </tr>
    <%  
      }
    }else{
    %>
    <tr class="containerBody">
      <td colspan="3">
        No maintenance notes found.
      </td>
    </tr>
    <%
    }
    %></table>
<br>
<dhv:pagedListControl object="SunListInfo"/>
</td>
</tr>
</table>
