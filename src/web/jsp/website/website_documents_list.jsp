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
  - Version: $Id:  $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,com.zeroio.iteam.base.*,java.text.*,org.aspcfs.modules.base.Constants, org.aspcfs.utils.web.RequestUtils" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="fileFolderList" class="com.zeroio.iteam.base.FileFolderList" scope="request"/>
<jsp:useBean id="fileItemList" class="com.zeroio.iteam.base.FileItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="website_documents_list_menu.jsp" %>
<script language="JavaScript" type="text/javascript">
  loadImages('select');
  
	function fetchImageURL(imageId){
		<%if (request.getParameter("row") != null) {%> 
			var imageURL = "ProcessFileItemImage.do?command=StreamImage&id="+imageId+"&path=website&row="+<%=request.getParameter("row")%>;
			window.opener.ImageLibrary.insertImage(imageURL);
			top.close();
		<%} else if (request.getParameter("siteId") != null) {%>
			var params = "<%=addLinkParams(request,"popup|siteId")%>";
			var url = "Sites.do?command=UpdateLogo&logoImageId="+imageId + params;		
	    window.opener.location.href=url;
			top.close();
		<%} else if (request.getParameter("forEmail") != null){%>
			var baseURL = "<%=RequestUtils.getServerUrl(request)%>";
			var imageURL = "ProcessFileItemImage.do?command=StreamImage&id="+imageId+"&path=website&baseURL=${baseURL=" + baseURL +"}";
			window.opener.ImageLibrary.insertImage(imageURL);
			top.close();
		<%}%>
	}
	
</script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%-- Trails --%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Website.do">Website</a> > 
Media
</td>
</tr>
</table>
</dhv:evaluate>
<%-- End Trails --%>
  <%
    String permission_doc_folders_add ="accounts-accounts-documents-add";
    String permission_doc_files_upload = "accounts-accounts-documents-add";
    String permission_doc_folders_edit = "accounts-accounts-documents-edit";
    String documentFolderAdd ="WebsiteMediaFolders.do?command=Add";
    String documentFileAdd = "WebsiteMedia.do?command=Add";
    String documentFolderModify = "WebsiteMediaFolders.do?command=Modify";
    String documentFolderList = "WebsiteMedia.do?command=View" + addLinkParams(request,"popup|row|siteId|forEmail");
    String documentFileDetails = "WebsiteMedia.do?command=Details";
    String documentModule = "Website";
    String specialID = ""+ Constants.DOCUMENTS_WEBSITE;
    boolean hasPermission = true;
  %>
<%-- START Document folder trails--%>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
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
<a href="<%= documentFolderAdd %>&parentId=<%= fileItemList.getFolderId() %>&folderId=<%= fileItemList.getFolderId()%><%=addLinkParams(request,"popup|row|siteId|forEmail")%>"><dhv:label name="documents.documents.newFolder">New Folder</dhv:label></a>
</dhv:permission>
<dhv:permission name='<%= permission_doc_folders_add+","+ permission_doc_files_upload %>' all="true">
|
</dhv:permission>
<dhv:permission name="<%= permission_doc_files_upload %>">
<img src="images/icons/stock_insert-file-16.gif" border="0" align="absmiddle"/>
<a href="<%= documentFileAdd %>&parentId=<%= fileItemList.getFolderId() %>&folderId=<%= fileItemList.getFolderId() %><%=addLinkParams(request,"popup|row|siteId||forEmail")%>"><dhv:label name="documents.documents.submitFile">Submit File</dhv:label></a>
</dhv:permission>
<dhv:evaluate if="<%= fileItemList.getFolderId() != -1 %>">
<dhv:permission name="<%= permission_doc_folders_edit %>">
  <dhv:permission name='<%= permission_doc_folders_add +","+permission_doc_files_upload %>' all="false">
|
  </dhv:permission>
<img src="images/icons/stock_rename-page-16.gif" border="0" align="absmiddle">
<a href="<%= documentFolderModify %>&folderId=<%= fileItemList.getFolderId() %>&id=<%= fileItemList.getFolderId() %>&parentId=<%= fileItemList.getFolderId() %><%=addLinkParams(request,"popup|row|siteId||forEmail")%>"><dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label></a>
</dhv:permission>
</dhv:evaluate>
<dhv:permission name='<%= permission_doc_folders_add +","+ permission_doc_files_upload +","+ permission_doc_folders_edit %>' all="false">
<br>
&nbsp;<br>
</dhv:permission>
</dhv:evaluate>
<dhv:evaluate if="<%= fileItemList.size() == 0 && fileFolderList.size() == 0 %>">
	<table cellpadding="4" cellspacing="0" width="100%">
		<tr class="row2">
			<td colspan="6"><dhv:label name="project.noFilesToDisplay">No files to display.</dhv:label></td>
		</tr>
	</table>
