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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,java.lang.*,java.text.*,org.aspcfs.modules.accounts.base.*" %>
<jsp:useBean id="fileFolderList" class="com.zeroio.iteam.base.FileFolderList" scope="request"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
</script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%-- START Document folder trails--%>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <zeroio:folderHierarchy module="<%= documentModule %>" link="<%= documentFolderList %>"/>
    </td>
  </tr>
</table>
<%-- END Document folder trails--%>
<br />
<dhv:evaluate if="<%= hasPermission %>">
<dhv:permission name="<%= permission_doc_folders_add %>">
<img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle"/>
<a href="<%= documentFolderAdd %>&parentId=<%= fileItemList.getFolderId() %>&folderId=<%= fileItemList.getFolderId() %>"><dhv:label name="documents.documents.newFolder">New Folder</dhv:label></a>
</dhv:permission>
<dhv:permission name='<%= permission_doc_folders_add+","+ permission_doc_files_upload %>' all="true">
|
</dhv:permission>
<dhv:permission name="<%= permission_doc_files_upload %>">
<img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle"/>
<a href="<%= documentFileAdd %>&parentId=<%= fileItemList.getFolderId() %>&folderId=<%= fileItemList.getFolderId() %>"><dhv:label name="documents.documents.submitFile">Submit File</dhv:label></a>
</dhv:permission>
<dhv:evaluate if="<%= fileItemList.getFolderId() != -1 %>">
<dhv:permission name="<%= permission_doc_folders_edit %>">
  <dhv:permission name='<%= permission_doc_folders_add +","+permission_doc_files_upload %>' all="false">
|
  </dhv:permission>
<img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle">
<a href="<%= documentFolderModify %>&folderId=<%= fileItemList.getFolderId() %>&id=<%= fileItemList.getFolderId() %>&parentId=<%= fileItemList.getFolderId() %>"><dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label></a>
</dhv:permission>
</dhv:evaluate>
</dhv:evaluate>
<dhv:permission name='<%= permission_doc_folders_add +","+ permission_doc_files_upload +","+ permission_doc_folders_edit %>' all="false">
<br>
&nbsp;<br>
</dhv:permission>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" align="center" nowrap>&nbsp;</th>
    <th width="100%"><strong><dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="accounts.accounts_add.Type">Type</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="accounts.accounts_documents_details.Size">Size</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="documents.documents.dateModified">Date Modified</dhv:label></strong></th>
  </tr>
<dhv:evaluate if="<%= fileItemList.size() == 0 && fileFolderList.size() == 0 %>">
  <tr class="row2">
    <td colspan="6"><dhv:label name="project.noFilesToDisplay">No files to display.</dhv:label></td>
  </tr>
