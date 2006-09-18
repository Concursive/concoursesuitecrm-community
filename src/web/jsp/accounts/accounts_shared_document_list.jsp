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
  - Version: $Id: accounts_projects_list.jsp 15115 2006-05-31 16:47:51 +0000 (Wed, 31 May 2006) matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.documents.base.AccountDocument,com.zeroio.iteam.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="accountsSharedDocumentsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="orgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="accountDocumentList" class="org.aspcfs.modules.documents.base.AccountDocumentList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_shared_document_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select_<%= SKIN %>');
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="Accounts.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Accounts.do?command=Details&orgId=<%=orgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<dhv:label name="dependency.sharedDocument">Shared Documents</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="accounts" selected="documentStore" object="orgDetails" param="<%= "orgId=" + orgDetails.getOrgId() %>" appendToUrl="<%= addLinkParams(request, "popup|popupType|actionId") %>">
  <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="accountsSharedDocumentsInfo"/>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th width="8">&nbsp;</th>
      <th nowrap><a href="<%= accountsSharedDocumentsInfo.getLink() %>&column=ds.entered<%= addLinkParams(request, "popup|popupType") %>"><dhv:label name="document.startDate">Start Date</dhv:label></a><%= accountsSharedDocumentsInfo.getSortIcon("ds.entered") %></th>
      <th width="100%" nowrap><a href="<%= accountsSharedDocumentsInfo.getLink() %>&column=store_name<%= addLinkParams(request, "popup|popupType") %>"><dhv:label name="document.documentStoreName">Document Store Name</dhv:label></a><%= accountsSharedDocumentsInfo.getSortIcon("store_name") %></th>
      <%--
      <th width="118">Category</th>
      --%>
    </tr>
  <%
    if (accountDocumentList.size() == 0) {
  %>
    <tr class="row2">
      <td colspan="4"><dhv:label name="document.noDocumentsToDisplay">No documents to display.</dhv:label></td>
    </tr>
  <%
    }
    int rowid = 0;
    int count = 0;
    Iterator i = accountDocumentList.iterator();
    while (i.hasNext()) {
      rowid = (rowid != 1?1:2);
      ++count;
      AccountDocument thisAccountDocument = (AccountDocument) i.next();

      boolean hasAccess = false;
  %>
  <dhv:permission name="documents-view">

      <% hasAccess = true; %>

  </dhv:permission>
    <tr class="row<%= rowid %>">
      <td valign="top" align="center" nowrap>
       <dhv:evaluate if="<%= !isPopup(request) %>">
        <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>','menuItem','<%= thisAccountDocument.getDocumentStoreId() %>','<%= hasAccess %>');"
           onMouseOver="over(0, <%= count %>)"
           onmouseout="out(0, <%= count %>); hideMenu('menuItem');"><img
           src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
       </dhv:evaluate>
       <dhv:evaluate if="<%= isPopup(request) %>">&nbsp;</dhv:evaluate>
      </td>
      <td valign="top" align="center" nowrap>
        <zeroio:tz timestamp="<%= thisAccountDocument.getEntered() %>" dateOnly="true" default="&nbsp;" />
      </td>
      <td valign="top">
       <dhv:evaluate if="<%= !isPopup(request) %>">
          <a href="DocumentManagement.do?command=DocumentStoreCenter&documentStoreId=<%= thisAccountDocument.getDocumentStoreId() %>"><%= toHtml(thisAccountDocument.getDocumentStoreName()) %></a>
       </dhv:evaluate>
       <dhv:evaluate if="<%= isPopup(request) %>"><%= toHtml(thisAccountDocument.getDocumentStoreName()) %></dhv:evaluate>
      </td>
    </tr>
  <%
    }
  %>
  </table>
</dhv:container>
