<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do">Help Desk</a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTickets.do?command=SearchTicketsForm">Search Form</a> >
  <a href="TroubleTickets.do?command=SearchTickets">Search Results</a> >
<%}else{%> 
  <a href="TroubleTickets.do?command=Home">View Tickets</a> >
<%}%>
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>">Ticket Details</a> >
<a href="TroubleTicketsDocuments.do?command=View&tId=<%=TicketDetails.getId()%>">Documents</a> >
Modify Document
</td>
</tr>
</table>
<%-- End Trails --%>
<form method="post" name="inputForm" action="TroubleTicketsDocuments.do?command=Update" onSubmit="return checkFileForm(this);">
<%@ include file="ticket_header_include.jsp" %>
<% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="tickets" selected="documents" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
		<td class="containerBack">
    <%= showError(request, "actionError") %>
    <%-- include modify form --%>
    <%@ include file="documents_modify_include.jsp" %>
  &nbsp;<br>
  <input type="submit" value=" Update " name="update">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='TroubleTicketsDocuments.do?command=View&tId=<%= TicketDetails.getId() %>';">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="tId" value="<%= TicketDetails.getId() %>">
	<input type="hidden" name="fid" value="<%= FileItem.getId() %>">
  </td>
</tr>
</table>
</form>
</body>
