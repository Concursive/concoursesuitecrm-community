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
<%@ page import="java.util.*, java.sql.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="com.zeroio.iteam.base.*,org.aspcfs.modules.login.beans.*,org.aspcfs.controller.*" %>
<%@ page import="org.aspcfs.modules.admin.base.User" %>
<jsp:useBean id="SalesListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="contacts" class="org.aspcfs.modules.contacts.base.ContactList" scope="request" />
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request" />
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<jsp:useBean id="allOpenLeads" class="java.lang.String" scope="request" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="sales_list_menu.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

<%-- Clear form function --%>
  function clearForm(form) {
  }
  
  function checkForm(form) {
    return true;
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
<% if (listForm != null && !"".equals(listForm)) { %>
  <a href="Sales.do?command=SearchForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
<%}%>
  <dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SalesListInfo" showHiddenParams="false" />
<table cellpadding="3" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>&nbsp;</th>
    <th><strong><dhv:label name="contacts.name">Name</dhv:label></strong></th>
    <th><strong><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></strong></th>
    <th><strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong></th>
    <th><strong><dhv:label name="reports.owner">Owner</dhv:label></strong></th>
    <th><strong><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></strong></th>
  </tr>
<%
	Iterator iterator = contacts.iterator();
  if (iterator.hasNext()) {
    int rowid = 0;
    int menuCount=0;
    while (iterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      menuCount++;
      Contact thisLead = (Contact) iterator.next();
%>
    <tr class="row<%= rowid %>">
     <td valign="top" align="center">
        <a href="javascript:displayMenu('select<%= menuCount %>','menuContact','<%= thisLead.getId() %>','list','<%= thisLead.getIsLead() %>');" 
        onMouseOver="over(0, <%= menuCount %>);" 
        onmouseout="out(0, <%= menuCount %>);hideMenu('menuContact');"><img
        src="images/select.gif" name="select<%= menuCount %>" id="select<%= menuCount %>" align="absmiddle" border="0" /></a>
      </td>
      <td valign="top" width="30%">
        <%= toHtml(thisLead.getNameLastFirst()) %>
      </td>
      <td valign="top" width="30%">
        <%= toHtml(thisLead.getCompany()) %>
      </td>
      <td valign="top" width="10%">
        <dhv:evaluate if="<%= !thisLead.getIsLead() %>" >
          <dhv:label name="sales.working">Working</dhv:label>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisLead.getIsLead() %>">
          <dhv:label name="sales.<%= thisLead.getLeadStatusString() %>"><%= toHtml(thisLead.getLeadStatusString()) %></dhv:label>
        </dhv:evaluate>
    </td>
      <td valign="top" width="20%">
        <dhv:username id="<%= thisLead.getOwner() %>" />
      </td>
      <td valign="top" width="10%">
        <zeroio:tz timestamp="<%= thisLead.getEntered() %>" dateOnly="true" showTimeZone="false" />
      </td>
    </tr>
<%
      }
	  } else {
%>
  <tr>
    <td valign="center" colspan="6"><dhv:label name="sales.noLeadsFound">No leads found.</dhv:label></td>
  </tr>
<%}%>
</table>
<table width="100%" border="0" cellpadding="3">
  <tr>
    <td style="text-align: center;">
      <dhv:pagedListStatus object="SalesListInfo" showRefresh="false" showControlOnly="true" showHiddenParams="false" />
    </td>
  </tr>
</table>
<iframe src="../empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

