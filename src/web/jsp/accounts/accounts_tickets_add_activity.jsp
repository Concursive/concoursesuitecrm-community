<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.tasks.base.*, org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="activityDetails" class="org.aspcfs.modules.troubletickets.base.TicketActivityLog" scope="request" />
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="onsiteModelList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<form name="details" action="AccountTicketActivityLog.do?command=Save&auto-populate=true&id=<%=ticketDetails.getId()%>" onSubmit="return doCheck(this);" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=ticketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=ticketDetails.getOrgId()%>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=ticketDetails.getId()%>">Ticket Details</a> >
<a href="AccountTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>">Activity Log</a> >
Add Activity Log
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
      <br />
      <br />
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';" />
      <input type="button" value="Cancel" onClick="window.location.href='AccountTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>';this.form.dosubmit.value='false';" />
      <br />
      <%= showError(request, "actionError") %>
      <%@ include file="../troubletickets/troubletickets_update_activity_include.jsp" %>
      <br />
      <input type="submit" value="Save" onClick="this.form.dosubmit.value='true';" />
      <input type="button" value="Cancel" onClick="window.location.href='AccountTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>';this.form.dosubmit.value='false';" />
      <input type="hidden" name="dosubmit" value="true" />
     </td>
    </tr> 
   </td>
 </tr>
</table>
</form>

