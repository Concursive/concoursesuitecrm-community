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
  - Version: $Id: accounts_documents_folders_add.jsp 11310 2005-04-13 16:05:00 -0400 (Wed, 13 Apr 2005) mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page import="java.util.*,com.zeroio.iteam.base.*"%>
<jsp:useBean id="fileFolder" class="com.zeroio.iteam.base.FileFolder"
	scope="request" />
<jsp:useBean id="documentStore"
	class="org.aspcfs.modules.documents.base.DocumentStore" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<%@ include file="../initPage.jsp"%>
<body onLoad="document.inputForm.subject.focus();">
	<script language="JavaScript">
  function checkForm(form) {
    if (form.dosubmit.value == "false") {
      return true;
    }
    var formTest = true;
    var messageText = "";
    if (form.subject.value == "") {
      messageText += label("Name.required", "- Name is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      messageText = label("Form.not.submitted", "The form could not be submitted.          \r\nPlease verify the following:\r\n\r\n") + messageText;
      form.dosubmit.value = "true";
      alert(messageText);
      return false;
    }
    return true;
  }
</script>
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
	<form method="POST" name="inputForm"
		action="WebsiteDocumentsFolders.do?command=Save&auto-populate=true"
		onSubmit="return checkForm(this);">
		<dhv:evaluate if="<%=!isPopup(request)%>">
			<%-- Trails --%>
			<table class="trails" cellspacing="0">
				<tr>
					<td>
						<a href="Website.do">Website</a>
						<a href="WebsiteDocuments.do?command=List"><dhv:label
								name="accounts.accounts_documents_details.Documents">Documents</dhv:label>
						</a> >
						<%
						if (fileFolder.getId() > -1) {
						%>
						<dhv:label
							name="accounts.accounts_documents_folders_add.ModifyFolder">Modify Folder</dhv:label>
						<%
						} else {
						%>
						<dhv:label name="documents.documents.newFolder">New Folder</dhv:label>
						<%
						}
						%>
					</td>
				</tr>
			</table>
		</dhv:evaluate>
		<%-- End Trails --%>
		<br>
		<input type="submit"
			value=" <dhv:label name="global.button.save">Save</dhv:label> "
			name="save" />
		<input type="submit"
			value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
			onClick="javascript:this.form.dosubmit.value='false';this.form.action='WebsiteDocuments.do?command=DocumentStoreCenter<%=addLinkParams(request, "popup|row|siteId|forEmail")%>';" />
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
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr class="subtab">
							<td>
								<%
								          String documentFolderList = "WebsiteDocuments.do?command=View"
								          + addLinkParams(request, "popup|row|siteId|forEmail");
								      String documentModule = "Website";
								%>
								<zeroio:folderHierarchy module="<%=documentModule%>"
									link="<%=documentFolderList%>" />
							</td>
						</tr>
					</table>
					<dhv:formMessage />
					<table cellpadding="4" cellspacing="0" width="100%"
						class="pagedList">
						<tr>
							<th colspan="2">
								<strong> <%
 if (fileFolder.getId() > -1) {
 %> <dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label>
									<%
									} else {
									%> <dhv:label name="documents.documents.newFolder">New Folder</dhv:label>
									<%
									}
									%> </strong>
							</th>
						</tr>
						<tr class="containerBody">
							<td nowrap class="formLabel">
								<dhv:label name="contacts.name">Name</dhv:label>
							</td>
							<td>
								<input type="text" name="subject" size="59" maxlength="255"
									value="<%=toHtmlValue(fileFolder.getSubject())%>">
								<input type="hidden" name="display" value="-1" />
								<font color="red">*</font>
								<%=showAttribute(request, "subjectError")%>
							</td>
						</tr>
					</table>
					<br>
					<input type="hidden" name="modified"
						value="<%=fileFolder.getModifiedString()%>">
					<input type="hidden" name="id" value="<%=fileFolder.getId()%>">
					<input type="hidden" name="parentId"
						value="<%=fileFolder.getParentId()%>">
					<input type="hidden" name="documentStoreId"
						value="<%=documentStore.getId()%>">
					<input type="hidden" name="folderId"
						value="<%=request.getParameter("folderId")%>">
					<input type="hidden" name="dosubmit" value="true">
					<%=addHiddenParams(request, "popup|row|siteId|forEmail")%>
				</td>
			</tr>
		</table>
		<br />
		<input type="submit"
			value=" <dhv:label name="global.button.save">Save</dhv:label> "
			name="save" />
		<input type="submit"
			value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
			onClick="javascript:this.form.dosubmit.value='false';this.form.action='WebsiteDocuments.do?command=DocumentStoreCenter<%=addLinkParams(request, "popup|row|siteId|forEmail")%>';" />
		<br />
	</form>
</body>
