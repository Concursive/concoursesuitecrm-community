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
<a href="Admin.do">Admin</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
Object Events
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="50%">
      <strong>Object Event</strong>
    </th>
    <th width="50%" nowrap>
      <strong>Triggered Process</strong>
    </th>
    <th nowrap>
      <strong>Number of<br>components</strong>
    </th>
    <th>
      <strong>Available</strong>
    </th>
  </tr>
<dhv:evaluate if="<%= hookList.values().size() == 0 %>">
  <tr>
    <td colspan="5">
      No object events found.
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
      <%= toHtml(thisAction.getTypeText()) %>
      <%= toHtml(thisAction.getBaseClassName().toLowerCase()) %>
      object
    </td>
    <td valign="top">
       <a href="AdminObjectEvents.do?command=Workflow&moduleId=<%= PermissionCategory.getId() %>&process=<%= thisAction.getProcessId() %>&return=AdminObjectEvents"><%= toHtml(thisAction.getProcessName()) %></a>
    </td>
    <td align="center" valign="top">
      <%= ((BusinessProcess) processList.get(thisAction.getProcessName())).getComponents().size() %>
    </td>
    <td align="center" valign="top">
      <dhv:evaluate if="<%= ((BusinessProcess) processList.get(thisAction.getProcessName())).getEnabled() %>">
        Yes
      </dhv:evaluate>
      <dhv:evaluate if="<%= !((BusinessProcess) processList.get(thisAction.getProcessName())).getEnabled() %>">
        No
      </dhv:evaluate>
    </td>
  </tr>
<%
      }
    }
%>
</table>
