<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.base.Import,com.zeroio.iteam.base.FileItem" %>
<jsp:useBean id="ImportList" class="org.aspcfs.modules.base.ImportList" scope="request"/>
<jsp:useBean id="ExternalContactsImportListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="companydirectory_listimports_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="ExternalContacts.do">Contacts</a> > 
View Imports
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="contacts-external_contacts-imports-add">
  <a href="ExternalContactsImports.do?command=New">New Import</a><br>
</dhv:permission><br>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="ExternalContactsImports.do?command=View">
    <td align="left">
      <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= ExternalContactsImportListInfo.getOptionValue("all") %>>All Imports</option>
        <option <%= ExternalContactsImportListInfo.getOptionValue("my") %>>My Imports</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ExternalContactsImportListInfo"/>
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
      <strong><a href="ExternalContactsImports.do?command=View&column=m.name">Name</a></strong>
      <%= ExternalContactsImportListInfo.getSortIcon("m.name") %>
    </th>
    <th rowspan="2" valign="middle">
      <strong>Status</strong>
    </th>
    <th colspan="3" align="center">
      <strong>Results</strong>
    </th>
    <th rowspan="2" valign="middle" nowrap>
      <strong><a href="ExternalContactsImports.do?command=View&column=m.entered">Entered</a></strong>
      <%= ExternalContactsImportListInfo.getSortIcon("m.entered") %>
    </th>
    <th rowspan="2" valign="middle" nowrap>
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
      <a href="javascript:displayMenu('menuImport','<%= thisImport.getId() %>', '<%= thisImport.getStatusId() == Import.RUNNING ? "1" : "0" %>','<%= thisImport.getStatusId() == Import.UNPROCESSED ? "1" : "0"%>','<%= thisImport.canDelete()? "1" : "0"%>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%">
      <a href="ExternalContactsImports.do?command=Details&importId=<%= thisImport.getId() %>"><%= toHtmlValue(thisImport.getName()) %></a>
      <dhv:evaluate if="<%= !thisImport.canProcess() && thisImport.getFile().hasVersion(Import.ERROR_FILE_VERSION) %>">
      &nbsp;&nbsp;<%= thisImport.getFile().getImageTag() %>[ <a href="javascript:window.location.href='ExternalContactsImports.do?command=Download&importId=<%= thisImport.getId() %>&fid=<%= thisImport.getFile().getId() %>&ver=<%= Import.ERROR_FILE_VERSION %>';">Download Error File</a> ]
      </dhv:evaluate>
    </td>
    <td nowrap>
      <%= toString(thisImport.getStatusString()) %>
    </td>
    <td nowrap>
      <%= thisImport.getTotalImportedRecords() + thisImport.getTotalFailedRecords() %>
    </td>
    <td nowrap>
      <%= thisImport.getTotalImportedRecords() %>
    </td>
    <td nowrap>
      <%= thisImport.getTotalFailedRecords() %>
    </td>
    <td nowrap align="center">
      <dhv:tz timestamp="<%= thisImport.getEntered() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>
    <td nowrap align="center">
      <dhv:tz timestamp="<%= thisImport.getModified() %>" dateFormat="<%= DateFormat.SHORT %>" timeFormat="<%= DateFormat.LONG %>"/>
    </td>
  </tr>
<%}
}else{%>
      <tr>
        <td class="containerBody" colspan="5" valign="center">
          No Imports found in this view.
        </td>
      </tr>
<%}%>
</table>
&nbsp;<br>
<dhv:pagedListControl object="ExternalContactsImportListInfo" tdClass="row1"/>

