<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.documents.base.*" %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <% String documentLink = "DocumentManagement.do?command=DocumentStoreCenter&section=File_Library&documentStoreId="+documentStore.getId(); %>
      <zeroio:folderHierarchy module="Documents" link="<%= documentLink %>" showLastLink="true"/> >
      <%= FileItem.getSubject() %>
    </td>
  </tr>
</table>
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="7">
      <strong><dhv:label name="documents.documents.fileVersions">File Versions</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <th align="center" nowrap>
      <dhv:label name="documents.documents.action">Action</dhv:label>
    </th>
    <th width="100%">
      <dhv:label name="documents.documents.file">File</dhv:label>
    </th>
    <th align="center" nowrap>
      <dhv:label name="documents.documents.size">Size</dhv:label>
    </th>
    <th align="center" nowrap>
      <dhv:label name="documents.documents.version">Version</dhv:label>
    </th>
    <th align="center" nowrap>
      <dhv:label name="documents.documents.submitted">Submitted</dhv:label>
    </th>
    <th align="center" nowrap>
      <dhv:label name="documents.documents.sentBy">Sent By</dhv:label>
    </th>
    <th align="center" nowrap>
      <dhv:label name="documents.documents.DL">D/L</dhv:label>
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
      <dhv:documentPermission name="documentcenter-documents-files-download">
        <img src="images/icons/stock_data-save-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="DocumentStoreManagementFiles.do?command=Download&documentStoreId=<%= documentStore.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>"><dhv:label name="button.download">Download</dhv:label></a><br />
        <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="javascript:popURL('DocumentStoreManagementFiles.do?command=Download&documentStoreId=<%= documentStore.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>&view=true', 'Content', 640,480, 1, 1);"><dhv:label name="accounts.accounts_documents_list_menu.ViewFileContents">View File Contents</dhv:label></a><br />
      </dhv:documentPermission>
      <dhv:evaluate if="<%= !documentStore.isTrashed() %>" >
        <dhv:documentPermission name="documentcenter-documents-files-delete">
          <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
          <a href="javascript:confirmDelete('DocumentStoreManagementFiles.do?command=Delete&documentStoreId=<%= documentStore.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>&folderId=<%= FileItem.getFolderId() %>');"><dhv:label name="project.deleteVersion">Delete Version</dhv:label></a><br />
        </dhv:documentPermission>
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
