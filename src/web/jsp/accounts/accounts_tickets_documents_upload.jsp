<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getId()%>">Ticket Details</a> >
<a href="AccountTicketsDocuments.do?command=View&tId=<%=TicketDetails.getId()%>">Documents</a> >
Add Document
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
      <% String param2 = "id=" + TicketDetails.getId(); %>
      <strong>Ticket # <%=TicketDetails.getPaddedId()%>:</strong>
      [ <dhv:container name="accountstickets" selected="documents" param="<%= param2 %>"/> ]
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
        <br><font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font>
      </dhv:evaluate>
      <br><br>
      <form method="post" name="inputForm" action="AccountTicketsDocuments.do?command=Upload" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
        <%-- include add document form --%>
        <%@ include file="../troubletickets/documents_add_include.jsp" %>
        <p align="center">
          * Large files may take a while to upload.<br>
          Wait for file completion message when upload is complete.
        </p>
        <input type="submit" value=" Upload " name="upload">
        <input type="submit" value="Cancel" onClick="javascript:this.form.dosubmit.value='false';this.form.action='AccountTicketsDocuments.do?command=View&tId=<%= TicketDetails.getId() %>';">
        <input type="hidden" name="dosubmit" value="true">
        <input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
        </form>
    </td>
  </tr>
  <%-- account container end --%>
</table>
</body>
