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
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.documents.base.* " %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="currentTeam" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="vectorUserId" class="java.lang.String" scope="request"/>
<jsp:useBean id="vectorState" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/checkEmail.js"></script>
<%
  currentTeam.setSelectSize(10);
  currentTeam.setSelectStyle("width: 230px");
  currentTeam.setJsEvent("onClick=\"removeList(this.form)\"");
%>
<script language="JavaScript">
  var items = "";<%-- Maintains groups in the selected category --%>
  var vectorUserId = "<%= vectorUserId %>".split("|");<%-- User ID --%>
  var vectorState = "<%= vectorState %>".split("|");<%-- State --%>
  var groupType= ""; <%--Maintains whether group is a department or a role --%>
</script>
<script language="JavaScript" type="text/javascript" src="javascript/document_store_center_group_membership_modify.js"></script>
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
          <dhv:label name="documents.team.modifyGroupMembership">Modify Group Membership</dhv:label>
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
              <dhv:label name="documents.team.addGroup">Add a group from:</dhv:label>
            </td>
            <td align="center">
              <span id="select1SpanRole" name="select1SpanRole" style="display:none">
                <dhv:label name="documents.team.selectRole">Select a role:</dhv:label>
              </span>
              <span id="select1SpanDepartment" name="select1SpanDepartment" style="display:none">
                <dhv:label name="documents.team.selectDepartment">Select a department:</dhv:label>
              </span></td>
            <td align="center" class="shade2" valign="top">
              <dhv:label name="documents.team.teamMembers">Team Members:</dhv:label>
            </td>
          </tr>
          <tr>
            <td align="center" valign="top">
              <select size='10' name='selDirectory' style='width: 160px' onChange="updateCategory();">
            <%  if(User.getUserRecord().getSiteId() == -1) { 
                  Iterator iter = (Iterator) SiteIdList.iterator();
                  while (iter.hasNext()) {
                    LookupElement element = (LookupElement) iter.next();
            %>
               <option value="role|all|<%= element.getCode() %>"><dhv:label name="documents.team.roleList">Role list</dhv:label><%= element.getCode() != -1?" ("+element.getDescription()+")":"" %></option>
               <option value="dept|all|<%= element.getCode() %>"><dhv:label name="documents.team.departmentList">Department list</dhv:label><%= element.getCode() != -1?" ("+element.getDescription()+")":"" %></option>
            <%  } } else { %>
               <option value="role|all|<%= User.getUserRecord().getSiteId() %>"><dhv:label name="documents.team.roleList">Role list</dhv:label><%= User.getUserRecord().getSiteId() != -1?" (" + SiteIdList.getSelectedValue(User.getUserRecord().getSiteId()) + ")":"" %></option>
               <option value="dept|all|<%= User.getUserRecord().getSiteId() %>"><dhv:label name="documents.team.departmentList">Department list</dhv:label><%= User.getUserRecord().getSiteId() != -1?" (" + SiteIdList.getSelectedValue(User.getUserRecord().getSiteId()) + ")":"" %></option>
            <%  } %>
              </select>
            </td>
            <td align="center" valign="top">
              <span id="listSpan" name="listSpan">
                <select size='10' name='selDepartment' style='width: 160px' onClick="addList(this.form)">
                </select>
              </span>
            </td>
            <td align="center" class="shade2" valign="top"><font size="2"><%= currentTeam.getHtml("selRoleList", 0) %></font></td>
          </tr>
          <tr>
            <td align="center">
              &nbsp;<%--(click to view categories)--%>
            </td>
            <td align="center">
              &nbsp;<%--(click to view contacts)--%>
            </td>
            <td align="center" class="shade2">
              <dhv:label name="documents.team.clickToRemove">(click to remove)</dhv:label>
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
              <input type="submit" value="<dhv:label name="documents.team.group.updateTeam">Update Team</dhv:label>"/> &nbsp;
              <input type="button" value="<dhv:label name="documents.team.group.cancelTeam">Cancel Changes</dhv:label>" onClick="javascript:window.location.href='DocumentManagement.do?command=DocumentStoreCenter&section=Team&documentStoreId=<%= documentStore.getId() %>'" />
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <input type="hidden" name="memberType" value="<%=DocumentStoreTeamMemberList.GROUP%>" />
</form>
