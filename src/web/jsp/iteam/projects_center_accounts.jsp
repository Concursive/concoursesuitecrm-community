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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,
                 org.aspcfs.modules.accounts.base.Organization" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.web.LookupElement" %>
<%@ page import="org.aspcfs.modules.tasks.base.*" %>
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="projectAccountsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="organizationList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_center_accounts_menu.jsp" %>
<%-- Preload image rollovers --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" type="text/javascript">
  function changeDivContent(divName, divContents) {
    if(document.forms['projectForm'].changeaccount1.value != '-1'){
      window.frames['server_commands'].location.href=
        'ProjectManagementAccounts.do?command=Add&pid=<%= Project.getId() %>&orgId=' + document.forms['projectForm'].changeaccount1.value;
    }
  }
</script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <form name="projectForm">
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
<dhv:evaluate if="<%= !Project.isTrashed() %>" >
  <zeroio:permission name="project-accounts-manage">
    <img border="0" src="images/icons/stock_link_account-16.gif" align="absmiddle">
    <a href="javascript:popAccountsListSingle('changeaccount1','changeaccount2', 'showMyCompany=false&filters=all|my');"><dhv:label name="project.linkAnAccount">Link an Account</dhv:label></a><br>
  </zeroio:permission>
</dhv:evaluate>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <form name="pagedListView" method="post" action="ProjectManagementAccounts.do?command=List&pid=<%= Project.getId() %>">
    <td align="left">
      &nbsp;
    </td>
    <td>
      <dhv:pagedListStatus label="Accounts" title="<%= showError(request, "actionError") %>" object="projectAccountsInfo"/>
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
  if (organizationList.size() == 0) {
%>
  <tr class="row2">
    <td colspan="4"><dhv:label name="project.noAccountsToDisplay">No accounts to display.</dhv:label></td>
  </tr>
<%
  }
  int count = 0;
  int rowid = 0;
  Iterator i = organizationList.iterator();
  while (i.hasNext()) {
    ++count;
    rowid = (rowid != 1?1:2);
    Organization thisOrganization = (Organization) i.next();
%>
  <tr class="row<%= rowid %>">
    <td valign="top" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>', 'menuItem', <%= thisOrganization.getId() %>,'<%= Project.isTrashed() %>');"
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
            <a href="Accounts.do?command=Details&orgId=<%= thisOrganization.getId() %>"><%= toHtml(thisOrganization.getName()) %></a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<%
  }
%>
</table>
