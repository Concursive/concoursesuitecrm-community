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
<jsp:useBean id="APP_TEXT" class="java.lang.String" scope="application"/>
<jsp:useBean id="APP_ORGANIZATION" class="java.lang.String" scope="application"/>
<%@ include file="../initPage.jsp" %>
<form name="modify" action="AdminConfig.do?command=UpdateLicense" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="AdminConfig.do?command=ListGlobalParams"><dhv:label name="admin.configureSystem">Configure System</dhv:label></a> >
<dhv:label name="admin.license">License</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><b><dhv:label name="admin.license.question">Have you upgraded your license?</dhv:label></b><br />
<dhv:label name="admin.license.text">Every Centric CRM application instance requires a license agreement from Dark Horse Ventures.
If changes have been made to the license, for example, 
additional seats have been purchased, then the installed license should be updated to
reflect those changes.</dhv:label>
<dhv:label name="admin.modifyLicense.contactForUpgrade.text" param="url=<a href=\"http://www.centriccrm.com\" target=\"_blank\">|end=</a>">Contact <a href="http://www.centriccrm.com" target="_blank">www.centriccrm.com</a> for upgrade information</dhv:label>
</td></tr></table>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="admin.installedLicense">Installed License</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="admin.edition">Edition</dhv:label>
      </td>
      <td>
         <%= toHtml(APP_TEXT) %>
      </td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="admin.licenseTo">Licensed To</dhv:label>
      </td>
      <td>
         <%= toHtml(APP_ORGANIZATION) %>
      </td>
    </tr>
  </table>
  <br />
  <%--
  <input type="radio" name="doLicense" value="internet" checked />
  --%>
  <input type="hidden" name="doLicense" value="internet" />
  <dhv:label name="admin.license.notes">Use HTTP/S to have this system remotely check and download an updated license</dhv:label><br />
  <%--
  <input type="radio" name="doLicense" value="email"/>
  Manually enter an updated license from an email message<br />
  --%>
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>
