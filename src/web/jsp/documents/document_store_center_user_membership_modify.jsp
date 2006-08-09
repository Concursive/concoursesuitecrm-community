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
  - Author(s): 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*, org.aspcfs.modules.documents.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="currentTeam" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="vectorUserId" class="java.lang.String" scope="request"/>
<jsp:useBean id="vectorState" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/checkEmail.js"></script>
<%
  currentTeam.setSelectSize(10);
  currentTeam.setSelectStyle("width: 200px");
  currentTeam.setJsEvent("onClick=\"removeList(this.form)\"");
%>
<script language="JavaScript">
  var items = "";<%-- Maintains users in the selected category --%>
  var vectorUserId = "<%= vectorUserId %>".split("|");<%-- User ID --%>
  var vectorState = "<%= vectorState %>".split("|");<%-- State --%>
</script>
<script language="JavaScript" type="text/javascript" src="javascript/document_store_center_user_membership_modify.js"></script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_new-bcard-16.gif" border="0" align="absmiddle">
      <a href="DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=<%= documentStore.getId() %>"><dhv:label name="documents.team.team">Team</dhv:label></a> >
      <dhv:label name="documents.team.modify">Modify</dhv:label>
    </td>
  </tr>
</table>
<br>
<form name="documentStoreMemberForm" method="post" action="DocumentStoreManagementTeam.do?command=Update&documentStoreId=<%= documentStore.getId() %>&auto-populate=true" onSubmit="return checkForm(this)">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong>
          <dhv:label name="documents.team.modifyUserMembership">Modify User Membership</dhv:label>
         </strong>
      </th>
    </tr>
    <tr bgColor="#EDEDED">
      <td>
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="empty">
          <tr>
            <td colspan="3">
              &nbsp;
            </td>
            <td class="shade2">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="center">
              <dhv:label name="documents.team.addContact">Add a user from:</dhv:label>
            </td>
            <td align="center">
              <span id="select1SpanDocumentStore" name="select1SpanDocumentStore" style="display:none">
              <dhv:label name="documents.team.selectDocumentStore">Select a document store:</dhv:label>
              </span>
              <span id="select1SpanDepartment" name="select1SpanDepartment" style="display:none">
              <dhv:label name="documents.team.selectDepartment">Select a department:</dhv:label>
              </span>
              <span id="select1SpanAccountType" name="select1SpanAccountType" style="display:none">
              <dhv:label name="documents.team.searchAccountType">Search an Account:</dhv:label>
              </span>
            </td>
            <td align="center">
              <span id="select2Span" name="select2Span" style="display:none">
                <dhv:label name="documents.team.selectContact">Select a contact:</dhv:label>
              </span>
            </td>
            <td align="center" class="shade2" valign="top">
              <dhv:label name="documents.team.teamMembers">Team Members:</dhv:label>
            </td>
          </tr>
          <tr>
            <td align="center" valign="top">
              <select size='10' name='selDirectory' style='width: 160px' onChange="updateCategory();">
                <option value="my|open"><dhv:label name="documents.team.documentStores">Document stores</dhv:label></option>
                <option value="dept|all"><dhv:label name="documents.team.employees">Employees</dhv:label></option>
                <option value="acct|all"><dhv:label name="documents.team.accounts">Accounts</dhv:label></option>
              </select>
            </td>
            <td align="center" valign="top">
              <span id="searchSpan" name="searchSpan" style="display:none">
                <input type="text" name="accountSearch" maxlength="255" value="" style="width: 160px" />
                <br/><input type="button" name="search" value="<dhv:label name="global.button.search">Search</dhv:label>" onclick="searchAccounts(this.form);">
                <br/>
                <select size='7' name='selAccountList' style='width: 160px' onChange="updateContactList();">
                </select>
              </span>
              <span id="listSpan" name="listSpan">
                <select size='10' name='selDepartment' style='width: 160px' onChange="updateItemList();">
                </select>
              </span>
            </td>
            <td align="center" valign="top">
              <span id="listSpan2" name="listSpan2">
                <select size='10' name='selTotalList' style='width: 200px' onClick="addList(this.form)">
                </select>
              </span>
            </td>
            <td align="center" class="shade2" valign="top"><font size="2"><%= currentTeam.getHtml("selDocumentStoreList", 0) %></font></td>
          </tr>
          <tr>
            <td align="center">
              &nbsp;<%--(click to view categories)--%>
            </td>
            <td align="center">
              &nbsp;<%--(click to view contacts)--%>
            </td>
            <td align="center">
              <%--(click contact to add)--%>
            </td>
            <td align="center" class="shade2">
              <dhv:label name="documents.team.clickUserToRemove">(click user to remove)</dhv:label>
            </td>
          </tr>
          <tr>
            <td colspan="3">&nbsp;</td>
            <td class="shade2">&nbsp;</td>
            <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0" width="0" border="0" frameborder="0"></iframe>
          </tr>
          <tr>
            <td colspan="4" align="center" height='30'>
              <input type="hidden" name="insertMembers">
              <input type="hidden" name="deleteMembers">
              <input type="hidden" name="documentStoreId" id="documentStoreId" value="<%= documentStore.getId() %>">
              <input type="submit" value="<dhv:label name="documents.team.user.updateTeam">Update Team</dhv:label>" /> &nbsp;
              <input type="button" value="<dhv:label name="documents.team.user.cancelTeam">Cancel Changes</dhv:label>" onClick="javascript:window.location.href='DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=<%= documentStore.getId() %>'" />
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <input type="hidden" name="memberType" value="<%=DocumentStoreTeamMemberList.USER%>" />
</form>
