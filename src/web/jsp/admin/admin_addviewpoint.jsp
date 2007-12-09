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
<%@ include file="../initPage.jsp" %>
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.Permission" %>
<jsp:useBean id="PermissionList" class="org.aspcfs.modules.admin.base.PermissionList" scope="request"/>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="Viewpoint" class="org.aspcfs.modules.admin.base.Viewpoint" scope="request"/>
<jsp:useBean id="vpUserId" class="java.lang.String" scope="request" />
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js?v=20070827"></script>
<script language="JavaScript" type="text/javascript" src="javascript/submit.js"></script>
<form name="viewpointForm" action="Viewpoints.do?command=InsertViewpoint&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="Users.do?command=ListUsers"><dhv:label name="admin.viewUsers">View Users</dhv:label></a> >
<a href="Users.do?command=UserDetails&id=<%= request.getParameter("userId") %>"><dhv:label name="admin.userDetails">User Details</dhv:label></a> >
<a href="Viewpoints.do?command=ListViewpoints&userId=<%= request.getParameter("userId") %>"><dhv:label name="users.viewpoints.long_html">Viewpoints</dhv:label></a> >
<dhv:label name="admin.addViewpoint">Add Viewpoint</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="users" selected="viewpoints" object="UserRecord" param='<%= "id=" + UserRecord.getId() %>'>
  <input type="submit" value="<dhv:label name="button.add">Add</dhv:label>" name="Save">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Viewpoints.do?command=ListViewpoints'">
  <br />
  <dhv:formMessage />
  <input type="hidden" name="userId" value="<%= UserRecord.getId() %>">
  <table cellpadding="4" cellspacing="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="admin.selectUser">Select User</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="campaign.user">User</dhv:label>
      </td>
      <td valign="bottom">
        <table class="empty">
          <tr>
            <td>
              <div id="changecontact">
                <dhv:evaluate if='<%= vpUserId != null && !"".equals(vpUserId) %>'>
                  <dhv:username id="<%= vpUserId %>" lastFirst="true" />
                </dhv:evaluate>
                <dhv:evaluate if='<%= vpUserId == null || "".equals(vpUserId) %>'>
                  <dhv:label name="accounts.accounts_contacts_calls_details_followup_include.None">None</dhv:label>
                </dhv:evaluate>
              </div>
            </td>
            <td>
              <input type="hidden" name="vpUserId" id="contactid" value="-1">
              [<a href="javascript:popContactsListSingle('contactid','changecontact','&listView=employees&usersOnly=true&siteId=<%=UserRecord.getSiteId()%>&mySiteOnly=true&reset=true&filters=accountcontacts|employees');"><dhv:label name="admin.changeUser">Change User</dhv:label></a>]&nbsp;
              [<a href="javascript:document.viewpointForm.vpUserId.value='-1';javascript:changeDivContent('changecontact',label('label.none','None'));"><dhv:label name="admin.clearUser">Clear User</dhv:label></a>]
              <font color="red">*</font><%= showAttribute(request, "ContactError") %>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <br />
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th nowrap colspan="2">
        <strong><dhv:label name="admin.selectPermission">Select Permissions</dhv:label></strong>
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
      <td width="40" align="center"><dhv:label name="admin.access">Access</dhv:label></td>
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
  <input type="submit" value="<dhv:label name="button.add">Add</dhv:label>" name="Save">
  <input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='Viewpoints.do?command=ListViewpoints'">
</dhv:container>
</form>
