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
<%-- Displays any global items for this setup page --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<%
  String image = "<img alt=\"Current Step\" src=\"images/bullet.gif\" align=\"absmiddle\"/>&nbsp;";
%>
<table width="100%" border="0">
  <tr>
    <td><b>Dark Horse CRM Installation</b></td>
  </tr>
</table>
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th colspan="2">
      You are here...
    </th>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "Welcome".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td width="100%">Welcome</td>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "Register".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td>Registration</td>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "Storage".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td nowrap>Configure Storage</td>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "Servers".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td nowrap>Configure Servers</td>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "Database".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td nowrap>Configure Database</td>
  </tr>
  <tr>
    <td><dhv:evaluate if="<%= "CRMSetup".equals(ModuleBean.getCurrentAction()) %>"><%= image %></dhv:evaluate></td>
    <td nowrap>Configure CRM</td>
  </tr>
</table>
<br>
<%-- info tab --%>
<dhv:evaluate if="<%= "Welcome".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Welcome
    </th>
  </tr>
  <tr>
    <td>
      Acquire, retain, and service your customers with
      Dark Horse CRM.<br>
      <br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.darkhorsecrm.com" target="_blank">Learn more about our products</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if="<%= "Register".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Registration
    </th>
  </tr>
  <tr>
    <td>
      Privacy is extremely important to us.<br>
      <br>
      Your information will not be provided to others.<br>
      <br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="javascript:popURL('privacy_statement.html','CRM_Privacy','400','400','yes','yes');">Review Privacy Statement</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if="<%= "Storage".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Storage Resources
    </th>
  </tr>
  <tr>
    <td>
      Remember to make backups of your data.<br>
      <br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.darkhorsecrm.com/Website.do?topic=Resources" target="_blank">File Backup Help</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if="<%= "Servers".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Server Resources
    </th>
  </tr>
  <tr>
    <td>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.darkhorsecrm.com/Website.do?topic=Resources" target="_blank">Sendmail Help</a><br>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.darkhorsecrm.com/Website.do?topic=Resources" target="_blank">HylaFax Help</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if="<%= "Database".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Database Resources
    </th>
  </tr>
  <tr>
    <td>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.darkhorsecrm.com/Website.do?topic=Resources" target="_blank">PostgreSQL Help</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
<%-- info tab --%>
<dhv:evaluate if="<%= "CRMSetup".equals(ModuleBean.getCurrentAction()) %>">
<table width="100%" border="0" class="globalItem" cellpadding="0" cellspacing="0">
  <tr>
    <th>
      Dark Horse CRM Resources
    </th>
  </tr>
  <tr>
    <td>
      <img alt="" src="images/bullet.gif" align="absmiddle"/>
      <a href="http://www.darkhorsecrm.com/Website.do?topic=Resources" target="_blank">Dark Horse CRM Help</a><br>
      &nbsp;
    </td>
  </tr>
</table>
</dhv:evaluate>
