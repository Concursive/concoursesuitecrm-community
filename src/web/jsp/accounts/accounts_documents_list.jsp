<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="accounts_documents_list_menu.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
Documents
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>
<dhv:container name="accounts" selected="documents" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <%--TODO:: set the default strings here for the document_list.jsp --%> 
      <%
        String permission_doc_folders_add ="accounts-accounts-documents-add";
        String permission_doc_files_upload = "accounts-accounts-documents-add";
        String permission_doc_folders_edit = "accounts-accounts-documents-edit";
        String documentFolderAdd ="AccountsDocumentsFolders.do?command=Add&orgId="+OrgDetails.getOrgId();
        String documentFileAdd = "AccountsDocuments.do?command=Add&orgId="+ OrgDetails.getOrgId();
        String documentFolderModify = "AccountsDocumentsFolders.do?command=Modify&orgId="+ OrgDetails.getOrgId();
        String documentFolderList = "AccountsDocuments.do?command=View&orgId="+OrgDetails.getOrgId();
        String documentFileDetails = "AccountsDocuments.do?command=Details&orgId="+ OrgDetails.getOrgId();
        String documentModule = "Accounts";
        String specialID = ""+OrgDetails.getId();
      %>
      <%@ include file="documents_list_include.jsp" %>&nbsp;
    </td>
  </tr>
</table>

