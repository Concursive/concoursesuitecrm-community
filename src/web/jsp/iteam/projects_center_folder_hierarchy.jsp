<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="FileFolder" class="com.zeroio.iteam.base.FileFolder" scope="request"/>
<jsp:useBean id="folderHierarchy" class="com.zeroio.iteam.base.FileFolderHierarchy" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
<tr>
  <td>
    Select a folder to move the item to:<br>
    <img src="images/icons/stock_folder-16-19.gif" border="0" align="absmiddle">
    <%= toHtml(FileFolder.getSubject()) %>
  </td>
</tr>
</table>
<br />
<table cellpadding="0" cellspacing="0" width="100%" border="1" rules="cols">
  <tr class="section">
    <td valign="top" width="100%">
      <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
      <img alt="" src="images/icons/stock_open-16-19.gif" border="0" align="absmiddle" height="16" width="19"/>
    <dhv:evaluate if="<%= FileFolder.getParentId() != -1 %>">
      <a href="ProjectManagementFileFolders.do?command=SaveMove&pid=<%= Project.getId() %>&id=<%= FileFolder.getId() %>&popup=true&folderId=0&return=ProjectFiles&param=<%= Project.getId() %>&param2=<%= FileFolder.getParentId() %>">Home</a>
    </dhv:evaluate>
    <dhv:evaluate if="<%= FileFolder.getParentId() == -1 %>">
      Home
      (current folder)
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
    <% if(FileFolder.getParentId() != thisFolder.getId() && FileFolder.getId() != thisFolder.getId()) {  %>
      <a href="ProjectManagementFileFolders.do?command=SaveMove&pid=<%= Project.getId() %>&id=<%= FileFolder.getId() %>&popup=true&folderId=<%= thisFolder.getId() %>&return=ProjectFiles&param=<%= Project.getId() %>&param2=<%= FileFolder.getParentId() %>"><%= toHtml(thisFolder.getSubject()) %></a>
    <% } else if(FileFolder.getId() == thisFolder.getId()) { %>
        <%= toHtml(thisFolder.getSubject()) %>
    <% } else {%>
      <%= toHtml(thisFolder.getSubject()) %>
      (current folder)
    <% } %>
    </td>
  </tr>
<%
  }
%>
</table>
&nbsp;<br>
<input type="button" value="Cancel" onClick="javascript:window.close()">
