<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*,java.text.DateFormat" %>
<jsp:useBean id="CreatedByMeList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="CreatedByMeInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AssignedToMeList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="AssignedToMeInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="OpenList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="OpenInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AllTicketsList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="AllTicketsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="troubletickets_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<dhv:label name="tickets.view">View Tickets</dhv:label> 
</td>
</tr>
</table>
<%-- End Trails --%>
<% int count = 0;
if ((request.getParameter("pagedListSectionId") == null && !(OpenInfo.getExpandedSelection()) && !(CreatedByMeInfo.getExpandedSelection()) && !(AllTicketsInfo.getExpandedSelection())) || AssignedToMeInfo.getExpandedSelection()) { %>
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Tickets Assigned to Me" type="tickets.assigned.to.me" object="AssignedToMeInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th valign="center" align="left">
      <strong>Action</strong>
    </th>
    <th valign="center" align="left">
      <strong>Number</strong>
    </th>
    <th><b>Priority</b></th>
    <th><b>Est. Resolution Date</b></th>
    <th><b>Age</b></th>
    <th><b>Company</b></th>
	<th><b>Resource Assigned</b></th>
  </tr>
<%
	Iterator k = AssignedToMeList.iterator();
	if ( k.hasNext() ) {
		int rowid = 0;
		while (k.hasNext()) {
      ++count;
		  rowid = (rowid != 1?1:2);
      Ticket assignedTic = (Ticket)k.next();
%>   
	<tr class="row<%= rowid %>">
    <td rowspan="2" width="8" valign="top" nowrap>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= count %>','menuTicket','<%= assignedTic.getId() %>');" 
          onMouseOver="over(0, <%= count %>)" 
          onmouseout="out(0, <%= count %>); hideMenu('menuTicket');"><img 
          src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
		<td width="10%" valign="top" nowrap>
			<a href="TroubleTickets.do?command=Details&id=<%= assignedTic.getId() %>"><%= assignedTic.getPaddedId() %></a>
		</td>
		<td width="12%" valign="top" nowrap>
			<%= toHtml(assignedTic.getPriorityName()) %>
		</td>
		<td width="15%" valign="top" nowrap>
      <zeroio:tz timestamp="<%=assignedTic.getEstimatedResolutionDate()%>" dateOnly="true" default="&nbsp;"/>
		</td>
		<td width="6%" align="right" valign="top" nowrap>
			<%= assignedTic.getAgeOf() %>
		</td>
		<td width="45%" valign="top">
			<%= toHtml(assignedTic.getCompanyName()) %><dhv:evaluate exp="<%= !(assignedTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
		<td width="20%" nowrap valign="top">
			<dhv:username id="<%= assignedTic.getAssignedTo() %>" default="-- unassigned --"/><dhv:evaluate exp="<%= !(assignedTic.getHasEnabledOwnerAccount()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
	</tr>
  <tr class="row<%= rowid %>">
    <td colspan="7" valign="top">
<%
  if (1==1) {
    Iterator files = assignedTic.getFiles().iterator();
    while (files.hasNext()) {
      FileItem thisFile = (FileItem)files.next();
      if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
%>
  <a href="TroubleTicketsDocuments.do?command=Download&stream=true&tId=<%= assignedTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
<%
      }
    }
  }
%>
      <%= toHtml(assignedTic.getProblemHeader()) %>&nbsp;
      <% if (assignedTic.getClosed() == null) { %>
        [<font color="green">open</font>]
      <%} else {%>
        [<font color="red">closed</font>]
      <%}%>
    </td>
  </tr>
	<%}%>
</table>
  <% if (AssignedToMeInfo.getExpandedSelection()) {%>
<br>
<dhv:pagedListControl object="AssignedToMeInfo" tdClass="row1"/>
  <%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="7">
      <dhv:label name="tickets.search.notFound">No tickets found</dhv:label>
    </td>
  </tr>
</table>
	<%}%>
<br>
<%}%>
<% if ( (request.getParameter("pagedListSectionId") == null && !(AssignedToMeInfo.getExpandedSelection()) && !(CreatedByMeInfo.getExpandedSelection()) && !(AllTicketsInfo.getExpandedSelection())) || OpenInfo.getExpandedSelection()) { %>
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Other Tickets in My Department" type="tickets.other" object="OpenInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th valign="center" align="left">
      <strong>Action</strong>
    </th>
    <th valign="center" align="left">
      <strong>Number</strong>
    </th>
    <th><b>Priority</b></th>
    <th><b>Est. Resolution Date</b></th>
    <th><b>Age</b></th>
    <th><b>Company</b></th>
	<th><b>Resource Assigned</b></th>
  </tr>
<%
	Iterator n = OpenList.iterator();
	if ( n.hasNext() ) {
		int rowid = 0;
		while (n.hasNext()) {
      ++count;
      rowid = (rowid != 1?1:2);
      Ticket openTic = (Ticket)n.next();
%>   
	<tr>
    <td rowspan="2" width="8" valign="top" nowrap class="row<%= rowid %>">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= count %>','menuTicket','<%= openTic.getId() %>');" 
          onMouseOver="over(0, <%= count %>)" 
          onmouseout="out(0, <%= count %>); hideMenu('menuTicket');"><img 
          src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
		<td width="10%" valign="top" nowrap class="row<%= rowid %>">
			<a href="TroubleTickets.do?command=Details&id=<%= openTic.getId() %>"><%= openTic.getPaddedId() %></a>
		</td>
		<td width="12%" valign="top" nowrap class="row<%= rowid %>">
			<%= toHtml(openTic.getPriorityName()) %>
		</td>
		<td width="15%" valign="top" class="row<%= rowid %>">
      <zeroio:tz timestamp="<%=openTic.getEstimatedResolutionDate()%>" dateOnly="true" default="&nbsp;"/>
		</td>
		<td width="6%" align="right" valign="top" nowrap class="row<%= rowid %>">
			<%= openTic.getAgeOf() %>
		</td>
		<td width="45%" valign="top" class="row<%= rowid %>">
			<%= toHtml(openTic.getCompanyName()) %><dhv:evaluate exp="<%= !(openTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
		<td width="20%" nowrap valign="top" class="row<%= rowid %>">
      <dhv:evaluate if="<%= openTic.isAssigned() %>">
        <dhv:username id="<%= openTic.getAssignedTo() %>" default="-- unassigned --"/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !(openTic.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
      <dhv:evaluate if="<%= (!openTic.isAssigned()) %>">
        <font color="red"><dhv:username id="<%= openTic.getAssignedTo() %>" default="-- unassigned --"/></font>
      </dhv:evaluate>
		</td>
	</tr>
  <tr>
    <td colspan="7" valign="top" class="row<%= rowid %>">
<%
  if (1==1) {
    Iterator files = openTic.getFiles().iterator();
    while (files.hasNext()) {
      FileItem thisFile = (FileItem)files.next();
      if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
%>
  <a href="TroubleTicketsDocuments.do?command=Download&stream=true&tId=<%= openTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
<%
      }
    }
  }
%>
      <%= toHtml(openTic.getProblemHeader()) %>
      <% if (openTic.getClosed() == null) { %>
        [<font color="green">open</font>]
      <%} else {%>
        [<font color="red">closed</font>]
      <%}%>
    </td>
  </tr>
	<%}%>
</table>
  <% if (OpenInfo.getExpandedSelection()) {%>
  <br>
  <dhv:pagedListControl object="OpenInfo" tdClass="row1"/>
  <%}%>
	<%} else {%>
		<tr class="containerBody">
      <td colspan="7">
        <dhv:label name="tickets.search.notFound">No tickets found</dhv:label>
      </td>
    </tr>
  </table>
	<%}%>
<br>
<%}%>
<dhv:include name="ticketList.ticketsCreatedByMe" none="true">
<% if ( (request.getParameter("pagedListSectionId") == null && !(AssignedToMeInfo.getExpandedSelection()) && !(OpenInfo.getExpandedSelection()) && !(AllTicketsInfo.getExpandedSelection())) || CreatedByMeInfo.getExpandedSelection()) { %>
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Tickets Created by Me" type="tickets.created.by.me" object="CreatedByMeInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th valign="center" align="left">
      <strong>Action</strong>
    </th>
    <th valign="center" align="left">
      <strong>Number</strong>
    </th>
    <th><b>Priority</b></th>
    <th><b>Est. Resolution Date</b></th>
    <th><b>Age</b></th>
    <th><b>Company</b></th>
	<th><b>Resource Assigned</b></th>
  </tr>
<%
	Iterator j = CreatedByMeList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
		while (j.hasNext()) {
      ++count;
      rowid = (rowid != 1?1:2);
      Ticket thisTic = (Ticket)j.next();
%>   
	<tr class="row<%= rowid %>">
    <td rowspan="2" width="8" valign="top" nowrap>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= count %>','menuTicket','<%= thisTic.getId() %>');" 
          onMouseOver="over(0, <%= count %>)" 
          onmouseout="out(0, <%= count %>); hideMenu('menuTicket');"><img 
          src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
		<td width="10%" valign="top" nowrap>
			<a href="TroubleTickets.do?command=Details&id=<%= thisTic.getId() %>"><%= thisTic.getPaddedId() %></a>
		</td>
		<td width="12%" valign="top" nowrap>
			<%= toHtml(thisTic.getPriorityName()) %>
		</td>
		<td width="15%" valign="top" class="row<%= rowid %>">
      <zeroio:tz timestamp="<%=thisTic.getEstimatedResolutionDate()%>" dateOnly="true" default="&nbsp;"/>
		</td>
		<td width="6%" align="right" valign="top" nowrap>
			<%= thisTic.getAgeOf() %>
		</td>
		<td width="45%" valign="top">
			<%= toHtml(thisTic.getCompanyName()) %><dhv:evaluate exp="<%= !(thisTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
		<td width="20%" nowrap valign="top">
      <dhv:evaluate exp="<%= thisTic.isAssigned() %>">
        <dhv:username id="<%= thisTic.getAssignedTo() %>" default="-- unassigned --"/>
      </dhv:evaluate>
      <dhv:evaluate exp="<%= !(thisTic.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
      <dhv:evaluate exp="<%= (!thisTic.isAssigned()) %>">
        <font color="red"><dhv:username id="<%= thisTic.getAssignedTo() %>" default="-- unassigned --"/></font>
      </dhv:evaluate>
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
  <a href="TroubleTicketsDocuments.do?command=Download&stream=true&tId=<%= thisTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
<%
      }
    }
  }
