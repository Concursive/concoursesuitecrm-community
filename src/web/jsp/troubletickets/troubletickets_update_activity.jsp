<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request" />
<jsp:useBean id="activityDetails" class="org.aspcfs.modules.troubletickets.base.TicketActivityLog" scope="request" />
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
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
  <form name="details" action="TroubleTicketActivityLog.do?command=Update&auto-populate=true&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>&return=<%=request.getParameter("return")%>"  onSubmit="return doCheck(this);" method="post">
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';" />
      <%if ("list".equals(request.getParameter("return"))) { %>
        <input type="button" value="Cancel" onClick="window.location.href='TroubleTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>';this.form.dosubmit.value='false';" />
      <%}else{ %>
        <input type="button" value="Cancel" onClick="window.location.href='TroubleTicketActivityLog.do?command=View&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>';this.form.dosubmit.value='false';" />
      <%}%>
      <br /> <br />   
      <%@ include file="troubletickets_update_activity_include.jsp" %>
      <br />
      <input type="submit" value="Update" onClick="this.form.dosubmit.value='true';" />
      <%if ("list".equals(request.getParameter("return"))) { %>
        <input type="button" value="Cancel" onClick="window.location.href='TroubleTicketActivityLog.do?command=List&id=<%=ticketDetails.getId()%>';this.form.dosubmit.value='false';" />
      <%}else{ %>
        <input type="button" value="Cancel" onClick="window.location.href='TroubleTicketActivityLog.do?command=View&id=<%=ticketDetails.getId()%>&formId=<%=activityDetails.getId()%>';this.form.dosubmit.value='false';" />
      <%}%>
      <input type="hidden" name="dosubmit" value="true" />
     </td>
    </tr> 
</form>
 </td>
 </tr>
</table>
