<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.apps.workFlowManager.*" %>
<jsp:useBean id="processList" class="org.aspcfs.apps.workFlowManager.BusinessProcessList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="Admin.do">Setup</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
Scheduled Events<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="50%" nowrap>
      <strong>Process to Execute</strong>
    </th>
    <th width="50%">
      <strong>Schedule</strong>
    </th>
    <th nowrap>
      <strong>Number of<br>components</strong>
    </th>
    <th>
      <strong>Available</strong>
    </th>
  </tr>
<%
    Iterator i = processList.values().iterator();
    while (i.hasNext()) {
      BusinessProcess thisProcess = (BusinessProcess) i.next();
%>
  <tr class="containerBody">
    <td align="center" valign="top">
      Edit
    </td>
    <td valign="top">
       <a href="AdminScheduledEvents.do?command=Workflow&moduleId=<%= PermissionCategory.getId() %>&process=<%= thisProcess.getId() %>&return=AdminScheduledEvents"><%= toHtml(thisProcess.getDescription()) %></a>
    </td>
    <td width="50%" valign="top">
      <dhv:evaluate if="<%= thisProcess.getEvents().isEmpty() %>">
        Not scheduled
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
        Yes
      </dhv:evaluate>
      <dhv:evaluate if="<%= !thisProcess.getEnabled() %>">
        No
      </dhv:evaluate>
    </td>
  </tr>
<%
    }
%>
</table>
