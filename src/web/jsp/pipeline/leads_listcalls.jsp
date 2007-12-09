<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*" %>
<%@ page import="org.aspcfs.modules.base.*,org.aspcfs.modules.contacts.base.Call,com.zeroio.iteam.base.*" %>
<jsp:useBean id="opportunityHeader" class="org.aspcfs.modules.pipeline.base.OpportunityHeader" scope="request"/>
<jsp:useBean id="CallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="CompletedCallList" class="org.aspcfs.modules.contacts.base.CallList" scope="request"/>
<jsp:useBean id="LeadsCallsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="LeadsCompletedCallsListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="CallTypeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="callResultList" class="org.aspcfs.modules.contacts.base.CallResultList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="leads_listcalls_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
</script>
<script type="text/javascript">
function reopenOpportunity(id) {
  if (id == '<%= opportunityHeader.getId() %>') {
    if ('<%= "dashboard".equals(request.getParameter("viewSource")) %>' == 'true') {
      scrollReload('Leads.do?command=Dashboard');
    } else {
      scrollReload('Leads.do?command=Search');
    }
    return id;
  } else {
    return '<%= opportunityHeader.getId() %>';
  }
}
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do"><dhv:label name="pipeline.pipeline">Pipeline</dhv:label></a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
	<a href="Leads.do?command=Dashboard"><dhv:label name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
<% }else{ %>
	<a href="Leads.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>"><dhv:label name="accounts.accounts_contacts_oppcomponent_add.OpportunityDetails">Opportunity Details</dhv:label></a> >
<dhv:label name="accounts.accounts_calls_list.Activities">Activities</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <dhv:label name="pipeline.viewpoint.colon" param='<%= "username="+PipelineViewpointInfo.getVpUserName() %>'><b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b></dhv:label><br />
  &nbsp;<br>
</dhv:evaluate>
<% String param1 = "id=" + opportunityHeader.getId(); 
   String param2 = addLinkParams(request, "viewSource");
   int i = 0;
%>            
<dhv:container name="opportunities" selected="calls" object="opportunityHeader" param="<%= param1 %>" appendToUrl="<%= param2 %>">
  <dhv:hasAuthority owner="<%= opportunityHeader.getManagerOwnerIdRange() %>">
  <dhv:evaluate if="<%= !opportunityHeader.isTrashed() %>" >
    <dhv:permission name="pipeline-opportunities-calls-add">
      <a href="LeadsCalls.do?command=Log&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>&return=list"><dhv:label name="accounts.accounts_contacts_calls_list.LogAnActivity">Log an Activity</dhv:label></a>
      |
      <a href="LeadsCalls.do?command=Schedule&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>&return=list"><dhv:label name="accounts.accounts_contacts_calls_list.ScheduleAnActivity">Schedule an Activity</dhv:label></a><br />
      
      <br /><br />
    </dhv:permission>
  </dhv:evaluate>
  </dhv:hasAuthority>
  <% if ((request.getParameter("pagedListSectionId") == null && !LeadsCompletedCallsListInfo.getExpandedSelection()) || LeadsCallsListInfo.getExpandedSelection()) { %>
  <%-- Pending list --%>
  <dhv:pagedListStatus showExpandLink="true" title="Pending Activities" type="accounts.accounts_contacts_calls_list.PendingActivities" object="LeadsCallsListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th>
        &nbsp;
      </th>
      <th nowrap="true">
        <strong><dhv:label name="accounts.accountasset_include.Contact">Contact</dhv:label></strong>
      </th>
      <th nowrap="true">
        <strong><dhv:label name="accounts.accounts_calls_list.DueDate">Due Date</dhv:label></strong>
      </th>
      <th nowrap="true">
        <strong><dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label></strong>
      </th>
      <th>
        <strong><dhv:label name="accounts.accounts_add.Type">Type</dhv:label></strong>
      </th>
      <th width="100%">
        <strong><dhv:label name="accounts.accountasset_include.Description">Description</dhv:label></strong>
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
        boolean hasPermission = false;
%>
  <dhv:hasAuthority owner="<%= opportunityHeader.getManagerOwnerIdRange() %>">
    <% hasPermission = true; %>
  </dhv:hasAuthority>
    <tr class="row<%= rowid %>">
      <td <%= LeadsCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getFollowupNotes())) ? "rowspan=\"2\"" : ""%> width="8" valign="top" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuCall', '<%= opportunityHeader.getId() %>', '<%= thisCall.getId() %>', 'pending','<%= thisCall.isTrashed() %>', '<%= hasPermission %>');"
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
        <a href="LeadsCalls.do?command=Details&headerId=<%= opportunityHeader.getId() %>&id=<%= thisCall.getId() %><%= addLinkParams(request, "viewSource") %>&view=pending&trailSource=accounts">
        <%= toHtml(thisCall.getAlertText()) %>
        </a>
      </td>
    </tr>
    <dhv:evaluate if='<%= LeadsCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getFollowupNotes())) %>'>
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
<% if ((request.getParameter("pagedListSectionId") == null && !LeadsCallsListInfo.getExpandedSelection()) || LeadsCompletedCallsListInfo.getExpandedSelection()) { %>
 <%-- Completed/Canceled list --%>
  <dhv:pagedListStatus showExpandLink="true" title="Completed/Canceled Activities" type="accounts.accounts_contacts_calls_list.CompletedCanceledActivities" object="LeadsCompletedCallsListInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th>
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
        <strong><dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label></strong>
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
        boolean hasPermission = false;
%>
  <dhv:hasAuthority owner="<%= opportunityHeader.getManagerOwnerIdRange() %>">
    <% hasPermission = true; %>
  </dhv:hasAuthority>
    <tr class="row<%= rowid %>">
      <td <%= LeadsCompletedCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getNotes())) ? "rowspan=\"2\"" : ""%> width="8" valign="top" nowrap>
         <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuCall', '<%= opportunityHeader.getId() %>', '<%= thisCall.getId() %>', '<%= thisCall.getStatusId() == Call.CANCELED ? "cancel" : ""%>','<%= thisCall.isTrashed() %>','<%= hasPermission %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>);hideMenu('menuCall');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="top" nowrap>
        <%= thisCall.getContactName() %>
      </td>
      <td valign="top" nowrap>
        <%= thisCall.getStatusString() %>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(CallTypeList.getSelectedValue(thisCall.getCallTypeId())) %>
      </td>
      <td width="50%" valign="top">
        <a href="LeadsCalls.do?command=Details&headerId=<%= opportunityHeader.getId() %>&id=<%= thisCall.getId() %><%= addLinkParams(request, "viewSource") %>&trailSource=accounts">
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
    <dhv:evaluate if='<%= LeadsCompletedCallsListInfo.getExpandedSelection()  && !"".equals(toString(thisCall.getNotes()))%>'>
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
