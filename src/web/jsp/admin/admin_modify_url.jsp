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
<a href="Admin.do">Admin</a> >
<a href="AdminConfig.do?command=ListGlobalParams">Configure System</a> >
Modify Setting
</td>
</tr>
</table>
<%-- End Trails --%>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><b>Which
Dark Horse CRM URL should be included in emails to allow the user to return back to the site?</b>
<ul>
<li>Typically this is used when a survey is sent to a contact</li>
<li>The URL must be specific and must exist in your DNS</li>
<li>If a domain name is not configured, you can specify the IP address, include the full path to
get to the Dark Horse CRM web application.</li>
</ul>
</td></tr></table>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong>Server URL</strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Dark Horse CRM URL
      </td>
      <td>
         <input type="text" size="40" name="url" value="<%= toHtmlValue(getPref(getServletContext(), "WEBSERVER.URL")) %>"/><font color="red">*</font>
         <input type="button" value="Test" onClick="javascript:popURL('<%= request.getScheme() %>://' + document.forms[0].url.value + '/setup/testpage_ok.jsp','CRM_UrlTest','500','325','yes','yes');"/>
         <%= showAttribute(request, "urlError") %>
         <br />
         (ex: www.yourcompany.com<%= request.getContextPath() %> or
         crm.yourcompany.com)
      </td>
    </tr>
  </table>
  <br />
  <input type="submit" value="Update">
  <input type="button" value="Cancel" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>
