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
<form name="modifyTimeout" action="AdminConfig.do?command=Update" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a> >
<a href="AdminConfig.do?command=ListGlobalParams"><dhv:label name="admin.configureSystem">Configure System</dhv:label></a> >
<dhv:label name="admin.modifySetting">Modify Setting</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
<tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><b><dhv:label name="admin.faxServer.text" param="server=<a href=\"http://www.hylafax.org\" target=\"_new\">|end=</a>">Which fax server should Centric CRM use?</b><br />Users will have the capability to send faxes using Centric CRM.<br /><font color="red">* </font>The HylaFax server application requires Linux or Unix.<br />The faxing component requires a properly configured <a href="http://www.hylafax.org" target="_new">HylaFax</a> server including fax hardware.</dhv:label><br />
<ul>
<li><dhv:label name="admin.faxServer.note.1">The specified server must allow this server to send faxes</dhv:label></li>
<li><dhv:label name="admin.faxServer.note.2">Leave blank if faxing will not be used</dhv:label></li>
</ul>
</td></tr></table>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="admin.modifyFaxServer">Modify Fax Server</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="admin.faxServer">Fax Server</dhv:label>
      </td>
      <td>
         <input type="text" size="30" name="fax" value="<%= toHtmlValue(getPref(getServletContext(), "FAXSERVER")) %>"/>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>
