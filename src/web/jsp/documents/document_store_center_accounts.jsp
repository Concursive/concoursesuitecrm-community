<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id: projects_center_accounts.jsp 12404 2005-08-05 17:37:07 +0000 (Fri, 05 Aug 2005) mrajkowski $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,
                 org.aspcfs.modules.documents.base.AccountDocument" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.web.LookupElement" %>
<%@ page import="org.aspcfs.modules.tasks.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="documentStoreAccountInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="accountDocumentList" class="org.aspcfs.modules.documents.base.AccountDocumentList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="document_store_center_accounts_menu.jsp" %>
<%-- Preload image rollovers --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" type="text/javascript">
  function changeDivContent(divName, divContents) {
    if(document.forms['documentForm'].changeaccount1.value != '-1'){
      window.frames['server_commands'].location.href=
        'DocumentStoreManagementAccounts.do?command=LinkAccount&documentStoreId=<%= documentStore.getId() %>&orgId=' + document.forms['documentForm'].changeaccount1.value;
    }
  }
</script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <form name="documentForm">
    <td>
      <img border="0" src="images/icons/stock_account-16.gif" align="absmiddle">
      <dhv:label name="project.accounts">Accounts</dhv:label>
    </td>
      <input type="hidden" name="changeaccount1" id="changeaccount1" value="-1" />
      <input type="hidden" name="changeaccount2" id="changeaccount2" value="" />
    </form>
  </tr>
</table>
<br>
<dhv:evaluate if="<%= !documentStore.isTrashed() %>" >
  <dhv:documentPermission name="documentcenter-accounts-add">
    <img border="0" src="images/icons/stock_link_account-16.gif" align="absmiddle">
    <a href="javascript:popAccountsListSingle('changeaccount1','changeaccount2', 'showMyCompany=false&filters=all|my');"><dhv:label name="project.linkAnAccount">Link an Account</dhv:label></a><br>
  </dhv:documentPermission>
</dhv:evaluate>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <form name="pagedListView" method="post" action="DocumentStoreManagementAccounts.do?command=View&documentStoreId=<%= documentStore.getId() %>">
    <td align="left">
      &nbsp;
    </td>
    <td>
      <dhv:pagedListStatus label="Accounts" title="<%= showError(request, "actionError") %>" object="documentStoreAccountInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" nowrap>&nbsp;</th>
    <th width="100%" nowrap><strong><dhv:label name="project.account">Account</dhv:label></strong></th>
  </tr>
<%
  if (accountDocumentList.size() == 0) {
%>
  <tr class="row2">
    <td colspan="4"><dhv:label name="project.noAccountsToDisplay">No accounts to display.</dhv:label></td>
  </tr>
<%
  }
  int count = 0;
  int rowid = 0;
  Iterator i = accountDocumentList.iterator();
  while (i.hasNext()) {
    ++count;
    rowid = (rowid != 1?1:2);
    AccountDocument thisAccountDocument = (AccountDocument) i.next();
%>
  <tr class="row<%= rowid %>">
    <td valign="top" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>', 'menuItem', <%= thisAccountDocument.getOrgId() %>,'<%= documentStore.isTrashed() %>');"
         onMouseOver="over(0, <%= count %>)"
         onmouseout="out(0, <%= count %>); hideMenu('menuItem');"><img
         src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%" valign="top">
      <table border="0" cellpadding="0" cellspacing="0" width="100%" class="empty">
        <tr>
          <td valign="top" nowrap>
            <img border="0" src="images/icons/stock_account-16.gif" align="absmiddle">&nbsp;
          </td>
          <td valign="top" width="100%">
            <a href="Accounts.do?command=Details&orgId=<%= thisAccountDocument.getOrgId() %>"><%= toHtml(thisAccountDocument.getAccountName()) %></a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<%
  }
%>
</table>
