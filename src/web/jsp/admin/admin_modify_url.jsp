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
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
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
    <td><b><dhv:label name="admin.defaultURL.question">Which Centric URL should be included in emails to allow the user to return back to the site?</dhv:label></b>
<ul>
<li><dhv:label name="admin.url.note.1">Typically this is used when a survey is sent to a contact</dhv:label></li>
<li><dhv:label name="admin.url.note.2">The URL must be specific and must exist in your DNS</dhv:label></li>
<li><dhv:label name="admin.url.note.3">If a domain name is not configured, you can specify the IP address, include the full path to get to the Centric CRM web application.</dhv:label></li>
</ul>
</td></tr></table>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="admin.serverURL">Server URL</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="admin.centricCrmUrl">Centric CRM URL</dhv:label>
      </td>
      <td>
         <input type="text" size="40" name="url" value="<%= toHtmlValue(getPref(getServletContext(), "WEBSERVER.URL")) %>"/><font color="red">*</font>
         <input type="button" value="Test" onClick="javascript:popURL('<%= request.getScheme() %>://' + document.modifyTimeout.url.value + '/setup/testpage_ok.jsp','CRM_UrlTest','500','325','yes','yes');"/>
         <%= showAttribute(request, "urlError") %>
         <br />
         <dhv:label name="admin.serverURL.example" param='<%= "contextPath="+request.getContextPath() %>'>(ex: www.yourcompany.com<%= request.getContextPath() %> or crm.yourcompany.com)</dhv:label>
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>
