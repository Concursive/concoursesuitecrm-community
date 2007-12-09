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
<%@ page import="org.aspcfs.utils.web.CountrySelect" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelect" %>
<jsp:useBean id="countrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="modify" action="AdminConfig.do?command=Update" method="post">
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
    <td><b><dhv:label name="admin.defaultCountry.question">What should the default country be for the system?</dhv:label></b><br />
</td></tr></table>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <strong><dhv:label name="admin.modifyDefaultCountry">Modify Default Country</dhv:label></strong>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="accounts.accounts_add.Country">Country</dhv:label>
      </td>
      <td>
         <%= countrySelect.getHtml("country", getPref(getServletContext(), "SYSTEM.COUNTRY")) %><font color="red">*</font>
         <%= showAttribute(request, "countryError") %>
      </td>
    </tr>
  </table>
  <br>
  <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>">
  <input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.location.href='AdminConfig.do?command=ListGlobalParams';">
</dhv:permission>
</form>
