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
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="historyList" class="org.aspcfs.modules.contacts.base.ContactHistoryList" scope="request"/>
<jsp:useBean id="contactHistoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="companydirectory_history_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
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
  
</script>
<script language="JavaScript" TYPE="text/javascript">
function reopen() {
  window.location.href='ExternalContactsHistory.do?command=List&contactId=<%= ContactDetails.getId() %>&popup=<%= isPopup(request) %>';
}
</script>
<% String param2 = addLinkParams(request, "popup|popupType|actionId");%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="ExternalContacts.do"><dhv:label name="Contacts" mainMenuItem="true">Contacts</dhv:label></a> >
  <a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
  <dhv:label name="reports.helpdesk.ticket.history.history">History</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="contacts" selected="history" object="ContactDetails" param='<%= "id=" + ContactDetails.getId() %>' appendToUrl="<%= param2 %>">
<dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>"><dhv:permission name="contacts-external_contacts-history-add">
<a href=" javascript:popURL('ExternalContactsHistory.do?command=AddNote&contactId=<%= ContactDetails.getId() %>','Note','575','200','yes','yes');" ><dhv:label name="accounts.accountHistory.addANote">Add a Note</dhv:label></a><br /><br />
</dhv:permission></dhv:evaluate>
<span name="filterForm" id="filterForm" style="display:none">
<form name="history" action="ExternalContactsHistory.do?command=List&contactId=<%= ContactDetails.getId() %><%= param2 %>" method="post" onSubmit="return checkForm(this);">
<input type="hidden" name="searchcodeContactId" value="<%= ContactDetails.getId() %>"/>
<% boolean check = contactHistoryListInfo.getSavedCriteria().size() == 0; %>
<table cellpadding="4" cellspacing="0" class="empty"><tr><td>
  <table cellpadding="0" cellspacing="0" class="floatWrap"><tr><td valign="top" width="100%">
    <div><dhv:permission name="contacts-external_contacts-history-view">
      <input type="checkbox" name="searchcodeNotes" value="true" <%= contactHistoryListInfo.getSearchOptionValue("searchcodeNotes").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="contacts-external_contacts-calls-edit,contacts-external_contacts-calls-view">
      <input type="checkbox" name="searchcodeActivities" value="true" <%= contactHistoryListInfo.getSearchOptionValue("searchcodeActivities").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="myhomepage-inbox-view,myhomepage-inbox-edit,contacts-external_contacts-messages-view">
      <input type="checkbox" name="searchcodeEmail" value="true" <%= contactHistoryListInfo.getSearchOptionValue("searchcodeEmail").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_add.EmailOrMessages">Email/Messages</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="tickets-tickets-edit,tickets-tickets-view">
      <input type="checkbox" name="searchcodeTickets" value="true" <%= contactHistoryListInfo.getSearchOptionValue("searchcodeTickets").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.tickets.tickets">Tickets</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="accounts-assets-view,accounts-assets-edit">
      <input type="checkbox" name="searchcodeAssets" value="true" <%= contactHistoryListInfo.getSearchOptionValue("searchcodeAssets").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.Assets">Assets</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="quotes-quotes-edit,quotes-quotes-view">
      <input type="checkbox" name="searchcodeQuotes" value="true" <%= contactHistoryListInfo.getSearchOptionValue("searchcodeQuotes").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_quotes_list.Quotes">Quotes</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="myhomepage-tasks-edit,myhomepage-tasks-view">
      <input type="checkbox" name="searchcodeTasks" value="true" <%= contactHistoryListInfo.getSearchOptionValue("searchcodeTasks").equals("true")||check?"checked":"" %> /> <dhv:label name="myitems.tasks">Tasks</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="accounts-service-contracts-edit,accounts-service-contracts-view">
      <input type="checkbox" name="searchcodeServiceContracts" value="true" <%= contactHistoryListInfo.getSearchOptionValue("searchcodeServiceContracts").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_sc_add.ServiceContracts">Service Contracts</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="contacts-external_contacts-opportunities-edit,contacts-external_contacts-opportunities-view">
      <input type="checkbox" name="searchcodeOpportunities" value="true" <%= contactHistoryListInfo.getSearchOptionValue("searchcodeOpportunities").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label>
    </dhv:permission></div>
