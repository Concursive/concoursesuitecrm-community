<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.* " %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="activityList" class="org.aspcfs.modules.troubletickets.base.TicketActivityLogList" scope="request"/>
<jsp:useBean id="TMListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="ticket_activity_log_menu.jsp" %>
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
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=ticketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=ticketDetails.getOrgId()%>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=ticketDetails.getId()%>">Ticket Details</a> >
Forms
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
      [ <dhv:container name="accountstickets" selected="activitylog" param="<%= param2 %>"/> ]
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<br />
<dhv:permission name="accounts-accounts-tickets-activity-log-add,tickets-activity-log-view" all="false">
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" >
  <tr>
    <td>
      <dhv:permission name="accounts-accounts-tickets-activity-log-add"><a href="AccountTicketActivityLog.do?command=Add&id=<%=ticketDetails.getId()%>">Add activities</a><br /></dhv:permission>
    </td>
  </tr>
</table>
</dhv:permission>
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
    <th width="44%" nowrap>
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
