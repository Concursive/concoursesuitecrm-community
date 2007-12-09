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
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat"%>
<jsp:useBean id="userGroup" class="org.aspcfs.modules.admin.base.UserGroup" scope="request"/>
<jsp:useBean id="currentTeam" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/> 
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="vectorUserId" class="java.lang.String" scope="request"/>
<jsp:useBean id="vectorState" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" src="javascript/popContacts.js?v=20070827"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<script language="JavaScript" type="text/javascript" src="javascript/admin_group_add.js"></script>
<%
  currentTeam.setSelectSize(10);
  currentTeam.setSelectStyle("width: 200px");
  currentTeam.setJsEvent("onClick=\"removeList(this.form)\"");
%>
<script type="text/javascript">
var items = "";<%-- Maintains users in the selected category --%>
var vectorUserId = "<%= vectorUserId %>".split("|");<%-- User ID --%>
var vectorState = "<%= vectorState %>".split("|");<%-- State --%>

function checkForm(form) {
    formTest = true;
    formTest = checkFormValidity(form);
    message = "";
    if (checkNullString(form.name.value)) {
      message += label("check.name","- Please specify the  name\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    } else {
      return true;
    }
  }
  
  function checkFormValidity(form) {
    form.insertMembers.value = "";
    form.deleteMembers.value = "";
    //add only if not on list already
    for (i = 0; i < form.selectedUserList.options.length; i++) {
      var found = false;
      for (j = 0; j < vectorUserId.length; j++) {
        if (form.selectedUserList.options[i].value == vectorUserId[j]) {
          found = true;
        }
      }
      if (!found) {
        if (form.insertMembers.value.length > 0) {
          form.insertMembers.value += "|";
        }
        if (form.selectedUserList.options[i].value == -1) {
          form.insertMembers.value += form.selectedUserList.options[i].text;
        } else {
          form.insertMembers.value += form.selectedUserList.options[i].value;
        }
      }
    }
    //check deletes
    for (j = 0; j < vectorUserId.length; j++) {
      if (vectorState[j] == "0") {
        if (form.deleteMembers.value.length > 0) {
          form.deleteMembers.value += "|";
        }
        form.deleteMembers.value += vectorUserId[j];
      }
    }
    return true;
  }
</script>
<form name="addUserGroup" action="UserGroups.do?command=Save&auto-populate=true" onSubmit="return checkForm(this);" method="post">
<input type="hidden" name="id" value="<%= userGroup.getId() %>"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
  <a href="UserGroups.do?command=List"><dhv:label name="usergroups.manageGroups">Manage Groups</dhv:label></a> >
<dhv:evaluate if="<%= userGroup.getId() > -1 %>">
  <a href="UserGroups.do?command=Details&groupId=<%= userGroup.getId() %>"><dhv:label name="campaign.groupDetails">Group Details</dhv:label></a> >
  <dhv:label name="documents.team.modifyGroup">Modify Group</dhv:label>
</dhv:evaluate>
<dhv:evaluate if="<%= userGroup.getId() == -1 %>">
  <dhv:label name="usergroups.addGroup">Add Group</dhv:label>
</dhv:evaluate>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= userGroup.getId() > -1 %>">
  <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>"/>
</dhv:evaluate>
<dhv:evaluate if="<%= userGroup.getId() == -1 %>">
  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
</dhv:evaluate>
 <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='UserGroups.do?command=List';"/>
<br /><br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<dhv:formMessage showSpace="false" />
  <tr>
    <th colspan="2">
      <strong>
      <dhv:evaluate if="<%= userGroup.getId() == -1 %>">
        <dhv:label name="usergroups.addGroup">Add Group</dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if="<%= userGroup.getId() > -1 %>">
        <dhv:label name="documents.team.modifyGroupMembership">Modify Group</dhv:label>
      </dhv:evaluate>
      </strong>
    </th>
  </tr>
	<tr>
    <td class="formLabel">
      <dhv:label name="admin.groupName">Group Name</dhv:label>
    </td>
    <td>
      <input type="text" name="name" value="<%= toHtmlValue(userGroup.getName()) %>" size="58" maxlength="255"><font color=red>*</font>
      <%= showAttribute(request, "nameError") %>
    </td>
  </tr>
	<tr>
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <textarea name="description"><%= toString(userGroup.getDescription()) %></textarea>
    </td>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="admin.user.site">Site</dhv:label>
    </td>
    <td>
      <dhv:evaluate if="<%= (userGroup.getId() == -1 && User.getUserRecord().getSiteId() == -1) %>">
        <% SiteIdList.setJsEvent("onChange=\"javascript:resetGroupUsers(document.forms['addUserGroup']);\""); %>
        <%= SiteIdList.getHtmlSelect("siteId", userGroup.getSiteId()) %>
        <%= showAttribute(request, "siteIdError") %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= userGroup.getId() > -1 || User.getUserRecord().getSiteId() > -1 %>">
        <%= toHtml(SiteIdList.getSelectedValue(User.getUserRecord().getSiteId() > -1?User.getUserRecord().getSiteId():userGroup.getSiteId())) %>
        <input type="hidden" id="siteId" name="siteId" value="<%= User.getUserRecord().getSiteId() > -1?User.getUserRecord().getSiteId():userGroup.getSiteId() %>"/>
      </dhv:evaluate>
    </td>
  </tr>
	<tr>
    <td class="formLabel">
      <dhv:label name="product.enabled">Enabled</dhv:label>
    </td>
    <td>
     <input type="checkbox" name="enabled" value="true" <%= userGroup.getEnabled()?"checked":"" %> />
    </td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2"><dhv:label name="usergroups.groupUsers">Group Users</dhv:label></th>
  </tr>
  <tr>
    <td colspan="2" class="formLabel">
      <table class="empty">
        <tr>
          <td align="center">
            <dhv:label name="documents.team.addContact">Add a user from:</dhv:label>
          </td>
          <td align="center">
            <span id="select1SpanDepartment" name="select1SpanDepartment" style="display:none">
            <dhv:label name="documents.team.selectDepartment">Select a department:</dhv:label>
            </span>
            <span id="select1SpanAccountType" name="select1SpanAccountType" style="display:none">
            <dhv:label name="documents.team.selectAccountType">Select an account type:</dhv:label>
            </span>
          </td>
          <td align="center">
            <span id="select2Span" name="select2Span" style="display:none">
              <dhv:label name="documents.team.selectContact">Select a contact:</dhv:label>
            </span>
          </td>
          <td align="center" class="shade2" valign="top">
            <dhv:label name="usergroups.groupUsers.colon">Group Users:</dhv:label>
          </td>
        </tr>
        <tr>
          <td align="center" valign="top">
            <select size='10' name='selDirectory' style='width: 160px' onChange="updateCategory();">
              <option value="dept|all"><dhv:label name="documents.team.employees">Employees</dhv:label></option>
              <option value="acct|all"><dhv:label name="documents.team.accounts">Accounts</dhv:label></option>
            </select>
          </td>
          <td align="center" valign="top">
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
          <td align="center" class="shade2" valign="top"><font size="2"><%= currentTeam.getHtml("selectedUserList", 0) %></font></td>
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
      </table>
    </td>
  </tr>
</table>
<br />
<dhv:evaluate if="<%= userGroup.getId() > -1 %>">
  <input type="submit" value="<dhv:label name="button.update">Update</dhv:label>"/>
</dhv:evaluate>
<dhv:evaluate if="<%= userGroup.getId() == -1 %>">
  <input type="submit" value="<dhv:label name="button.save">Save</dhv:label>"/>
</dhv:evaluate>
<input type="hidden" name="insertMembers" />
<input type="hidden" name="deleteMembers" />
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='UserGroups.do?command=List';"/>
</form>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0" width="0" border="0" frameborder="0"></iframe>

