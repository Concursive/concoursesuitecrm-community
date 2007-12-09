<%-- 
  - Copyright(c) 2005 Concursive Corporation (http://www.concursive.com/) All
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
<form name="modifyValue" action="AdminConfig.do?command=Update" method="post">
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
  <td><b><dhv:label name="setup.xmpp">XMPP Server</dhv:label></b><br />
    <br />
    <dhv:label name="setup.xmpp.question">For monitoring user presence and performing inbound screen-pops for Asterisk calls, which XMPP server should be used?</dhv:label><br>
  </td>
</tr>
</table>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.xmpp.status">XMPP Status:</dhv:label>
      </td>
      <td>
        <dhv:checkbox name="xmppEnabled" value="true" checked='<%= "true".equals(getPref(getServletConfig().getServletContext(), "XMPP.ENABLED")) %>'/>Monitor user presence and send instant messages
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.xmpp.server">XMPP Server:</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="xmppUrl" value='<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "XMPP.CONNECTION.URL")) %>'/>
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.xmpp.port">XMPP Port:</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="xmppPort" value='<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "XMPP.CONNECTION.PORT")) %>'/>
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.xmpp.ssl">XMPP SSL:</dhv:label>
      </td>
      <td>
        <dhv:checkbox name="xmppSSL" value="true" checked='<%= "true".equals(getPref(getServletConfig().getServletContext(), "XMPP.CONNECTION.SSL")) %>'/>Use SSL connection to XMPP server
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.xmpp.username">XMPP Username:</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="xmppUsername" value='<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "XMPP.MANAGER.USERNAME")) %>'/>
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.xmpp.password">XMPP Password:</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="xmppPassword" value="<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "XMPP.MANAGER.PASSWORD")) %>"/>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>
