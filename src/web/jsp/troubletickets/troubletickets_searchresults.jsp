<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="TicList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="TicListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<a href="TroubleTickets.do">Tickets</a> > 
<a href="TroubleTickets.do?command=SearchTickets&reset=true">Search Form</a> >
Search Results
<hr color="#BFBFBB" noshade>
<dhv:pagedListStatus title="Current Search Results" object="TicListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <dhv:permission name="tickets-tickets-edit,tickets-tickets-delete">
		<th valign="center" align="left">
      <strong>Action</strong>
    </th>
    </dhv:permission>
    <th valign="center" align="left">
      <strong>Number</strong>
    </th>
    <th><b>Priority</b></th>
    <th><b>Age</b></th>
    <th><b>Company</b></th>
		<th nowrap><b>Assigned To</b></th>
  </tr>
<%
	Iterator j = TicList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
		while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
      Ticket thisTic = (Ticket)j.next();
%>   
	<tr>
	<dhv:permission name="tickets-tickets-edit,tickets-tickets-delete">
    <td rowspan="2" width="8" valign="top" nowrap class="row<%= rowid %>">
      <dhv:permission name="tickets-tickets-edit"><a href="TroubleTickets.do?command=Modify&id=<%= thisTic.getId() %>&return=list">Edit</a></dhv:permission><dhv:permission name="tickets-tickets-edit,tickets-tickets-delete" all="true">|</dhv:permission><dhv:permission name="tickets-tickets-delete"><a href="javascript:confirmDelete('TroubleTickets.do?command=Delete&id=<%= thisTic.getId() %>');">Del</a></dhv:permission>
    </td>
  </dhv:permission>
		<td width="15" valign="top" nowrap class="row<%= rowid %>">
			<a href="TroubleTickets.do?command=Details&id=<%= thisTic.getId() %>"><%= thisTic.getPaddedId() %></a>
		</td>
		<td width="10" valign="top" nowrap class="row<%= rowid %>">
			<%= toHtml(thisTic.getPriorityName()) %>
		</td>
		<td width="8%" align="right" valign="top" nowrap class="row<%= rowid %>">
			<%= thisTic.getAgeOf() %>
		</td>
		<td width="90%" valign="top" class="row<%= rowid %>">
			<%= toHtml(thisTic.getCompanyName()) %><dhv:evaluate exp="<%= !(thisTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
		<td width="150" nowrap valign="top" class="row<%= rowid %>">
      <dhv:username id="<%= thisTic.getAssignedTo() %>" default="-- unassigned --"/>
		</td>
	</tr>
  <tr>
    <td colspan="6" valign="top" class="row<%= rowid %>">
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
<br>
<dhv:pagedListControl object="TicListInfo" tdClass="row1"/>
	<%} else {%>
		<tr class="containerBody">
      <td colspan="7">
        No tickets found.
      </td>
    </tr>
  </table>
<%}%>