</dhv:evaluate>
<%
  int rowid = 0;
  if ( fileFolderList.size() > 0 ) {
    Iterator folders = fileFolderList.iterator();
    while (folders.hasNext()) {
      FileFolder thisFolder = (FileFolder) folders.next();
      rowid = (rowid != 1?1:2);
%>
  <tr>
    <td class="row<%= rowid %>" align="center" nowrap>
    <% if (documentModule.equals("Pipeline")) { %>
      <a href="javascript:displayMenu('selectfo<%= thisFolder.getId() %>', 'menuFolder', '<%= thisFolder.getId() %>','<%= thisFolder.getParentId() %>', '<%= specialID %>', '<%= hasPermission %>')"
         onMouseOver="over(0, 'fo<%= thisFolder.getId() %>')"
         onMouseOut="out(0, 'fo<%= thisFolder.getId() %>'); hideMenu('menuFolder');">
         <img src="images/select.gif" name="selectfo<%= thisFolder.getId() %>" id="selectfo<%= thisFolder.getId() %>" align="absmiddle" border="0"></a>
    <% } else { %>
      <a href="javascript:displayMenu('selectfo<%= thisFolder.getId() %>', 'menuFolder', '<%= thisFolder.getId() %>','<%= thisFolder.getParentId() %>', '<%= specialID %>')"
         onMouseOver="over(0, 'fo<%= thisFolder.getId() %>')"
         onMouseOut="out(0, 'fo<%= thisFolder.getId() %>'); hideMenu('menuFolder');">
         <img src="images/select.gif" name="selectfo<%= thisFolder.getId() %>" id="selectfo<%= thisFolder.getId() %>" align="absmiddle" border="0"></a>
    <% } %>
    </td>
    <td class="row<%= rowid %>" width="100%">
      <img src="images/stock_folder-23.gif" border="0" align="absmiddle">
      <a href="<%= documentFolderList %>&folderId=<%= thisFolder.getId() %><%= thisFolder.getDisplay() == 2?"&details=true":"" %>"><%= toHtml(thisFolder.getSubject()) %></a>
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <dhv:label name="project.folder.lowercase">folder</dhv:label>
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <%= thisFolder.getItemCount() %> 
      <% if (thisFolder.getItemCount() == 1) {%>
        <dhv:label name="project.item.lowercase">item</dhv:label>
      <%} else {%>
        <dhv:label name="project.items.lowercase">items</dhv:label>
      <%}%>
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      --
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <zeroio:tz timestamp="<%= thisFolder.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" /><br />
      <dhv:username id="<%= thisFolder.getModifiedBy() %>"/>
    </td>
  </tr>
<%
    }
  }
  if ( fileItemList.size() > 0) {
    Iterator iterator = fileItemList.iterator();
    while (iterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      FileItem thisFile = (FileItem)iterator.next();
%>    
  <tr>
    <td class="row<%= rowid %>" align="center" nowrap>
    <% if (documentModule.equals("Pipeline")) { %>
      <zeroio:debug value='<%= "JSP:: the value of hasPermission is "+hasPermission %>'/>
      <a href="javascript:displayMenu('selectfi<%= thisFile.getId() %>', 'menuFile', '<%= thisFile.getFolderId() %>', '<%= thisFile.getId() %>','<%= specialID %>', '<%= hasPermission %>')"
         onMouseOver="over(0, 'fi<%= thisFile.getId() %>')"
         onmouseout="out(0, 'fi<%= thisFile.getId() %>'); hideMenu('menuFile');">
         <img src="images/select.gif" name="selectfi<%= thisFile.getId() %>" id="selectfi<%= thisFile.getId() %>" align="absmiddle" border="0"></a>
   <% } else { %>
      <a href="javascript:displayMenu('selectfi<%= thisFile.getId() %>', 'menuFile', '<%= thisFile.getFolderId() %>', '<%= thisFile.getId() %>','<%= specialID %>')"
         onMouseOver="over(0, 'fi<%= thisFile.getId() %>')"
         onmouseout="out(0, 'fi<%= thisFile.getId() %>'); hideMenu('menuFile');">
         <img src="images/select.gif" name="selectfi<%= thisFile.getId() %>" id="selectfi<%= thisFile.getId() %>" align="absmiddle" border="0"></a>
   <% } %>
     </td>
    <td class="row<%= rowid %>" width="100%">
      <%= thisFile.getImageTag("-23") %>
      <a href="<%= documentFileDetails %>&folderId=<%= thisFile.getFolderId() %>&fid=<%= thisFile.getId() %>"><%= toHtml(thisFile.getSubject()) %></a>
    </td>
    <td class="row<%= rowid %>" align="center" nowrap><%= toHtml(thisFile.getExtension()) %></td>
    <td class="row<%= rowid %>" align="right" nowrap>
      <%= thisFile.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <%= thisFile.getVersion() %>&nbsp;
    </td>
    <td class="row<%= rowid %>" align="center" nowrap>
      <zeroio:tz timestamp="<%= thisFile.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" /><br />
      <dhv:username id="<%= thisFile.getModifiedBy() %>"/>
    </td>
  </tr>
<% }} %>
</table>
