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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="Campaign" class="org.aspcfs.modules.communications.base.Campaign" scope="request"/>
<jsp:useBean id="ActiveSurvey" class="org.aspcfs.modules.communications.base.ActiveSurvey" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="CampaignManager.do">Communications</a> >
<a href="CampaignManager.do?command=Dashboard">Dashboard</a> >
Campaign Details
</td>
</tr>
</table>
<%-- End Trails --%>
<strong>Campaign: </strong><%= toHtml(Campaign.getName()) %>
<% String param1="id=" + Campaign.getId(); %>
<dhv:container name="communications" selected="details" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td class="containerBack">
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong>Campaign Details </strong>
          </th>
        </tr>
        <tr class="containerBody">
          <td class="formLabel" valign="top">
            Description
          </td>
          <td>
            <%= toHtml(Campaign.getDescription()) %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Groups
          </td>
          <td>
            <font color="green"><%= Campaign.getGroupCount() %> selected</font>
          </td>
        </tr>
        <tr class="containerBody">
          <td valign="top" class="formLabel">
            Message
          </td>
          <td>
            <font color="green"><%= toHtml(Campaign.getMessageName()) %></font>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Schedule
          </td>
          <td>
            <font color="green">Scheduled to run on 
            <zeroio:tz timestamp="<%= Campaign.getActiveDate() %>" dateOnly="true" timeZone="<%= Campaign.getActiveDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
            <% if(!User.getTimeZone().equals(Campaign.getActiveDateTimeZone())){%>
            <br />
            <zeroio:tz timestamp="<%= Campaign.getActiveDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
            <% } %>
            </font>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Delivery
          </td>
          <td>
            <%= (Campaign.hasDetails()?"<font color='green'>" + toHtml(Campaign.getDeliveryName())  + "</font>":"<font color='red'>Not Specified</font>") %>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Entered
          </td>
          <td>
            <dhv:username id="<%= Campaign.getEnteredBy() %>" />
            <zeroio:tz timestamp="<%= Campaign.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
          </td>
        </tr>
        <tr class="containerBody">
          <td class="formLabel">
            Modified
          </td>
          <td>
            <dhv:username id="<%= Campaign.getModifiedBy() %>" />
            <zeroio:tz timestamp="<%= Campaign.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
