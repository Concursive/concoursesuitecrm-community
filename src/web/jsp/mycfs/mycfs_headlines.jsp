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
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.accounts.base.Organization" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="HeadlineListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:include page="cfsor2.js" flush="true"/>
<%@ include file="../initPage.jsp" %>
<dhv:permission name="myhomepage-miner-add">
<body onLoad="javascript:document.addAccount.name.focus();">
<form name="addAccount" action="MyCFS.do?command=InsertHeadline" method="post">
</dhv:permission>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="actionList.myHomePage">My Home Page</dhv:label></a> > 
<dhv:label name="Headlines">Headlines</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="myhomepage-miner-add">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="calendar.monitorNewCompany">Monitor a New Company</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="contacts.name">Name</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="name">
    </td>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.TickerSymbol">Ticker Symbol</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="stockSymbol">
    </td>
  </tr>
</table>
&nbsp;<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>">
</form>
</dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="HeadlineListInfo"/></center></dhv:include>
<br>
<form name="delAccount" action="MyCFS.do?command=DeleteHeadline" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <dhv:permission name="myhomepage-miner-delete">
      <th width="3%" valign="center" align="left">
        &nbsp;
      </th>
    </dhv:permission>
    <th>
      <strong><dhv:label name="calendar.myMonitoredCompanies">My Monitored Companies</dhv:label></strong>
    </th>
    <th valign=center align=left>
      <strong><dhv:label name="accounts.accounts_reports_generate.Ticker">Ticker</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="calendar.dateEntered">Date Entered</dhv:label></strong>
    </th>
</tr>
<%
	Iterator j = OrgList.iterator();
	int rowid = 0;
	if ( j.hasNext() ) {
    while (j.hasNext()) {
      rowid = (rowid != 1?1:2);
			Organization thisOrg = (Organization)j.next();
%>      
  <tr class="row<%= rowid %>">
    <dhv:permission name="myhomepage-miner-delete">
    <td width="5" valign="center" align="center">
      <input type="checkbox" name="<%= thisOrg.getOrgId() %>" value="true">
    </td>
    </dhv:permission>
    <td valign="center">
      <%= toHtml(thisOrg.getName()) %>
    </td>
    <td width="15" align="center">
      <%= toHtml(thisOrg.getTicker()) %>
    </td>
    <td width="100" valign="center" align="center">
      <%-- TODO: fix this --%>
      <%--<%= thisOrg.getEnteredStringLongYear() %>--%>
    </td>
  </tr>
<%}%>
</table>
<dhv:permission name="myhomepage-miner-delete">
	<br>
  <input type="submit" name="<dhv:label name="accounts.Action">Action</dhv:label>" value="Delete Checked" />
  <br>
</dhv:permission>
<br>
<dhv:pagedListControl object="HeadlineListInfo" tdClass="row1"/>
<%} else {%>
  <tr class="containerBody">
    <td colspan="4"><dhv:label name="calendar.noCompaniesFound">No companies found.</dhv:label></td>
  </tr>
</table>
<%}%>
</form>
<dhv:permission name="myhomepage-miner-add">
</body>
</dhv:permission>
