<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,com.zeroio.iteam.base.*,org.aspcfs.modules.pipeline.base.*" %>
<jsp:useBean id="FileList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="LeadRptListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="leads_reports_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do">Pipeline</a> >
Export Data
</td>
</tr>
</table>
<%-- End Trails --%>
<%-- Display viewpoint info --%>
<dhv:evaluate exp="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>">
  <b>Viewpoint: </b><b class="highlight"><%= PipelineViewpointInfo.getVpUserName() %></b><br>
  &nbsp;<br>
</dhv:evaluate>
<%-- Begin the contents --%>
<dhv:permission name="pipeline-reports-add"><a href="LeadsReports.do?command=ExportForm">Generate new export</a></dhv:permission>
<center><%= LeadRptListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="LeadsReports.do?command=ExportList">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= LeadRptListInfo.getOptionValue("my") %>>My Exported Data</option>
        <option <%= LeadRptListInfo.getOptionValue("all") %>>All Exported Data</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="LeadRptListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th>
      <strong>Subject</strong>
    </th>
    <th>
      <strong>Size</strong>
    </th>
    <th nowrap>
      <strong>Create Date</strong>
    </th>
    <th nowrap>
      <strong>Created By</strong>
    </th>
   <th nowrap>
      <strong>D/L</strong>
    </th>
  </tr>
<%
	Iterator j = FileList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
      rowid = (rowid != 1?1:2);
      FileItem thisItem = (FileItem)j.next();
%>      
  <tr>
    <td nowrap class="row<%= rowid %>">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('menuReport', '<%= thisItem.getId() %>');"
       onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%" class="row<%= rowid %>">
      <a href="javascript:popURL('LeadsReports.do?command=ShowExportHtml&pid=-1&fid=<%= thisItem.getId() %>&popup=true','Report','600','400','yes','yes');"><%=toHtml(thisItem.getSubject())%></a>
    </td>
    <td align="right" class="row<%= rowid %>">
      <%= thisItem.getRelativeSize() %>k
    </td>
    <td class="row<%= rowid %>" nowrap>
      <dhv:tz timestamp="<%= thisItem.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>
    <td class="row<%= rowid %>" nowrap>
      <%= toHtml(thisItem.getEnteredByString()) %>
    </td>
    <td align="right" class="row<%= rowid %>" nowrap>
      <%= thisItem.getDownloads() %>
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="LeadRptListInfo"/>
<%} else {%>
  <tr class="containerBody"><td colspan="6">No exported data found.</td></tr>
</table>
<%}%>

