<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="fileFolderList" class="com.zeroio.iteam.base.FileFolderList" scope="request"/>
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
<a href="ProjectManagementFileFolders.do?command=Add&pid=<%= Project.getId() %>&parentId=<%= Project.getFiles().getFolderId() %>&folderId=<%= Project.getFiles().getFolderId() %>">New Folder</a>
</zeroio:permission>
<zeroio:permission name="project-documents-folders-add,project-documents-files-upload" if="all">
|
</zeroio:permission>
<zeroio:permission name="project-documents-files-upload">
<img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle">
<a href="ProjectManagementFiles.do?command=Add&pid=<%= Project.getId() %>&folderId=<%= Project.getFiles().getFolderId() %>">Submit File</a>
</zeroio:permission>
<dhv:evaluate if="<%= Project.getFiles().getFolderId() != -1 %>">
<zeroio:permission name="project-documents-folders-edit">
  <zeroio:permission name="project-documents-folders-add,project-documents-files-upload" if="any">
|
  </zeroio:permission>
<img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle">
<a href="ProjectManagementFileFolders.do?command=Modify&pid=<%= Project.getId() %>&folderId=<%= Project.getFiles().getFolderId() %>&id=<%= Project.getFiles().getFolderId() %>&parentId=<%= Project.getFiles().getFolderId() %>">Rename Folder</a>
</zeroio:permission>
</dhv:evaluate>
<zeroio:permission name="project-documents-folders-add,project-documents-files-upload,project-documents-folders-edit" if="any">
<br>
&nbsp;<br>
</zeroio:permission>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" align="center" nowrap><strong>Action</strong></th>
    <th width="100%"><strong>File</strong></th>
    <th align="center"><strong>Type</strong></th>
    <th align="center"><strong>Size</strong></th>
    <th align="center"><strong>Version</strong></th>
    <th align="center" nowrap><strong>Date Modified</strong></th>
  </tr>
<dhv:evaluate if="<%= Project.getFiles().size() == 0 && fileFolderList.size() == 0 %>">
  <tr class="row2">
    <td colspan="6">No files to display.</td>
  </tr>
</dhv:evaluate>
<%
  int rowid = 0;
  Iterator folders = fileFolderList.iterator();
  while (folders.hasNext()) {
    FileFolder thisFolder = (FileFolder) folders.next();
    rowid = (rowid != 1?1:2);
%>
  <tr>
    <td class="row<%= rowid %>" align="center" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %>fo<%= thisFolder.getId() %>', 'menuFolder', <%= thisFolder.getId() %>, -1, <%= thisFolder.getDisplay() %>)"
         onMouseOver="over(0, 'fo<%= thisFolder.getId() %>')"
         onmouseout="out(0, 'fo<%= thisFolder.getId() %>'); hideMenu('menuFolder');"><img 
        src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %>fo<%= thisFolder.getId() %>" id="select_<%= SKIN %>fo<%= thisFolder.getId() %>" align="absmiddle" border="0"></a>
    </td>
    <td class="row<%= rowid %>" width="100%">
      <img src="images/stock_folder-23.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=File_<%= thisFolder.getDisplay() == -1?"Library":"Gallery" %>&pid=<%= Project.getId() %>&folderId=<%= thisFolder.getId() %><%= thisFolder.getDisplay() == 2?"&details=true":"" %>"><%= toHtml(thisFolder.getSubject()) %></a>
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      folder
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <%= thisFolder.getItemCount() %> item<%= (thisFolder.getItemCount() == 1?"":"s") %>
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      --
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <zeroio:tz timestamp="<%= thisFolder.getModified() %>"/><br />
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
  <tr>
    <td class="row<%= rowid %>" align="center" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %>fi<%= thisFile.getId() %>', 'menuFile', -1, <%= thisFile.getId() %>, -1)"
         onMouseOver="over(0, 'fi<%= thisFile.getId() %>')"
         onmouseout="out(0, 'fi<%= thisFile.getId() %>'); hideMenu('menuFile');"><img 
        src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %>fi<%= thisFile.getId() %>" id="select_<%= SKIN %>fi<%= thisFile.getId() %>" align="absmiddle" border="0"></a>
    </td>
    <td class="row<%= rowid %>" width="100%">
      <%= thisFile.getImageTag("-23") %>
      <a href="ProjectManagementFiles.do?command=Details&pid=<%= Project.getId() %>&fid=<%= thisFile.getId() %>&folderId=<%= request.getParameter("folderId") %>"><%= toHtml(thisFile.getSubject()) %></a>
    </td>
    <td class="row<%= rowid %>" align="center" nowrap><%= toHtml(thisFile.getExtension()) %></td>
    <td class="row<%= rowid %>" align="right" nowrap>
      <%= thisFile.getRelativeSize() %> k&nbsp;
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <%= thisFile.getVersion() %>&nbsp;
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <zeroio:tz timestamp="<%= thisFile.getModified() %>"/><br />
      <dhv:username id="<%= thisFile.getModifiedBy() %>"/>
    </td>
  </tr>
<%
  }
%>
</table>
