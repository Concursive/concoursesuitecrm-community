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
  - Author(s): 
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="fileFolderList" class="com.zeroio.iteam.base.FileFolderList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="document_store_center_files_menu.jsp" %>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <% String documentLink = "DocumentManagement.do?command=DocumentStoreCenter&section=File_Library&documentStoreId="+documentStore.getId(); %>
      <zeroio:folderHierarchy module="Documents" link="<%= documentLink %>" />
    </td>
  </tr>
</table>
<dhv:evaluate if="<%= !documentStore.isTrashed() %>" >
<br>
<dhv:documentPermission name="documentcenter-documents-folders-add">
<img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle" />
<a href="DocumentStoreManagementFileFolders.do?command=Add&documentStoreId=<%= documentStore.getId() %>&parentId=<%= documentStore.getFiles().getFolderId() %>&folderId=<%= documentStore.getFiles().getFolderId() %>"><dhv:label name="documents.documents.newFolder">New Folder</dhv:label></a>
</dhv:documentPermission>
<dhv:documentPermission name="documentcenter-documents-folders-add,documentcenter-documents-files-upload" if="all">
|
</dhv:documentPermission>
<dhv:documentPermission name="documentcenter-documents-files-upload">
<img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle" />
<a href="DocumentStoreManagementFiles.do?command=Add&documentStoreId=<%= documentStore.getId() %>&folderId=<%= documentStore.getFiles().getFolderId() %>"><dhv:label name="documents.documents.submitFile">Submit File</dhv:label></a>
</dhv:documentPermission>
<dhv:evaluate if="<%= documentStore.getFiles().getFolderId() != -1 %>">
<dhv:documentPermission name="documentcenter-documents-folders-edit">
  <dhv:documentPermission name="documentcenter-documents-folders-add,documentcenter-documents-files-upload" if="any">
|
  </dhv:documentPermission>
<img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle" />
<a href="DocumentStoreManagementFileFolders.do?command=Modify&documentStoreId=<%= documentStore.getId() %>&folderId=<%= documentStore.getFiles().getFolderId() %>&id=<%= documentStore.getFiles().getFolderId() %>&parentId=<%= documentStore.getFiles().getFolderId() %>"><dhv:label name="documents.documents.renameFolder">Rename Folder</dhv:label></a>
</dhv:documentPermission>
</dhv:evaluate>
<dhv:documentPermission name="documentcenter-documents-folders-add,documentcenter-documents-files-upload,documentcenter-documents-folders-edit" if="any">
  <br /><br />
</dhv:documentPermission>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8">&nbsp;</th>
    <th width="100%"><strong><dhv:label name="documents.documents.file">File</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="documents.documents.type">Type</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="documents.documents.size">Size</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="documents.documents.version">Version</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="documents.documents.dateModified">Date Modified</dhv:label></strong></th>
  </tr>
<dhv:evaluate if="<%= documentStore.getFiles().size() == 0 && fileFolderList.size() == 0 %>">
  <tr class="row2">
    <td colspan="6"><dhv:label name="documents.documents.noFilesMessage">No files to display.</dhv:label></td>
  </tr>
</dhv:evaluate>
<%
  int rowid = 0;
  Iterator folders = fileFolderList.iterator();
  while (folders.hasNext()) {
    FileFolder thisFolder = (FileFolder) folders.next();
    rowid = (rowid != 1?1:2);
%>
  <tr class="row<%= rowid %>">
    <td align="center" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %>fo<%= thisFolder.getId() %>', 'menuFolder', <%= thisFolder.getId() %>, -1, <%= thisFolder.getDisplay() %>,'<%= documentStore.isTrashed() %>')"
         onMouseOver="over(0, 'fo<%= thisFolder.getId() %>')"
         onmouseout="out(0, 'fo<%= thisFolder.getId() %>'); hideMenu('menuFolder');"><img 
        src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %>fo<%= thisFolder.getId() %>" id="select_<%= SKIN %>fo<%= thisFolder.getId() %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%">
      <img src="images/stock_folder-23.gif" border="0" align="absmiddle">
      <a href="DocumentManagement.do?command=DocumentStoreCenter&section=File_<%= thisFolder.getDisplay() == -1?"Library":"Gallery" %>&documentStoreId=<%= documentStore.getId() %>&folderId=<%= thisFolder.getId() %><%= thisFolder.getDisplay() == 2?"&details=true":"" %>"><%= toHtml(thisFolder.getSubject()) %></a>
    </td>
    <td align="center" nowrap>
      <dhv:label name="project.folder.lowercase">folder</dhv:label>
    </td>
    <td align="center" nowrap>
      <%= thisFolder.getItemCount() %> 
      <% if(thisFolder.getItemCount() != 1) {%>
        <dhv:label name="project.items.lowercase">items</dhv:label>
      <%} else {%>
        <dhv:label name="project.item.lowercase">item</dhv:label>
      <%}%>
    </td>
    <td align="center" nowrap>
      --
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= thisFolder.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/><br />
      <dhv:username id="<%= thisFolder.getModifiedBy() %>"/>
    </td>
  </tr>
<%
  }
  FileItemList files = documentStore.getFiles();
  Iterator i = files.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    FileItem thisFile = (FileItem)i.next();
%>    
  <tr class="row<%= rowid %>">
    <td align="center" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %>fi<%= thisFile.getId() %>', 'menuFile', -1, <%= thisFile.getId() %>, -1, '<%= documentStore.isTrashed() %>')"
         onMouseOver="over(0, 'fi<%= thisFile.getId() %>')"
         onmouseout="out(0, 'fi<%= thisFile.getId() %>'); hideMenu('menuFile');"><img 
        src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %>fi<%= thisFile.getId() %>" id="select_<%= SKIN %>fi<%= thisFile.getId() %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%">
      <%= thisFile.getImageTag("-23") %>
      <a href="DocumentStoreManagementFiles.do?command=Details&documentStoreId=<%= documentStore.getId() %>&fid=<%= thisFile.getId() %>&folderId=<%= documentStore.getFiles().getFolderId() %>"><%= toHtml(thisFile.getSubject()) %></a>
    </td>
    <td align="center" nowrap><%= toHtml(thisFile.getExtension()) %></td>
    <td align="right" nowrap>
      <%= thisFile.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
    </td>
    <td align="center" nowrap>
      <%= thisFile.getVersion() %>&nbsp;
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= thisFile.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/><br />
      <dhv:username id="<%= thisFile.getModifiedBy() %>"/>
    </td>
  </tr>
<%
  }
%>
</table>
