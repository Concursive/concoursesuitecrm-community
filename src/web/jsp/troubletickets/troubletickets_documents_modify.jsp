<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<a href="TroubleTickets.do">Tickets</a> > 
<a href="TroubleTickets.do?command=Home">View Tickets</a> >
<a href="TroubleTickets.do?command=Details&id=<%= TicketDetails.getId() %>">Ticket Details</a> >
<a href="TroubleTicketsDocuments.do?command=View&tId=<%=TicketDetails.getId()%>">Documents</a> >
Modify Document<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
<form method="post" name="inputForm" action="TroubleTicketsDocuments.do?command=Update" onSubmit="return checkFileForm(this);">
  <tr class="containerHeader">
    <td>
    <strong>Ticket # <%= TicketDetails.getPaddedId() %><br>
    <%= toHtml(TicketDetails.getCompanyName()) %></strong>
    <dhv:evaluate exp="<%= !(TicketDetails.getCompanyEnabled()) %>"><font color="red">(account disabled)</font></dhv:evaluate>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + TicketDetails.getId(); %>
      <dhv:container name="tickets" selected="documents" param="<%= param1 %>"/>
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
      <font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font>
      </dhv:evaluate>
   </td>
  </tr>
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
</form>
</table>
</body>
