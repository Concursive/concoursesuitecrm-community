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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
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
<script language="JavaScript" type="text/javascript" src="javascript/projects_center_team_modify.js"></script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_new-bcard-16.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>"><dhv:label name="documents.team.long_html">Team</dhv:label></a> >
      <dhv:label name="global.button.modify">Modify</dhv:label>
    </td>
  </tr>
</table>
<br>
<form name="projectMemberForm" method="post" action="ProjectManagementTeam.do?command=Update&pid=<%= Project.getId() %>&auto-populate=true" onSubmit="return checkForm(this)">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong><dhv:label name="documents.team.modifyTeamMembers">Modify Team Members</dhv:label></strong>
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
            <td align="center"><dhv:label name="documents.team.addContact">Add a user from:</dhv:label></td>
            <td align="center">
              <span id="select1SpanProject" name="select1SpanProject" style="display:none"><dhv:label name="project.selectAProject.colon">Select a project:</dhv:label></span>
              <span id="select1SpanDepartment" name="select1SpanDepartment" style="display:none"><dhv:label name="project.selectADepartment.colon">Select a department:</dhv:label></span>
              <span id="select1SpanAccountType" name="select1SpanAccountType" style="display:none"><dhv:label name="project.searchAnAccountType.colon">Search an Account:</dhv:label></span>
            </td>
            <td align="center"><span id="select2Span" name="select2Span" style="display:none"><dhv:label name="project.selectAContact">Select a contact:</dhv:label></span></td>
            <td align="center" class="shade2" valign="top"><dhv:label name="documents.team.teamMembers">Team Members</dhv:label></td>
          </tr>
          <tr>
            <td align="center" valign="top">
              <select size='10' name='selDirectory' style='width: 160px' onChange="updateCategory();">
                <option value="my|open"><dhv:label name="project.openProjects">Open projects</dhv:label></option>
                <option value="my|closed"><dhv:label name="project.closedProjects">Closed projects</dhv:label></option>
                <option value="dept|all"><dhv:label name="project.departmentList">Department list</dhv:label></option>
                <option value="acct|all">Accounts</option>
                 <%--<dhv:evaluate if="<%= "true".equals(applicationPrefs.get("INVITE")) || User.getAccessInvite() %>"><option value="email|one">Email address</option></dhv:evaluate> --%>
                <%--<option value="groups|all">My custom groups</option>--%>
              </select>
            </td>
            <td align="center" valign="top">
              <span id="emailSpan" name="emailSpan" style="display:none">
                <%-- Only show if permission to --%>
                <dhv:label name="project.emailAdderssOfContactToAdd.colon">Email Address of contact to add:</dhv:label><br>
                <input type="text" name="email" maxlength="255" value="" style="width: 160px" />
                <input type="button" name="Add >" value ="<dhv:label name="accounts.accounts_reports_generate.AddR">Add ></dhv:label>" onClick="javascript:addEmail(form);" />
                <br />
                <br />
                <dhv:label name="project.emailAddressNotFound.text" param="break=<br />">If the email address is not found in<br />the system when the team is updated,<br />an additional screen will appear<br />with the option to invite the person<br />to the project.</dhv:label>
              </span>
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
              <span id="emailSpan2" name="emailSpan2" style="display:none">
                &nbsp;
              </span>
              <span id="listSpan2" name="listSpan2">
                <select size='10' name='selTotalList' style='width: 160px' onClick="addList(this.form)">
                </select>
              </span>
            </td>
            <td align="center" class="shade2" valign="top"><font size="2"><%= currentTeam.getHtml("selProjectList", 0) %></font></td>
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
              <dhv:label name="project.clickContactToRemove">(click contact to remove)</dhv:label>
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
              <input type="hidden" name="projId" value="<%= Project.getId() %>">
              <input type="submit" value="<dhv:label name="documents.team.user.updateTeam">Update Team</dhv:label>"> &nbsp;
              <input type="button" value="<dhv:label name="documents.team.user.cancelChanges">Cancel Changes</dhv:label>" onClick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>'">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
