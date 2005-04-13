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
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="EmployeeBean" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:document.webdavForm.password.focus();">
<form name="webdavForm" action="MyCFSWebdav.do?command=ActivateWebdav&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="actionList.myHomePage">My Home Page</dhv:label></a> > 
<a href="MyCFS.do?command=MyProfile"><dhv:label name="Settings">Settings</dhv:label></a> >
<dhv:label name="calendar.activateWebFolderAccess">Activate Web Folder Access</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td>
      <dhv:label name="calendar.webdavFolder.text">A web folder allows you to access documents and data in Centric CRM as if they were available on a network file share.  Once web folder access has been activated, create a shared volume or folder to the following URL:</dhv:label><br />
      <br />
      http://<%= getServerUrl(request) %>/files
    </td>
  </tr>
</table>
<dhv:permission name="myhomepage-profile-password-edit">
<input type="submit" value="<dhv:label name="campaign.activate">Activate</dhv:label>" name="Save">
<input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile'">
</dhv:permission>
<br />
<dhv:formMessage />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="calendar.activateWebFolderAccess">Activate Web Folder Access</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="calendar.currentPassword">Current Password</dhv:label>
    </td>
    <td>
      <input type="password" name="password" value=""><font color="red">*</font>
      <%= showAttribute(request, "passwordError") %>
    </td>
  </tr>
</table>
<dhv:permission name="myhomepage-profile-password-edit">
<br />
<input type="hidden" value="<%= User.getId() %>" name="id">
<input type="submit" value="<dhv:label name="campaign.activate">Activate</dhv:label>" name="Save">
<input type="submit" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile'">
</dhv:permission>
</form>
</body>
