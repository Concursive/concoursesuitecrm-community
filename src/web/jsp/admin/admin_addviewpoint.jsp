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
      <br />
      <dhv:formMessage />
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
            [<a href="javascript:popContactsListSingle('contactid','changecontact','usersOnly=true&reset=true&filters=employees');">Change Contact</a>]&nbsp;
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
    <td align="center">
      <input type="checkbox" value="ON" name="permission<%= idCount %>view" <%= (Viewpoint.getPermissionList().hasPermission(thisPermission.getName(), "view"))?"checked":"" %>>
    </td>
    <td width="100%">
      <input type="hidden" name="permission<%= idCount %>id" value="<%= thisPermission.getId() %>">
      <%= toHtml(thisPermission.getDescription()) %>
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

