<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
