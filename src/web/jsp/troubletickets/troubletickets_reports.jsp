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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,com.zeroio.iteam.base.*,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="FileList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="TicketRptListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="troubletickets_reports_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<form name="listView" method="post" action="TroubleTickets.do?command=Reports">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
<dhv:label name="accounts.accounts_relationships_view.ExportData">Export Data</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="tickets-reports-add"><a href="TroubleTickets.do?command=GenerateForm&searchcodeSited=<%= User.getUserRecord().getSiteId() %>"><dhv:label name="accounts.accounts_reports.GenerateNewExport">Generate new export</dhv:label></a></dhv:permission>
<dhv:permission name="tickets-reports-add" none="true"><br></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="TicketRptListInfo"/></center></dhv:include>
<table width="100%" border="0">
  <tr>
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.listView.submit();">
        <option <%= TicketRptListInfo.getOptionValue("my") %>><dhv:label name="accounts.accounts_reports.MyExportedData">My Exported Data</dhv:label></option>
        <option <%= TicketRptListInfo.getOptionValue("all") %>><dhv:label name="accounts.accounts_reports.AllExportedData">All Exported Data</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="TicketRptListInfo"/>
    </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      &nbsp;
    </th>
    <th>
      <strong><dhv:label name="accounts.accounts_contacts_calls_details_include.Subject">Subject</dhv:label></strong>
    </th>
    <th>
      <strong><dhv:label name="accounts.accounts_documents_details.Size">Size</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="accounts.accounts_reports.CreateDate">Create Date</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="accounts.accounts_reports.CreatedBy">Created By</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="accounts.accounts_documents_details.DL">D/L</dhv:label></strong>
    </th>
  </tr>
<%
	Iterator j = FileList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int count = 0;
    while (j.hasNext()) {
    count++;
		rowid = (rowid != 1?1:2);
    FileItem thisItem = (FileItem)j.next();
%>      
  <tr>
    <td nowrap class="row<%= rowid %>">
      <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayMenu('select<%= count %>','menuReport','<%= thisItem.getId() %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuReport');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%" class="row<%= rowid %>">
      <a href="javascript:popURL('TroubleTickets.do?command=ShowReportHtml&pid=-1&fid=<%= thisItem.getId() %>&popup=true','Report','600','400','yes','yes');"><%=toHtml(thisItem.getSubject())%></a>
    </td>
    <td align="right" class="row<%= rowid %>" nowrap>
      <%= thisItem.getRelativeSize() %>k
    </td>
    <td class="row<%= rowid %>" nowrap>
      <zeroio:tz timestamp="<%= thisItem.getEntered() %>" />
    </td>
    <td class="row<%= rowid %>" nowrap>
      <dhv:username id="<%= thisItem.getEnteredBy() %>" />
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
      <dhv:label name="accounts.accounts_reports.NoExporteddataFound">No exported data found.</dhv:label>
    </td>
  </tr>
</table>
<%}%>
</form>
