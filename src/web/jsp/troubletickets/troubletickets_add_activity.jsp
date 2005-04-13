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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.tasks.base.*, org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="activityDetails" class="org.aspcfs.modules.troubletickets.base.TicketActivityLog" scope="request" />
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<form name="details" action="TroubleTicketActivityLog.do?command=Save&auto-populate=true&id=<%=ticketDetails.getId()%>" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do?"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="TroubleTickets.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home"><dhv:label name="tickets.view">View Tickets</dhv:label></a> >
<%}%>
<a href="TroubleTickets.do?command=Details&id=<%= ticketDetails.getId() %>"><dhv:label name="tickets.details">Ticket Details</dhv:label></a> >
<a href="TroubleTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>"><dhv:label name="tickets.activitylog.long_html">Activity Log</dhv:label></a> >
<dhv:label name="project.addActivityLog">Add Activity Log</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<% String param1 = "id=" + ticketDetails.getId(); %>
<dhv:container name="tickets" selected="activitylog" object="ticketDetails" param="<%= param1 %>">
  <%@ include file="ticket_header_include.jsp" %>
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';" />
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='TroubleTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>';this.form.dosubmit.value='false';" />
  <br />
  <dhv:formMessage />
  <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
  <%@ include file="troubletickets_update_activity_include.jsp" %>
  <br />
  <input type="submit" value="<dhv:label name="global.button.save">Save</dhv:label>" onClick="this.form.dosubmit.value='true';" />
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="window.location.href='TroubleTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>';this.form.dosubmit.value='false';" />
  <input type="hidden" name="dosubmit" value="true" />
  <input type="hidden" name="onlyWarnings" value="<%=(activityDetails.getOnlyWarnings()?"on":"off")%>" />
</dhv:container>
</form>
