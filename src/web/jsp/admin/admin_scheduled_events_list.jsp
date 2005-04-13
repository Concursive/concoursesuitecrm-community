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
<%@ page import="java.util.*,org.aspcfs.apps.workFlowManager.*" %>
<jsp:useBean id="processList" class="org.aspcfs.apps.workFlowManager.BusinessProcessList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
<dhv:label name="admin.scheduledEvents">Scheduled Events</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      &nbsp;
    </th>
    <th width="50%" nowrap>
      <strong><dhv:label name="admin.processToExecute">Process to Execute</dhv:label></strong>
    </th>
    <th width="50%">
      <strong><dhv:label name="admin.schedule">Schedule</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="admin.numberOfComponents" param="break=<br />">Number of<br />components</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="admin.available">Available</dhv:label></strong>
    </th>
  </tr>
<%
    int rowid = 0;
    Iterator i = processList.values().iterator();
    if(i.hasNext()) {
    while (i.hasNext()) {
      rowid = (rowid != 1 ? 1 : 2);
      BusinessProcess thisProcess = (BusinessProcess) i.next();
%>
  <tr class="row<%= rowid %>">
    <td align="center" valign="top">
      <dhv:label name="button.edit">Edit</dhv:label>
    </td>
    <td valign="top">
       <a href="AdminScheduledEvents.do?command=Workflow&moduleId=<%= PermissionCategory.getId() %>&process=<%= thisProcess.getId() %>&return=AdminScheduledEvents"><%= toHtml(thisProcess.getDescription()) %></a>
    </td>
    <td width="50%" valign="top">
      <dhv:evaluate if="<%= thisProcess.getEvents().isEmpty() %>">
        <dhv:label name="admin.notScheduled">Not scheduled</dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !thisProcess.getEvents().isEmpty() %>">
<%
      ScheduledEventList events = thisProcess.getEvents();
      Iterator eventsIterator = events.iterator();
      while (eventsIterator.hasNext()) {
        ScheduledEvent thisEvent = (ScheduledEvent) eventsIterator.next();
%>
        <%= toHtml(thisEvent.toString()) %>
<%
      }
%>
      </dhv:evaluate>
    </td>
    <td align="center" valign="top">
      <%= thisProcess.getComponents().size() %>
    </td>
    <td align="center" valign="top">
      <dhv:evaluate if="<%= thisProcess.getEnabled() %>">
        <dhv:label name="account.yes">Yes</dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !thisProcess.getEnabled() %>">
        <dhv:label name="account.no">No</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%
    }
  }else{
%>
   <tr class="containerBody">
    <td align="left" colspan="5">
      <dhv:label name="admin.noScheduledEventsFound">No Scheduled Events found.</dhv:label>
    </td>
   </tr>
<%
  }
%>
</table>
