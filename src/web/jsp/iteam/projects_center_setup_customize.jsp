<%--
 Copyright 2000-2004 Matt Rajkowski
 matt.rajkowski@teamelements.com
 http://www.teamelements.com
 This source code cannot be modified, distributed or used without
 permission from Matt Rajkowski
--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/checkDate.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<form method="POST" name="inputForm" action="ProjectManagement.do?command=UpdateFeatures&auto-populate=true">
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img src="images/icons/stock_macro-objects-16.gif" border="0" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Setup&pid=<%= Project.getId() %>">Setup</a> >
      Customize Project
    </td>
  </tr>
</table>
<br>
<input type="submit" value=" Update ">
<input type="button" value="Cancel" onClick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=Setup&pid=<%= Project.getId() %>'"><br>
&nbsp;
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <input type="hidden" name="id" value="<%= Project.getId() %>">
  <input type="hidden" name="modified" value="<%= Project.getModified().toString() %>">
  <tr>
    <th colspan="2" valign="center">
      <strong>Update Project Features</strong>
    </th>
  </tr>
<%-- 
<dhv:evaluate if="<%= User.getAccessGuestProjects() %>">
  <tr class="containerBody">
    <td class="formLabel" valign="top">Global Settings</td>
    <td>
      <input type="checkbox" name="allowGuests" value="ON"<%= Project.getAllowGuests() ? " checked" : "" %>>
      Guests are allowed to view this project, without logging in and without being a member of the project
    </td>
  </tr>
</dhv:evaluate>
--%>
  <tr class="containerBody">
    <td class="formLabel" valign="top">Project Tabs</td>
    <td>
      <table width="100%" class="empty">
        <tr>
          <td>Enabled</td>
          <td>Tab</td>
          <td width="100%">Label</td>
        </tr>
        <tr>
          <td align="center"><input type="checkbox" name="showNews" value="ON"<%= Project.getShowNews() ? " checked" : "" %>></td>
          <td>News</td>
          <td><input type="text" name="labelNews" value="<%= toHtmlValue(Project.getLabelNews()) %>" maxlength="50"/></td>
        </tr>
        <tr>
          <td align="center"><input type="checkbox" name="showDiscussion" value="ON"<%= Project.getShowDiscussion() ? " checked" : "" %>></td>
          <td>Discussion</td>
          <td><input type="text" name="labelDiscussion" value="<%= toHtmlValue(Project.getLabelDiscussion()) %>" maxlength="50"/></td>
        </tr>
        <tr>
          <td align="center"><input type="checkbox" name="showDocuments" value="ON"<%= Project.getShowDocuments() ? " checked" : "" %>></td>
          <td>Documents</td>
          <td><input type="text" name="labelDocuments" value="<%= toHtmlValue(Project.getLabelDocuments()) %>" maxlength="50"/></td>
        </tr>
        <tr>
          <td align="center"><input type="checkbox" name="showLists" value="ON"<%= Project.getShowLists() ? " checked" : "" %>></td>
          <td>Lists</td>
          <td><input type="text" name="labelLists" value="<%= toHtmlValue(Project.getLabelLists()) %>" maxlength="50"/></td>
        </tr>
        <tr>
          <td align="center"><input type="checkbox" name="showPlan" value="ON"<%= Project.getShowPlan() ? " checked" : "" %>></td>
          <td>Plan</td>
          <td><input type="text" name="labelPlan" value="<%= toHtmlValue(Project.getLabelPlan()) %>" maxlength="50"/></td>
        </tr>
        <tr>
          <td align="center"><input type="checkbox" name="showTickets" value="ON"<%= Project.getShowTickets() ? " checked" : "" %>></td>
          <td>Tickets</td>
          <td><input type="text" name="labelTickets" value="<%= toHtmlValue(Project.getLabelTickets()) %>" maxlength="50"/></td>
        </tr>
        <tr>
          <td align="center"><input type="checkbox" name="showTeam" value="ON"<%= Project.getShowTeam() ? " checked" : "" %>></td>
          <td>Team</td>
          <td><input type="text" name="labelTeam" value="<%= toHtmlValue(Project.getLabelTeam()) %>" maxlength="50"/></td>
        </tr>
        <tr>
          <td align="center"><input type="checkbox" name="showDetails" value="ON"<%= Project.getShowDetails() ? " checked" : "" %>></td>
          <td>Details</td>
          <td><input type="text" name="labelDetails" value="<%= toHtmlValue(Project.getLabelDetails()) %>" maxlength="50"/></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
<input type="submit" value=" Update ">
<input type="button" value="Cancel" onClick="javascript:window.location.href='ProjectManagement.do?command=ProjectCenter&section=Setup&pid=<%= Project.getId() %>'">
</form>
