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
<jsp:useBean id="Locale" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZone" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form action="MyCFSSettings.do?command=UpdateSettings" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<a href="MyCFS.do?command=MyProfile"><dhv:label name="Settings">Settings</dhv:label></a> >
<dhv:label name="accounts.accountasset_include.Location">Location</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="myhomepage-profile-settings-edit,myhomepage-profile-view">
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile'">
</dhv:permission>
<br>
&nbsp;
<input type="hidden" name="modified" value="<%= User.getModifiedString() %>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="calendar.locationSettings">Location Settings</dhv:label></strong>
    </th>
  </tr>
<%--
  <tr>
    <td nowrap class="formLabel">Start of Day</td>
    <td><input type="text" name="dayStart" value=""></td>
  </tr>
  <tr>
    <td nowrap class="formLabel">End of Day</td>
    <td><input type="text" name="dayEnd" value=""></td>
  </tr>
--%>
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accounts_contacts_communication_preference_add.TimeZone">Time Zone</dhv:label>
    </td>
    <td>
      <%= TimeZone.getSelect("timeZone", User.getTimeZone()).getHtml() %>
    </td>
  </tr>
</table>
<dhv:permission name="myhomepage-profile-settings-edit,myhomepage-profile-view">
<br>
<input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" name="Save">
<input type="submit" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:this.form.action='MyCFS.do?command=MyProfile'">
</form>
</dhv:permission>
