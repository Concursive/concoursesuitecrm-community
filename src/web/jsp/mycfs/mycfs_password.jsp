<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="User" class="com.darkhorseventures.cfsbase.User" scope="request"/>
<jsp:useBean id="EmployeeBean" class="com.darkhorseventures.cfsbase.Contact" scope="request"/>
<%@ include file="initPage.jsp" %>
<body onLoad="javascript:document.forms[0].oldPassword.focus();">
<form action="/MyCFSPassword.do?command=UpdatePassword" method="post">
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/MyCFS.do?command=MyProfile'">
<input type="reset" value="Reset">
<br>
&nbsp;
<input type="hidden" name="empid" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="id" value="<%= EmployeeBean.getId() %>">
<input type="hidden" name="modified" value="<%= EmployeeBean.getModified() %>">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr bgcolor="#DEE0FA">
    <td colspan=2 valign=center align=left>
      <strong>Update Password</strong>
    </td>
  </tr>
  <tr><td nowrap class="formLabel">Old Password</td><td><input type="text" name="oldPassword" value=""><font color="red">*</font> <%= showAttribute(request, "oldPasswordError") %></td></tr>
  <tr><td nowrap class="formLabel">New Password</td><td><input type="text" name="newPassword1" value=""><font color="red">*</font> <%= showAttribute(request, "newPassword1Error") %></td></tr>
  <tr><td nowrap class="formLabel">Verify New Password</td><td><input type="text" name="newPassword2" value=""><font color="red">*</font> <%= showAttribute(request, "newPassword2Error") %></td></tr>
</table>
<br>
<input type="submit" value="Update" name="Save">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='/MyCFS.do?command=MyProfile'">
<input type="reset" value="Reset">
</form>
</body>
