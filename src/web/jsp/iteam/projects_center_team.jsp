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
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.admin.base.User" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="currentMember" class="com.zeroio.iteam.base.TeamMember" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_new-bcard-16.gif" border="0" align="absmiddle">
      Team
    </td>
  </tr>
</table>
<br>
<zeroio:permission name="project-team-edit">
<a href="ProjectManagementTeam.do?command=Modify&pid=<%= Project.getId() %>">Modify Team</a><br>
</zeroio:permission>
<zeroio:permission name="project-team-edit-role">
<script language="javascript" type="text/javascript">
  function updateRole(pid, id, rid) {
    window.frames['server_commands'].location.href='ProjectManagementTeam.do?command=ChangeRole&pid=' + pid + '&id=' + id + '&role=' + rid;
  }
</script>
<font color="blue">* Immediately change the team member's role by making changes below</font>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0" width="0" border="0" frameborder="0"></iframe>
</zeroio:permission>
<dhv:pagedListStatus label="Members" title="<%= showError(request, "actionError") %>" object="projectTeamInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th><strong>Role</strong></th>
    <th width="50%"><strong>Name</strong></th>
    <th width="50%"><strong>Department</strong></th>
    <zeroio:permission name="project-team-view-email">
      <th nowrap><strong>Email Address</strong></th>
    </zeroio:permission>
    <th nowrap><strong>Status</strong></th>
    <th nowrap><strong>Last Accessed</strong></th>
  </tr>
<%
  TeamMemberList team = Project.getTeam();
  if (team.size() == 0) {
%>
  <tr class="row2">
    <td colspan="6">Team has not been defined</td>
  </tr>
<%
  }
  int rowId = 0;
  Iterator i = team.iterator();
  while (i.hasNext()) {
    rowId = (rowId != 1?1:2);
    TeamMember thisMember = (TeamMember) i.next();
    User thisContact = (User) thisMember.getUser();
    if (thisContact == null) thisContact = new User();
%>    
  <tr class="row<%= rowId %>">
    <td valign="top" nowrap>
      <zeroio:permission name="project-team-edit-role" if="none">
        <zeroio:role id="<%= thisMember.getUserLevel() %>"/>
      </zeroio:permission>
      <zeroio:permission name="project-team-edit-role">
        <zeroio:roleSelect
          name="<%= "role" + thisMember.getUserId() %>"
          value="<%= thisMember.getUserLevel() %>"
          onChange="<%= "javascript:updateRole(" + thisMember.getProjectId() + ", " + thisMember.getUserId() + ", this.options[this.selectedIndex].value);" %>"/>
      </zeroio:permission>
    </td>
    <td valign="top"><%= toHtml(thisContact.getContact().getNameFirstLast()) %></td>
    <td valign="top"><%= toHtml(thisContact.getContact().getDepartmentName()) %></td>
    <zeroio:permission name="project-team-view-email"> 
       <td valign="top" nowrap>
        <dhv:evaluate if="<%= thisContact.getContact().getEmailAddress(1) != null %>"><a href="mailto:<%= thisContact.getContact().getEmailAddress(1) %>"><%= thisContact.getContact().getEmailAddress(1) %></a></dhv:evaluate>&nbsp;
      </td>
    </zeroio:permission>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_ADDED %>">
      <td valign="top" nowrap>Added <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_PENDING %>">
      <td valign="top" nowrap><font color="green">Invited <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/></font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_INVITING %>">
      <td valign="top" nowrap><font color="green">Invited <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/></font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_MAILERROR %>">
      <td valign="top" nowrap><font color="red">Invitation could not be sent to email address</font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_REFUSED %>">
      <td valign="top" nowrap><font color="red">Invitation refused <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/></font></td>
    </dhv:evaluate>
    <td nowrap valign="top">
      <zeroio:tz timestamp="<%= thisMember.getLastAccessed() %>" dateOnly="true" default="--" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes"/>
    </td>
  </tr>
<%    
  }
%>
</table>
