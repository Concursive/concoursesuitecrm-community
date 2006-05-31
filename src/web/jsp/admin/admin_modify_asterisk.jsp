<%-- 
  - Copyright(c) 2005 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  <td><b><dhv:label name="setup.asterisk">Asterisk Server</dhv:label></b><br />
    <br />
    <dhv:label name="setup.asterisk.question">For inbound call monitoring and outbound call dialing, which Asterisk server should be used?</dhv:label><br>
  </td>
</tr>
</table>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.asterisk.inbound">Inbound:</dhv:label>
      </td>
      <td>
        <dhv:checkbox name="asteriskInbound" value="true" checked="<%= "true".equals(getPref(getServletConfig().getServletContext(), "ASTERISK.INBOUND.ENABLED")) %>"/>Monitor inbound calls
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.asterisk.outbound">Outbound:</dhv:label>
      </td>
      <td>
        <dhv:checkbox name="asteriskOutbound" value="true" checked="<%= "true".equals(getPref(getServletConfig().getServletContext(), "ASTERISK.OUTBOUND.ENABLED")) %>"/>Enable outbound call dialing
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.asterisk.server">Asterisk Server:</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="asteriskUrl" value="<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "ASTERISK.URL")) %>"/>
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.asterisk.username">Asterisk Username:</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="asteriskUsername" value="<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "ASTERISK.USERNAME")) %>"/>
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.asterisk.password">Asterisk Password:</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="asteriskPassword" value="<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "ASTERISK.PASSWORD")) %>"/>
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.asterisk.context">Asterisk Context:</dhv:label>
      </td>
      <td>
        <input type="text" size="30" name="asteriskContext" value="<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "ASTERISK.CONTEXT")) %>"/>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>
