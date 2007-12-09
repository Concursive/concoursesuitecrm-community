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
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.apps.workFlowManager.*" %>
<%@ page import="org.aspcfs.controller.objectHookManager.*" %>
<jsp:useBean id="processList" class="org.aspcfs.apps.workFlowManager.BusinessProcessList" scope="request"/>
<jsp:useBean id="hookList" class="org.aspcfs.controller.objectHookManager.ObjectHookList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="admin_object_events_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Admin.do?command=Config"><dhv:label name="trails.configureModules">Configure Modules</dhv:label></a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
<dhv:label name="admin.objectEvents">Object Events</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      &nbsp;
    </th>
    <th width="50%">
      <strong><dhv:label name="admin.objectEvent">Object Event</dhv:label></strong>
    </th>
    <th width="50%" nowrap>
      <strong><dhv:label name="admin.triggeredProcess">Triggered Process</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="admin.numberOfComponents" param="break=<br />">Number of<br />components</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="admin.available">Available</dhv:label></strong>
    </th>
  </tr>
<dhv:evaluate if="<%= hookList.values().size() == 0 %>">
  <tr>
    <td colspan="5">
      <dhv:label name="admin.noObjectEventsFound">No object events found.</dhv:label>
    </td>
  </tr>
</dhv:evaluate>
<%
    Iterator hooks = hookList.values().iterator();
    int count = 0;
    int rowid = 0;
    while (hooks.hasNext()) {
      ObjectHookActionList actionList = (ObjectHookActionList) hooks.next();
      Iterator actions = actionList.values().iterator();
      while (actions.hasNext()) {
        count++;
        rowid = (rowid != 1 ? 1 : 2);
        ObjectHookAction thisAction = (ObjectHookAction) actions.next();
%>
  <tr class="row<%= rowid %>">
    <td align="center" valign="top">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
          <a href="javascript:displayMenu('select<%= count %>','menuProcess', '<%= PermissionCategory.getId() %>', '<%= thisAction.getProcessId() %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuProcess');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td width="50%" valign="top">
      <dhv:label name="admin.object.lowercase" param='<%= "action.TypeText="+toHtml(thisAction.getTypeText())+"|action.baseClassName="+toHtml(thisAction.getBaseClassName().toLowerCase()) %>'><%= toHtml(thisAction.getTypeText()) %> <%= toHtml(thisAction.getBaseClassName().toLowerCase()) %> object</dhv:label>
    </td>
    <td valign="top">
       <a href="AdminObjectEvents.do?command=Workflow&moduleId=<%= PermissionCategory.getId() %>&process=<%= thisAction.getProcessId() %>&return=AdminObjectEvents"><%= toHtml(thisAction.getProcessName()) %></a>
    </td>
    <td align="center" valign="top">
      <%= ((BusinessProcess) processList.get(thisAction.getProcessName())).getComponents().size() %>
    </td>
    <td align="center" valign="top">
      <dhv:evaluate if="<%= ((BusinessProcess) processList.get(thisAction.getProcessName())).getEnabled() %>">
        <dhv:label name="account.yes">Yes</dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !((BusinessProcess) processList.get(thisAction.getProcessName())).getEnabled() %>">
        <dhv:label name="account.no">No</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%
      }
    }
%>
</table>
