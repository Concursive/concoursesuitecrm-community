<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.Permission" %>
<jsp:useBean id="PermissionList" class="org.aspcfs.modules.admin.base.PermissionList" scope="request"/>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="Viewpoint" class="org.aspcfs.modules.admin.base.Viewpoint" scope="request"/>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/submit.js"></script>
<form action='Viewpoints.do?command=InsertViewpoint&auto-populate=true' method='post'>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> >
<a href="Users.do?command=ListUsers">View Users</a> >
<a href="Users.do?command=UserDetails&id=<%= request.getParameter("userId") %>">User Details</a> >
<a href="Viewpoints.do?command=ListViewpoints&userId=<%= request.getParameter("userId") %>">Viewpoints</a> >
Add Viewpoint
</td>
</tr>
</table>
<%-- End Trails --%>
<strong><%= toHtml(UserRecord.getUsername()) %> (<%= toHtml(UserRecord.getContact().getNameLastFirst()) %>)</strong>
<% String param1 = "id=" + UserRecord.getId(); %>
<dhv:container name="users" selected="viewpoints" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td class="containerBack">
      <input type="submit" value="Add" name="Save">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Viewpoints.do?command=ListViewpoints'">
      <br>
      <%= showError(request, "actionError") %>
      <input type="hidden" name="userId" value="<%= UserRecord.getId() %>">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong>Select a Contact</strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Contact
    </td>
    <td valign="bottom">
      <table class="empty">
        <tr>
          <td>
            <div id="changecontact">None</div>
          </td>
          <td>
            <font color="red">*</font><%= showAttribute(request, "ContactError") %>
            <input type="hidden" name="vpUserId" id="contactid" value="-1">
            [<a href="javascript:popContactsListSingle('contactid','changecontact','usersOnly=true&reset=true');">Change Contact</a>]&nbsp;
            [<a href="javascript:document.forms[0].vpUserId.value='-1';javascript:changeDivContent('changecontact','None');">Clear Contact</a>]
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th nowrap colspan="2">
      <strong>Select Permissions</strong>
    </th>
  </tr>
<%
  Iterator i = PermissionList.iterator();
  int idCount = 0;
  while (i.hasNext()) {
    ++idCount;
    Permission thisPermission = (Permission)i.next();
    if (PermissionList.isNewCategory(thisPermission.getCategoryName())) {
%>
  <tr class="row1">
    <td>
      <%= toHtml(thisPermission.getCategoryName()) %>
    </td>
    <td width="40" align="center">Access</td>
  </tr>
<%
   }
%>
  <tr class="containerBody">
    <td>
      <input type="hidden" name="permission<%= idCount %>id" value="<%= thisPermission.getId() %>">
      &nbsp; &nbsp;<%= toHtml(thisPermission.getDescription()) %>
    </td>
    <td align="center">
        <input type="checkbox" value="ON" name="permission<%= idCount %>view" <%= (Viewpoint.getPermissionList().hasPermission(thisPermission.getName(), "view"))?"checked":"" %>>
    </td>
  </tr>
<%
  }
%>
</table>
  <br>
  <input type="submit" value="Add" name="Save">
  <input type="submit" value="Cancel" onClick="javascript:this.form.action='Viewpoints.do?command=ListViewpoints'">
</td></tr>
</table>
</form>