%>
      <%= toHtml(thisTic.getProblemHeader()) %>
      <% if (thisTic.getClosed() == null) { %>
        [<font color="green">open</font>]
      <%} else {%>
        [<font color="red">closed</font>]
      <%}%>
    </td>
  </tr>
	<%}%>
</table>
  <% if (CreatedByMeInfo.getExpandedSelection()) {%>
<br>
<dhv:pagedListControl object="CreatedByMeInfo" tdClass="row1"/>
  <%}%>
	<%} else {%>
		<tr class="containerBody">
      <td colspan="7">
        <dhv:label name="tickets.search.notFound">No tickets found</dhv:label>
      </td>
    </tr>
  </table>
	<%}%>
  <br />
<%}%>
</dhv:include>
<dhv:include name="ticketList.allTickets" none="true">
<% if ( (request.getParameter("pagedListSectionId") == null && !(AssignedToMeInfo.getExpandedSelection()) && !(OpenInfo.getExpandedSelection()) && !(CreatedByMeInfo.getExpandedSelection())) || AllTicketsInfo.getExpandedSelection()) { %>
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="All Tickets" type="tickets.all" object="AllTicketsInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th valign="center" align="left">
      <strong>Action</strong>
    </th>
    <th valign="center" align="left">
      <strong>Number</strong>
    </th>
    <th><b>Priority</b></th>
    <th><b>Est. Resolution Date</b></th>
    <th><b>Age</b></th>
    <th><b>Company</b></th>
	<th><b>Resource Assigned</b></th>
  </tr>
<%
	Iterator j = AllTicketsList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
		while (j.hasNext()) {
      ++count;
      rowid = (rowid != 1?1:2);
      Ticket thisTic = (Ticket)j.next();
%>   
	<tr class="row<%= rowid %>">
    <td rowspan="2" width="8" valign="top" nowrap>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= count %>','menuTicket','<%= thisTic.getId() %>');" 
          onMouseOver="over(0, <%= count %>)" 
          onmouseout="out(0, <%= count %>); hideMenu('menuTicket');"><img 
          src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
		<td width="10%" valign="top" nowrap>
			<a href="TroubleTickets.do?command=Details&id=<%= thisTic.getId() %>"><%= thisTic.getPaddedId() %></a>
		</td>
		<td width="12%" valign="top" nowrap>
			<%= toHtml(thisTic.getPriorityName()) %>
		</td>
		<td width="15%" valign="top" class="row<%= rowid %>">
      <zeroio:tz timestamp="<%=thisTic.getEstimatedResolutionDate()%>" dateOnly="true" default="&nbsp;"/>
		</td>
		<td width="6%" align="right" valign="top" nowrap>
			<%= thisTic.getAgeOf() %>
		</td>
		<td width="45%" valign="top">
			<%= toHtml(thisTic.getCompanyName()) %><dhv:evaluate exp="<%= !(thisTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
		<td width="20%" nowrap valign="top">
      <dhv:evaluate exp="<%= thisTic.isAssigned() %>">
        <dhv:username id="<%= thisTic.getAssignedTo() %>" default="-- unassigned --"/>
      </dhv:evaluate>
      <dhv:evaluate exp="<%= !(thisTic.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
      <dhv:evaluate exp="<%= (!thisTic.isAssigned()) %>">
        <font color="red"><dhv:username id="<%= thisTic.getAssignedTo() %>" default="-- unassigned --"/></font>
      </dhv:evaluate>
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
  <a href="TroubleTicketsDocuments.do?command=Download&stream=true&tId=<%= thisTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
<%
      }
    }
  }
%>
      <%= toHtml(thisTic.getProblemHeader()) %>
      <% if (thisTic.getClosed() == null) { %>
        [<font color="green">open</font>]
      <%} else {%>
        [<font color="red">closed</font>]
      <%}%>
    </td>
  </tr>
	<%}%>
</table>
  <% if (AllTicketsInfo.getExpandedSelection()) {%>
<br>
<dhv:pagedListControl object="AllTicketsInfo" tdClass="row1"/>
  <%}%>
	<%} else {%>
		<tr class="containerBody">
      <td colspan="7">
        <dhv:label name="tickets.search.notFound">No tickets found</dhv:label>
      </td>
    </tr>
  </table>
	<%}%>
<%}%>
</dhv:include>

