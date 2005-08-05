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
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <zeroio:folderHierarchy showLastLink="true"/> >
      <%= FileItem.getSubject() %>
    </td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="7">
      <strong><dhv:label name="documents.documents.fileVersions">File Versions</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <th width="8">&nbsp;</th>
    <th width="100%">
      <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
    </th>
    <th align="center" nowrap>
      <dhv:label name="accounts.accounts_documents_details.Size">Size</dhv:label>
    </th>
    <th align="center" nowrap>
      <dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label>
    </th>
    <th align="center" nowrap>
      <dhv:label name="accounts.accounts_documents_details.Submitted">Submitted</dhv:label>
    </th>
    <th align="center" nowrap>
      <dhv:label name="accounts.accounts_documents_details.SentBy">Sent By</dhv:label>
    </th>
    <th align="center" nowrap>
      <dhv:label name="accounts.accounts_documents_details.DL">D/L</dhv:label>
    </th>
  </tr>
<%          
  int rowid = 0;
  Iterator versionList = FileItem.getVersionList().iterator();
  while (versionList.hasNext()) {
    rowid = (rowid != 1?1:2);
    FileItemVersion thisVersion = (FileItemVersion)versionList.next();
%>
  <tr class="row<%= rowid %>">
    <td rowspan="2" valign="middle" nowrap>
      <zeroio:permission name="project-documents-files-download">
        <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="ProjectManagementFiles.do?command=Download&pid=<%= Project.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>"><dhv:label name="accounts.accounts_documents_details.Download">Download</dhv:label></a><br />
        <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="javascript:popURL('ProjectManagementFiles.do?command=Download&pid=<%= Project.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>&view=true', 'Content', 640,480, 1, 1);"><dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label></a><br />
      </zeroio:permission>
      <dhv:evaluate if="<%= !Project.isTrashed() %>" >
        <zeroio:permission name="project-documents-files-delete">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
          <a href="javascript:confirmDelete('ProjectManagementFiles.do?command=Delete&pid=<%= Project.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>&folderId=<%= FileItem.getFolderId() %>');"><dhv:label name="project.deleteVersion">Delete Version</dhv:label></a><br />
        </zeroio:permission>
      </dhv:evaluate>
       &nbsp;
    </td>
    <td width="100%">
      <%= thisVersion.getImageTag() %><%= thisVersion.getClientFilename() %>
    </td>
    <td align="right" nowrap>
      <%= thisVersion.getRelativeSize() %>k&nbsp;
    </td>
    <td align="right" nowrap>
      <%= thisVersion.getVersion() %>&nbsp;
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= thisVersion.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
    </td>
    <td align="center" nowrap>
      <dhv:username id="<%= thisVersion.getEnteredBy() %>"/>
    </td>
    <td align="center" nowrap>
      <%= thisVersion.getDownloads() %>
    </td>
  </tr>
  <tr class="row<%= rowid %>">
    <td colspan="6">
      <i><%= thisVersion.getSubject() %></i>
    </td>
  </tr>
<%
  }
%>
</table>
