<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.utils.web.*, org.aspcfs.modules.assets.base.*, org.aspcfs.modules.troubletickets.base.* " %>
<jsp:useBean id="asset" class="org.aspcfs.modules.assets.base.Asset" scope="request"/>
<jsp:useBean id="ticketList" class="org.aspcfs.modules.troubletickets.base.TicketList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="AssetHistoryInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript">
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Accounts.do">Accounts</a> > 
  <a href="Accounts.do?command=Search">Search Results</a> >
  <a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>">Account Details</a> >
  <a href="AccountsAssets.do?command=List&orgId=<%= OrgDetails.getOrgId() %>">Assets</a> >
  Asset History
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="assets" param="<%= "orgId=" + asset.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <% String param2 = "id=" + asset.getId(); %>
      [ <dhv:container name="accountsassets" selected="history" param="<%= param2 %>"/> ]
      <br /><br />
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AssetHistoryInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="12%">
      <strong>Ticket Number</strong>
    </th>
    <th width="12%">
      <b><a href="AccountsAssets.do?command=History&id=<%=asset.getId()%>&popup=true&popupType=inline&column=entered">Date Entered</a></b>
      <%= AssetHistoryInfo.getSortIcon("t.entered") %>
    </th>
    <th width="32%">
      <strong>Issue</strong>
    </th>
    <th width="12%" nowrap>
      <b><a href="AccountsAssets.do?command=History&id=<%=asset.getId()%>&popup=true&popupType=inline&column=closed">Date Closed</a></b>
      <%= AssetHistoryInfo.getSortIcon("closed") %>
    </th>
    <th width="32%" nowrap>
      <strong>Resolution</strong>
    </th>
  </tr>
  <% 
    Iterator itr = ticketList.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        Ticket thisTicket = (Ticket)itr.next();
    %>    
  <tr valign="top" class="row<%=rowid%>">
    <td width=8 valign="center"  nowrap >
        <dhv:permission name="accounts-accounts-tickets-view"><a href="AccountTickets.do?command=TicketDetails&id=<%=thisTicket.getId()%>"></dhv:permission><%=thisTicket.getPaddedId() %><dhv:permission name="accounts-accounts-tickets-view"></a></dhv:permission>
    </td>
    <td width="12%" >
      <dhv:tz timestamp="<%=thisTicket.getEntered()%>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
		</td>
    <td width="32%" >
      <%=toHtml(thisTicket.getProblem())%>
		</td>
		<td width="12%" >
    <dhv:tz timestamp="<%=thisTicket.getClosed()%>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
		</td>
		<td width="32%" >
      <%=toHtml(thisTicket.getSolution())%>
		</td>
   </tr>
   <%}
   }else{%>
   <tr>
		<td colspan="5">
      There is no maintenance or service history for this asset.
		</td>
   </tr>
   <%}%>
</table>
<br>
<dhv:pagedListControl object="AssetHistoryInfo"/>
 </td>
</tr>
</table>