</dhv:evaluate>
<%
  int rowid = 0;
  if ( fileFolderList.size() > 0 ) {
%>
<table cellpadding="4" cellspacing="0" width="100%">
<%
    Iterator folders = fileFolderList.iterator();
    while (folders.hasNext()) {
      FileFolder thisFolder = (FileFolder) folders.next();
      rowid = (rowid != 1?1:2);
%>
  <tr>
		<dhv:evaluate if="<%= !isPopup(request) %>">
			<td class="row<%= rowid %>" align="center" nowrap>
				<a href="javascript:displayMenu('selectfo<%= thisFolder.getId() %>', 'menuFolder', '<%= thisFolder.getId() %>','<%= thisFolder.getParentId() %>', '<%= specialID %>')"
					 onMouseOver="over(0, 'fo<%= thisFolder.getId() %>')"
					 onMouseOut="out(0, 'fo<%= thisFolder.getId() %>'); hideMenu('menuFolder');">
					 <img src="images/select.gif" name="selectfo<%= thisFolder.getId() %>" id="selectfo<%= thisFolder.getId() %>" align="absmiddle" border="0"></a>
			</td>
		</dhv:evaluate>
    <td class="row<%= rowid %>" width="100%">
      <img src="images/stock_folder-23.gif" border="0" align="absmiddle">
      <a href="<%= documentFolderList %>&folderId=<%= thisFolder.getId() %><%= thisFolder.getDisplay() == 2?"&details=true":"" %>"><%= toHtml(thisFolder.getSubject()) %></a>
      (<%= thisFolder.getItemCount() %>) 
      <% if (thisFolder.getItemCount() == 1) {%>
        <dhv:label name="project.item.lowercase">item</dhv:label>
      <%} else {%>
        <dhv:label name="project.items.lowercase">items</dhv:label>
      <%}%>
    </td>
  </tr>
<%
    }
%>
</table>

<%
  }
  if ( fileItemList.size() > 0) {
%>
<br />
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
		 <td>
<%
    Iterator iterator = fileItemList.iterator();
    while (iterator.hasNext()) {
      rowid = (rowid != 1?1:2);
      FileItem thisFile = (FileItem)iterator.next();
%>    
				<div style="float: left;width: 140px;height: 190px;padding-right: 3px;">
          <table border="0" width="140" height="160" cellpadding="0" cellspacing="0">
            <tr>
							<td width="140" height="160" valign="top">
							 <div>
							 <dhv:evaluate if="<%= !isPopup(request) %>">
									<a href="javascript:displayMenu('selectfi<%= thisFile.getId() %>', 'menuFile', '<%= thisFile.getFolderId() %>', '<%= thisFile.getId() %>','<%= specialID %>')"
										 onMouseOver="over(0, 'fi<%= thisFile.getId() %>')"
										 onmouseout="out(0, 'fi<%= thisFile.getId() %>'); hideMenu('menuFile');">
										 <img src="images/select.gif" name="selectfi<%= thisFile.getId() %>" id="selectfi<%= thisFile.getId() %>" align="absmiddle" border="0"></a><div>
								</dhv:evaluate>
							 <dhv:evaluate if="<%= isPopup(request) %>">
							 	<a href="javascript:fetchImageURL(<%=thisFile.getId()%>);">
							 </dhv:evaluate>
								<dhv:fileItemImage id="<%=  thisFile.getId() %>" path="website" thumbnail="true" name="<%=  toHtml(thisFile.getSubject()) %>"/>
								<div><%=  toHtml(thisFile.getSubject()) %></div>
								<div><%= toHtml(thisFile.getClientFilename())%></div>
								<div><%= thisFile.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label></div>
							 <dhv:evaluate if="<%= isPopup(request) %>">
							 </a>
							 </dhv:evaluate>
							</td>
						</tr>
          </table>
				</div>
<% }
%>
    </td>
  </tr>
</table>&nbsp;
<%
} %>
