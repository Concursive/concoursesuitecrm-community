<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="org.aspcfs.modules.reports.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="completedQueue" class="org.aspcfs.modules.reports.base.ReportQueueList" scope="request"/>
<jsp:useBean id="pendingQueue" class="org.aspcfs.modules.reports.base.ReportQueueList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="reports_queue_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Reports.do">Reports</a> >
Queue
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td>Generated reports are deleted from the server every 24 hours.</td></tr>
</table>
<%-- Completed Reports --%>
<dhv:formMessage showSpace="false" />
<a href="Reports.do?command=RunReport">Add a Report</a><br>
<br />
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tr>
    <td nowrap class="pagedListTab"><img src="images/icons/stock_form-16.gif" align="absMiddle" alt="" />
    Generated reports ready to be retrieved</td>
    <td width="100%" align="right">Records: <%= completedQueue.size() %></td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="100%">
      <strong>Subject</strong>
    </th>
    <th>
      <strong>Date</strong>
    </th>
    <th>
      <strong>Status</strong>
    </th>
    <th>
      <strong>Size</strong>
    </th>
  </tr>
<dhv:evaluate if="<%= completedQueue.isEmpty() %>">
  <tr class="row2">
    <td colspan="5">No reports found</td>
  </tr>
</dhv:evaluate>
<%
  int rowid = 0;
  Iterator cIterator = completedQueue.iterator();
  while (cIterator.hasNext()) {
    rowid = (rowid != 1 ? 1 : 2);
    ReportQueue thisQueue = (ReportQueue) cIterator.next();
%>
  <tr class="row<%= rowid %>">
    <td><a href="javascript:displayMenu('select<%= thisQueue.getId() %>','menu1<%= (thisQueue.getStatus() == ReportQueue.STATUS_PROCESSED ? "a" : "b") %>','<%= thisQueue.getId() %>');" onMouseOver="over(0, <%= thisQueue.getId() %>)" onmouseout="out(0, <%= thisQueue.getId() %>); hideMenu('menu1<%= (thisQueue.getStatus() == ReportQueue.STATUS_PROCESSED ? "a" : "b") %>');"><img src="images/select.gif" name="select<%= thisQueue.getId() %>" id="select<%= thisQueue.getId() %>" align="absmiddle" border="0"></a></td>
    <td width="100%"><%= toHtml(thisQueue.getReport().getTitle()) %></td>
    <td nowrap><zeroio:tz timestamp="<%= thisQueue.getProcessed() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/></td>
    <td nowrap>
<dhv:evaluate if="<%= thisQueue.getStatus() == ReportQueue.STATUS_QUEUED %>">Queued</dhv:evaluate>
<dhv:evaluate if="<%= thisQueue.getStatus() == ReportQueue.STATUS_PROCESSING %>">Processing</dhv:evaluate>
<dhv:evaluate if="<%= thisQueue.getStatus() == ReportQueue.STATUS_PROCESSED %>"><font color="green">Ready to download</font></dhv:evaluate>
<dhv:evaluate if="<%= thisQueue.getStatus() == ReportQueue.STATUS_ERROR %>"><font color="red">Error creating report</font></dhv:evaluate>
    </td>
    <td nowrap align="right"><dhv:filesize bytes="<%= thisQueue.getSize() %>"/></td>
  </tr>
<%
  }
%>
</table>
<%-- Reports in Queue --%>
<br>
<table width="100%" border="0" cellpadding="4" cellspacing="0">
  <tr>
    <td nowrap class="pagedListTab"><img src="images/icons/stock_form-autopilots-16.gif" align="absMiddle" alt="" />
    Reports scheduled to be processed by server</td>
    <td width="100%" align="right">Records: <%= pendingQueue.size() %></td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="100%">
      <strong>Subject</strong>
    </th>
    <th>
      <strong>Date</strong>
    </th>
    <th>
      <strong>Status</strong>
    </th>
  </tr>
<dhv:evaluate if="<%= pendingQueue.isEmpty() %>">
  <tr class="row2">
    <td colspan="4">No reports found</td>
  </tr>
</dhv:evaluate>
<%
  rowid = 0;
  Iterator pIterator = pendingQueue.iterator();
  while (pIterator.hasNext()) {
    rowid = (rowid != 1 ? 1 : 2);
    ReportQueue thisQueue = (ReportQueue) pIterator.next();
%>
  <tr class="row<%= rowid %>">
    <td><a href="javascript:displayMenu('select<%= thisQueue.getId() %>','menu2','<%= thisQueue.getId() %>',0);" onMouseOver="over(0, <%= thisQueue.getId() %>)" onmouseout="out(0, <%= thisQueue.getId() %>); hideMenu('menu2');"><img src="images/select.gif" name="select<%= thisQueue.getId() %>" id="select<%= thisQueue.getId() %>" align="absmiddle" border="0"></a></td>
    <td width="100%"><%= toHtml(thisQueue.getReport().getTitle()) %></td>
    <td nowrap><zeroio:tz timestamp="<%= thisQueue.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/></td>
    <td nowrap>
<dhv:evaluate if="<%= thisQueue.getStatus() == ReportQueue.STATUS_QUEUED %>">Queued</dhv:evaluate>
<dhv:evaluate if="<%= thisQueue.getStatus() == ReportQueue.STATUS_PROCESSING %>">Processing</dhv:evaluate>
<dhv:evaluate if="<%= thisQueue.getStatus() == ReportQueue.STATUS_PROCESSED %>">Processed</dhv:evaluate>
<dhv:evaluate if="<%= thisQueue.getStatus() == ReportQueue.STATUS_ERROR %>">Error creating report</dhv:evaluate>
    </td>
  </tr>
<%
  }
%>
</table>

