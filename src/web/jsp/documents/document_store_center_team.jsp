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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %> 
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.admin.base.User, org.aspcfs.modules.documents.base.*" %>
<jsp:useBean id="documentStore" class="org.aspcfs.modules.documents.base.DocumentStore" scope="request"/>
<jsp:useBean id="currentMember" class="org.aspcfs.modules.documents.base.DocumentStoreTeamMember" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_new-bcard-16.gif" border="0" align="absmiddle">
        <dhv:label name="documents.team.team">Team</dhv:label>
    </td>
  </tr>
</table>
<dhv:evaluate if="<%= !documentStore.isTrashed() %>" >
  <br />
  <dhv:documentPermission name="documentcenter-team-edit">
    <a href="DocumentStoreManagementTeam.do?command=Modify&documentStoreId=<%= documentStore.getId() %>&modifyTeam=user&auto-populate=true">
      <dhv:label name="documents.team.modifyUserMembership">Modify User Membership</dhv:label></a>
    |
    <a href="DocumentStoreManagementTeam.do?command=Modify&documentStoreId=<%= documentStore.getId() %>&modifyTeam=group&auto-populate=true">
      <dhv:label name="documents.team.modifyGroupMembership">Modify Group Membership</dhv:label></a>
    <br />
    </dhv:documentPermission>
    <dhv:documentPermission name="documentcenter-team-edit-role">
    <script language="javascript" type="text/javascript">
      function updateRole(documentStoreId, id, rid, memberType,sid) {
        window.frames['server_commands'].location.href='DocumentStoreManagementTeam.do?command=ChangeRole&documentStoreId=' + documentStoreId + '&id=' + id + '&role=' + rid + '&memberType=' + memberType+'&siteId='+sid;
      }
    </script>
    <br />
    <font color="blue"><dhv:label name="documents.team.teamMessage">* Immediately change the team member's role by making changes below</dhv:label></font>
    <iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0" width="0" border="0" frameborder="0"></iframe>
  </dhv:documentPermission>
  &nbsp;<br /><br />
</dhv:evaluate>
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="false" title="Employees" type="documents.team.employees" object="documentStoreEmployeeTeamInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>
        <dhv:label name="documents.team.accessLevel">Access level</dhv:label>
      </strong>
    </th>
    <th width="50%">
      <strong>
        <dhv:label name="documents.team.name">Name</dhv:label>
      </strong>
    </th>
    <th width="50%">
      <strong>
        <dhv:label name="documents.team.department">Department</dhv:label>
       </strong>
     </th>
    <dhv:documentPermission name="documentcenter-team-view-email">
      <th nowrap>
        <strong>
          <dhv:label name="documents.team.emailAddress">Email Address</dhv:label>
        </strong>
     </th>
    </dhv:documentPermission>
    <th nowrap>
      <strong>
        <dhv:label name="documents.team.lastAccessed">Last Accessed</dhv:label>
      </strong>
    </th>
  </tr>
<%
  DocumentStoreTeamMemberList employeeTeam = documentStore.getEmployeeTeam();
  if (employeeTeam.size() == 0) {
%>
  <tr class="row2">
    <td colspan="6">
      <dhv:label name="documents.team.noEmployeeMessage">Employees have not been added to the store</dhv:label>
    </td>
  </tr>
<%
  }
  int rowId = 0;
  Iterator i = employeeTeam.iterator();
  while (i.hasNext()) {
    rowId = (rowId != 1?1:2);
    DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
    User thisContact = (User) thisMember.getUser();
    if (thisContact == null) thisContact = new User();
%>    
  <tr class="row<%= rowId %>">
    <td valign="top" nowrap>
      <dhv:documentPermission name="documentcenter-team-edit-role" if="none">
        <dhv:documentRole id="<%= thisMember.getUserLevel() %>"/>
      </dhv:documentPermission>
      <dhv:documentPermission name="documentcenter-team-edit-role">
        <dhv:evaluate if="<%= !documentStore.isTrashed() %>" >
          <dhv:documentRoleSelect
            name='<%= "role" + thisMember.getItemId() %>'
            value="<%= thisMember.getUserLevel() %>"
            onChange='<%= "javascript:updateRole(" + thisMember.getDocumentStoreId() + ", " + thisMember.getItemId() + ", this.options[this.selectedIndex].value, 'user', "+ thisMember.getSiteId()+");" %>'/>
         </dhv:evaluate>
         <dhv:evaluate if="<%= documentStore.isTrashed() %>" >
           <dhv:documentRole id="<%= thisMember.getUserLevel() %>"/>
         </dhv:evaluate>
      </dhv:documentPermission>
    </td>
    <td valign="top">
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"><font color="red"></dhv:evaluate>
      <%= toHtml(thisContact.getContact().getNameFirstLast()) %>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"> (X)</dhv:evaluate>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"></font></dhv:evaluate>
    </td>
    <td valign="top"><%= toHtml(thisContact.getContact().getDepartmentName()) %></td>
    <dhv:documentPermission name="documentcenter-team-view-email">
       <td valign="top" nowrap>
        <dhv:evaluate if="<%= thisContact.getContact().getEmailAddress(1) != null %>"><a href="mailto:<%= thisContact.getContact().getEmailAddress(1) %>"><%= thisContact.getContact().getEmailAddress(1) %></a></dhv:evaluate>&nbsp;
      </td>
    </dhv:documentPermission>
    <td nowrap valign="top">
      <zeroio:tz timestamp="<%= thisMember.getLastAccessed() %>" dateOnly="true" default="--" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
<%    
    }
