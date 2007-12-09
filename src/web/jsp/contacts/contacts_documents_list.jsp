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
  - Version: $Id: contacts_documents_list.jsp 17754 2006-12-11 vadim.vishnevsky@corratech.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="contacts_documents_list_menu.jsp" %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ExternalContacts.do"><dhv:label name="Contacts">Contacts</dhv:label></a> > 
<a href="ExternalContacts.do?command=SearchContacts"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ExternalContacts.do?command=ContactDetails&id=<%=ContactDetails.getId()%>"><dhv:label name="accounts.accounts_contacts_add.ContactDetails">Contact Details</dhv:label></a> >
<dhv:label name="contacts.documents">Documents</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="contacts" selected="documents" object="ContactDetails" hideContainer="<%= "true".equals(request.getParameter("actionplan")) %>" param="<%= "id=" + ContactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <%
    String permission_doc_folders_add ="contacts-external_contacts-documents-add";
    String permission_doc_files_upload;
    if (ContactDetails.getOrgId() > 0) {
      permission_doc_files_upload = "accounts-accounts-contacts-documents-add";
    } else {
      permission_doc_files_upload = "contacts-external_contacts-documents-add";
    }
    String permission_doc_folders_edit = "contacts-external_contacts-documents-edit";
    String documentFolderAdd ="ExternalContactsDocumentsFolders.do?command=Add&contactId="+ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
    String documentFileAdd = "ExternalContactsDocuments.do?command=Add&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
    String documentFolderModify = "ExternalContactsDocumentsFolders.do?command=Modify&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
    String documentFolderList = "ExternalContactsDocuments.do?command=View&contactId="+ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
    String documentFileDetails = "ExternalContactsDocuments.do?command=Details&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan");
    String documentModule = "Contacts";
    String specialID = ""+ContactDetails.getId();
    boolean hasPermission = true;
  %>
  
  <%@ include file="../accounts/documents_list_include.jsp" %>&nbsp;
</dhv:container>
