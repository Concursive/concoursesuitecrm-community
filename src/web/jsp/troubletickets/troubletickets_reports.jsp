<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="FileList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="TicketRptListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="/javascript/popURL.js"></script>
<form name="listView" method="post" action="/TroubleTickets.do?command=Reports">
<a href="TroubleTickets.do">Tickets</a> > 
Reports<br>
<hr color="#BFBFBB" noshade>
<dhv:permission name="tickets-tickets-reports-add"><a href="TroubleTickets.do?command=GenerateForm">Generate new report</a></dhv:permission>
<dhv:permission name="tickets-tickets-reports-add" none="true"><br></dhv:permission>
<center><%= TicketRptListInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= TicketRptListInfo.getOptionValue("my") %>>My Reports</option>
        <option <%= TicketRptListInfo.getOptionValue("all") %>>All Reports</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="TicketRptListInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="tickets-tickets-reports-view,tickets-tickets-reports-delete">
    <td>
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td>
      <strong>File Subject</strong>
    </td>
    <td align="center">
      <strong>Size</strong>
    </td>
    <td align="center" nowrap>
      <strong>Create Date</strong>
    </td>
    <td align="center" nowrap>
      <strong>Created By</strong>
    </td>
    <td align="center">
      <strong>D/L</strong>
    </td>
  </tr>
<%
	Iterator j = FileList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    while (j.hasNext()) {
		rowid = (rowid != 1?1:2);
    FileItem thisItem = (FileItem)j.next();
%>      
  <tr>
  <dhv:permission name="tickets-tickets-reports-view,tickets-tickets-reports-delete">
    <td width="8" valign="center" nowrap class="row<%= rowid %>">
      <dhv:permission name="tickets-tickets-reports-view"><a href="TroubleTickets.do?command=DownloadCSVReport&fid=<%= thisItem.getId() %>">D/L</a></dhv:permission><dhv:permission name="tickets-tickets-reports-view,tickets-tickets-reports-delete" all="true">|</dhv:permission><dhv:permission name="tickets-tickets-reports-delete"><a href="javascript:confirmDelete('/TroubleTickets.do?command=DeleteReport&pid=-1&fid=<%= thisItem.getId() %>');">Del</a></dhv:permission>
    </td>
  </dhv:permission>
    <td width="100%" class="row<%= rowid %>">
      <a href="javascript:popURL('TroubleTickets.do?command=ShowReportHtml&pid=-1&fid=<%= thisItem.getId() %>&popup=true','Report','600','400','yes','yes');"><%=toHtml(thisItem.getSubject())%></a>
    </td>
    <td align="right" class="row<%= rowid %>" nowrap>
      <%= thisItem.getRelativeSize() %>k
    </td>
    <td align="center" class="row<%= rowid %>" nowrap>
      <%= toHtml(thisItem.getEnteredDateTimeString()) %>
    </td>
    <td align="center" class="row<%= rowid %>" nowrap>
      <%= toHtml(thisItem.getEnteredByString()) %>
    </td>
    <td align="right" class="row<%= rowid %>">
    <%= thisItem.getDownloads() %>
    </td>
 </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="TicketRptListInfo" tdClass="row1"/>
<%} else {%>
  <tr class="containerBody">
    <td colspan="6">
      No reports found.
    </td>
  </tr>
</table>
<%}%>
</form>