%>
</table>
&nbsp;<br />
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="false" title="Account Contacts" type="documents.team.accountContacts" object="documentStoreAccountContactTeamInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>
        <dhv:label name="documents.team.accessLevel">Access level</dhv:label>
      </strong>
    </th>
    <th width="50%">
      <strong>
        <dhv:label name="documents.team.name">Name</dhv:label>
      </strong>
    </th>
    <th width="50%">
      <strong>
        <dhv:label name="documents.team.organization">Organization</dhv:label>
       </strong>
     </th>
    <dhv:documentPermission name="documentcenter-team-view-email">
      <th nowrap>
        <strong>
          <dhv:label name="documents.team.emailAddress">Email Address</dhv:label>
        </strong>
     </th>
    </dhv:documentPermission>
    <th nowrap>
      <strong>
        <dhv:label name="documents.team.lastAccessed">Last Accessed</dhv:label>
      </strong>
    </th>
  </tr>
<%
  DocumentStoreTeamMemberList accountContactTeam = documentStore.getAccountContactTeam();
  if (accountContactTeam.size() == 0) {
%>
  <tr class="row2">
    <td colspan="6">
      <dhv:label name="documents.team.noAccountContactsMessage">Account contacts have not been added to the store</dhv:label>
    </td>
  </tr>
<%
  }
  rowId = 0;
  i = accountContactTeam.iterator();
  while (i.hasNext()) {
    rowId = (rowId != 1?1:2);
    DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
    User thisContact = (User) thisMember.getUser();
    if (thisContact == null) thisContact = new User();
%>    
  <tr class="row<%= rowId %>">
    <td valign="top" nowrap>
      <dhv:documentPermission name="documentcenter-team-edit-role" if="none">
        <dhv:documentRole id="<%= thisMember.getUserLevel() %>"/>
      </dhv:documentPermission>
      <dhv:documentPermission name="documentcenter-team-edit-role">
        <dhv:evaluate if="<%= !documentStore.isTrashed() %>" >
          <dhv:documentRoleSelect
            name='<%= "role" + thisMember.getItemId() %>'
            value="<%= thisMember.getUserLevel() %>"
            onChange='<%= "javascript:updateRole(" + thisMember.getDocumentStoreId() + ", " + thisMember.getItemId() + ", this.options[this.selectedIndex].value, 'user',"+thisMember.getSiteId()+");" %>'/>
        </dhv:evaluate>
        <dhv:evaluate if="<%= documentStore.isTrashed() %>" >
          <dhv:documentRole id="<%= thisMember.getUserLevel() %>"/>
        </dhv:evaluate>
      </dhv:documentPermission>
    </td>
    <td valign="top">
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"><font color="red"></dhv:evaluate>
      <%= toHtml(thisContact.getContact().getNameFirstLast()) %>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"> (X)</dhv:evaluate>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"></font></dhv:evaluate>
    </td>
    <td valign="top"><%= toHtml(thisContact.getContact().getCompany()) %></td>
    <dhv:documentPermission name="documentcenter-team-view-email">
       <td valign="top" nowrap>
        <dhv:evaluate if="<%= thisContact.getContact().getEmailAddress(1) != null %>"><a href="mailto:<%= thisContact.getContact().getEmailAddress(1) %>"><%= thisContact.getContact().getEmailAddress(1) %></a></dhv:evaluate>&nbsp;
      </td>
    </dhv:documentPermission>
    <td nowrap valign="top">
      <zeroio:tz timestamp="<%= thisMember.getLastAccessed() %>" dateOnly="true" default="--" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
<%    
    }
