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
<form name="inputForm" action="AdminConfig.do?command=Update" method="post">
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
  <td><b><dhv:label name="setup.ldap">LDAP Server</dhv:label></b><br />
    <br />
    <dhv:label name="setup.ldap.question">For authenticating users, should an LDAP server be used?</dhv:label><br>
  </td>
</tr>
</table>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.ldap.enabled">LDAP Enabled:</dhv:label>
      </td>
      <td>
        <dhv:checkbox name="ldapEnabled" value="true" checked='<%= "true".equals(getPref(getServletConfig().getServletContext(), "LDAP.ENABLED")) %>' />
        <dhv:label name="admin.ldap.authenticateUsers">Authenticate users against LDAP server</dhv:label>
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.ldap.userMapping">User Mapping:</dhv:label>
      </td>
      <td>
        <input type="radio" name="ldapCentricCRMField" value="username" <%= ("username".equals(getPref(getServletConfig().getServletContext(), "LDAP.CENTRIC_CRM.FIELD")) ? "checked" : "") %> /><dhv:label name="admin.ldap.useCentricLogin">Use user's Centric CRM login</dhv:label><br />
        <input type="radio" name="ldapCentricCRMField" value="email" <%= ("email".equals(getPref(getServletConfig().getServletContext(), "LDAP.CENTRIC_CRM.FIELD")) ? "checked" : "") %> /><dhv:label name="admin.ldap.useUserEmail">Lookup user's primary email from Centric CRM records</dhv:label><br />
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.ldap.factory">LDAP Factory:</dhv:label>
      </td>
      <td>
        <input type="text" size="40" name="ldapFactory" value="<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "LDAP.FACTORY")) %>"/>
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.ldap.url">LDAP Server URL:</dhv:label>
      </td>
      <td>
        <input type="text" size="40" name="ldapUrl" value="<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "LDAP.SERVER")) %>"/>
      </td>
    </tr>
    <tr>
      <td class="formLabel">
        <dhv:label name="admin.ldap.type">Type:</dhv:label>
      </td>
      <td>
        <table class="empty">
          <tr>
            <td nowrap valign="top">
              <table class="empty">
                <tr>
                  <td colspan="2">
                    <input type="radio" name="ldapSearchByAttribute" value="true" <%= ("true".equals(getPref(getServletConfig().getServletContext(), "LDAP.SEARCH.BY_ATTRIBUTE")) ? "checked" : "") %> /><dhv:label name="admin.ldap.searchByAttribute">Search for user in LDAP by Attribute</dhv:label>
                  </td>
                </tr>
                <tr>
                  <td>
                    <dhv:label name="admin.ldap.username">Username:</dhv:label>
                  </td>
                  <td>
                    <input type="text" size="30" name="ldapSearchUsername" value="<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "LDAP.SEARCH.USERNAME")) %>" />
                  </td>
                </tr>
                <tr>
                  <td>
                    <dhv:label name="admin.ldap.password">Password:</dhv:label>
                  </td>
                  <td>
                    <input type="text" size="30" name="ldapSearchPassword" value="<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "LDAP.SEARCH.PASSWORD")) %>"/>
                  </td>
                </tr>
                <tr>
                  <td>
                    <dhv:label name="admin.ldap.container">Container:</dhv:label>
                  </td>
                  <td nowrap>
                    <input type="text" size="30" name="ldapSearchContainer" value='<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "LDAP.SEARCH.CONTAINER")) %>'/>
                    <dhv:checkbox name="ldapSearchSubtree" value="true" checked='<%= "true".equals(getPref(getServletConfig().getServletContext(), "LDAP.SEARCH.SUBTREE")) %>'/>Search subtree
                  </td>
                </tr>
                <tr>
                  <td>
                    <dhv:label name="admin.ldap.objectClass">Object Class:</dhv:label>
                  </td>
                  <td>
                    <input type="text" size="30" name="ldapSearchOrgPerson" value='<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "LDAP.SEARCH.ORGPERSON")) %>'/>
                  </td>
                </tr>
                <tr>
                  <td>
                    <dhv:label name="admin.ldap.attribute">Attribute:</dhv:label>
                  </td>
                  <td>
                    <input type="text" size="30" name="ldapSearchAttribute" value='<%= toHtmlValue(getPref(getServletConfig().getServletContext(), "LDAP.SEARCH.ATTRIBUTE")) %>'/>
                  </td>
                </tr>
              </table>
            </td>
            <td nowrap valign="top">
              <table class="empty">
                <tr>
                  <td colspan="2">
                    <input type="radio" name="ldapSearchByAttribute" value="false" <%= (!"true".equals(getPref(getServletConfig().getServletContext(), "LDAP.SEARCH.BY_ATTRIBUTE")) ? "checked" : "") %> /><dhv:label name="admin.ldap.useComposite">Use LDAP Composite DN</dhv:label>
                  </td>
                </tr>
                <tr>
                  <td>
                    <dhv:label name="admin.ldap.compositePrefix">Composite DN Prefix:</dhv:label>
                  </td>
                  <td>
                    <input type="text" size="30" name="ldapSearchPrefix" value='<%= "true".equals(getPref(getServletConfig().getServletContext(), "LDAP.SEARCH.PREFIX")) %>' />
                  </td>
                </tr>
                <tr>
                  <td>
                    <dhv:label name="admin.ldap.compositePostfix">Composite DN Postfix:</dhv:label>
                  </td>
                  <td>
                    <input type="text" size="30" name="ldapSearchPostfix" value='<%= "true".equals(getPref(getServletConfig().getServletContext(), "LDAP.SEARCH.POSTFIX")) %>' />
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>
