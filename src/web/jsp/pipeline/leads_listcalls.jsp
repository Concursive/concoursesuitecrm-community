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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.pipeline.base.*,org.aspcfs.modules.contacts.base.Call,com.zeroio.iteam.base.*" %>
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
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do">Pipeline</a> >
<% if ("dashboard".equals(request.getParameter("viewSource"))){ %>
	<a href="Leads.do?command=Dashboard">Dashboard</a> >
<% }else{ %>
	<a href="Leads.do?command=Search">Search Results</a> >
<% } %>
<a href="Leads.do?command=DetailsOpp&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>">Opportunity Details</a> >
Activities
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%@ include file="leads_details_header_include.jsp" %>
<% String param1 = "id=" + opportunityHeader.getId(); 
   String param2 = addLinkParams(request, "viewSource");
   int i = 0;
%>            
<dhv:container name="opportunities" selected="calls" param="<%= param1 %>" appendToUrl="<%= param2 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <dhv:permission name="pipeline-opportunities-calls-add"><a href="LeadsCalls.do?command=Add&headerId=<%= opportunityHeader.getId() %><%= addLinkParams(request, "viewSource") %>&return=list">Add an Activity</a><br><br></dhv:permission>
      <% if ((request.getParameter("pagedListSectionId") == null && !LeadsCompletedCallsListInfo.getExpandedSelection()) || LeadsCallsListInfo.getExpandedSelection()) { %>
      <%-- Pending list --%>
      <dhv:pagedListStatus showExpandLink="true" title="Pending Activities" object="LeadsCallsListInfo"/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th>
            <strong>Action</strong>
          </th>
          <th nowrap="true">
            <strong>Contact</strong>
          </th>
          <th nowrap="true">
            <strong>Due Date</strong>
          </th>
          <th nowrap="true">
            <strong>Assigned To</strong>
          </th>
          <th>
            <strong>Type</strong>
          </th>
          <th width="100%">
            <strong>Description</strong>
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
          <td <%= LeadsCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getFollowupNotes())) ? "rowspan=\"2\"" : ""%> width="8" valign="top" nowrap>
            <%-- Use the unique id for opening the menu, and toggling the graphics --%>
             <a href="javascript:displayMenu('select<%= i %>','menuCall', '<%= opportunityHeader.getId() %>', '<%= thisCall.getId() %>', 'pending');"
             onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>);hideMenu('menuCall');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
          </td>
          <td valign="top" nowrap>
            <%= toHtml(thisCall.getContactName()) %>
          </td>
          <td valign="top" nowrap>
            <% if(!User.getTimeZone().equals(thisCall.getAlertDateTimeZone())){%>
            <zeroio:tz timestamp="<%= thisCall.getAlertDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
            <% } else { %>
            <zeroio:tz timestamp="<%= thisCall.getAlertDate() %>" dateOnly="true" timeZone="<%= thisCall.getAlertDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
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
        <dhv:evaluate if="<%= LeadsCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getFollowupNotes())) %>">
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
            No activities found.
          </td>
        </tr>
    <%}%>
     </table>
     <br>
<%}%>
<% if ((request.getParameter("pagedListSectionId") == null && !LeadsCallsListInfo.getExpandedSelection()) || LeadsCompletedCallsListInfo.getExpandedSelection()) { %>
     <%-- Completed/Canceled list --%>
      <dhv:pagedListStatus showExpandLink="true" title="Completed/Canceled Activities" object="LeadsCompletedCallsListInfo"/>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th>
            <strong>Action</strong>
          </th>
          <th nowrap>
            <strong>Contact</strong>
          </th>
          <th>
            Status
          </th>
          <th>
            <strong>Type</strong>
          </th>
          <th width="100%">
            <strong>Subject</strong>
          </th>
          <th>
            Result
          </th>
          <th nowrap="true">
            <strong>Entered By</strong>
          </th>
          <th>
            <strong>Entered</strong>
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
          <td <%= LeadsCompletedCallsListInfo.getExpandedSelection() && !"".equals(toString(thisCall.getNotes())) ? "rowspan=\"2\"" : ""%> width="8" valign="top" nowrap>
             <%-- Use the unique id for opening the menu, and toggling the graphics --%>
             <a href="javascript:displayMenu('select<%= i %>','menuCall', '<%= opportunityHeader.getId() %>', '<%= thisCall.getId() %>', '<%= thisCall.getStatusId() == Call.CANCELED ? "cancel" : ""%>');"
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
          <td width="100%" valign="top">
            <a href="LeadsCalls.do?command=Details&headerId=<%= opportunityHeader.getId() %>&id=<%= thisCall.getId() %><%= addLinkParams(request, "viewSource") %>&trailSource=accounts">
              <%= toHtml(thisCall.getSubject()) %>
            </a>
          </td>
          <td nowrap valign="top">
            <%= toHtml(callResultList.getLookupList(thisCall.getResultId()).getSelectedValue(thisCall.getResultId())) %>
          </td>
          <td valign="top" nowrap>
            <dhv:username id="<%= thisCall.getEnteredBy() %>" firstInitialLast="true"/>
          </td>
          <td valign="top" nowrap>
            <zeroio:tz timestamp="<%= thisCall.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" />
          </td>
        </tr>
        <dhv:evaluate if="<%= LeadsCompletedCallsListInfo.getExpandedSelection()  && !"".equals(toString(thisCall.getNotes()))%>">
        <tr class="row<%= rowid %>">
          <td colspan="8" valign="top">
            <%= toHtmlValue(thisCall.getNotes()) %>
          </td>
        </tr>
        </dhv:evaluate>
     <%}%>
    <%} else {%>
        <tr class="containerBody">
          <td colspan="9">
            No activities found.
          </td>
        </tr>
    <%}%>
     </table>
<%}%>
     <%-- End Container --%>
</td>
</tr>
</table>
