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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="CallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="CompletedCallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="AccountContactCallsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountContactCompletedCallsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="callResultList" class="org.aspcfs.modules.contacts.base.CallResultList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_calls_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<% int i = 0; %>
<dhv:container name="accounts" selected="activities" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
<dhv:permission name="accounts-accounts-contacts-calls-add">
  <a href="AccountsCalls.do?command=Log&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>&return=list&trailSource=accounts"><dhv:label name="accounts.accounts_contacts_calls_list.LogAnActivity">Log an Activity</dhv:label></a>
|
  <a href="AccountsCalls.do?command=Schedule&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>&return=list&trailSource=accounts"><dhv:label name="accounts.accounts_contacts_calls_list.ScheduleAnActivity">Schedule an Activity</dhv:label></a><br />
<br />
</dhv:permission>
  <% if ((request.getParameter("pagedListSectionId") == null && !AccountContactCompletedCallsListInfo.getExpandedSelection()) || AccountContactCallsListInfo.getExpandedSelection()) { %>
  <%-- Pending list --%>
  <dhv:pagedListStatus showExpandLink="true" title="Scheduled Activities" object="AccountContactCallsListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="8">
        &nbsp;
      </th>
      <th nowrap="true">
        <dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label>
      </th>
      <th nowrap="true">
        <dhv:label name="accounts.accounts_calls_list.DueDate">Due Date</dhv:label>
      </th>
      <th nowrap="true">
        <dhv:label name="accounts.accounts_calls_list.AssignedTo">Assigned To</dhv:label>
      </th>
      <th>
        <dhv:label name="accounts.accounts_add.Type">Type</dhv:label></strong>
      </th>
      <th width="100%">
        <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
      </th>
    </tr>
<%
      Iterator j = CallList.iterator();
      if ( j.hasNext() ) {
        int rowid = 0;
          while (j.hasNext()) {
          i++;
            rowid = (rowid != 1?1:2);
            Call thisCall = (Call) j.next();
%>
    <tr class="row<%= rowid %>">
      <td <%= AccountContactCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getFollowupNotes())) ? "rowspan=\"2\"" : ""%> width="9" valign="top" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuCall', '<%= thisCall.getContactId() %>', '<%= thisCall.getId() %>', 'pending','<%= thisCall.getOrgId()%>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>);hideMenu('menuCall');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(thisCall.getContactName()) %>
      </td>
      <td valign="top" nowrap>
<% if(!User.getTimeZone().equals(thisCall.getAlertDateTimeZone())){%>
<zeroio:tz timestamp="<%= thisCall.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
<% } else { %>
<zeroio:tz timestamp="<%= thisCall.getAlertDate() %>" dateOnly="true" timeZone="<%= thisCall.getAlertDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
<% } %>
      </td>
      <td valign="top" nowrap>
        <dhv:username id="<%= thisCall.getOwner() %>" firstInitialLast="true"/>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(CallTypeList.getSelectedValue(thisCall.getAlertCallTypeId())) %>
      </td>
      <td width="100%" valign="top">
        <a href="AccountsCalls.do?command=Details&id=<%= thisCall.getId() %>&orgId=<%= thisCall.getOrgId() %>&contactId=<%= thisCall.getContactId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&view=pending&trailSource=accounts">
        <%= toHtml(thisCall.getAlertText()) %>
        </a>
      </td>
    </tr>
    <dhv:evaluate if="<%= AccountContactCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getFollowupNotes())) %>">
        <tr class="row<%= rowid %>">
          <td colspan="5" valign="top">
            <%= toHtmlValue(thisCall.getFollowupNotes()) %>
          </td>
        </tr>
    </dhv:evaluate>
 <%}%>
<%} else {%>
    <tr class="containerBody">
      <td colspan="6">
        <dhv:label name="accounts.accounts_calls_list.NoActivitiesFound">No activities found.</dhv:label>
      </td>
    </tr>
<%}%>
  </table>
  <br>
<%}%>
<% if ((request.getParameter("pagedListSectionId") == null && !AccountContactCallsListInfo.getExpandedSelection()) || AccountContactCompletedCallsListInfo.getExpandedSelection()) { %>
  <%-- Completed/Canceled list --%>
  <dhv:pagedListStatus showExpandLink="true" title="Completed/Canceled Activities" object="AccountContactCompletedCallsListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="8">
        &nbsp;
      </th>
      <th nowrap>
        <strong><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></strong>
      </th>
      <th>
        <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
      </th>
      <th>
        <strong><dhv:label name="accounts.accounts_add.Type">Type</dhv:label></strong>
      </th>
      <th width="50%">
        <strong><dhv:label name="accounts.accounts_calls_list.Subject">Subject</dhv:label></strong>
      </th>
      <th width="50%">
        <dhv:label name="accounts.accounts_calls_list.Result">Result</dhv:label>
      </th>
      <th nowrap="true">
        <strong><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></strong>
      </th>
    </tr>
<%
      Iterator jc = CompletedCallList.iterator();
      if ( jc.hasNext() ) {
        int rowid = 0;
          while (jc.hasNext()) {
          i++;
            rowid = (rowid != 1?1:2);
            Call thisCall = (Call) jc.next();
%>
    <tr class="row<%= rowid %>">
      <td <%= AccountContactCompletedCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getNotes())) ? "rowspan=\"2\"" : ""%> width="9" valign="top" nowrap>
         <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuCall', '<%= thisCall.getContactId() %>', '<%= thisCall.getId() %>', '<%= thisCall.getStatusId() == Call.CANCELED ? "cancel" : ""%>', '<%= thisCall.getOrgId() %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>);hideMenu('menuCall');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(thisCall.getContactName()) %>
      </td>
      <td valign="top" nowrap>
        <%= thisCall.getStatusString() %>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(CallTypeList.getSelectedValue(thisCall.getCallTypeId())) %>
      </td>
      <td width="50%" valign="top">
        <a href="AccountsCalls.do?command=Details&id=<%= thisCall.getId() %>&orgId=<%= thisCall.getOrgId() %>&contactId=<%= thisCall.getContactId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&trailSource=accounts">
          <%= toHtml(thisCall.getSubject()) %>
        </a>
      </td>
      <td width="50%" valign="top">
        <%= toHtml(callResultList.getLookupList(thisCall.getResultId()).getSelectedValue(thisCall.getResultId())) %>
      </td>
      <td valign="top" nowrap>
        <dhv:username id="<%= thisCall.getEnteredBy() %>" firstInitialLast="true"/><br />
        <zeroio:tz timestamp="<%= thisCall.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
      </td>
    </tr>
    <dhv:evaluate if="<%= AccountContactCompletedCallsListInfo.getExpandedSelection()  && !"".equals(toString(thisCall.getNotes()))%>">
    <tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
        <%= toHtmlValue(thisCall.getNotes()) %>
      </td>
    </tr>
    </dhv:evaluate>
 <%}%>
<%} else {%>
    <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="accounts.accounts_calls_list.NoActivitiesFound">No activities found.</dhv:label>
      </td>
    </tr>
<%}%>
  </table>
<%}%>
</dhv:container>