<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Timeout" class="java.lang.String" scope="request"/>
<jsp:useBean id="APP_TEXT" class="java.lang.String" scope="application"/>
<%@ page import="org.aspcfs.utils.web.HtmlSelectTimeZone" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelectLanguage" %>
<%@ page import="org.aspcfs.utils.web.HtmlSelectCurrency" %>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> >
Configure System
</td>
</tr>
</table>
<%-- End Trails --%>
<% int count = 1; %>
<dhv:permission name="admin-sysconfig-view">
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th width="8">
        <strong>Action</strong>
      </th>
      <th>
        <strong>Parameter</strong>
      </th>
      <th>
        <strong>Current Value</strong>
      </th>
    </tr>
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=ModifyTimeout&timeout=<%= Timeout %>">Edit</a>
      </td>
      <td>
         Session Timeout
      </td>
      <td>
         <%= Timeout %> minutes
      </td>
    </tr>
<dhv:evaluate if="<%= getPref(getServletContext(), "WEBSERVER.ASPMODE") == null || !"true".equals(getPref(getServletContext(), "WEBSERVER.ASPMODE")) %>">
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=LICENSE">Edit</a>
      </td>
      <td>
         License Information and Details
      </td>
      <td>
         <%= toHtml(APP_TEXT) %>
      </td>
    </tr>
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=MAILSERVER">Edit</a>
      </td>
      <td>
         Email Server
      </td>
      <td>
         <%= toHtml(getPref(getServletContext(), "MAILSERVER")) %>
      </td>
    </tr>
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=EMAILADDRESS">Edit</a>
      </td>
      <td>
         Email Address
      </td>
      <td>
         <%= toHtml(getPref(getServletContext(), "EMAILADDRESS")) %>
      </td>
    </tr>
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=WEBSERVER.URL">Edit</a>
      </td>
      <td>
         Dark Horse CRM URL
      </td>
      <td>
         <%= toHtml(getPref(getServletContext(), "WEBSERVER.URL")) %>
      </td>
    </tr>
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=FAXSERVER">Edit</a>
      </td>
      <td>
         Fax Server
      </td>
      <td>
         <%= toHtml(getPref(getServletContext(), "FAXSERVER")) %>
      </td>
    </tr>
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=SYSTEM.TIMEZONE">Edit</a>
      </td>
      <td>
         Default Time Zone for new users
      </td>
      <td>
         <%= HtmlSelectTimeZone.getSelect("timeZone", getPref(getServletContext(), "SYSTEM.TIMEZONE")).getValueFromId(getPref(getServletContext(), "SYSTEM.TIMEZONE")) %>
      </td>
    </tr>
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=SYSTEM.CURRENCY">Edit</a>
      </td>
      <td>
         Default currency for system
      </td>
      <td>
         <%= HtmlSelectCurrency.getSelect("currency", getPref(getServletContext(), "SYSTEM.CURRENCY")).getValueFromId(getPref(getServletContext(), "SYSTEM.CURRENCY")) %>
      </td>
    </tr>
    <tr class="row<%= (++count % 2 == 0 ? "1":"2") %>">
      <td align="center">
        <a href="AdminConfig.do?command=Modify&param=SYSTEM.LANGUAGE">Edit</a>
      </td>
      <td>
         Default locale for system
      </td>
      <td>
         <%= HtmlSelectLanguage.getSelect("language", getPref(getServletContext(), "SYSTEM.LANGUAGE")).getValueFromId(getPref(getServletContext(), "SYSTEM.LANGUAGE")) %>
      </td>
    </tr>
</dhv:evaluate>
  </table>
  &nbsp;
</dhv:permission>
