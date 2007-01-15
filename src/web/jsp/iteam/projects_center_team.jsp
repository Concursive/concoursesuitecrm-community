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
      <dhv:label name="project.team">Team</dhv:label>
    </td>
  </tr>
</table>
<br />
<dhv:evaluate if="<%= !Project.isTrashed() %>" >
  <zeroio:permission name="project-team-edit">
    <a href="ProjectManagementTeam.do?command=Modify&pid=<%= Project.getId() %>"><dhv:label name="project.modifyTeam">Modify Team</dhv:label></a><br>
  </zeroio:permission>
  <zeroio:permission name="project-team-edit-role">
    <script language="javascript" type="text/javascript">
      function updateRole(pid, id, rid) {
        window.frames['server_commands'].location.href='ProjectManagementTeam.do?command=ChangeRole&pid=' + pid + '&id=' + id + '&role=' + rid;
      }
    </script>
    <font color="blue"><dhv:label name="project.changeTeamMemberRole.text">* Immediately change the team member's role by making changes below</dhv:label></font>
    <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0" width="0" border="0" frameborder="0"></iframe>
  </zeroio:permission>
</dhv:evaluate>
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="false" title="Employees" object="projectEmployeeTeamInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap><dhv:label name="project.role">Role</dhv:label></th>
    <th nowrap><dhv:label name="project.id">Id</dhv:label></th>
    <th width="80%" nowrap><dhv:label name="contacts.name">Name / Department</dhv:label></th>
    <th nowrap><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></th>
    <th nowrap><dhv:label name="documents.team.lastAccessed">Last Accessed</dhv:label></th>
  </tr>
<%
  TeamMemberList team = Project.getEmployeeTeam();
  if (team.size() == 0) {
%>
  <tr class="row2">
    <td colspan="5"><dhv:label name="project.employeeTeam.text">Employee team has not been defined</dhv:label></td>
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
    // Temp. fix for Weblogic
    String roleName = "role" + thisMember.getUserId();
    String roleValue = String.valueOf(thisMember.getUserLevel());
    String roleOnChange = "javascript:updateRole(" + thisMember.getProjectId() + ", " + thisMember.getUserId() + ", this.options[this.selectedIndex].value);";
%>    
  <tr class="row<%= rowId %>">
    <td valign="top" nowrap>
      <zeroio:permission name="project-team-edit-role" if="none">
        <zeroio:role id="<%= thisMember.getUserLevel() %>"/>
      </zeroio:permission>
      <zeroio:permission name="project-team-edit-role">
        <dhv:evaluate if="<%= !Project.isTrashed() %>" >
          <zeroio:roleSelect
            name="<%= roleName %>"
            value="<%= roleValue %>"
            onChange="<%= roleOnChange %>"/>
        </dhv:evaluate>
      </zeroio:permission>
    </td>
    <td valign="top"><%= thisMember.getUserId() %></td>
    <td valign="top">
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"><font color="red"></dhv:evaluate>
      <%= toHtml(thisContact.getContact().getNameFirstLast()) %>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"> (X)</dhv:evaluate>
      <dhv:evaluate if="<%= hasText(thisContact.getContact().getDepartmentName()) %>">
        /
        <%= toHtml(thisContact.getContact().getDepartmentName()) %>
      </dhv:evaluate>
      <zeroio:permission name="project-team-view-email">
        <dhv:evaluate if="<%= thisContact.getContact().getEmailAddress(1) != null %>"><br /><a href="mailto:<%= thisContact.getContact().getEmailAddress(1) %>"><%= thisContact.getContact().getEmailAddress(1) %></a></dhv:evaluate>&nbsp;
      </zeroio:permission>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"></font></dhv:evaluate>
    </td>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_ADDED %>">
      <td valign="top" nowrap>
        <dhv:label name="project.added" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>'>Added <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label>
      </td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_PENDING %>">
      <td valign="top" nowrap><font color="green"><dhv:label name="project.invited" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>'>Invited <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_INVITING %>">
      <td valign="top" nowrap><font color="green"><dhv:label name="project.invited" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>'>Invited <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_MAILERROR %>">
      <td valign="top" nowrap><font color="red"><dhv:label name="project.invitationCouldNotBeSent">Invitation could not be sent to email address</dhv:label></font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_REFUSED %>">
      <td valign="top" nowrap><font color="red"><dhv:label name="project.invitationRefused" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>' >Invitation refused <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font></td>
    </dhv:evaluate>
    <td nowrap valign="top">
      <zeroio:tz timestamp="<%= thisMember.getLastAccessed() %>" dateOnly="true" default="--" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
<%    
  }
