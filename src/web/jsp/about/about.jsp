
<jsp:directive.page import="org.aspcfs.modules.service.tasks.GetURL"/><%-- 
  - Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
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
  - Version: $Id:  Exp$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ include file="../initPage.jsp" %>

<jsp:useBean id="version" class="java.lang.String" scope="request"/>
<jsp:useBean id="software" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="license" class="java.lang.StringBuffer" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>

<%-- Trails --%>
<table class="trails" cellspacing="0">
  <tr><td>
    <dhv:label name="about.aboutCentricCRM">About Concourse Suite Community Edition</dhv:label>
  </td></tr>
</table>
<%-- End Trails --%>
<%= toHtml(version) %>
<br/><br/>
<dhv:evaluate if="<%= hasText(applicationPrefs.get("SUPPORT.TEXT")) %>">
	<%= toHtml(applicationPrefs.get("SUPPORT.TEXT")) %>
	<br/><br />
	<a href="javascript:window.opener.open('<%= applicationPrefs.get("SUPPORT.URL")%>','_new')" ><%= applicationPrefs.get("SUPPORT.IMAGE") %></a>
</dhv:evaluate>
<table width="100%" class="pagedList" cellpadding="2">
  <tr>
    <th width="50%">
      <dhv:label name="about.Library">Library</dhv:label>
    </th>
    <th>
      <dhv:label name="about.license">License</dhv:label>
    </th>
  </tr>

  <% String[] softwareRecord = null;
     String[] licenseRecord = null;
     int row = 0;
     softwareRecord = software.toString().split(System.getProperty("line.separator"));
     licenseRecord = license.toString().split(System.getProperty("line.separator"));
     int maxLength = (softwareRecord.length > licenseRecord.length)?softwareRecord.length:licenseRecord.length;
     for (int i = 0; i < maxLength; i++) {
       row = (row != 1 ? 1 : 2);
  %>
  <tr class="row<%= row %>">
    <td>
      <% if (i < softwareRecord.length) { %>
        <%= softwareRecord[i] %> 
      <% } %>
      &nbsp;
    </td>
    <td>
      <% if (i < licenseRecord.length) { %>
        <%= licenseRecord[i] %> 
      <% } %>
      &nbsp;
    </td>
  </tr>
  <%
    }
  %>

</table>
<br/>
<input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:window.close();"/>
