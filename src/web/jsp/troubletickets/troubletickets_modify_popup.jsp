<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.communications.base.Campaign,org.aspcfs.modules.communications.base.CampaignList,org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<body>
<form name="details" action="TroubleTickets.do?command=Update&auto-populate=true&popup=true" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
  	<td>
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>">
        <font color="red">This ticket was closed on <%= toHtml(TicketDetails.getClosedString()) %></font><br>
        &nbsp;<br>
      </dhv:evaluate>
      <% if (TicketDetails.getClosed() != null) { %>
        <input type="button" value="Reopen" onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&id=<%= TicketDetails.getId()%>';submit();" />
      <%} else {%>
        <input type="submit" value="Update" onClick="return checkForm(this.form)" />
         <input type="button" value="Cancel" onClick="window.close()" />
      <%}%>
      <br /><br />
<%-- include basic troubleticket add form --%>
<%@ include file="troubletickets_modify_include.jsp" %>
<% if (TicketDetails.getClosed() != null) { %>
  <input type="button" value="Reopen" onClick="javascript:this.form.action='TroubleTickets.do?command=Reopen&id=<%= TicketDetails.getId()%>';submit();" />
<%} else {%>
  <input type="submit" value="Update" onClick="return checkForm(this.form)" />
  <input type="button" value="Cancel" onClick="window.close()" />
<%}%>
  </td>
  </tr>
</table>
</form>
</body>
