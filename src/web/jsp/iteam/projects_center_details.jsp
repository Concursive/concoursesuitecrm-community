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
<jsp:useBean id="projectCategoryList" class="com.zeroio.iteam.base.ProjectCategoryList" scope="request"/>
<jsp:useBean id="currentMember" class="com.zeroio.iteam.base.TeamMember" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-objects-16.gif" border="0" align="absmiddle">
      <dhv:label name="documents.details.overview">Overview</dhv:label>
    </td>
  </tr>
</table>
<br>
<dhv:evaluate if="<%= currentMember.getRoleId() <= TeamMember.PROJECT_LEAD %>">
<dhv:evaluate if="<%= !Project.isTrashed() %>">
<a href="ProjectManagement.do?command=ModifyProject&pid=<%= Project.getId() %>&return=ProjectCenter"><dhv:label name="project.modifyProject">Modify Project</dhv:label></a>
|
<a href="javascript:confirmDelete('ProjectManagement.do?command=TrashProject&pid=<%= Project.getId() %>');"><dhv:label name="project.deleteProject">Delete Project</dhv:label></a>
</dhv:evaluate>
<dhv:evaluate if="<%= Project.isTrashed() %>">
  <a href="javascript:confirmDelete('ProjectManagement.do?command=RestoreProject&pid=<%= Project.getId() %>');"><dhv:label name="project.restoreProject">Restore Project</dhv:label></a>
</dhv:evaluate>
<br /><br />
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="documents.details.generalInformation">General Information</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></td>
    <td>
      <dhv:evaluate if="<%= !Project.getClosed() && Project.getApprovalDate() == null %>">
        <img border="0" src="images/box-hold.gif" alt="<dhv:label name='alt.onHold'>On Hold</dhv:label>" align="absmiddle">
      </dhv:evaluate>
      <dhv:evaluate if="<%= Project.getClosed() %>">
        <font color="blue">
          <dhv:label name="project.projectClosedOn" param='<%= "time="+ getTime(pageContext,Project.getCloseDate(),User.getTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;")  %>'>This project was closed on <zeroio:tz timestamp="<%= Project.getCloseDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label>
        </font>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !Project.getClosed() %>">
        <dhv:evaluate if="<%= Project.getApprovalDate() == null %>">
          <font color="red"><dhv:label name="project.projectUnderReview.text">This project is currently under review and has not been approved</dhv:label></font>
        </dhv:evaluate>
        <dhv:evaluate if="<%= Project.getApprovalDate() != null %>">
          <font color="darkgreen"><dhv:label name="project.projectApprovedOn" param='<%= "time="+getTime(pageContext,Project.getApprovalDate(),User.getTimeZone(),DateFormat.SHORT,true,false,false,"&nbsp;") %>'>This project was approved on <zeroio:tz timestamp="<%= Project.getApprovalDate() %>" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/></dhv:label></font>
        </dhv:evaluate>
      </dhv:evaluate>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></td>
    <td>
      <%= toHtml(Project.getTitle()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.details.shortDescription">Short Description</dhv:label></td>
    <td>
      <%= toHtml(Project.getShortDescription()) %>
    </td>
  </tr>
  <dhv:evaluate if="<%= projectCategoryList.size() > 1 %>">
  <tr class="containerBody">
    <td nowrap class="formLabel">Category</td>
    <td>
      <%= toHtml(projectCategoryList.getValueFromId(Project.getCategoryId())) %>
    </td>
  </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.details.startDate">Start Date</dhv:label></td>
    <td>
      <zeroio:tz timestamp="<%= Project.getRequestDate() %>" timeZone="<%= Project.getRequestDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(Project.getRequestDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= Project.getRequestDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="project.estimatedCloseDate">Estimated Close Date</dhv:label></td>
    <td>
      <zeroio:tz timestamp="<%= Project.getEstimatedCloseDate() %>" timeZone="<%= Project.getEstimatedCloseDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% if(!User.getTimeZone().equals(Project.getEstimatedCloseDateTimeZone())){%>
      <br />
      <zeroio:tz timestamp="<%= Project.getEstimatedCloseDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.details.requestedBy">Requested By</dhv:label></td>
    <td>
      <%= toHtml(Project.getRequestedBy()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="documents.details.organization">Organization</dhv:label></td>
    <td>
      <%= toHtml(Project.getRequestedByDept()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="project.budget">Budget</dhv:label></td>
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
    <td nowrap class="formLabel"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></td>
    <td>
      <dhv:username id="<%= Project.getEnteredBy() %>"/>
      <zeroio:tz timestamp="<%= Project.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel"><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></td>
    <td>
      <dhv:username id="<%= Project.getModifiedBy() %>"/>
      <zeroio:tz timestamp="<%= Project.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
    </td>
  </tr>
</table>

