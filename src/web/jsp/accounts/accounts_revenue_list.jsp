<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="RevenueList" class="org.aspcfs.modules.accounts.base.RevenueList" scope="request"/>
<jsp:useBean id="RevenueListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
Revenue<br>
<hr color="#BFBFBB" noshade>
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
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
<tr class="title">
  <dhv:permission name="accounts-accounts-revenue-edit,accounts-accounts-revenue-delete">
  <td width="8">
    <strong>Action</strong>
  </td>
  </dhv:permission>
  <td width="100%" nowrap>
    <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=description">Description</a></strong>
    <%= RevenueListInfo.getSortIcon("description") %>
  </td>  
  <td align="center" nowrap>
    <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=r.month">Month</a></strong>
    <%= RevenueListInfo.getSortIcon("r.month") %>
  </td>   
  <td align="center" nowrap>
    <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=r.year,r.month">Year</a></strong>
    <%= RevenueListInfo.getSortIcon("r.year,r.month") %>
  </td>
  <td align="center" nowrap>
    <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=amount">Amount</a></strong>
    <%= RevenueListInfo.getSortIcon("amount") %>
  </td>
</tr>
<%
	Iterator j = RevenueList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    while (j.hasNext()) {
		  rowid = (rowid != 1?1:2);
      Revenue thisRevenue = (Revenue)j.next();
%>      
		<tr class="containerBody">
		<dhv:permission name="accounts-accounts-revenue-edit,accounts-accounts-revenue-delete">
      <td valign=center nowrap class="row<%= rowid %>">
        <dhv:permission name="accounts-accounts-revenue-edit"><a href="RevenueManager.do?command=Modify&orgId=<%= OrgDetails.getOrgId()%>&id=<%=thisRevenue.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="accounts-accounts-revenue-edit,accounts-accounts-revenue-delete" all="true">|</dhv:permission><dhv:permission name="accounts-accounts-revenue-delete"><a href="javascript:confirmDelete('RevenueManager.do?command=Delete&orgId=<%= OrgDetails.getOrgId() %>&id=<%=thisRevenue.getId()%>');">Del</a></dhv:permission>
      </td>
    </dhv:permission>
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
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan="5">
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

