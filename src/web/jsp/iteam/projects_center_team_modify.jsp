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
  currentTeam.setSelectStyle("width: 160px");
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
      <a href="ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>">Team</a> >
      Modify
    </td>
  </tr>
</table>
<br>
<form name="projectMemberForm" method="post" action="ProjectManagementTeam.do?command=Update&pid=<%= Project.getId() %>&auto-populate=true" onSubmit="return checkForm(this)">
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th>
        <strong>Modify Team Members</strong>
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
            <td align="center">Add a contact from:</td>
            <td align="center"><span id="select1SpanProject" name="select1SpanProject" style="display:none">Select a project:</span><span id="select1SpanDepartment" name="select1SpanDepartment" style="display:none">Select a department:</span></td>
            <td align="center"><span id="select2Span" name="select2Span" style="display:none">Select a contact:</span></td>
            <td align="center" class="shade2" valign="top">Team Members</td>
          </tr>
          <tr>
            <td align="center" valign="top">
              <select size='10' name='selDirectory' style='width: 160px' onChange="updateCategory();">
                <option value="my|open">Open projects</option>
                <option value="my|closed">Closed projects</option>
                <option value="dept|all">Department list</option>
                <%--<dhv:evaluate if="<%= "true".equals(applicationPrefs.get("INVITE")) || User.getAccessInvite() %>"><option value="email|one">Email address</option></dhv:evaluate> --%>
                <%--<option value="groups|all">My custom groups</option>--%>
              </select>
            </td>
            <td align="center" valign="top">
              <span id="emailSpan" name="emailSpan" style="display:none">
                <%-- Only show if permission to --%>
                <%--<dhv:evaluate if="<%= "true".equals(applicationPrefs.get("INVITE")) || User.getAccessInvite() %>">---%>
                Email Address of contact to add:<br>
                <input type="text" name="email" maxlength="255" value="" style="width: 160px" />
                <input type="button" name="Add >" value ="Add >" onClick="javascript:addEmail(form);" />
                <br>
                &nbsp;<br>
                If the email address is not found in<br>
                the system when the team is updated,<br>
                an additional screen will appear<br>
                with the option to invite the person<br>
                to the project.
                <%--</dhv:evaluate>--%>
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
              (click contact to remove)
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
              <input type="submit" value="Update Team"> &nbsp;
              <%--<input type="button" value="Revert Changes" onClick="resetValues(this.form)"> &nbsp;--%>
              <input type="button" value="Cancel Changes" onClick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=Team&pid=<%= Project.getId() %>'">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</form>
