<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="RevenueList" class="org.aspcfs.modules.accounts.base.RevenueList" scope="request"/>
<jsp:useBean id="RevenueListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_revenue_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
Revenue<br>
<hr color="#BFBFBB" noshade>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="revenue" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<dhv:permission name="accounts-accounts-revenue-add"><a href="RevenueManager.do?command=Add&orgId=<%=request.getParameter("orgId")%>">Add Revenue</a></dhv:permission>
<center><%= RevenueListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="RevenueManager.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= RevenueListInfo.getOptionValue("my") %>>My Revenue </option>
        <option <%= RevenueListInfo.getOptionValue("all") %>>All Revenue</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showAttribute(request, "actionError") %>" object="RevenueListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
<tr>
  <th width="8">
    <strong>Action</strong>
  </th>
  <th width="100%" nowrap>
    <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=description">Description</a></strong>
    <%= RevenueListInfo.getSortIcon("description") %>
  </th>  
  <th align="center" nowrap>
    <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=r.month">Month</a></strong>
    <%= RevenueListInfo.getSortIcon("r.month") %>
  </th>
  <th align="center" nowrap>
    <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=r.year,r.month">Year</a></strong>
    <%= RevenueListInfo.getSortIcon("r.year,r.month") %>
  </th>
  <th align="center" nowrap>
    <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=amount">Amount</a></strong>
    <%= RevenueListInfo.getSortIcon("amount") %>
  </th>
  <dhv:evaluate if="<%= "all".equals(RevenueListInfo.getListView()) %>">
  <th align="center" nowrap>
    <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=owner">Owner</a></strong>
    <%= RevenueListInfo.getSortIcon("owner") %>
  </th>
  </dhv:evaluate>
</tr>
<%
	Iterator j = RevenueList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i =0;
    while (j.hasNext()) {
      i++;
		  rowid = (rowid != 1?1:2);
      Revenue thisRevenue = (Revenue)j.next();
%>      
		<tr class="containerBody">
      <td valign="center" nowrap class="row<%= rowid %>">
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- To display the menu, pass the actionId, accountId and the contactId--%>
        <a href="javascript:displayMenu('menuRevenue','<%= OrgDetails.getId() %>','<%= thisRevenue.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="center" class="row<%= rowid %>">
        <a href="RevenueManager.do?command=Details&id=<%=thisRevenue.getId()%>"><%= toHtml(thisRevenue.getDescription()) %></a>
      </td>
      <td valign="center" align="center" class="row<%= rowid %>">
        <%= toHtml(thisRevenue.getMonthName()) %>
      </td>
      <td valign="center" align="center" class="row<%= rowid %>" nowrap>
        <%= thisRevenue.getYear() %>
      </td>
      <td valign="center" align="right" class="row<%= rowid %>">
        $<%=thisRevenue.getAmountCurrency()%>
      </td>
      <dhv:evaluate if="<%= "all".equals(RevenueListInfo.getListView()) %>">
      <td valign="center" align="right" class="row<%= rowid %>">
        <dhv:username id="<%= thisRevenue.getOwner()%>" />
      </td>
      </dhv:evaluate>
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan="<%= "all".equals(RevenueListInfo.getListView()) ? "7" : "6" %>">
        No revenue found.
      </td>
    </tr>
<%}%>
	</table>
	<br>
  <dhv:pagedListControl object="RevenueListInfo"/>
</td>
</tr>
</table>

