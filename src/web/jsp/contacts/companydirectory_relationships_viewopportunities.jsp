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
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.pipeline.base.*" %>
<%@ page import="org.aspcfs.modules.pipeline.beans.*" %>
<jsp:useBean id="opportunityList" class="org.aspcfs.modules.pipeline.base.OpportunityList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th valign="center" align="left">
      <strong>Organization</strong>
    </th>
    <th valign="center" align="left">
      <strong>Description</strong>
    </th>
    <th valign="center" align="left">
      <strong>Amount</strong>
    </th>
    <th valign="center" align="left">
      <strong>Close</strong>
    </th>
  </tr>
<%
  Iterator oppList = opportunityList.iterator();
  if (oppList.hasNext()) {
    int row = 0;
    while (oppList.hasNext()) {
      row = row == 1?2:1;
      OpportunityBean thisOpportunity = (OpportunityBean) oppList.next();
%>
  <tr class="containerBody">
    <td width="20%" valign="center" nowrap class="row<%= row %>">
      <%= toHtml(thisOpportunity.getHeader().getAccountName()) %>
    </td>
    <td width="40%" valign="center" class="row<%= row %>">
      <%= toHtml(thisOpportunity.getHeader().getDescription()) %>:
      <%= toHtml(thisOpportunity.getComponent().getDescription()) %>
    </td>
    <td width="20%" valign="center" align="right" nowrap class="row<%= row %>">
      <zeroio:currency value="<%= thisOpportunity.getComponent().getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
    <td width="20%" valign="center" align="right" nowrap class="row<%= row %>">
      <zeroio:tz timestamp="<%= thisOpportunity.getComponent().getCloseDate() %>" dateOnly="true" default="&nbsp;"/>
    </td>
  </tr>
<%  
    }
  } else {
%>
  <tr>
    <td colspan="4">
      No opportunities found.
    </td>
  </tr>
<%
  }
%>
</table>
