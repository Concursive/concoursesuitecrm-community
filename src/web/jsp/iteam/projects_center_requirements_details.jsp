<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="Requirement" class="com.zeroio.iteam.base.Requirement" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="project.outline.colon" param='<%= "outline="+toHtml(Requirement.getShortDescription()) %>'>Outline: <%= toHtml(Requirement.getShortDescription()) %></dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top"><dhv:label name="contacts.details">Details</dhv:label></td>
    <td>
      <%= toHtml(Requirement.getDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.details.requestedBy">Requested By</dhv:label></td>
    <td>
      <%= toHtml(Requirement.getSubmittedBy()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">Department or<br><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></td>
    <td valign="top">
      <%= toHtml(Requirement.getDepartmentBy()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top"><dhv:label name="project.expectedDates">Expected Dates</dhv:label></td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td align="right">
            <dhv:label name="project.start.colon">Start:</dhv:label>
          </td>
          <td>
            &nbsp;
            <zeroio:tz timestamp="<%= Requirement.getStartDate() %>" dateOnly="true" timeZone="<%= Requirement.getStartDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
            <% if(!User.getTimeZone().equals(Requirement.getStartDateTimeZone())){%>
            <br />
            <zeroio:tz timestamp="<%= Requirement.getStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="--"/>
            <% } %>
          </td>
        </tr>
        <tr>
          <td align="right">
            <dhv:label name="project.finish.colon">Finish:</dhv:label>
          </td>
          <td>
            &nbsp;<zeroio:tz timestamp="<%= Requirement.getDeadline() %>" dateOnly="true" timeZone="<%= Requirement.getDeadlineTimeZone() %>" showTimeZone="true" default="--"/>
            <% if(!User.getTimeZone().equals(Requirement.getDeadlineTimeZone())){%>
            <br />
            <zeroio:tz timestamp="<%= Requirement.getDeadline() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="--"/>
            <% } %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top"><dhv:label name="project.levelOfEffort">Level of Effort</dhv:label></td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td align="right">
            <dhv:label name="project.estimated.colon">Estimated:</dhv:label>
          </td>
          <td>
            &nbsp;<%= toHtml(Requirement.getEstimatedLoeString()) %>
          </td>
        </tr>
        <tr>
          <td align="right">
            <dhv:label name="project.actual">Actual:</dhv:label>
          </td>
          <td>
            &nbsp;<%= toHtml(Requirement.getActualLoeString()) %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
    <td>
      <% if(Requirement.getApproved()) { %>
        <dhv:label name="project.outlineApproved">Outline Approved</dhv:label>
      <% } else { %>
        <dhv:label name="project.outlineNotApproved">Outline Not Approved</dhv:label>
      <% } %>
      <br />
      <% if(Requirement.getClosed()) { %>
        <dhv:label name="project.outlineClosed">Outline Closed</dhv:label>
      <% } else { %>
        <dhv:label name="project.outlineOpen">Outline Open</dhv:label>
      <% } %>
    </td>
  </tr>    
</table>
<br />
<input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:window.close()"/>