%>
</table>
&nbsp;<br />
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="false" title="Roles" type="documents.team.roles" object="documentStoreRoleTeamInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>
        <dhv:label name="documents.team.accessLevel">Access level</dhv:label>
      </strong>
    </th>
    <th width="90%">
      <strong>
        <dhv:label name="documents.team.name">Name</dhv:label>
      </strong>
     </th>
  </tr>
<%
  DocumentStoreTeamMemberList roleTeam = documentStore.getRoleTeam();
  if (roleTeam.size() == 0) {
%>
  <tr class="row2">
    <td colspan="6">
      <dhv:label name="documents.team.noRoleMessage">User Role membership has not been defined</dhv:label>
     </td>
  </tr>
<%
  }
  rowId = 0;
  i = roleTeam.iterator();
  while (i.hasNext()) {
    rowId = (rowId != 1?1:2);
    DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
    String roleName = (String) thisMember.getUser() + (thisMember.getSiteId() == -1?"":" ("+SiteIdList.getSelectedValue(thisMember.getSiteId()) +")");
%>    
  <tr class="row<%= rowId %>">
    <td valign="top" nowrap>
      <dhv:documentPermission name="documentcenter-team-edit-role" if="none">
        <dhv:documentRole id="<%= thisMember.getUserLevel() %>"/>
      </dhv:documentPermission>
      <dhv:documentPermission name="documentcenter-team-edit-role">
        <dhv:documentRoleSelect
          name='<%= "role" + thisMember.getItemId() %>'
          value="<%= thisMember.getUserLevel() %>"
          onChange='<%= "javascript:updateRole(" + thisMember.getDocumentStoreId() + ", " + thisMember.getItemId() + ", this.options[this.selectedIndex].value, 'role',"+thisMember.getSiteId()+");" %>'/>
      </dhv:documentPermission>
    </td>
    <td valign="top"><%= toHtml(roleName) %></td>
  </tr>
<%    
  }
%>
</table>
&nbsp;<br />
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="false" title="Departments" type="documents.team.departments" object="documentStoreDepartmentTeamInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>
        <dhv:label name="documents.team.accessLevel">Access level</dhv:label>
      </strong>
    </th>
    <th width="90%">
      <strong>
        <dhv:label name="documents.team.name">Name</dhv:label>
      </strong>
     </th>
  </tr>
<%
  DocumentStoreTeamMemberList departmentTeam = documentStore.getDepartmentTeam();
  if (departmentTeam.size() == 0) {
%>
  <tr class="row2">
    <td colspan="6">
      <dhv:label name="documents.team.noDepartmentMessage">Department membership has not been defined</dhv:label>
     </td>
  </tr>
<%
  }
  rowId = 0;
  i = departmentTeam.iterator();
  while (i.hasNext()) {
    rowId = (rowId != 1?1:2);
    DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
    String departmentName = (String) thisMember.getUser() + (thisMember.getSiteId() == -1?"":" ("+SiteIdList.getSelectedValue(thisMember.getSiteId()) +")");
%>    
  <tr class="row<%= rowId %>">
    <td valign="top" nowrap>
      <dhv:documentPermission name="documentcenter-team-edit-role" if="none">
        <dhv:documentRole id="<%= thisMember.getUserLevel() %>"/>
      </dhv:documentPermission>
      <dhv:documentPermission name="documentcenter-team-edit-role">
        <dhv:evaluate if="<%= !documentStore.isTrashed() %>" >
          <dhv:documentRoleSelect
            name='<%= "role" + thisMember.getItemId() %>'
            value="<%= thisMember.getUserLevel() %>"
            onChange='<%= "javascript:updateRole(" + thisMember.getDocumentStoreId() + ", " + thisMember.getItemId() + ", this.options[this.selectedIndex].value, 'department',"+thisMember.getSiteId()+");" %>'/>
        </dhv:evaluate>
        <dhv:evaluate if="<%= documentStore.isTrashed() %>" >
          <dhv:documentRole id="<%= thisMember.getUserLevel() %>"/>
        </dhv:evaluate>
      </dhv:documentPermission>
    </td>
    <td valign="top"><%= toHtml(departmentName) %></td>
  </tr>
<%    
  }
