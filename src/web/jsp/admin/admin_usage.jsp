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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat,org.aspcfs.modules.admin.base.PermissionCategory, java.util.*" %>
<jsp:useBean id="rangeSelect" class="java.lang.String" scope="request"/>
<%
  java.sql.Timestamp dateStart = (java.sql.Timestamp) request.getAttribute("dateStart");
  java.sql.Timestamp dateEnd = (java.sql.Timestamp) request.getAttribute("dateEnd");
%>
<jsp:useBean id="usageList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="usageList2" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="applicationVersion" class="java.lang.String" scope="request"/>
<jsp:useBean id="databaseVersion" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  function page_init() {
    if ('custom' == '<%= rangeSelect %>') {
      showSpan('customFields');
      showSpan('customFields2');
      showSpan('customFields3');
    }
  }
  function checkSubmit() {
    var sel = document.forms['usage'].elements['rangeSelect'];
    var value = sel.options[sel.selectedIndex].value;
    if (value == 'custom') {
      showSpan('customFields');
      showSpan('customFields2');
      showSpan('customFields3');
      document.usage.dateStart.focus();
      return false;
    } else {
      hideSpan('customFields');
      hideSpan('customFields2');
      hideSpan('customFields3');
      document.usage.dateStart.value = '';
      document.usage.dateEnd.value = '';
      document.forms['usage'].submit();
    }
  }
  function checkForm(form) {
    formTest = true;
    message = "";
    if (formTest == false) {
      alert("Form could not be saved, please check the following:\r\n\r\n" + message);
      return false;
    } else {
      return true;
    }
  }
</SCRIPT>
<body onLoad="page_init();">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Admin.do">Admin</a> > 
Usage
</td>
</tr>
</table>
<%-- End Trails --%>
Current Usage and Billing Usage Information<br>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong>Current system resources:</strong>
    </th>
  </tr>
<%
  Iterator items = usageList.iterator();
  while (items.hasNext()) {
  String thisItem = (String)items.next();
%>
  <tr class="containerBody">
    <td>
      <%= thisItem %>
    </td>
  </tr>
<%
  }
%>
</table>
&nbsp;<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <form name="usage" action="Admin.do?command=Usage" onSubmit="return checkForm(this);" method="post">
    <tr>
      <td nowrap valign="top">
        Date Range:
        <select name="rangeSelect" onChange="javascript:checkSubmit();">
          <option value="today">Today</option>
          <option value="custom"<%= ("custom".equals(rangeSelect)?" selected":"") %>>Custom Date Range</option>
        </select>
      </td>
      <td nowrap align="right">
        <span name="customFields3" id="customFields3" style="display:none">
          Start:<br>
          End:
        </span>
      </td>
      <td nowrap align="left">
        <span name="customFields" id="customFields" style="display:none">
          <zeroio:dateSelect form="usage" field="dateStart" timestamp="<%= dateStart %>" />
          <%= showAttribute(request, "dateStartError") %>
          <br />
          <zeroio:dateSelect form="usage" field="dateEnd" timestamp="<%= dateEnd %>" />
          <%= showAttribute(request, "dateEndError") %>
        </span>
      </td>
      <td width="100%" align="left" valign="top" nowrap>
        <span name="customFields2" id="customFields2" style="display:none">
          <input type="submit" value="Update" name="Update">
        </span>
      </td>
    </tr>
  </form>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong>Usage for <zeroio:tz timestamp="<%= dateStart %>" dateOnly="true" /> - <zeroio:tz timestamp="<%= dateEnd %>" dateOnly="true" /></strong>
    </th>
  </tr>
<%
  Iterator items2 = usageList2.iterator();
  while (items2.hasNext()) {
  String thisItem2 = (String)items2.next();
%>
  <tr class="containerBody">
    <td>
      <%= thisItem2 %>
    </td>
  </tr>
<%
  }
%>
</table>
<br>
Application Version: <%= toHtml(applicationVersion) %><br>
Database Version: (<%= toHtml(databaseVersion) %>)
</body>
