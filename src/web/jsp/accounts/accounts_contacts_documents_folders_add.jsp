<%-- 
  - Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: accounts_contacts_documents_folders_add.jsp 28.12.2006 16:35:07 zhenya.zhidok $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.accounts.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="fileFolder" class="com.zeroio.iteam.base.FileFolder" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
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
<form method="POST" name="inputForm" action="AccountsContactsDocumentsFolders.do?command=Save&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>" onSubmit="return checkForm(this);">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=ContactDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<a href="Contacts.do?command=View&orgId=<%=ContactDetails.getOrgId()%>"><dhv:label name="accounts.Contacts">Contacts</dhv:label></a> >
<a href="Contacts.do?command=Details&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<a href="AccountsContactsDocuments.do?command=List&contactId=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label></a> >
<% if(fileFolder.getId() > -1) {%>
  <dhv:label name="accounts.accounts_documents_folders_add.ModifyFolder">Modify Folder</dhv:label>
<%} else {%>
  <dhv:label name="documents.documents.newFolder">New Folder</dhv:label>
<%}%>


</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <dhv:container name="accountscontacts" selected="documents" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>" hideContainer="<%= !ContactDetails.getEnabled() || ContactDetails.isTrashed() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
	<table border="0" cellpadding="4" cellspacing="0" width="100%">
	  <tr class="subtab">
	    <td>
	<%
	String documentFolderList = "AccountsContactsDocuments.do?command=View&contactId="+ContactDetails.getId()+ addLinkParams(request, "popup|popupType|actionId|actionplan");
	String documentModule = "Accounts";
	%>
	      <zeroio:folderHierarchy module="<%= documentModule %>" link="<%= documentFolderList %>"/>
	    </td>
	  </tr>
	</table>
	<br>
	  <input type="submit" value=" <dhv:label name="global.button.save">Save</dhv:label> " name="save" />
	  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='AccountsContactsDocuments.do?command=View&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';" /><br />
	  <dhv:formMessage />
	  <br />
	  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
	    <tr>
	      <th colspan="2">
	        <strong>
	          <% if(fileFolder.getId() > -1) {%>
	            <dhv:label name="accounts.accounts_documents_list_menu.RenameFolder">Rename Folder</dhv:label>
	          <%} else {%>
	            <dhv:label name="documents.documents.newFolder">New Folder</dhv:label>
	          <%}%>
	        </strong>
	      </th>
	    </tr>
	    <tr class="containerBody">
	      <td nowrap class="formLabel"><dhv:label name="contacts.name">Name</dhv:label></td>
	      <td>
	        <input type="text" name="subject" size="59" maxlength="255" value="<%= toHtmlValue(fileFolder.getSubject()) %>">
	        <input type="hidden" name="display" value="-1"/>
	        <font color="red">*</font> <%= showAttribute(request, "subjectError") %>
	      </td>
	    </tr>
	  </table>
	  <br>
	  <input type="hidden" name="modified" value="<%= fileFolder.getModifiedString() %>">
	  <input type="hidden" name="contactId" value="<%= ContactDetails.getId() %>">
	  <input type="hidden" name="id" value="<%= fileFolder.getId() %>">
	  <input type="hidden" name="parentId" value="<%= fileFolder.getParentId() %>">
	  <input type="hidden" name="folderId" value="<%= request.getParameter("folderId") %>">
	  <input type="hidden" name="dosubmit" value="true">
	  <input type="submit" value=" <dhv:label name="global.button.save">Save</dhv:label> " name="save" />
	  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.dosubmit.value='false';this.form.action='AccountsContactsDocuments.do?command=View&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|actionplan") %>';" /><br />
  </dhv:container>
</dhv:container>
</form>
</body>
  