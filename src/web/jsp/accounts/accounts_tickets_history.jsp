<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>">Account Details</a> >
<a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>">Tickets</a> >
<a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getId()%>">Ticket Details</a> >
History<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <%@ include file="accounts_details_header_include.jsp" %>
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
        <% String param2 = "id=" + TicketDetails.getId(); %>
        <strong>Ticket # <%=TicketDetails.getPaddedId()%>:</strong>
        [ <dhv:container name="accountstickets" selected="history" param="<%= param2 %>"/> ]
        <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
          <br><font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font>
        </dhv:evaluate>
        <br><br>
        <%-- display history --%>
        <table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
          <tr class="title">
            <td colspan="4">
              <strong>Ticket Log History</strong>
            </td>     
          </tr>
        <%  
            Iterator hist = TicketDetails.getHistory().iterator();
            if (hist.hasNext()) {
              while (hist.hasNext()) {
                TicketLog thisEntry = (TicketLog)hist.next();
        %>    
        <% if (thisEntry.getSystemMessage() == true) {%>
          <tr bgColor="#F1F0E0">
        <% } else { %>
          <tr class="containerBody">
        <%}%>
            <td nowrap valign="top" class="formLabel">
              <%= toHtml(thisEntry.getEnteredByName()) %>
            </td>
            <td nowrap valign="top">
              <%= thisEntry.getEnteredString() %>
            </td>
            <td valign="top" width="100%">
              <%= toHtml(thisEntry.getEntryText()) %>
            </td>
          </tr>
        <%    
            }
          } else {
        %>
          <tr class="containerBody">
            <td>
              <font color="#9E9E9E" colspan="3">No Log Entries.</font>
            </td>
          </tr>
        <%}%>
       <%-- ticket container end --%>
      </table>
    </td>
  </tr>
  <%-- account container end --%>
</table>
