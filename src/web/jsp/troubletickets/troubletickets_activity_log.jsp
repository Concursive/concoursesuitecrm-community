<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.* " %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="activityList" class="org.aspcfs.modules.troubletickets.base.TicketActivityLogList" scope="request"/>
<jsp:useBean id="TMListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="activity_log_menu.jsp" %>
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
<table class="trails">
<tr>
<td>
<a href="TroubleTickets.do?">Help Desk</a> > 
<a href="TroubleTickets.do?command=Home">View Tickets</a> >
<a href="TroubleTickets.do?command=Details&id=<%= ticketDetails.getId() %>">Ticket Details</a> >
Activity Log
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="ticket_header_include.jsp" %>
<% String param1 = "id=" + ticketDetails.getId(); %>
<dhv:container name="tickets" selected="activitylog" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<table cellspacing="0" border="0" width="100%">
  <tr>
    <td>
      <dhv:permission name="tickets-activity-log-add">
        <a href="TroubleTicketActivityLog.do?command=Add&id=<%=ticketDetails.getId()%>">Add activities</a>
      </dhv:permission>
      <dhv:permission name="tickets-activity-log-add" none="true"></dhv:permission>
    </td>
    <td align="right">
      <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
      <a href="javascript:popURLReturn('TroubleTicketActivityLog.do?command=PrintActivityForm&id=<%=thisTicket.getId()%>&orgId=<%=thisTicket.getOrgId()%>')">Printable Form</a>
    </td>
  </tr>
</table>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="TMListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="12%">
      <strong>First Activity Date</strong>
    </th>
    <th width="12%">
      <strong>Last Activity Date</strong>
    </th>
    <th width="12%">
      <strong>Follow Up?</strong>
    </th>
    <th width="10%" nowrap>
      <strong>Alert Date</strong>
    </th>
    <th width="36%" nowrap>
      <strong>Follow Up Description</strong>
    </th>
    <th width="10%" nowrap>
      <strong>Modified</strong>
    </th>
  </tr>
  <% 
    Iterator itr = activityList.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        TicketActivityLog thisMaintenance = (TicketActivityLog)itr.next();
    %>    
  <tr valign="top" class="row<%=rowid%>">
    <td width=8 valign="center"  nowrap >
        <% int status = -1;%>
        <% status = thisMaintenance.getEnabled() ? 1 : 0; %>
      	<%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('menuTicketForm', '<%=ticketDetails.getId() %>', '<%= thisMaintenance.getId() %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
    <td width="12%" >
    <dhv:tz timestamp="<%=thisMaintenance.getFirstActivityDate()%>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
		</td>
		<td width="12%" >
    <dhv:tz timestamp="<%=thisMaintenance.getLastActivityDate()%>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
		</td>
		<td width="12%" >
    <%if (thisMaintenance.getFollowUpRequired()) { %>
    Yes
    <%}else{%>
    No
    <%}%>
		</td>
		<td width="10%" >
    <dhv:tz timestamp="<%=thisMaintenance.getAlertDate()%>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
		</td>
		<td width="48%" >
    <%if (thisMaintenance.getFollowUpRequired() == false) { %>
    N/A
    <%}else{%>
    <%= toHtml(thisMaintenance.getFollowUpDescription())%>
    <%}%>
		</td>
		<td width="10%" >
      <dhv:tz timestamp="<%=thisMaintenance.getModified()%>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
		</td>
   </tr>
    <%  
      }
    }else{
    %>
    <tr>
      <td colspan="7" class="containerBody">
        No activities found.
      </td>
    </tr>
    <%
    }
    %></table>
<br>
<dhv:pagedListControl object="TMListInfo"/>
 </td>
</tr>
</table>
