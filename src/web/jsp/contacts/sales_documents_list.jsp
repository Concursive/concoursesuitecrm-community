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
  - Version: $Id: contacts_documents_list.jsp 17754 2006-12-11 vadim.vishnevsky@corratech.com $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.contacts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="from" class="java.lang.String" scope="request" />
<jsp:useBean id="listForm" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp" %>
<%@ include file="sales_documents_list_menu.jsp" %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Sales.do"><dhv:label name="Leads" mainMenuItem="true">Leads</dhv:label></a> >
<% if (from != null && "list".equals(from) && !"dashboard".equals(from)) { %>
  <a href="Sales.do?command=List&listForm=<%= (listForm != null? listForm:"") %>"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}%>
<a href="Sales.do?command=Details&contactId=<%= ContactDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>"><dhv:label name="sales.leadDetails">Lead Details</dhv:label></a> >
<dhv:label name="contacts.documents">Documents</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="leads" selected="documents" object="ContactDetails" hideContainer="<%= "true".equals(request.getParameter("actionplan")) %>" param="<%= "id=" + ContactDetails.getId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId|from|listForm") %>">
  <%
    String permission_doc_folders_add ="sales-leads-documents-add";
    String permission_doc_files_upload;
    if (ContactDetails.getOrgId() > 0) {
      permission_doc_files_upload = "accounts-accounts-contacts-documents-add";
    } else {
      permission_doc_files_upload = "sales-leads-documents-add";
    }
    String permission_doc_folders_edit = "sales-leads-documents-edit";
    String documentFolderAdd ="SalesDocumentsFolders.do?command=Add&contactId="+ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan|from|listForm");
    String documentFileAdd = "SalesDocuments.do?command=Add&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan|from|listForm");
    String documentFolderModify = "SalesDocumentsFolders.do?command=Modify&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan|from|listForm");
    String documentFolderList = "SalesDocuments.do?command=View&contactId="+ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan|from|listForm");
    String documentFileDetails = "SalesDocuments.do?command=Details&contactId="+ ContactDetails.getId()+  addLinkParams(request, "popup|popupType|actionId|actionplan|from|listForm");
    String documentModule = "Contacts";
    String specialID = ""+ContactDetails.getId();
    boolean hasPermission = true;
  %>
  
  <%@ include file="../accounts/documents_list_include.jsp" %>&nbsp;
</dhv:container>