%>
</table>
&nbsp;<br />
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="false" title="Portal Users" type="documents.team.portalUsers" object="documentStorePortalUserTeamInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>
        <dhv:label name="documents.team.accessLevel">Access level</dhv:label>
      </strong>
    </th>
    <th width="50%">
      <strong>
        <dhv:label name="documents.team.name">Name</dhv:label>
      </strong>
    </th>
    <th width="50%">
      <strong>
        <dhv:label name="documents.team.organization">Organization</dhv:label>
       </strong>
     </th>
    <dhv:documentPermission name="documentcenter-team-view-email">
      <th nowrap>
        <strong>
          <dhv:label name="documents.team.emailAddress">Email Address</dhv:label>
        </strong>
     </th>
    </dhv:documentPermission>
    <th nowrap>
      <strong>
        <dhv:label name="documents.team.lastAccessed">Last Accessed</dhv:label>
      </strong>
    </th>
  </tr>
<%
  DocumentStoreTeamMemberList portalUserTeam = documentStore.getPortalUserTeam();
  if (portalUserTeam.size() == 0) {
%>
  <tr class="row2">
    <td colspan="6">
      <dhv:label name="documents.team.noPortalUserMessage">Portal Users contacts have not been added to the store</dhv:label>
    </td>
  </tr>
<%
  }
  rowId = 0;
  i = portalUserTeam.iterator();
  while (i.hasNext()) {
    rowId = (rowId != 1?1:2);
    DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) i.next();
    User thisContact = (User) thisMember.getUser();
    if (thisContact == null) thisContact = new User();
%>    
  <tr class="row<%= rowId %>">
    <td valign="top" nowrap>
      <dhv:documentPermission name="documentcenter-team-edit-role" if="none">
        <dhv:documentRole id="<%= thisMember.getUserLevel() %>"/>
      </dhv:documentPermission>
      <dhv:documentPermission name="documentcenter-team-edit-role">
        <dhv:evaluate if="<%= !documentStore.isTrashed() %>" >
          <dhv:documentRoleSelect
            name='<%= "role" + thisMember.getItemId() %>'
            value="<%= thisMember.getUserLevel() %>"
            onChange='<%= "javascript:updateRole(" + thisMember.getDocumentStoreId() + ", " + thisMember.getItemId() + ", this.options[this.selectedIndex].value, 'user',"+thisMember.getSiteId()+");" %>'
            isPortalUser="true"/>
        </dhv:evaluate>
        <dhv:evaluate if="<%= documentStore.isTrashed() %>" >
          <dhv:documentRole id="<%= thisMember.getUserLevel() %>"/>
        </dhv:evaluate>
      </dhv:documentPermission>
    </td>
    <td valign="top">
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"><font color="red"></dhv:evaluate>
      <%= toHtml(thisContact.getContact().getNameFirstLast()) %>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"> (X)</dhv:evaluate>
      <dhv:evaluate if="<%= !thisContact.getEnabled() || !thisContact.getContact().getEnabled() || thisContact.getContact().isTrashed() %>"></font></dhv:evaluate>
    </td>
    <td valign="top"><%= toHtml(thisContact.getContact().getCompany()) %></td>
    <dhv:documentPermission name="documentcenter-team-view-email">
       <td valign="top" nowrap>
        <dhv:evaluate if="<%= thisContact.getContact().getEmailAddress(1) != null %>"><a href="mailto:<%= thisContact.getContact().getEmailAddress(1) %>"><%= thisContact.getContact().getEmailAddress(1) %></a></dhv:evaluate>&nbsp;
      </td>
    </dhv:documentPermission>
    <td nowrap valign="top">
      <zeroio:tz timestamp="<%= thisMember.getLastAccessed() %>" dateOnly="true" default="--" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
<%    
    }
%>
</table>
