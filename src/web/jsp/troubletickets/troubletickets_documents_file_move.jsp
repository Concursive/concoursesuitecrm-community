<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.troubletickets.base.Ticket" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="FileItem" class="com.zeroio.iteam.base.FileItem" scope="request"/>
<jsp:useBean id="folderHierarchy" class="com.zeroio.iteam.base.FileFolderHierarchy" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
  <tr>
    <td>
      Select a folder to move the item to:<br>
      <%= FileItem.getImageTag("-23") %>
      <%= toHtml(FileItem.getSubject()) %>
    </td>
  </tr>
</table>
&nbsp;<br>
<table cellpadding="0" cellspacing="0" width="100%" border="1" rules="cols">
  <tr class="section">
    <td valign="top" width="100%">
      <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
      <img alt="" src="images/icons/stock_open-16-19.gif" border="0" align="absmiddle" height="16" width="19"/>
      <a href="TroubleTicketsDocuments.do?command=SaveMove&tId=<%= TicketDetails.getId() %>&fid=<%= FileItem.getId() %>&popup=true&folderId=0&return=TroubleTicketsDocuments&param=<%= TicketDetails.getId() %>&param2=<%= FileItem.getFolderId() %>">Home</a>
      <dhv:evaluate if="<%= (FileItem.getFolderId() == 0) || (FileItem.getFolderId() == -1) %>">
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
    <% for(int j=1;j<thisFolder.getLevel();j++){ %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%}%>
      <img border="0" src="images/treespace.gif" align="absmiddle" height="16" width="19">
      <img alt="" src="images/tree7o.gif" border="0" align="absmiddle" height="16" width="19"/>
      <img border="0" src="images/icons/stock_open-16-19.gif" align="absmiddle">
      <a href="TroubleTicketsDocuments.do?command=SaveMove&tId=<%= TicketDetails.getId() %>&fid=<%= FileItem.getId() %>&popup=true&folderId=<%= thisFolder.getId() %>&return=TroubleTicketsDocuments&param=<%= TicketDetails.getId() %>&param2=<%= FileItem.getFolderId() %>"><%= toHtml(thisFolder.getSubject()) %></a>
      <dhv:evaluate if="<%= FileItem.getFolderId() == thisFolder.getId() %>">
      (current folder)
      </dhv:evaluate>
    </td>
  </tr>
<%    
  }
%>
</table>
&nbsp;<br>
<input type="button" value="Cancel" onClick="javascript:window.close()">

