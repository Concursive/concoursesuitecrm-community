<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="RevenueList" class="com.darkhorseventures.cfsbase.RevenueList" scope="request"/>
<jsp:useBean id="RevenueListInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></script>
<a href="/Accounts.do?command=View">Back to Account List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="revenue" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="accounts-accounts-revenue-add"><a href="RevenueManager.do?command=Add&orgId=<%=request.getParameter("orgId")%>">Add a new Revenue</a></dhv:permission>
<center><%= RevenueListInfo.getAlphabeticalPageLinks() %></center>
<%= showAttribute(request, "actionError") %>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
<tr class="title">
  <dhv:permission name="accounts-accounts-revenue-edit,accounts-accounts-revenue-delete">
  <td valign=center align=left>
    <strong>Action</strong>
  </td>
  </dhv:permission>
  <td width=30% valign=center align=left>
    <strong><a href="/RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=description">Description</a></strong>
    <%= RevenueListInfo.getSortIcon("description") %>
  </td>  
  <td width=20% valign=center align=left>
    <strong><a href="/RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=month">Month</a></strong>
    <%= RevenueListInfo.getSortIcon("month") %>
  </td>   
  <td width=20% valign=center align=left>
    <strong><a href="/RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=year">Year</a></strong>
    <%= RevenueListInfo.getSortIcon("year") %>
  </td>
  <td width=30% valign=center align=left>
    <strong><a href="/RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=amount">Amount</a></strong>
    <%= RevenueListInfo.getSortIcon("amount") %>
  </td>
</tr>
<%
	Iterator j = RevenueList.iterator();
	
	if ( j.hasNext() ) {
		int rowid = 0;
	       	while (j.hasNext()) {
		
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
		
		Revenue thisRevenue = (Revenue)j.next();
%>      
		<tr class="containerBody">
		<dhv:permission name="accounts-accounts-revenue-edit,accounts-accounts-revenue-delete">
      <td width=8 valign=center nowrap class="row<%= rowid %>">
        <dhv:permission name="accounts-accounts-revenue-edit"><a href="/RevenueManager.do?command=Modify&orgId=<%= OrgDetails.getOrgId()%>&id=<%=thisRevenue.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-revenue-edit,accounts-accounts-revenue-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-revenue-delete"><a href="javascript:confirmDelete('/RevenueManager.do?command=Delete&orgId=<%= OrgDetails.getOrgId() %>&id=<%=thisRevenue.getId()%>');">Del</a></dhv:permission>
      </td>
      		</dhv:permission>
      <td width=30% valign=center class="row<%= rowid %>">
        <a href="/RevenueManager.do?command=Details&id=<%=thisRevenue.getId()%>"><%= toHtml(thisRevenue.getDescription()) %></a>
      </td>
      <td width=20% valign=center class="row<%= rowid %>">
        <%= toHtml(thisRevenue.getMonthName()) %>
      </td>
      <td width=20% valign=center class="row<%= rowid %>" nowrap>
        <%= thisRevenue.getYear() %>
      </td>
      <td width=30% valign=center class="row<%= rowid %>">
        $<%=thisRevenue.getAmountCurrency()%>
      </td>
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan=5 valign=center>
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

