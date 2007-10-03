<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="TicketDetails"
             class="org.aspcfs.modules.troubletickets.base.Ticket"
             scope="request"/>
<jsp:useBean id="OrgDetails"
             class="org.aspcfs.modules.accounts.base.Organization"
             scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr>
    <td>
      <a href="Accounts.do"><dhv:label
          name="accounts.accounts">Accounts</dhv:label></a> >
      <a href="Accounts.do?command=Search"><dhv:label
          name="accounts.SearchResults">Search Results</dhv:label></a> >
      <a href="Accounts.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label
          name="accounts.details">Account Details</dhv:label></a> >
      <a href="Accounts.do?command=ViewTickets&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label
          name="accounts.tickets.tickets">Tickets</dhv:label></a> >
      <a href="AccountTickets.do?command=TicketDetails&id=<%=TicketDetails.getId()%>"><dhv:label
          name="accounts.tickets.details">Ticket Details</dhv:label></a> >
      <a href="AccountTickets.do?command=ViewHistory&id=<%=TicketDetails.getId()%>"><dhv:label
          name="accountsassets.history.long_html">History</dhv:label></a> >
      <dhv:label name="accountsassets.history.long_html">Add a Note to
        History</dhv:label>
    </td>
  </tr>
</table>

<%-- End Trails --%>
<dhv:container name="accounts" selected="tickets" object="OrgDetails"
               param='<%= "orgId=" + OrgDetails.getOrgId() %>'
               appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="accountstickets" selected="history"
                 object="TicketDetails"
                 param='<%= "id=" + TicketDetails.getId() %>'>
    <form
        action="AccountTickets.do?command=AddTicketNote&id=<%=TicketDetails.getId()%>"
        method="post">
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"></textarea>
          </td>
          <td valign="top">

          </td>
        </tr>
      </table>
      <input type="submit"
             value="<dhv:label name="button.insert">Insert</dhv:label>"/>
    </form>
  </dhv:container>
</dhv:container>