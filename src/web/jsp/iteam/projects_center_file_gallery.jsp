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
<link rel="stylesheet" href="css/iteam-images.css" type="text/css">
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
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
<dhv:pagedListStatus label="Items" title="<%= showError(request, "actionError") %>" object="projectDocumentsGalleryInfo" scrollReload="true"/>
<table cellpadding="10" cellspacing="0" border="0" width="100%">
<%
	if (fileFolderList.size() == 0 && Project.getFiles().size() == 0) {
%>
    <tr>
      <td class="ImageList" valign="center">
        No files to display.
      </td>
    </tr>
<%
  } else {
    int rowcount = 0;
    int count = 0;
    //Show the folders
    Iterator i = fileFolderList.iterator();
    while (i.hasNext()) {
      FileFolder thisFolder = (FileFolder) i.next();
      ++count;
      if ((count+2) % 3 == 0) {
        ++rowcount;
      }
%>
<dhv:evaluate exp="<%= (count+2) % 3 == 0 %>">  
  <tr>
</dhv:evaluate>
    <td class="ImageList<%= (rowcount == 1?"":"AdditionalRow") %>">
      <span>
        <a href="ProjectManagement.do?command=ProjectCenter&section=File_<%= thisFolder.getDisplay() == -1?"Library":"Gallery" %>&pid=<%= Project.getId() %>&folderId=<%= thisFolder.getId() %>"><img src="images/stock_folder.gif" border="0" align="absmiddle"></a><br>
        <%= toHtml(thisFolder.getSubject()) %>
      </span>
    </td>
<dhv:evaluate exp="<%= count % 3 == 0 %>">  
  </tr>
</dhv:evaluate>
<%
    }
    //Show the images
    int pagedItemCount = projectDocumentsGalleryInfo.getCurrentOffset() - 1;
    i = Project.getFiles().iterator();
    while (i.hasNext()) {
      FileItem thisItem = (FileItem) i.next();
      ++count;
      ++pagedItemCount;
      if ((count+2) % 3 == 0) {
        ++rowcount;
      }
%>
<dhv:evaluate exp="<%= (count+2) % 3 == 0 %>">  
  <tr>
</dhv:evaluate>
    <td class="ImageList<%= (rowcount == 1?"":"AdditionalRow") %>">
      <span>
        <dhv:evaluate if="<%= request.getParameter("details") == null %>">
          <a href="ProjectManagement.do?command=ProjectCenter&section=File_Gallery&pid=<%= Project.getId() %>&folderId=<%= Project.getFiles().getFolderId() %>&details=true&offset=<%= pagedItemCount %>"><%= thisItem.getThumbnail() %></a><br>
        </dhv:evaluate>
        <dhv:evaluate if="<%= request.getParameter("details") != null %>">
          <%= thisItem.getFullImage() %><br>
        </dhv:evaluate>
        <%= toHtml(thisItem.getSubject()) %>
      </span>
    </td>
<dhv:evaluate exp="<%= count % 3 == 0 %>">  
  </tr>
</dhv:evaluate>
<%
    }
%>
<dhv:evaluate exp="<%= count % 3 != 0 %>">  
  </tr>
</dhv:evaluate>
<%}%>
</table>