<%--  <td><input type="checkbox" name="searchcodeShowDisabledWithEnabled" value="true" <%= contactHistoryListInfo.getSearchOptionValue("searchcodeShowDisabledWithEnabled").equals("true")||check?"checked":"" %> /> <dhv:label name="global.trashed">Trashed</dhv:label>  --%>
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
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="contactHistoryListInfo" externalJScript="flipFilterForm();" externalText="<dhv:label name=\"pagedListInfo.filters\">Filters</dhv:label>"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th width="8" nowrap>&nbsp;</th>
      <th nowrap>
        <strong><a href="ExternalContactsHistory.do?command=List&contactId=<%= ContactDetails.getId() %>&column=type<%= isPopup(request)?"&popup=true":"" %>"><dhv:label name="reports.accounts.type">Type</dhv:label></a></strong>
        <%= contactHistoryListInfo.getSortIcon("type") %>
      </th>
      <th width="100%">
        <strong><dhv:label name="reports.helpdesk.ticket.maintenance.partDescription">Description</dhv:label></strong>
      </th>
      <th nowrap>
        <strong><a href="ExternalContactsHistory.do?command=List&contactId=<%= ContactDetails.getId() %>&column=entered<%= isPopup(request)?"&popup=true":"" %>"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></a></strong>
        <%= contactHistoryListInfo.getSortIcon("entered") %>
      </th>
      <th nowrap>
        <strong><a href="ExternalContactsHistory.do?command=List&contactId=<%= ContactDetails.getId() %>&column=modified<%= isPopup(request)?"&popup=true":"" %>"><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></a></strong>
        <%= contactHistoryListInfo.getSortIcon("modified") %>
      </th>
    </tr>
<%
	Iterator j = historyList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i = 0;
    boolean popup = isPopup(request);
    while (j.hasNext()) {
      i++;
		  rowid = (rowid != 1?1:2);
      ContactHistory thisHistoryElement = (ContactHistory) j.next();
      String modify = thisHistoryElement.getPermission(true, (ContactDetails.getOrgId() == -1));
      String view = thisHistoryElement.getPermission(false,  (ContactDetails.getOrgId() == -1));
      String delete = thisHistoryElement.getDeletePermission((ContactDetails.getOrgId() == -1));
      String historyPermission = thisHistoryElement.getViewOrModifyOrDeletePermission((ContactDetails.getOrgId() == -1));
      System.out.println("view = "+ view+" , edit = "+ modify+" and delete = "+ delete);
      boolean canView = false;
      boolean canModify = false;
      boolean canDelete = false;
%>
		<tr class="row<%= rowid %>">
    <td valign="top" nowrap>
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
        <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
          <dhv:evaluate if="<%= (isPopup(request) && thisHistoryElement.canDisplayInPopup()) || !isPopup(request) %>">
            <a href="javascript:displayMenu('select<%= i %>','menuContactHistory', '<%= thisHistoryElement.getId() %>', '<%= thisHistoryElement.getContactId() %>', '<%= thisHistoryElement.getLinkObjectId() %>', '<%= thisHistoryElement.getLinkItemId() %>', <%= canView %>, <%= canModify %>, <%= canDelete %>);"
            onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuContactHistory');">
            <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0" /></a>
          </dhv:evaluate>
        </dhv:evaluate>
        <dhv:evaluate if="<%= (isPopup(request) && !thisHistoryElement.canDisplayInPopup()) || !ContactDetails.getEnabled() || ContactDetails.isTrashed() %>">&nbsp;</dhv:evaluate>
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
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></dhv:evaluate>
        <%-- <zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> --%>
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></dhv:evaluate>
        <%--<zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> --%>
      </td>
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan="5">
        <dhv:label name="accounts.accountHistory.noHistoryFound">No history found.</dhv:label>
      </td>
    </tr>
<%}%>
	</table>
	<br />
  <dhv:pagedListControl object="contactHistoryListInfo"/>
</dhv:container>
