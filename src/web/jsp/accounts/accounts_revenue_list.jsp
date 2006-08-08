<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="RevenueList" class="org.aspcfs.modules.accounts.base.RevenueList" scope="request"/>
<jsp:useBean id="RevenueListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
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
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<dhv:label name="accounts.accounts_add.Revenue">Revenue</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="revenue" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <dhv:permission name="accounts-accounts-revenue-add"><a href="RevenueManager.do?command=Add&orgId=<%=request.getParameter("orgId")%>"><dhv:label name="accounts.accounts_revenue_add.AddRevenue">Add Revenue</dhv:label></a></dhv:permission>
  <dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
  <center><dhv:pagedListAlphabeticalLinks object="RevenueListInfo"/></center></dhv:include>
  <table width="100%" border="0">
    <tr>
      <form name="listView" method="post" action="RevenueManager.do?command=View&orgId=<%=OrgDetails.getOrgId()%>">
      <td align="left">
        <select size="1" name="listView" onChange="javascript:document.listView.submit();">
          <option <%= RevenueListInfo.getOptionValue("my") %>><dhv:label name="accounts.accounts_revenue_list.MyRevenue">My Revenue</dhv:label> </option>
          <option <%= RevenueListInfo.getOptionValue("all") %>><dhv:label name="accounts.accounts_revenue_list.AllRevenue">All Revenue</dhv:label></option>
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
      &nbsp;
    </th>
    <th width="100%" nowrap>
      <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=description"><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></a></strong>
      <%= RevenueListInfo.getSortIcon("description") %>
    </th>
    <th align="center" nowrap>
      <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=r.month"><dhv:label name="accounts.accounts_revenue_add.Month">Month</dhv:label></a></strong>
      <%= RevenueListInfo.getSortIcon("r.month") %>
    </th>
    <th align="center" nowrap>
      <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=r.year,r.month"><dhv:label name="accounts.accounts_revenue_add.Year">Year</dhv:label></a></strong>
      <%= RevenueListInfo.getSortIcon("r.year,r.month") %>
    </th>
    <th align="center" nowrap>
      <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=amount"><dhv:label name="accounts.accounts_revenue_add.Amount">Amount</dhv:label></a></strong>
      <%= RevenueListInfo.getSortIcon("amount") %>
    </th>
    <dhv:evaluate if="<%= "all".equals(RevenueListInfo.getListView()) %>">
    <th align="center" nowrap>
      <strong><a href="RevenueManager.do?command=View&orgId=<%=request.getParameter("orgId")%>&column=owner"><dhv:label name="accounts.accounts_contacts_detailsimport.Owner">Owner</dhv:label></a></strong>
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
        <a href="javascript:displayMenu('select<%= i %>','menuRevenue','<%= OrgDetails.getId() %>','<%= thisRevenue.getId() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuRevenue');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
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
        <zeroio:currency value="<%= thisRevenue.getAmount() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
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
        <dhv:label name="accounts.accounts_revenue_list.NoRevenueFound">No revenue found.</dhv:label>
      </td>
    </tr>
<%}%>
	</table>
	<br>
  <dhv:pagedListControl object="RevenueListInfo"/>
</dhv:container>