%>
</table>
<br />
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="false" title="AccountContacts" object="projectAccountContactTeamInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap><dhv:label name="project.role">Role</dhv:label></th>
    <th nowrap><dhv:label name="project.id">Id</dhv:label></th>
    <th width="80%"><dhv:label name="contacts.name">Name / Organization</dhv:label></th>
    <th nowrap><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></th>
    <th nowrap><dhv:label name="documents.team.lastAccessed">Last Accessed</dhv:label></th>
  </tr>
<%
  team = Project.getAccountContactTeam();
  if (team.size() == 0) {
%>
  <tr class="row2">
    <td colspan="5"><dhv:label name="project.acctContactTeamNotDefined">Account contact team has not been defined</dhv:label></td>
  </tr>
<%
  }
  rowId = 0;
  i = team.iterator();
  while (i.hasNext()) {
    rowId = (rowId != 1?1:2);
    TeamMember thisMember = (TeamMember) i.next();
    User thisContact = (User) thisMember.getUser();
    if (thisContact == null) thisContact = new User();
    // Temp. fix for Weblogic
    String roleName = "role" + thisMember.getUserId();
    String roleValue = String.valueOf(thisMember.getUserLevel());
    String roleOnChange = "javascript:updateRole(" + thisMember.getProjectId() + ", " + thisMember.getUserId() + ", this.options[this.selectedIndex].value);";
%>    
  <tr class="row<%= rowId %>">
    <td valign="top" nowrap>
      <zeroio:permission name="project-team-edit-role" if="none">
        <zeroio:role id="<%= thisMember.getUserLevel() %>"/>
      </zeroio:permission>
      <zeroio:permission name="project-team-edit-role">
        <dhv:evaluate if="<%= !Project.isTrashed() %>" >
          <zeroio:roleSelect
            name="<%= roleName %>"
            value="<%= roleValue %>"
            onChange="<%= roleOnChange %>"/>
        </dhv:evaluate>
        <dhv:evaluate if="<%= Project.isTrashed() %>" >
          <zeroio:role id="<%= thisMember.getUserLevel() %>"/>
        </dhv:evaluate>
      </zeroio:permission>
    </td>
    <td valign="top"><%= thisMember.getUserId() %></td>
    <td valign="top">
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"><font color="red"></dhv:evaluate>
      <%= toHtml(thisContact.getContact().getNameFirstLast()) %>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"> (X)</dhv:evaluate>
    <dhv:evaluate if="<%= hasText(thisContact.getContact().getCompany()) %>">
        /
        <%= toHtml(thisContact.getContact().getCompany()) %>
      </dhv:evaluate>
      <zeroio:permission name="project-team-view-email">
        <dhv:evaluate if="<%= thisContact.getContact().getEmailAddress(1) != null %>"><br /><a href="mailto:<%= thisContact.getContact().getEmailAddress(1) %>"><%= thisContact.getContact().getEmailAddress(1) %></a></dhv:evaluate>&nbsp;
      </zeroio:permission>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"></font></dhv:evaluate>
    </td>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_ADDED %>">
      <td valign="top" nowrap>
        <dhv:label name="project.added" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>'>Added <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label>
      </td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_PENDING %>">
      <td valign="top" nowrap><font color="green"><dhv:label name="project.invited" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>'>Invited <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_INVITING %>">
      <td valign="top" nowrap><font color="green"><dhv:label name="project.invited" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>'>Invited <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_MAILERROR %>">
      <td valign="top" nowrap><font color="red"><dhv:label name="project.invitationCouldNotBeSent">Invitation could not be sent to email address</dhv:label></font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_REFUSED %>">
      <td valign="top" nowrap><font color="red"><dhv:label name="project.invitationRefused" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>' >Invitation refused <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font></td>
    </dhv:evaluate>
    <td nowrap valign="top">
      <zeroio:tz timestamp="<%= thisMember.getLastAccessed() %>" dateOnly="true" default="--" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
<%    
  }
