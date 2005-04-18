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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.modules.base.PhoneNumber" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="accounts.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>
<dhv:permission name="accounts-accounts-add"><a href="Accounts.do?command=Add"><dhv:label name="accounts.add">Add an Account</dhv:label></a></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="SearchOrgListInfo"/></center></dhv:include>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SearchOrgListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8" <% ++columnCount; %>>
      &nbsp;
    </th>
    <th nowrap <% ++columnCount; %>>
      <strong><a href="Accounts.do?command=Search&column=o.name"><dhv:label name="organization.name">Account Name</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
    <dhv:include name="organization.phoneNumbers" none="true">
    <th nowrap <% ++columnCount; %>>
        <strong><dhv:label name="account.phoneFax">Phone/Fax</dhv:label></strong>
		</th>
    </dhv:include>
    <dhv:include name="organization.emailAddresses" none="true">
    <th nowrap <% ++columnCount; %>>
        <strong><dhv:label name="accounts.accounts_add.Email">Email</dhv:label></strong>
		</th>
    </dhv:include>
  </tr>
<%
	Iterator j = OrgList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Organization thisOrg = (Organization)j.next();
%>
  <tr>
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <% int status = -1;%>
      <dhv:permission name="accounts-accounts-edit"><% status = thisOrg.getEnabled() ? 1 : 0; %></dhv:permission>
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= i %>','menuAccount', '<%= thisOrg.getOrgId() %>', '<%= status %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuAccount');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
		<td class="row<%= rowid %>">
      <a href="Accounts.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>
		</td>
    <dhv:include name="organization.phoneNumbers" none="true">
		<td valign="center" class="row<%= rowid %>" nowrap>
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() == null)%>">
      <%Iterator itr = thisOrg.getPhoneNumberList().iterator();%>
      <%if (!itr.hasNext()) {%>&nbsp;<%}%>
      <%while (itr.hasNext()) {
        PhoneNumber phoneNumber = (PhoneNumber)itr.next(); %>
        <%= phoneNumber.getPhoneNumber()%> (<%=phoneNumber.getTypeName()%>)<br />
      <%}%>
		</dhv:evaluate>
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() != null)%>">
      <%Iterator itr = thisOrg.getPrimaryContact().getPhoneNumberList().iterator();%>
      <%if (!itr.hasNext()) {%>&nbsp;<%}%>
      <%while (itr.hasNext()) {
        PhoneNumber phoneNumber = (PhoneNumber)itr.next(); %>
        <%= phoneNumber.getPhoneNumber()%> (<%=phoneNumber.getTypeName()%>)<br />
      <%}%>
		</dhv:evaluate>
		</td>
    </dhv:include>
    <dhv:include name="organization.emailAddresses" none="true">
		<td valign="center" class="row<%= rowid %>" nowrap>
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() == null)%>">
      <% if ( (!"".equals(thisOrg.getPrimaryEmailAddress()))) { %>
        <a href="mailto:<%= toHtml(thisOrg.getPrimaryEmailAddress()) %>"><%= toHtml(thisOrg.getPrimaryEmailAddress()) %></a>
      <%} else {%>
        &nbsp;
      <%}%>
		</dhv:evaluate>    
		<dhv:evaluate if="<%=(thisOrg.getPrimaryContact() != null)%>">
      <% if ( (!"".equals(thisOrg.getPrimaryContact().getPrimaryEmailAddress()))) { %>
        <a href="mailto:<%= toHtml(thisOrg.getPrimaryContact().getPrimaryEmailAddress()) %>"><%= toHtml(thisOrg.getPrimaryContact().getPrimaryEmailAddress()) %></a>
      <%} else {%>
        &nbsp;
      <%}%>    
		</dhv:evaluate>
		</td>
    </dhv:include>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="<%= columnCount %>">
      <dhv:label name="accounts.search.notFound">No accounts found with the specified search parameters.</dhv:label><br />
      <a href="Accounts.do?command=SearchForm"><dhv:label name="accounts.accounts_list.ModifySearch">Modify Search</dhv:label></a>.
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>

