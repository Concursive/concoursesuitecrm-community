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
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.* " %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="currentMember" class="org.aspcfs.modules.documents.base.DocumentStoreTeamMember" scope="request"/>
<jsp:useBean id="documentStoreView" class="java.lang.String" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="DocumentManagement.do"><dhv:label name="Documents" mainMenuItem="true">Documents</dhv:label></a> >
<dhv:label name="documents.documentStoreDetails">Document Store Details</dhv:label>
</td>
</tr>
</table>
<%-- End trails --%>
<%
  if (documentStore.getId() == -1) {
%>
<br /><font color="red"><dhv:label name="documents.documentStoreDoesNotExist">This document store does not belong to you, or does not exist!</dhv:label></font>
<%
  } else {
    String section = (String) request.getAttribute("IncludeSection");
    String includeSection = "document_store_center_" + section + ".jsp";
%>
<table border="0" width="100%">
  <tr>
    <td>
      <img src="images/icons/stock_navigator-open-toolbar-16.gif" border="0" align="absmiddle">
    </td>
    <td width="100%">
      <strong><%= toHtml(documentStore.getTitle()) %></strong>
    </td>
    <td align="right" nowrap>
      (<dhv:label name="documents.team.accessLevel">Access level</dhv:label>: <dhv:documentRole id="<%= currentMember.getUserLevel() %>"/>)
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2">
      <%= toHtml(documentStore.getShortDescription()) %>
    </td>
  </tr>
</table>
<div class="tabs" id="toptabs">
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <dhv:documentPermission name="documentcenter-documents-view">
        <zeroio:tabbedMenu text='<%= "Documents" %>' display="Documents" type="project.documents" key="file" value="<%= section %>" url='<%= "DocumentManagement.do?command=DocumentStoreCenter&section=File_Library&documentStoreId=" + documentStore.getId() + "&folderId=-1" %>'/>
    </dhv:documentPermission>
    <dhv:documentPermission name="documentcenter-accounts-view">
        <zeroio:tabbedMenu text='<%= "Accounts" %>' display="Accounts" type="project.accounts" key="accounts" value="<%= section %>" url='<%= "DocumentStoreManagementAccounts.do?command=View&section=Accounts&documentStoreId=" + documentStore.getId() %>'/>
    </dhv:documentPermission>
    <dhv:documentPermission name="documentcenter-team-view">
        <zeroio:tabbedMenu text='<%= "Team" %>' display="Team" type="project.team" key="team,user_membership_modify,group_membership_modify" value="<%= section %>" url='<%= "DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=" + documentStore.getId() %>'/>
    </dhv:documentPermission>
    <dhv:documentPermission name="documentcenter-details-view">
        <zeroio:tabbedMenu text='<%= "Details" %>' display="Details" type="project.details" key="details,modify_document_store" value="<%= section %>" url='<%= "DocumentManagement.do?command=DocumentStoreCenter&section=Details&documentStoreId=" + documentStore.getId() %>'/>
    </dhv:documentPermission>
    <dhv:evaluate if="<%= !documentStore.isTrashed() %>" >
      <dhv:evaluate if="<%= currentMember.getRoleId() <= DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER %>">
        <zeroio:tabbedMenu text="Permissions" display="Permissions" type="project.permissions" key="setup" value="<%= section %>" url='<%= "DocumentManagement.do?command=DocumentStoreCenter&section=Setup&documentStoreId=" + documentStore.getId() %>'/>
      </dhv:evaluate>
    </dhv:evaluate>
    <td width="100%" style="background-image: none; background-color: transparent; border: 0px; border-bottom: 1px solid #666; cursor: default;">&nbsp;</td>
  </tr>
</table>
</div>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
      <jsp:include page="<%= includeSection %>" flush="true"/>
      <br>
    </td>
  </tr>
</table>
<%
  }
%>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
