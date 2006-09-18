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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
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
<%@ include file="accounts_contacts_calls_list_menu.jsp" %>
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
<% if (request.getParameter("return") == null) { %>
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Accounts.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<%}%>
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param="<%= "orgId=" + ContactDetails.getOrgId() %>">
  <dhv:container name="accountscontacts" selected="calls" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>">
      <% int i = 0; %>
      <dhv:evaluate if="<%= !isPopup(request) %>">
        <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
          <dhv:permission name="accounts-accounts-contacts-calls-add">
            <a href="AccountContactsCalls.do?command=Log&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&return=list"><dhv:label name="accounts.accounts_contacts_calls_list.LogAnActivity">Log an Activity</dhv:label></a>
            |
            <a href="AccountContactsCalls.do?command=Schedule&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&return=list"><dhv:label name="accounts.accounts_contacts_calls_list.ScheduleAnActivity">Schedule an Activity</dhv:label></a>
            <br />
            <br />
          </dhv:permission>
        </dhv:evaluate>
      </dhv:evaluate>
    <% if ((request.getParameter("pagedListSectionId") == null && !AccountContactCompletedCallsListInfo.getExpandedSelection()) || AccountContactCallsListInfo.getExpandedSelection()) { %>
      <%-- Pending list --%>
      <dhv:pagedListStatus showExpandLink="true" title="<%= User.getSystemStatus(getServletConfig()).getLabel("accounts.accounts_contacts_calls_list.PendingActivities", "Pending Activities") %>" object="AccountContactCallsListInfo"/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th>
            &nbsp;
          </th>
          <th nowrap="true">
            <dhv:label name="accounts.accounts_calls_list.DueDate">Due Date</dhv:label>
          </th>
          <th nowrap="true">
            <dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label>
          </th>
          <th>
            <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
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
          <td <%= AccountContactCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getFollowupNotes())) ? "rowspan=\"2\"" : ""%> width="8" valign="top" nowrap>
            <%-- Use the unique id for opening the menu, and toggling the graphics --%>
            <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
              <a href="javascript:displayMenu('select<%= i %>','menuCall', '<%= ContactDetails.getId() %>', '<%= thisCall.getId() %>', 'pending','<%= thisCall.isTrashed()%>');"
              onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>);hideMenu('menuCall');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
            </dhv:evaluate>
            <dhv:evaluate if="<%= !ContactDetails.getEnabled() || ContactDetails.isTrashed() %>">&nbsp;</dhv:evaluate>
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
            <a href="AccountContactsCalls.do?command=Details&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&view=pending">
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
          <td colspan="5">
            <dhv:label name="accounts.accounts_calls_list.NoActivitiesFound">No activities found.</dhv:label>
          </td>
        </tr>
    <%}%>
     </table>
     <br>
<%}%>
<% if ((request.getParameter("pagedListSectionId") == null && !AccountContactCallsListInfo.getExpandedSelection()) || AccountContactCompletedCallsListInfo.getExpandedSelection()) { %>
     <%-- Completed/Canceled list --%>
      <%-- accounts.accounts_contacts_calls_list.CompletedCanceledActivities --%>
      <dhv:pagedListStatus showExpandLink="true" title="<%= User.getSystemStatus(getServletConfig()).getLabel("accounts.accounts_contacts_calls_list.CompletedCanceledActivities", "Completed/Canceled Activities") %>" object="AccountContactCompletedCallsListInfo"/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th>
            &nbsp;
          </th>
          <th>
            <dhv:label name="accounts.accountasset_include.Status">Status</dhv:label>
          </th>
          <th>
            <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
          </th>
          <th width="50%">
            <dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label>
          </th>
          <th width="50%">
            <dhv:label name="accounts.accounts_calls_list.Result">Result</dhv:label>
          </th>
          <th nowrap="true">
            <dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label>
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
          <td <%= AccountContactCompletedCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getNotes())) ? "rowspan=\"2\"" : ""%> width="8" valign="top" nowrap>
             <%-- Use the unique id for opening the menu, and toggling the graphics --%>
            <dhv:evaluate if="<%= ContactDetails.getEnabled() && !ContactDetails.isTrashed() %>">
               <a href="javascript:displayMenu('select<%= i %>','menuCall','<%= ContactDetails.getId() %>','<%= thisCall.getId() %>','<%= thisCall.getStatusId() == Call.CANCELED ? "cancel" : "" %>','<%= thisCall.isTrashed()%>');"
               onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>);hideMenu('menuCall');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
            </dhv:evaluate>
            <dhv:evaluate if="<%= !ContactDetails.getEnabled() || ContactDetails.isTrashed() %>">&nbsp;</dhv:evaluate>
          </td>
          <td valign="top" nowrap>
            <%= thisCall.getStatusString() %>
          </td>
          <td valign="top" nowrap>
            <%= toHtml(CallTypeList.getSelectedValue(thisCall.getCallTypeId())) %>
          </td>
          <td width="50%" valign="top">
            <dhv:evaluate if="<%= !isPopup(request) %>">
            <a href="AccountContactsCalls.do?command=Details&id=<%= thisCall.getId() %>&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>">
              <%= toHtml(thisCall.getSubject()) %>
            </a>
            </dhv:evaluate>
            <dhv:evaluate if="<%= isPopup(request) %>">
              <%= toHtml(thisCall.getSubject()) %>
            </dhv:evaluate>
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
</dhv:container>
