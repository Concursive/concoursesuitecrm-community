<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<%@ page import="java.util.Iterator,org.aspcfs.modules.admin.base.Permission" %>
<jsp:useBean id="PermissionList" class="org.aspcfs.modules.admin.base.PermissionList" scope="request"/>
<jsp:useBean id="UserRecord" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="Viewpoint" class="org.aspcfs.modules.admin.base.Viewpoint" scope="request"/>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/submit.js"></script>
<form action='Viewpoints.do?command=InsertViewpoint&auto-populate=true' method='post'>
<a href="Admin.do">Setup</a> >
<a href="Users.do?command=ListUsers">View Users</a> >
<a href="Users.do?command=UserDetails&id=<%= request.getParameter("userId") %>">User Details</a> >
<a href="Viewpoints.do?command=ListViewpoints&userId=<%= request.getParameter("userId") %>">Viewpoints</a> >
Add Viewpoint <br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(UserRecord.getUsername()) %> (<%= toHtml(UserRecord.getContact().getNameLastFirst()) %>)</strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "id=" + UserRecord.getId(); %>
      <dhv:container name="users" selected="viewpoints" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
      <input type="submit" value="Add" name="Save">
      <input type="submit" value="Cancel" onClick="javascript:this.form.action='Viewpoints.do?command=ListViewpoints'">
      <input type="reset" value="Reset"><br>
      <%= showError(request, "actionError") %>
      <input type="hidden" name="userId" value="<%= UserRecord.getId() %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="2">
	    <strong>Select a Contact</strong>
	  </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      Contact
    </td>
    <td valign="bottom">
      <table>
        <tr>
          <td>
            <div id="changecontact">None</div>
          </td>
          <td>
            <font color="red">*</font>
            <input type="hidden" name="vpContactId" id="contactid" value="-1">
            [<a href="javascript:popContactsListSingle('contactid','changecontact','usersOnly=true&reset=true');">Change Contact</a>]
          </td>
          <td>
            [<a href="javascript:document.forms[0].vpContactId.value='-1';javascript:changeDivContent('changecontact','None');">Clear Contact</a>]
            <%= showAttribute(request, "ContactError") %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td nowrap colspan="2">
      <strong>Select Permissions</strong>
    </td>
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
  <input type="reset" value="Reset">
</td></tr>
</table>
</form>

