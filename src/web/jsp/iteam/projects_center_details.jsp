<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="currentMember" class="com.zeroio.iteam.base.TeamMember" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-objects-16.gif" border="0" align="absmiddle">
      Overview
    </td>
  </tr>
</table>
<br>
<dhv:evaluate if="<%= currentMember.getRoleId() <= TeamMember.PROJECT_LEAD %>">
<a href="ProjectManagement.do?command=ModifyProject&pid=<%= Project.getId() %>&return=ProjectCenter">Modify Project</a>
|
<a href="javascript:confirmDelete('ProjectManagement.do?command=DeleteProject&pid=<%= Project.getId() %>');">Delete Project</a>
<br>
<br>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong>General Information</strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Status</td>
    <td>
      <dhv:evaluate if="<%= !Project.getClosed() && Project.getApprovalDate() == null %>">
        <img border="0" src="images/box-hold.gif" alt="On Hold" align="absmiddle">
      </dhv:evaluate>
      <dhv:evaluate if="<%= Project.getClosed() %>">
        <font color="blue">This project was closed on
        <zeroio:tz timestamp="<%= Project.getCloseDate() %>" default="&nbsp;"/>
        </font>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !Project.getClosed() %>">
        <dhv:evaluate if="<%= Project.getApprovalDate() == null %>">
          <font color="red">This project is currently under review and has not been approved</font>
        </dhv:evaluate>
        <dhv:evaluate if="<%= Project.getApprovalDate() != null %>">
          <font color="darkgreen">This project was approved on <zeroio:tz timestamp="<%= Project.getApprovalDate() %>" default="&nbsp;"/></font>
        </dhv:evaluate>
      </dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Title</td>
    <td>
      <%= toHtml(Project.getTitle()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Short Description</td>
    <td>
      <%= toHtml(Project.getShortDescription()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Start Date</td>
    <td>
      <zeroio:tz timestamp="<%= Project.getRequestDate() %>" dateOnly="true" timeZone="<%= Project.getRequestDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(Project.getRequestDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= Project.getRequestDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Estimated Close Date</td>
    <td>
      <zeroio:tz timestamp="<%= Project.getEstimatedCloseDate() %>" dateOnly="true" timeZone="<%= Project.getEstimatedCloseDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(Project.getEstimatedCloseDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= Project.getEstimatedCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Requested By</td>
    <td>
      <%= toHtml(Project.getRequestedBy()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Organization</td>
    <td>
      <%= toHtml(Project.getRequestedByDept()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Budget</td>
    <td>
      <zeroio:currency value="<%= Project.getBudget() %>" code="<%= Project.getBudgetCurrency() %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>
    </td>
  </tr>
  <%--
  <tr class="containerBody">
    <td nowrap class="formLabel">Category</td>
    <td>
      ---
    </td>
  </tr>
  --%>
  <tr class="containerBody">
    <td nowrap class="formLabel">Entered</td>
    <td>
      <dhv:username id="<%= Project.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= Project.getEntered() %>"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">Modified</td>
    <td>
      <dhv:username id="<%= Project.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= Project.getModified() %>"/>
    </td>
  </tr>
</table>

