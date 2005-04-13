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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="folderHierarchy" class="com.zeroio.iteam.base.FileFolderHierarchy" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
  <tr>
    <td>
      <dhv:label name="documents.documents.selectToMove">Select a folder to move the item to:</dhv:label><br>
      <%= FileItem.getImageTag("-23") %>
      <%= toHtml(FileItem.getSubject()) %>
    </td>
  </tr>
</table>
<br />
<table cellpadding="0" cellspacing="0" width="100%" border="1" rules="cols">
  <tr class="section">
    <td valign="top" width="100%">
      <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
      <img alt="" src="images/icons/stock_open-16-19.gif" border="0" align="absmiddle" height="16" width="19"/>
      <a href="DocumentStoreManagementFiles.do?command=SaveMove&documentStoreId=<%= documentStore.getId() %>&fid=<%= FileItem.getId() %>&popup=true&folderId=0&return=DocumentFiles&param=<%= documentStore.getId() %>&param2=<%= FileItem.getFolderId() %>"><dhv:label name="accounts.accounts_documents_file_move.Home">Home</dhv:label></a>
      <dhv:evaluate if="<%= FileItem.getFolderId() == -1 %>">
      <dhv:label name="documents.documents.currentFolder">(current folder)</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%
  int rowid = 0;
  Iterator i = folderHierarchy.getHierarchy().iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    FileFolder thisFolder = (FileFolder) i.next();
%>
  <tr class="row<%= rowid %>">
    <td valign="top">
      <% for(int j=1;j<thisFolder.getLevel();j++){ %><img border="0" src="images/treespace.gif" align="absmiddle" height="16" width="19">&nbsp;<%}%><img border="0" src="images/treespace.gif" align="absmiddle" height="16" width="19">
      <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
      <img border="0" src="images/icons/stock_open-16-19.gif" align="absmiddle">
      <a href="DocumentStoreManagementFiles.do?command=SaveMove&documentStoreId=<%= documentStore.getId() %>&fid=<%= FileItem.getId() %>&popup=true&folderId=<%= thisFolder.getId() %>&return=DocumentFiles&param=<%= documentStore.getId() %>&param2=<%= FileItem.getFolderId() %>"><%= toHtml(thisFolder.getSubject()) %></a>
      <dhv:evaluate if="<%= FileItem.getFolderId() == thisFolder.getId() %>">
      <dhv:label name="documents.documents.currentFolder">(current folder)</dhv:label>
      </dhv:evaluate>
    </td>
  </tr>
<%    
  }
%>
</table>
&nbsp;<br>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
