<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getId()%>">Ticket Details</a> >
<a href="AccountTicketsDocuments.do?command=View&tId=<%=TicketDetails.getId()%>">Documents</a> >
Modify Document
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + TicketDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="tickets" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
  	<td class="containerBack">
      <%@ include file="accounts_ticket_header_include.jsp" %>
      <% String param2 = "id=" + TicketDetails.getId(); %>
      [ <dhv:container name="accountstickets" selected="documents" param="<%= param2 %>"/> ]
      <br><br>
      <form method="post" name="inputForm" action="AccountTicketsDocuments.do?command=Update" onSubmit="return checkFileForm(this);">
      <%-- include modify form --%>
      <%@ include file="../troubletickets/documents_modify_include.jsp" %>
      &nbsp;<br>
  <input type="submit" value=" Update " name="update">
  <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='AccountTicketsDocuments.do?command=View&tId=<%= TicketDetails.getId() %>';">
  <input type="hidden" name="dosubmit" value="true">
  <input type="hidden" name="tId" value="<%= TicketDetails.getId() %>">
	<input type="hidden" name="fid" value="<%= FileItem.getId() %>">
      </form>
    </td>
  </tr>
  <%-- account container end --%>
</table>
</body>
