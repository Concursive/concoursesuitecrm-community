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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.troubletickets.base.Ticket,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="AccountTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_tickets_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
Tickets
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="tickets" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<dhv:permission name="accounts-accounts-tickets-add"><a href="AccountTickets.do?command=AddTicket&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.tickets.add">Add New Ticket</dhv:label></a>
<input type=hidden name="orgId" value="<%= OrgDetails.getOrgId() %>">
<br>
</dhv:permission>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountTicketInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="30%" valign="center" align="left">
      <strong>Number</strong>
    </th>
    <th width="20%" nowrap>
      <b><strong><a href="Accounts.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>&column=pri_code">Priority</a></strong></b>
      <%= AccountTicketInfo.getSortIcon("pri_code") %>
    </th>
    <th width="15%">
      <b>Est. Resolution Date</b>
    </th>
    <th width="15%">
      <b>Age</b>
    </th>
		<th width="20%">
      <b>Assigned&nbsp;To</b>
    </th>
    <th width="20%">
      <b>Modified</b>
    </th>
    </tr>
<%
	Iterator j = TicList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i =0;
    while (j.hasNext()) {
      i++;
      rowid = (rowid != 1?1:2);
      Ticket thisTic = (Ticket)j.next();
%>   
	<tr class="row<%= rowid %>">
    <td rowspan="2" width="8" valign="top" nowrap>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <%-- To display the menu, pass the actionId, accountId and the contactId--%>
      <a href="javascript:displayMenu('select<%= i %>','menuTic','<%= OrgDetails.getId() %>','<%= thisTic.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTic');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td width="15" valign="top" nowrap>
			<a href="AccountTickets.do?command=TicketDetails&id=<%= thisTic.getId() %>"><%= thisTic.getPaddedId() %></a>
		</td>
		<td valign="top" nowrap>
			<%= toHtml(thisTic.getPriorityName()) %>
		</td>
		<td width="15%" valign="top" nowrap>
      <% if(!User.getTimeZone().equals(thisTic.getEstimatedResolutionDateTimeZone())){%>
      <zeroio:tz timestamp="<%= thisTic.getEstimatedResolutionDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisTic.getEstimatedResolutionDate() %>" dateOnly="true" timeZone="<%= thisTic.getEstimatedResolutionDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
		</td>
		<td width="8%" align="right" valign="top" nowrap>
			<%= thisTic.getAgeOf() %>
		</td>
		<td width="150" nowrap valign="top">
			<dhv:username id="<%= thisTic.getAssignedTo() %>" default="-- unassigned --"/><dhv:evaluate exp="<%= !(thisTic.getHasEnabledOwnerAccount()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
    <td width="150" nowrap valign="top">
      <% if (thisTic.getClosed() == null) { %>
        <zeroio:tz timestamp="<%= thisTic.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
      <%} else {%>
        <zeroio:tz timestamp="<%= thisTic.getClosed() %>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      <%}%>
    </td>
   </tr>
   <tr class="row<%= rowid %>">
    <td colspan="7" valign="top">
      <%
        if (1==1) {
          Iterator files = thisTic.getFiles().iterator();
          while (files.hasNext()) {
            FileItem thisFile = (FileItem)files.next();
            if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
      %>
        <a href="AccountTicketsDocuments.do?command=Download&stream=true&tId=<%= thisTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
      <%
            }
          }
        }
      %>
      <%= toHtml(thisTic.getProblemHeader()) %>&nbsp;
      <% if (thisTic.getClosed() == null) { %>
        [<font color="green">open</font>]
      <%} else {%>
        [<font color="red">closed</font>]
      <%}%>
    </td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="7">
      <dhv:label name="accounts.tickets.search.notFound">No tickets found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
	<br>
  <dhv:pagedListControl object="AccountTicketInfo"/>
<br>
</td>
</tr>
</table>

