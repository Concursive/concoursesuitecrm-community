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
  - Version: $Id: sales_reports.jsp
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,com.zeroio.iteam.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="fileList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<jsp:useBean id="salesRptListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="sales_reports_menu.jsp" %>
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
<a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> > 
<dhv:label name="accounts.accounts_relationships_view.ExportData">Export Data</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="sales-reports-add"><a href="Sales.do?command=GenerateForm"><dhv:label name="accounts.accounts_reports.GenerateNewExport">Generate new export</dhv:label></a></dhv:permission>
<dhv:permission name="sales-reports-add" none="true"><br></dhv:permission>
<dhv:include name="pagedListInfo.alphabeticalLinks" none="true">
<center><dhv:pagedListAlphabeticalLinks object="salesRptListInfo"/></center></dhv:include>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="Sales.do?command=Reports">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.listView.submit();">
        <option <%= salesRptListInfo.getOptionValue("my") %>><dhv:label name="accounts.accounts_reports.MyExportedData">My Exported Data</dhv:label></option>
        <option <%= salesRptListInfo.getOptionValue("all") %>><dhv:label name="accounts.accounts_reports.AllExportedData">All Exported Data</dhv:label></option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="salesRptListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <dhv:permission name="sales-reports-view,sales-reports-delete">
    <th width="8">&nbsp;</th>
    </dhv:permission>
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
	Iterator j = fileList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int count = 0;
    while (j.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      FileItem thisItem = (FileItem)j.next();
%>      
  <tr>
      <td valign="center" class="row<%= rowid %>" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= count %>','menuReport','<%= thisItem.getId() %>');" onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuReport');"><img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
      </td>
    <td width="100%" class="row<%= rowid %>">
      <a href="javascript:popURL('Sales.do?command=ShowReportHtml&pid=-1&fid=<%= thisItem.getId() %>&popup=true&popup=true','Report','600','400','yes','yes');"><%=toHtml(thisItem.getSubject())%></a>
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
    <td align="right" class="row<%= rowid %>" nowrap>
      <%= thisItem.getDownloads() %>
    </td>
 </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="salesRptListInfo" tdClass="row1"/>
<%} else {%>
  <tr class="containerBody">
    <td colspan="6"><dhv:label name="accounts.accounts_reports.NoExporteddataFound">No exported data found.</dhv:label></td>
  </tr>
</table>
<%}%>