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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request" />
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="activityDetails" class="org.aspcfs.modules.troubletickets.base.TicketActivityLog" scope="request" />
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
<form name="details" action="AccountTicketActivityLog.do?command=Update&auto-populate=true&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>&return=<%=request.getParameter("return")%>"  onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=ticketDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=ticketDetails.getOrgId()%>"><dhv:label name="accounts.tickets.tickets">Tickets</dhv:label></a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=ticketDetails.getId()%>"><dhv:label name="accounts.tickets.details">Ticket Details</dhv:label></a> >
<a href="AccountTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>">Activity Log</a> >
Modify Activity Log
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
      <br /> <br />
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';" />
      <%if ("list".equals(request.getParameter("return"))) { %>
        <input type="button" value="Cancel" onClick="window.location.href='AccountTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>';this.form.dosubmit.value='false';" />
      <%}else{ %>
        <input type="button" value="Cancel" onClick="window.location.href='AccountTicketActivityLog.do?command=View&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>';this.form.dosubmit.value='false';" />
      <%}%>
      <br />
      <dhv:formMessage />
      <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
      <%@ include file="../troubletickets/troubletickets_update_activity_include.jsp" %>
      <br />
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';" />
      <%if ("list".equals(request.getParameter("return"))) { %>
        <input type="button" value="Cancel" onClick="window.location.href='AccountTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>';this.form.dosubmit.value='false';" />
      <%}else{ %>
        <input type="button" value="Cancel" onClick="window.location.href='AccountTicketActivityLog.do?command=View&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>';this.form.dosubmit.value='false';" />
      <%}%>
      <input type="hidden" name="dosubmit" value="true" />
     </td>
    </tr> 
   </td>
 </tr>
</table>
</form>
