<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.apps.workFlowManager.*" %>
<jsp:useBean id="processList" class="org.aspcfs.apps.workFlowManager.BusinessProcessList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
Scheduled Events
</td>
</tr>
</table>
<%-- End Trails --%>
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
    int rowid = 0;
    Iterator i = processList.values().iterator();
    if(i.hasNext()) {
    while (i.hasNext()) {
      rowid = (rowid != 1 ? 1 : 2);
      BusinessProcess thisProcess = (BusinessProcess) i.next();
%>
  <tr class="row<%= rowid %>">
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
  }else{
%>
   <tr class="containerBody">
    <td align="left" colspan="5">
      No Scheduled Events found. 
    </td>
   </tr>
<%
  }
%>
</table>
