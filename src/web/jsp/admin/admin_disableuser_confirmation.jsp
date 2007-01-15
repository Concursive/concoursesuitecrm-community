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
  - Version: $Id$
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.admin.base.*,java.util.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="ManagerUser" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="disableUser" action="Users.do?command=DisableUser" method="POST">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
	  <a href="Users.do?command=ListUsers"><dhv:label name="admin.viewUsers">View Users</dhv:label></a> >
  <%}%>
<%} else {%>
  <a href="Users.do?command=ListUsers"><dhv:label name="admin.viewUsers">View Users</dhv:label></a> >
  <a href="Users.do?command=UserDetails&id=<%= User.getId() %>"><dhv:label name="admin.userDetails">User Details</dhv:label></a> >
<%}%>
<dhv:label name="admin.disableUserAccount">Disable User Account</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong><dhv:label name="admin.disableUserAccount">Disable User Account</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBack">
    <td align="left">
      <dhv:label name="admin.disableUserAccount.text.1" param='<%= "username="+User.getUsername()+"|contactName="+User.getContact().getNameLastFirst() %>'>You have requested to disable the user account <b><%=User.getUsername()%></b> (<%=User.getContact().getNameLastFirst()%>).</dhv:label><p>
      <dhv:label name="admin.disableUserAccount.text.2" param="page=<p>">Nearly every object within ultimately depends on a user account, so it is imperative that any data directly associated with the user account you wish to disable gets re-assigned to an active user within the system.<p>If you click "Proceed", the following actions will occur:</dhv:label>
      <ul>
      <li><dhv:label name="admin.userImmediatelyDisabled" param='<%= "username="+User.getUsername() %>'><b><%= User.getUsername() %></b> will be immediately disabled</dhv:label></li>
      <li><dhv:label name="admin.userdisable.ticketCreated.text">A Ticket will be created and assigned to the direct manager, if there is one, of the newly disabled User in reference to any data re-assignment that needs to occur</dhv:label></li>
      <li><dhv:label name="admin.userDisable.managerNotified.text">The direct manager will be notified via email</dhv:label></li>
    <dhv:evaluate if="<%= ManagerUser.getId() > 0 %>">
      <li><dhv:label name="admin.usersManagerIs.colon" param='<%= "contactName="+ManagerUser.getContact().getNameFirstLast() %>'>This user's direct manager is listed as: <%= ManagerUser.getContact().getNameFirstLast() %></dhv:label></li>
    </dhv:evaluate>
      </ul>
<dhv:permission name="myhomepage-reassign-edit">
      If you are also responsible for the data re-assignments of this user, you can go to "Re-Assignments" in My Home Page, or just <a href="Reassignments.do?command=Reassign&userId=<%=User.getId()%>">click here</a>
      to go to that module and make those changes.<br><br>
</dhv:permission>
      <input type="checkbox" name="disablecontact" value="true"> <dhv:label name="admin.archiveContact.text">Check this box if you want to archive the contact associated with this user so that the information no longer displays in the Contact/Employee modules.</dhv:label><br>&nbsp;
    </td>
  </tr>
</table>
<br>
<input type="submit" value="<dhv:label name="button.proceed">Proceed</dhv:label>">
<% if (request.getParameter("return") != null) {%>
	<% if (request.getParameter("return").equals("list")) {%>
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Users.do?command=ListUsers';this.form.submit()">
	<%}%>
<%} else {%>
      <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Users.do?command=UserDetails';this.form.submit()">
<%}%>
      <input type="hidden" name="id" value="<%= User.getId() %>">
</form>
