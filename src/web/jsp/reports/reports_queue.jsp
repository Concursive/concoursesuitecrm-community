<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
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
<table class="trails">
<tr>
<td>
<a href="Reports.do">Reports</a> >
Queue
</td>
</tr>
</table>
<%-- End Trails --%>
<%-- Completed Reports --%>
<%= showError(request, "actionError", false) %>
<a href="Reports.do?command=RunReport">Add a Report</a><br>
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="padding-bottom:3px">
  <tr>
    <td><img src="images/icons/stock_form-16.gif" align="absMiddle" alt="" />
    Generated reports ready to be retrieved</td>
    <td align="right">Records: <%= completedQueue.size() %></td>
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
    <td><a href="javascript:displayMenu('menu1<%= (thisQueue.getStatus() == ReportQueue.STATUS_PROCESSED ? "a" : "b") %>','<%= thisQueue.getId() %>');" onMouseOver="over(0, <%= thisQueue.getId() %>)" onmouseout="out(0, <%= thisQueue.getId() %>)"><img src="images/select.gif" name="select<%= thisQueue.getId() %>" align="absmiddle" border="0"></a></td>
    <td width="100%"><%= toHtml(thisQueue.getReport().getTitle()) %></td>
    <td nowrap><dhv:tz timestamp="<%= thisQueue.getProcessed() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/></td>
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
* Generated reports are deleted from the server every 24 hours.<br>
<%-- Reports in Queue --%>
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="padding-bottom:3px">
  <tr>
    <td><img src="images/icons/stock_form-autopilots-16.gif" align="absMiddle" alt="" />
    Reports scheduled to be processed by server</td>
    <td align="right">Records: <%= pendingQueue.size() %></td>
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
    <td><a href="javascript:displayMenu('menu2','<%= thisQueue.getId() %>',0);" onMouseOver="over(0, <%= thisQueue.getId() %>)" onmouseout="out(0, <%= thisQueue.getId() %>)"><img src="images/select.gif" name="select<%= thisQueue.getId() %>" align="absmiddle" border="0"></a></td>
    <td width="100%"><%= toHtml(thisQueue.getReport().getTitle()) %></td>
    <td nowrap><dhv:tz timestamp="<%= thisQueue.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/></td>
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