%>
</table>
<br />
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="false" title="PortalUsers" object="projectPortalUserTeamInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap><dhv:label name="project.role">Role</dhv:label></th>
    <th nowrap><dhv:label name="project.id">Id</dhv:label></th>
    <th width="80%"><dhv:label name="contacts.name">Name / Organization</dhv:label></th>
    <th nowrap><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></th>
    <th nowrap><dhv:label name="documents.team.lastAccessed">Last Accessed</dhv:label></th>
  </tr>
<%
  team = Project.getPortalUserTeam();
  if (team.size() == 0) {
%>
  <tr class="row2">
    <td colspan="5"><dhv:label name="project.portalUserTeamNotDefined">Portal Users team has not been defined</dhv:label></td>
  </tr>
<%
  }
  rowId = 0;
  i = team.iterator();
  while (i.hasNext()) {
    rowId = (rowId != 1?1:2);
    TeamMember thisMember = (TeamMember) i.next();
    User thisContact = (User) thisMember.getUser();
    if (thisContact == null) thisContact = new User();
    // Temp. fix for Weblogic
    String roleName = "role" + thisMember.getUserId();
    String roleValue = String.valueOf(thisMember.getUserLevel());
    String roleOnChange = "javascript:updateRole(" + thisMember.getProjectId() + ", " + thisMember.getUserId() + ", this.options[this.selectedIndex].value);";
%>    
  <tr class="row<%= rowId %>">
    <td valign="top" nowrap>
      <zeroio:permission name="project-team-edit-role" if="none">
        <zeroio:role id="<%= thisMember.getUserLevel() %>"/>
      </zeroio:permission>
      <zeroio:permission name="project-team-view-role">
        <dhv:evaluate if="<%= !Project.isTrashed() %>" >
          <zeroio:roleSelect
            name="<%= roleName %>"
            value="<%= roleValue %>"
            onChange="<%= roleOnChange %>"
            isPortalUser="true"/>
        </dhv:evaluate>
        <dhv:evaluate if="<%= Project.isTrashed() %>" >
          <zeroio:role id="<%= thisMember.getUserLevel() %>"/>
        </dhv:evaluate>
      </zeroio:permission>
    </td>
    <td valign="top"><%= thisMember.getUserId() %></td>
    <td valign="top">
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"><font color="red"></dhv:evaluate>
      <%= toHtml(thisContact.getContact().getNameFirstLast()) %>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"> (X)</dhv:evaluate>
    <dhv:evaluate if="<%= hasText(thisContact.getContact().getCompany()) %>">
        /
        <%= toHtml(thisContact.getContact().getCompany()) %>
      </dhv:evaluate>
      <zeroio:permission name="project-team-view-email">
        <dhv:evaluate if="<%= thisContact.getContact().getEmailAddress(1) != null %>"><br /><a href="mailto:<%= thisContact.getContact().getEmailAddress(1) %>"><%= thisContact.getContact().getEmailAddress(1) %></a></dhv:evaluate>&nbsp;
      </zeroio:permission>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"></font></dhv:evaluate>
    </td>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_ADDED %>">
      <td valign="top" nowrap>
        <dhv:label name="project.added" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>'>Added <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label>
      </td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_PENDING %>">
      <td valign="top" nowrap><font color="green"><dhv:label name="project.invited" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>'>Invited <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_INVITING %>">
      <td valign="top" nowrap><font color="green"><dhv:label name="project.invited" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>'>Invited <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_MAILERROR %>">
      <td valign="top" nowrap><font color="red"><dhv:label name="project.invitationCouldNotBeSent">Invitation could not be sent to email address</dhv:label></font></td>
    </dhv:evaluate>
    <dhv:evaluate if="<%= thisMember.getStatus() == TeamMember.STATUS_REFUSED %>">
      <td valign="top" nowrap><font color="red"><dhv:label name="project.invitationRefused" param='<%= "time="+getTime(pageContext,thisMember.getModified(),User.getTimeZone(),DateFormat.SHORT,true,false,true,"&nbsp;") %>' >Invitation refused <zeroio:tz timestamp="<%= thisMember.getModified() %>" dateOnly="true" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font></td>
    </dhv:evaluate>
    <td nowrap valign="top">
      <zeroio:tz timestamp="<%= thisMember.getLastAccessed() %>" dateOnly="true" default="--" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
<%    
  }
%>
</table>