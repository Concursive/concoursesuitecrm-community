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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">My Home Page</dhv:label></a> >
<dhv:label name="myitems.settings">Settings</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong><dhv:label name="calendar.makeASelection">Make a selection</dhv:label></strong>
    </th>
  </tr>
  <dhv:permission name="myhomepage-profile-personal-view">
  <tr>
    <td><a href="MyCFSProfile.do?command=MyCFSProfile"><dhv:label name="calendar.updatePersonalInformation">Update my personal information</dhv:label></a></td>
  </tr>
  </dhv:permission>
  <dhv:permission name="myhomepage-profile-settings-view,myhomepage-profile-view">
  <tr>
    <td><a href="MyCFSSettings.do?command=MyCFSSettings"><dhv:label name="calendar.configureMyLocation">Configure my location</dhv:label></a></td>
  </tr>
  </dhv:permission>
  <dhv:permission name="myhomepage-profile-password-edit">
  <tr>
    <td><a href="MyCFSPassword.do?command=MyCFSPassword"><dhv:label name="calendar.changeMyPassword">Change my password</dhv:label></a></td>
  </tr>
  </dhv:permission>
  <tr>
    <td><a href="MyCFSWebdav.do?command=MyCFSWebdav"><dhv:label name="calendar.accessWebFolders">Accessing Web Folders</dhv:label></a></td>
  </tr>
</table>
<br>
<%-- The time is currently <zeroio:tz timestamp="<%= new java.util.Date() %>"/> --%>

