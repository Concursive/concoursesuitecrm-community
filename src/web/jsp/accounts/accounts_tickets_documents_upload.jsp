<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="document.inputForm.subject.focus();">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getId()%>">Ticket Details</a> >
<a href="AccountTicketsDocuments.do?command=View&tId=<%=TicketDetails.getId()%>">Documents</a> >
Add Document<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%=toHtml(TicketDetails.getCompanyName())%> - Ticket # <%=TicketDetails.getPaddedId()%></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <%-- submenu for accounts --%>
      <% String param1 = "orgId=" + TicketDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="tickets" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
  	<td class="containerBack">
      <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="containerMenu">
          <td>
            <%-- submenu for tickets --%>
            <% String param2 = "id=" + TicketDetails.getId(); %>
            <dhv:container name="accountstickets" selected="history" param="<%= param2 %>"/>
            <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
            <font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font>
            </dhv:evaluate>
         </td>
        </tr>
        <tr>
          <td>
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
        <%-- ticket container end --%>
      </table>
    </td>
  </tr>
  <%-- account container end --%>
</table>
</form>
</body>
