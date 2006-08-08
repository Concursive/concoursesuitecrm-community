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
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="historyList" class="org.aspcfs.modules.accounts.base.OrganizationHistoryList" scope="request"/>
<jsp:useBean id="accountContactHistoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_history_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');

  function checkForm(form) {
  alert('checking the form');
    if (!form.searchcodeNotes.checked) {
      form.searchcodeNotes.value="false";
    }
    if (!form.searchcodeActivities.checked) {
      form.searchcodeActivities.value='false';
    }
    if (!form.searchcodeEmail.checked) {
      form.searchcodeEmail.value='false';
    }
    if (!form.searchcodeTickets.checked) {
      form.searchcodeTickets.value='false';
    }
    if (!form.searchcodeQuotes.checked) {
      form.searchcodeQuotes.value='false';
    }
    if (!form.searchcodeTasks.checked) {
      form.searchcodeTasks.value='false';
    }
    if (!form.searchcodeServiceContracts.checked) {
      form.searchcodeServiceContracts.value='false';
    }
    if (!form.searchcodeOpportunities.checked) {
      form.searchcodeOpportunities.value='false';
    }
    alert('the value of notes is '+ form.searchcodeNotes.value);
    return true;
  }

  function reopen() {
    window.location.href='AccountContactsHistory.do?command=View&contactId = <%= ContactDetails.getId() %>';
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
 <tr><td>
  <a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
  <a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
  <a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
  <dhv:label name="reports.helpdesk.ticket.history.history">History</dhv:label>
 </td></tr>
</table>
<%-- End Trails --%>
<dhv:container name="accounts" selected="history" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <dhv:container name="accountscontacts" selected="history" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
<form name="history" action="AccountContactsHistory.do?command=View&contactId=<%= ContactDetails.getId() %>" method="post" onSubmit="return checkForm(this);">
<input type="hidden" name="searchcodeContactId" value="<%= ContactDetails.getId() %>"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="empty">
    <tr>
      <td>
        <a href=" javascript:popURL('AccountContactsHistory.do?command=AddNote&contactId=<%= ContactDetails.getId() %>','Note','575','200','yes','yes');" ><dhv:label name="accounts.accountHistory.addANote">Add a Note</dhv:label></a>
      </td>
      <td>
        <table cellpadding="4" cellspacing="0" class="empty">
          <tr>
            <td><input type="checkbox" name="searchcodeNotes" value="true" <%= accountContactHistoryListInfo.getSearchOptionValue("searchcodeNotes").equals("true")?"checked":"" %> /> <dhv:label name="dependency.quotenotes">Notes</dhv:label></td>
            <td><input type="checkbox" name="searchcodeActivities" value="true" <%= accountContactHistoryListInfo.getSearchOptionValue("searchcodeActivities").equals("true")?"checked":"" %> /> <dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label></td>
            <td><input type="checkbox" name="searchcodeEmail" value="true" <%= accountContactHistoryListInfo.getSearchOptionValue("searchcodeEmail").equals("true")?"checked":"" %> /> <dhv:label name="accounts.accounts_add.Email">Email</dhv:label></td>
            <td><input type="checkbox" name="searchcodeTickets" value="true" <%= accountContactHistoryListInfo.getSearchOptionValue("searchcodeTickets").equals("true")?"checked":"" %> /> <dhv:label name="project.tickets">Tickets</dhv:label></td>
         </tr><tr>
            <td><input type="checkbox" name="searchcodeQuotes" value="true" <%= accountContactHistoryListInfo.getSearchOptionValue("searchcodeQuotes").equals("true")?"checked":"" %> /> <dhv:label name="dependency.quotes">Quotes</dhv:label></td>
            <td><input type="checkbox" name="searchcodeTasks" value="true" <%= accountContactHistoryListInfo.getSearchOptionValue("searchcodeTasks").equals("true")?"checked":"" %> /> <dhv:label name="calendar.Tasks">Tasks</dhv:label></td>
            <td><input type="checkbox" name="searchcodeServiceContracts" value="true" <%= accountContactHistoryListInfo.getSearchOptionValue("searchcodeServiceContracts").equals("true")?"checked":"" %> /> <dhv:label name="accounts.servicecontracts.long_html">Service Contracts</dhv:label></td>
            <td><input type="checkbox" name="searchcodeOpportunities" value="true" <%= accountContactHistoryListInfo.getSearchOptionValue("searchcodeOpportunities").equals("true")?"checked":"" %> /> <dhv:label name="accounts.accounts_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label></td>
            <zeroio:debug value="<%= "JSP:"+ accountContactHistoryListInfo.getSearchOptionValue("searchcodeNotes") %>" />
            <zeroio:debug value="<%= "JSP:"+ historyList.getNotes() %>" />
            <td>
              <input type="submit" value="<dhv:label name="button.refresh">Refresh</dhv:label>" />
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
  <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="accountContactHistoryListInfo"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th width="8" nowrap>&nbsp;</th>
      <th nowrap>
        <strong><a href="AccountContactsHistory.do?command=View&contactId = <%= ContactDetails.getId() %>&column=modified"><dhv:label name="admin.dateTime">Date/Time</dhv:label></a></strong>
        <%= accountContactHistoryListInfo.getSortIcon("modified") %>
      </th>
      <th nowrap>
        <strong><a href="AccountContactsHistory.do?command=View&contactId = <%= ContactDetails.getId() %>&column=contact_id"><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></a></strong>
        <%= accountContactHistoryListInfo.getSortIcon("contact_id") %>
      <th nowrap>
        <strong><a href="AccountContactsHistory.do?command=View&contactId = <%= ContactDetails.getId() %>&column=type"><dhv:label name="reports.accounts.type">Type</dhv:label></a></strong>
        <%= accountContactHistoryListInfo.getSortIcon("type") %>
      </th>
      <th width="100%">
        <strong><dhv:label name="reports.helpdesk.ticket.maintenance.partDescription">Description</dhv:label></strong>
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
%>
		<tr class="row<%= rowid %>">
      <td valign="top">
        <a href="javascript:displayMenu('select<%= i %>','menuAccountHistory', '<%= thisHistoryElement.getId() %>', '<%= thisHistoryElement.getOrgId() %>','<%= thisHistoryElement.getContactId() %>', '<%= thisHistoryElement.getLinkObjectId() %>', '<%= thisHistoryElement.getLinkItemId() %>');"
        onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuAccountHistory');">
        <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0" /></a>
      </td>
      <td valign="top" nowrap>
        <zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
      <td>
        <dhv:evaluate if="<%= thisHistoryElement.getContactId() != -1 %>">
          <dhv:contactname id="<%= thisHistoryElement.getContactId() %>" listName="contacts"/>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getContactId() == -1 %>">
          &nbsp;
        </dhv:evaluate>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(thisHistoryElement.getType()) %>
      </td>
      <td valign="top" width="100%">
        <%= toHtml(thisHistoryElement.getDescription()) %>
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
  <dhv:pagedListControl object="accountContactHistoryListInfo"/>
  </dhv:container>
</dhv:container>

