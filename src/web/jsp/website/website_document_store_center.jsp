<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: document_store_center.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.documents.base.*"%>
<jsp:useBean id="documentStore"
	class="org.aspcfs.modules.documents.base.DocumentStore" scope="request" />
<jsp:useBean id="currentMember"
	class="org.aspcfs.modules.documents.base.DocumentStoreTeamMember"
	scope="request" />
<jsp:useBean id="documentStoreView" class="java.lang.String"
	scope="session" />
<jsp:useBean id="fileFolderList"
	class="com.zeroio.iteam.base.FileFolderList" scope="request" />
<%@ include file="../initPage.jsp"%>
<script language="JavaScript" type="text/javascript"
	src="javascript/spanDisplay.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a
				href="WebsiteDocuments.do?command=View<%=addLinkParams(request, "popup|row|siteId|forEmail")%>"><dhv:label
					name="Documents" mainMenuItem="true">Documents</dhv:label> </a> >
			<dhv:label name="documents.documentStoreDetails">Document Store Details</dhv:label>
		</td>
	</tr>
</table>
<%-- End trails --%>
<%
if (documentStore.getId() == -1) {
%>
<br />
<font color="red"><dhv:label
		name="documents.documentStoreDoesNotExist">This document store does not belong to you, or does not exist!</dhv:label>
</font>
<%
        } else {
        String section = (String) request.getAttribute("IncludeSection");
        String includeSection = "website_document_store_center_" + section
            + ".jsp";
%>
<table border="0" width="100%">
	<tr>
		<td>
			<img src="images/icons/stock_navigator-open-toolbar-16.gif"
				border="0" align="absmiddle">
		</td>
		<td width="100%">
			<strong><%=toHtml(documentStore.getTitle())%> </strong>
		</td>
	</tr>
</table>
<dhv:evaluate if="<%=!documentStore.isTrashed()%>">
	<br>
	<dhv:documentPermission name="documentcenter-documents-folders-add">
		<img src="images/icons/stock_new-dir-16.gif" border="0"
			align="absmiddle" />
		<a
			href="WebsiteDocumentsFolders.do?command=Add&documentStoreId=<%=documentStore.getId()%>&parentId=<%=documentStore.getFiles().getFolderId()%>&folderId=<%=documentStore.getFiles().getFolderId()%><%=addLinkParams(request, "popup|row|siteId|forEmail")%>"><dhv:label
				name="documents.documents.newFolder">New Folder</dhv:label>
		</a>
	</dhv:documentPermission>
	<dhv:documentPermission
		name="documentcenter-documents-folders-add,documentcenter-documents-files-upload"
		if="all">
|
</dhv:documentPermission>
	<dhv:documentPermission name="documentcenter-documents-files-upload">
		<img src="images/icons/stock_insert-file-16.gif" border="0"
			align="absmiddle" />
		<a
			href="WebsiteDocuments.do?command=Add&documentStoreId=<%=documentStore.getId()%>&folderId=<%=documentStore.getFiles().getFolderId()%><%=addLinkParams(request, "popup|row|siteId|forEmail")%>"><dhv:label
				name="documents.documents.submitFile">Submit File</dhv:label>
		</a>
	</dhv:documentPermission>
	<dhv:evaluate if="<%=documentStore.getFiles().getFolderId() != -1%>">
		<dhv:documentPermission name="documentcenter-documents-folders-edit">
			<dhv:documentPermission
				name="documentcenter-documents-folders-add,documentcenter-documents-files-upload"
				if="any">
|
  </dhv:documentPermission>
			<img src="images/icons/stock_rename-page-16.gif" border="0"
				align="absmiddle" />
			<a
				href="WebsiteDocumentsFolders.do?command=Modify&documentStoreId=<%=documentStore.getId()%>&folderId=<%=documentStore.getFiles().getFolderId()%>&id=<%=documentStore.getFiles().getFolderId()%>&parentId=<%=documentStore.getFiles().getFolderId()%><%=addLinkParams(request, "popup|row|siteId|forEmail")%>"><dhv:label
					name="documents.documents.renameFolder">Rename Folder</dhv:label>
			</a>
		</dhv:documentPermission>
	</dhv:evaluate>
	<dhv:documentPermission
		name="documentcenter-documents-folders-add,documentcenter-documents-files-upload,documentcenter-documents-folders-edit"
		if="any">
	</dhv:documentPermission>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
	<tr>
		<td width="100%"
			style="background-image: none; background-color: transparent; border: 0px; border-bottom: 1px solid #666; cursor: default;">
			&nbsp;
		</td>
	</tr>
</table>

<table cellpadding="4" cellspacing="0" border="0" width="100%">
	<tr>
		<td class="containerBack">
			<jsp:include page="<%=includeSection%>" flush="true" />
			<br>
		</td>
	</tr>
</table>
<%
}
%>
<iframe src="empty.html" name="server_commands" id="server_commands"
	style="visibility:hidden" height="0"></iframe>
