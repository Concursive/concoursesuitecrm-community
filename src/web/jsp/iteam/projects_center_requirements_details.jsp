<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="Requirement" class="com.zeroio.iteam.base.Requirement" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong>Outline: <%= toHtml(Requirement.getShortDescription()) %></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">Details</td>
    <td>
      <%= toHtml(Requirement.getDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Requested By</td>
    <td>
      <%= toHtml(Requirement.getSubmittedBy()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">Department or<br>Company</td>
    <td valign="top">
      <%= toHtml(Requirement.getDepartmentBy()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">Expected Dates</td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td align="right">
            Start:
          </td>
          <td>
            &nbsp;
            <zeroio:tz timestamp="<%= Requirement.getStartDate() %>" dateOnly="true" timeZone="<%= Requirement.getStartDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
            <% if(!User.getTimeZone().equals(Requirement.getStartDateTimeZone())){%>
            <br />
            <zeroio:tz timestamp="<%= Requirement.getStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="--"/>
            <% } %>
          </td>
        </tr>
        <tr>
          <td align="right">
            Finish:
          </td>
          <td>
            &nbsp;<zeroio:tz timestamp="<%= Requirement.getDeadline() %>" dateOnly="true" timeZone="<%= Requirement.getDeadlineTimeZone() %>" showTimeZone="yes" default="--"/>
            <% if(!User.getTimeZone().equals(Requirement.getDeadlineTimeZone())){%>
            <br />
            <zeroio:tz timestamp="<%= Requirement.getDeadline() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="--"/>
            <% } %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">Level of Effort</td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td align="right">
            Estimated:
          </td>
          <td>
            &nbsp;<%= toHtml(Requirement.getEstimatedLoeString()) %>
          </td>
        </tr>
        <tr>
          <td align="right">
            Actual:
          </td>
          <td>
            &nbsp;<%= toHtml(Requirement.getActualLoeString()) %>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel" valign="top">Status</td>
    <td>
      Outline <%= (Requirement.getApproved()?"":"not") %> approved
      <br>
      Outline <%= (Requirement.getClosed()?"Closed":"Open") %>
    </td>
  </tr>    
</table>
<br />
<input type="button" value="Close" onClick="javascript:window.close()"/>
