<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
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
<br>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="7">
      <strong>File Versions</strong>
    </th>
  </tr>
  <tr>
    <th align="center" nowrap>
      Action
    </th>
    <th width="100%">
      File
    </th>
    <th align="center" nowrap>
      Size
    </th>
    <th align="center" nowrap>
      Version
    </th>
    <th align="center" nowrap>
      Submitted
    </th>
    <th align="center" nowrap>
      Sent By
    </th>
    <th align="center" nowrap>
      D/L
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
        <a href="ProjectManagementFiles.do?command=Download&pid=<%= Project.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>">Download</a><br />
        <img src="images/icons/stock_zoom-page-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="javascript:popURL('ProjectManagementFiles.do?command=Download&pid=<%= Project.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>&view=true', 'Content', 640,480, 1, 1);">View File Contents</a><br />
      </zeroio:permission>
      <zeroio:permission name="project-documents-files-delete">
        <img src="images/icons/stock_delete-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <a href="javascript:confirmDelete('ProjectManagementFiles.do?command=Delete&pid=<%= Project.getId() %>&fid=<%= FileItem.getId() %>&ver=<%= thisVersion.getVersion() %>&folderId=<%= FileItem.getFolderId() %>');">Delete Version</a><br />
      </zeroio:permission>
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
      <zeroio:tz timestamp="<%= thisVersion.getEntered() %>"/>
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
