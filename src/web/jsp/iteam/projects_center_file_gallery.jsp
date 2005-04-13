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
<link rel="stylesheet" href="css/iteam-images.css" type="text/css">
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="currentFolder" class="com.zeroio.iteam.base.FileFolder" scope="request"/>
<jsp:useBean id="fileFolderList" class="com.zeroio.iteam.base.FileFolderList" scope="request"/>
<jsp:useBean id="projectDocumentsGalleryInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
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
      <zeroio:folderHierarchy showLastLink="<%= request.getParameter("details") != null %>"/>
    </td>
  </tr>
</table>
<br />
<zeroio:permission name="project-documents-folders-add">
<img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle" />
<a href="ProjectManagementFileFolders.do?command=Add&pid=<%= Project.getId() %>&parentId=<%= Project.getFiles().getFolderId() %>&folderId=<%= Project.getFiles().getFolderId() %>"><dhv:label name="documents.documents.newFolder">New Folder</dhv:label></a>
</zeroio:permission>
<zeroio:permission name="project-documents-folders-add,project-documents-files-upload" if="all">
|
</zeroio:permission>
<zeroio:permission name="project-documents-files-upload">
<img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle" />
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
<%-- Temp. fix for Weblogic --%>
<%
String actionError = showError(request, "actionError");
boolean showDetails = ("true".equals(request.getParameter("details")));
%>
<dhv:pagedListStatus label="Items" title="<%= actionError %>" object="projectDocumentsGalleryInfo" scrollReload="true"/>
<table cellpadding="10" cellspacing="0" border="0" width="100%">
<%
	if (fileFolderList.size() == 0 && Project.getFiles().size() == 0) {
%>
    <tr>
      <td class="ImageList" valign="center">
        <dhv:label name="project.noFilesToDisplay">No files to display.</dhv:label>
      </td>
    </tr>
<%
  }
  int rowcount = 0;
  int count = 0;
    int multiplier = 3;
  if (fileFolderList.size() + Project.getFiles().size() == 4) {
    multiplier = 2;
  }
%>
<%-- Show the folders --%>
<dhv:evaluate if="<%= !showDetails %>">
<%
  Iterator i = fileFolderList.iterator();
  while (i.hasNext()) {
    FileFolder thisFolder = (FileFolder) i.next();
    ++count;
    if ((count + (multiplier - 1)) % multiplier == 0) {
      ++rowcount;
    }
%>
<dhv:evaluate if="<%= (count + (multiplier - 1)) % multiplier == 0 %>">
  <tr>
</dhv:evaluate>
    <td class="ImageList<%= (rowcount == 1?"":"AdditionalRow") %>">
      <span>
        <a href="ProjectManagement.do?command=ProjectCenter&section=File_<%= thisFolder.getDisplay() == -1?"Library":"Gallery" %>&pid=<%= Project.getId() %>&folderId=<%= thisFolder.getId() %>"><img src="images/stock_folder.gif" border="0" align="absmiddle"></a><br />
        <%= toHtml(thisFolder.getSubject()) %>
      </span>
    </td>
<dhv:evaluate if="<%= count % multiplier == 0 %>">
  </tr>
</dhv:evaluate>
<%
    }
%>
</dhv:evaluate>
<%-- Show the image(s) --%>
<%
  int pagedItemCount = projectDocumentsGalleryInfo.getCurrentOffset() - 1;
  Iterator j = Project.getFiles().iterator();
  while (j.hasNext()) {
    FileItem thisItem = (FileItem) j.next();
    ++count;
    ++pagedItemCount;
    if ((count + (multiplier - 1)) % multiplier == 0) {
      ++rowcount;
    }
%>
<dhv:evaluate if="<%= (count + (multiplier - 1)) % multiplier == 0 %>">  
  <tr>
</dhv:evaluate>
    <td class="ImageList<%= (rowcount == 1?"":"AdditionalRow") %>">
      <span>
        <dhv:evaluate if="<%= !showDetails %>">
          <a href="ProjectManagement.do?command=ProjectCenter&section=File_Gallery&pid=<%= Project.getId() %>&folderId=<%= Project.getFiles().getFolderId() %>&details=true&offset=<%= pagedItemCount %>"><%= thisItem.getThumbnail() %></a><br />
        </dhv:evaluate>
        <dhv:evaluate if="<%= showDetails %>">
          <%= thisItem.getFullImage() %><br>
        </dhv:evaluate>
        <%= toHtml(thisItem.getSubject()) %>
      </span>
    </td>
<dhv:evaluate if="<%= count % multiplier == 0 %>">
  </tr>
</dhv:evaluate>
<%
    }
%>
<dhv:evaluate if="<%= count % multiplier != 0 %>">
  </tr>
</dhv:evaluate>
</table>
