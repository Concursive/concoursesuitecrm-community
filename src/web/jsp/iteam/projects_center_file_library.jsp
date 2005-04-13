<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="currentFolder" class="com.zeroio.iteam.base.FileFolder" scope="request"/>
<jsp:useBean id="fileFolderList" class="com.zeroio.iteam.base.FileFolderList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_center_files_menu.jsp" %>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <zeroio:folderHierarchy/>
    </td>
  </tr>
</table>
<br>
<zeroio:permission name="project-documents-folders-add">
<img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle">
<a href="ProjectManagementFileFolders.do?command=Add&pid=<%= Project.getId() %>&parentId=<%= Project.getFiles().getFolderId() %>&folderId=<%= Project.getFiles().getFolderId() %>"><dhv:label name="documents.documents.newFolder">New Folder</dhv:label></a>
</zeroio:permission>
<zeroio:permission name="project-documents-folders-add,project-documents-files-upload" if="all">
|
</zeroio:permission>
<zeroio:permission name="project-documents-files-upload">
<img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle">
<a href="ProjectManagementFiles.do?command=Add&pid=<%= Project.getId() %>&folderId=<%= Project.getFiles().getFolderId() %>"><dhv:label name="documents.documents.submitFile">Submit File</dhv:label></a>
</zeroio:permission>
<dhv:evaluate if="<%= Project.getFiles().getFolderId() != -1 %>">
<zeroio:permission name="project-documents-folders-edit">
  <zeroio:permission name="project-documents-folders-add,project-documents-files-upload" if="any">
|
  </zeroio:permission>
<img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle" />
<a href="ProjectManagementFileFolders.do?command=Modify&pid=<%= Project.getId() %>&folderId=<%= Project.getFiles().getFolderId() %>&id=<%= Project.getFiles().getFolderId() %>&parentId=<%= Project.getFiles().getFolderId() %>"><dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label></a>
</zeroio:permission>
<zeroio:permission name="project-documents-folders-delete">
  <zeroio:permission name="project-documents-folders-add,project-documents-files-upload,project-documents-folders-edit" if="any">
|
  </zeroio:permission>
<img src="images/icons/stock_left-with-subpoints-16.gif" border="0" align="absmiddle" />
<a href="javascript:confirmDelete('ProjectManagementFileFolders.do?command=Delete&pid=<%= Project.getId() %>&folderId=<%= currentFolder.getParentId() %>&id=<%= Project.getFiles().getFolderId() %>');">Delete Folder</a>
</zeroio:permission>
</dhv:evaluate>
<zeroio:permission name="project-documents-folders-add,project-documents-files-upload,project-documents-folders-edit" if="any">
<br />
<br />
</zeroio:permission>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" align="center" nowrap>&nbsp;</th>
    <th width="100%" nowrap><strong><dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="accounts.accounts_add.Type">Type</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="accounts.accounts_documents_details.Size">Size</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="documents.documents.dateModified">Date Modified</dhv:label></strong></th>
  </tr>
<dhv:evaluate if="<%= Project.getFiles().size() == 0 && fileFolderList.size() == 0 %>">
  <tr class="row2">
    <td colspan="6"><dhv:label name="project.noFilesToDisplay">No files to display.</dhv:label></td>
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
      <a href="javascript:displayMenu('select_<%= SKIN %>fo<%= thisFolder.getId() %>', 'menuFolder', <%= thisFolder.getId() %>, -1, <%= thisFolder.getDisplay() %>)"
         onMouseOver="over(0, 'fo<%= thisFolder.getId() %>')"
         onmouseout="out(0, 'fo<%= thisFolder.getId() %>'); hideMenu('menuFolder');"><img 
        src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %>fo<%= thisFolder.getId() %>" id="select_<%= SKIN %>fo<%= thisFolder.getId() %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%">
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td valign="top" nowrap>
            <img src="images/stock_folder-23.gif" border="0" align="absmiddle">&nbsp;
          </td>
          <td valign="top">
            <a href="ProjectManagement.do?command=ProjectCenter&section=File_<%= thisFolder.getDisplay() == -1?"Library":"Gallery" %>&pid=<%= Project.getId() %>&folderId=<%= thisFolder.getId() %><%= thisFolder.getDisplay() == 2?"&details=true":"" %>"><%= toHtml(thisFolder.getSubject()) %></a>
          </td>
        </tr>
      </table>
    </td>
    <td align="center" nowrap>
<%--  <dhv:evaluate if="<%= thisFolder.getDisplay() == FileFolder.VIEW_LIBRARY %>"> --%>
      <dhv:label name="project.folder.lowercase">folder</dhv:label>
<%--  </dhv:evaluate>
      <dhv:evaluate if="<%= thisFolder.getDisplay() == FileFolder.VIEW_GALLERY %>">images</dhv:evaluate>
      <dhv:evaluate if="<%= thisFolder.getDisplay() == FileFolder.VIEW_SLIDESHOW %>">slideshow</dhv:evaluate>
--%>
    </td>
    <td align="center" nowrap>
      <%= thisFolder.getItemCount() %>
      <% if(thisFolder.getItemCount() == 1) {%>
        <dhv:label name="project.item.lowercase">item</dhv:label>
      <%} else {%>
        <dhv:label name="project.items.lowercase">items</dhv:label>
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
  FileItemList files = Project.getFiles();
  Iterator i = files.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    FileItem thisFile = (FileItem)i.next();
%>    
  <tr class="row<%= rowid %>">
    <td align="center" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %>fi<%= thisFile.getId() %>', 'menuFile', -1, <%= thisFile.getId() %>, -1)"
         onMouseOver="over(0, 'fi<%= thisFile.getId() %>')"
         onmouseout="out(0, 'fi<%= thisFile.getId() %>'); hideMenu('menuFile');"><img 
        src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %>fi<%= thisFile.getId() %>" id="select_<%= SKIN %>fi<%= thisFile.getId() %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%">
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td valign="top" nowrap>
            <%= thisFile.getImageTag("-23") %>&nbsp;
          </td>
          <td valign="top">
            <a href="ProjectManagementFiles.do?command=Details&pid=<%= Project.getId() %>&fid=<%= thisFile.getId() %>&folderId=<%= request.getParameter("folderId") %>"><%= toHtml(thisFile.getSubject()) %></a>
          </td>
        </tr>
      </table>
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
