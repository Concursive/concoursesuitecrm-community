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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.base.Import,com.zeroio.iteam.base.FileItem" %>
<jsp:useBean id="ImportList" class="org.aspcfs.modules.base.ImportList" scope="request"/>
<jsp:useBean id="AccountContactsImportListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_listimports_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
View Imports
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="accounts-accounts-contacts-imports-add">
  <a href="AccountContactsImports.do?command=New">New Import</a><br>
</dhv:permission><br>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="AccountContactsImports.do?command=View">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= AccountContactsImportListInfo.getOptionValue("all") %>>All Imports</option>
        <option <%= AccountContactsImportListInfo.getOptionValue("my") %>>My Imports</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountContactsImportListInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th rowspan="2" valign="middle">
      <strong>Action</strong>
    </th>
    <th rowspan="2" valign="middle" width="100%">
      <strong><a href="AccountContactsImports.do?command=View&column=m.name">Name</a></strong>
      <%= AccountContactsImportListInfo.getSortIcon("m.name") %>
    </th>
    <th rowspan="2" valign="middle">
      <strong>Status</strong>
    </th>
    <th colspan="3" style="text-align:center !important">
      <strong>Results</strong>
    </th>
    <th rowspan="2" valign="middle" nowrap>
      <strong><a href="AccountContactsImports.do?command=View&column=m.entered">Entered</a></strong>
      <%= AccountContactsImportListInfo.getSortIcon("m.entered") %>
    </th>
    <th rowspan="2" valign="middle">
      <strong>Modified</strong>
    </th>
  </tr>
  <tr>
    <th>
      <strong>Total</strong>
    </th>
    <th>
      <strong>Success</strong>
    </th>
    <th>
      <strong>Failed</strong>
    </th>
  </tr>
<%
  Iterator j = ImportList.iterator();
  if ( j.hasNext() ) {
  int rowid = 0;
  int i =0;
  while (j.hasNext()) {
      i++;
      rowid = (rowid != 1 ? 1 : 2);
      Import thisImport = (Import) j.next();
%>
  <tr class="row<%= rowid %>">
    <td nowrap>
     <%-- Use the unique id for opening the menu, and toggling the graphics --%>
      <a href="javascript:displayMenu('select<%= i %>','menuImport','<%= thisImport.getId() %>', '<%= thisImport.getStatusId() == Import.RUNNING ? "1" : "0" %>','<%= thisImport.getStatusId() == Import.UNPROCESSED ? "1" : "0"%>','<%= thisImport.canDelete()? "1" : "0"%>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%" nowrap>
      <a href="AccountContactsImports.do?command=Details&importId=<%= thisImport.getId() %>"><%= toHtmlValue(thisImport.getName()) %></a>
      <dhv:evaluate if="<%= !thisImport.canProcess() && thisImport.getFile().hasVersion(Import.ERROR_FILE_VERSION) %>">
      &nbsp;<%= thisImport.getFile().getImageTag("-23") %><br />[<a href="javascript:window.location.href='AccountContactsImports.do?command=Download&importId=<%= thisImport.getId() %>&fid=<%= thisImport.getFile().getId() %>&ver=<%= Import.ERROR_FILE_VERSION %>';">Download Error File</a>]
      </dhv:evaluate>
    </td>
    <td>
      <%= toString(thisImport.getStatusString()) %>
    </td>
    <td nowrap align="center">
      <%= thisImport.getTotalImportedRecords() + thisImport.getTotalFailedRecords() %>
    </td>
    <td nowrap align="center">
      <%= thisImport.getTotalImportedRecords() %>
    </td>
    <td nowrap align="center">
      <%= thisImport.getTotalFailedRecords() %>
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= thisImport.getEntered() %>" />
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= thisImport.getModified() %>" />
    </td>
  </tr>
<%}
}else{%>
      <tr>
        <td class="containerBody" colspan="8" valign="center">
          No imports found.
        </td>
      </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="AccountContactsImportListInfo" tdClass="row1"/>

