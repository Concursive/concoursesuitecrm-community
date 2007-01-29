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
  - Version: $Id: accounts_contacts_documents_list.jsp 28.12.2006 14:06:21 zhenya.zhidok $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="accounts_contacts_documents_list_menu.jsp" %>
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
<dhv:label name="accounts.accounts_documents_details.Documents">Documents</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="contacts" object="OrgDetails" param="<%= "orgId=" + OrgDetails.getOrgId() %>" hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <dhv:container name="accountscontacts" selected="documents" object="ContactDetails" param="<%= "id=" + ContactDetails.getId() %>" hideContainer="<%= !ContactDetails.getEnabled() || ContactDetails.isTrashed() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
    <%
      String permission_doc_folders_add ="accounts-accounts-contacts-documents-add";
      String permission_doc_files_upload = "accounts-accounts-contacts-documents-add";
      String permission_doc_folders_edit = "accounts-accounts-contacts-documents-edit";
      String documentFolderAdd ="AccountsContactsDocumentsFolders.do?command=Add&contactId="+ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
      String documentFileAdd = "AccountsContactsDocuments.do?command=Add&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
      String documentFolderModify = "AccountsContactsDocumentsFolders.do?command=Modify&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
      String documentFolderList = "AccountsContactsDocuments.do?command=View&contactId="+ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
      String documentFileDetails = "AccountsContactsDocuments.do?command=Details&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
      String documentModule = "Contacts";
      String specialID = ""+ContactDetails.getId();
      boolean hasPermission = true;
    %>
    <%@ include file="documents_list_include.jsp" %>&nbsp;
  </dhv:container>
</dhv:container>
