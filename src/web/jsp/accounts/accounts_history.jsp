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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="historyList" class="org.aspcfs.modules.accounts.base.OrganizationHistoryList" scope="request"/>
<jsp:useBean id="orgHistoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_history_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  
  function checkForm(form) {
    return true;
  }
  
  function flipFilterForm() {
    var span = document.getElementById("filterForm");
    if (span.style.display != 'none') {
      hideSpan("filterForm");
    } else {
      showSpan("filterForm");
    }
  }

  function setChecked(val,chkName,thisForm) {
    var frm = document.forms[thisForm];
    var len = document.forms[thisForm].elements.length;
    var i=0;
    for( i=0 ; i<len ; i++) {
      if (frm.elements[i].name.indexOf(chkName)!=-1) {
        frm.elements[i].checked=val;
      }
    }
  }
  
  function reopen() {
    window.location.href='AccountsHistory.do?command=View&orgId=<%= OrgDetails.getOrgId() + (isPopup(request)?"&popup=true":"") %>';
  }
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<dhv:label name="reports.helpdesk.ticket.history.history">History</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="history" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<dhv:evaluate if="<%= OrgDetails.getTrashedDate() == null %>"><dhv:permission name="accounts-accounts-history-add">
<a href=" javascript:popURL('AccountsHistory.do?command=AddNote&orgId=<%= OrgDetails.getOrgId() %>','Note','575','200','yes','yes');" ><dhv:label name="accounts.accountHistory.addANote">Add a Note</dhv:label></a><br /><br />
</dhv:permission></dhv:evaluate>
<span name="filterForm" id="filterForm" style="display:none">
<form name="history" action="AccountsHistory.do?command=View&orgId=<%= OrgDetails.getOrgId() %><%= isPopup(request)?"&popup=true":"" %>" method="post" onSubmit="return checkForm(this);">
<input type="hidden" name="searchcodeOrgId" value="<%= OrgDetails.getOrgId() %>"/>
<% boolean check = orgHistoryListInfo.getSavedCriteria().size() == 0; %>
<table cellpadding="4" cellspacing="0" class="empty"><tr><td>
  <table cellpadding="0" cellspacing="0" class="floatWrap"><tr><td valign="top" width="100%">
    <div><dhv:permission name="accounts-accounts-view">
      <input type="checkbox" name="searchcodeNotes" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeNotes").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="accounts-accounts-contacts-calls-edit,accounts-accounts-contacts-calls-view">
      <input type="checkbox" name="searchcodeActivities" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeActivities").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="myhomepage-inbox-view,accounts-accounts-contacts-messages-view">
      <input type="checkbox" name="searchcodeEmail" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeEmail").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_add.EmailOrMessages">Email/Messages</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="accounts-accounts-documents-edit,accounts-accounts-documents-view">
      <input type="checkbox" name="searchcodeDocuments" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeDocuments").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="accounts-accounts-tickets-edit,accounts-accounts-tickets-view">
      <input type="checkbox" name="searchcodeTickets" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeTickets").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.tickets.tickets">Tickets</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="accounts-assets-view,accounts-assets-edit">
      <input type="checkbox" name="searchcodeAssets" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeAssets").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.Assets">Assets</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="accounts-quotes-edit,accounts-quotes-view">
      <input type="checkbox" name="searchcodeQuotes" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeQuotes").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_quotes_list.Quotes">Quotes</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="myhomepage-tasks-edit,myhomepage-tasks-view">
      <input type="checkbox" name="searchcodeTasks" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeTasks").equals("true")||check?"checked":"" %> /> <dhv:label name="myitems.tasks">Tasks</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="accounts-service-contracts-edit,accounts-service-contracts-view">
      <input type="checkbox" name="searchcodeServiceContracts" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeServiceContracts").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_sc_add.ServiceContracts">Service Contracts</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="accounts-accounts-opportunities-edit,accounts-accounts-opportunities-view">
      <input type="checkbox" name="searchcodeOpportunities" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeOpportunities").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="accounts-accounts-relationships-view">
      <input type="checkbox" name="searchcodeRelationships" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeRelationships").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_relationships_add.Relationships">Relationships</dhv:label>
    </dhv:permission></div>
      <%--  <td><input type="checkbox" name="searchcodeShowDisabledWithEnabled" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeShowDisabledWithEnabled").equals("true")||check?"checked":"" %> /> <dhv:label name="global.trashed">Trashed</dhv:label> --%>
  </td></tr></table></td><td nowrap>
    <table cellpadding="0" cellspacing="0" class="empty">
      <tr>
        <td valign="top" align="center">
          [<a href="javascript:setChecked(1,'searchcode','history');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
        </td>
      </tr>
      <tr>
        <td valign="top" align="center">
          [<a href="javascript:setChecked(0,'searchcode','history');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>]
        </td>
      </tr>
      <tr>
        <td nowrap valign="top" align="center">
          <input type="submit" value="<dhv:label name="accounts.history.applyFilters">Apply Filters</dhv:label>" />
        </td>
      </tr>
    </table>
</td></tr></table>
</form>
</span>
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="orgHistoryListInfo" externalJScript="flipFilterForm();" externalText="<dhv:label name=\"pagedListInfo.filters\">Filters</dhv:label>"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th width="8" nowrap>&nbsp;</th>
      <th nowrap>
        <strong><a href="AccountsHistory.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=type"><dhv:label name="reports.accounts.type">Type</dhv:label></a></strong>
        <%= orgHistoryListInfo.getSortIcon("type") %>
      </th>
      <th width="100%">
        <strong><dhv:label name="reports.helpdesk.ticket.maintenance.partDescription">Description</dhv:label></strong>
      </th>
      <th nowrap>
        <strong><a href="AccountsHistory.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=c.namelast"><dhv:label name="accounts.accountHistory.contact">Contact</dhv:label></a></strong>
        <%= orgHistoryListInfo.getSortIcon("c.namelast") %>
      </th>
      <th nowrap>
        <strong><a href="AccountsHistory.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=history.entered"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></a></strong>
        <%= orgHistoryListInfo.getSortIcon("history.entered") %>
      </th>
      <th nowrap>
        <strong><a href="AccountsHistory.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=history.modified"><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></a></strong>
        <%= orgHistoryListInfo.getSortIcon("history.modified") %>
      </th>
    </tr>
<%
	Iterator j = historyList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
		  rowid = (rowid != 1?1:2);
      OrganizationHistory thisHistoryElement = (OrganizationHistory) j.next();
      String modify = thisHistoryElement.getPermission(true);
      String view = thisHistoryElement.getPermission(false);
      String delete = thisHistoryElement.getDeletePermission();
      String historyPermission = thisHistoryElement.getViewOrModifyOrDeletePermission();
      boolean canView = false;
      boolean canModify = false;
      boolean canDelete = false;
%>
		<tr class="row<%= rowid %>">
      <td valign="top">
        <dhv:permission name="<%= historyPermission %>">
          <dhv:permission name="<%= view %>">
            <% canView = true; %>
          </dhv:permission>
          <dhv:permission name="<%= modify %>">
            <% if (thisHistoryElement.getEnabled()) {canModify = true;} %>
          </dhv:permission>
          <dhv:permission name="<%= delete %>">
            <% canDelete = true; %>
          </dhv:permission>
          <a href="javascript:displayMenu('select<%= i %>','menuAccountHistory', '<%= thisHistoryElement.getId() %>', '<%= thisHistoryElement.getOrgId() %>','<%= thisHistoryElement.getContactId() %>', '<%= thisHistoryElement.getLinkObjectId() %>', '<%= thisHistoryElement.getLinkItemId() %>', <%= canView %>, <%= canModify %>, <%= canDelete %>);"
          onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuAccountHistory');">
          <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0" /></a>
        </dhv:permission>
        <dhv:permission name="<%= historyPermission %>" none="true">&nbsp;</dhv:permission>
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><%= toHtml(thisHistoryElement.getType()) %></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><%= toHtml(thisHistoryElement.getType()) %></dhv:evaluate>
      </td>
      <td valign="top" width="100%">
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><%= toHtml(thisHistoryElement.getDescription()) %></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><%= toHtml(thisHistoryElement.getDescription()) %></dhv:evaluate>
      </td>
      <td valign="top">
        <dhv:evaluate if="<%= thisHistoryElement.getContactId() != -1 %>">
          <dhv:contactname id="<%= thisHistoryElement.getContactId() %>" listName="contacts"/>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getContactId() == -1 %>">
          &nbsp;
        </dhv:evaluate>
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></dhv:evaluate>
        <%-- <zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> --%>
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></dhv:evaluate>
        <%-- <zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> --%>
      </td>
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan="6">
        <dhv:label name="accounts.accountHistory.noHistoryFound">No history found.</dhv:label>
      </td>
    </tr>
<%}%>
	</table>
	<br />
  <dhv:pagedListControl object="orgHistoryListInfo"/>
</dhv:container>
