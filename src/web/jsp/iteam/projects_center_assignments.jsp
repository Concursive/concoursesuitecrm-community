<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%-- <%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %> --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*,org.aspcfs.modules.admin.base.*" %>
<zeroio:debug value="SETTING UP THE BEANS" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="requirement" class="com.zeroio.iteam.base.Requirement" scope="request"/>
<jsp:useBean id="projectAssignmentsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="mapList" class="com.zeroio.iteam.base.RequirementMapList" scope="request"/>
<jsp:useBean id="assignments" class="com.zeroio.iteam.base.AssignmentList" scope="request"/>
<jsp:useBean id="folders" class="com.zeroio.iteam.base.AssignmentFolderList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<zeroio:debug value="INCLUDED FILES"/>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_center_assignments_menu.jsp" %>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  loadImages('menu');
</script>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_list_bullet2-16.gif" align="absmiddle">
      <a href="ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>">Outlines</a> >
      <%= toHtml(requirement.getShortDescription()) %>
    </td>
  </tr>
</table>
<br>
<zeroio:debug value="<%= "Project: " + Project.getId() %>" />
<zeroio:debug value="<%= "Req: " + requirement.getId() %>" />
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <form name="listView" method="post" action="ProjectManagement.do?command=ProjectCenter&section=Assignments&pid=<%= Project.getId() %>&rid=<%= requirement.getId() %>">
    <td>
      <img alt="" src="images/icons/stock_filter-data-by-criteria-16.gif" align="absmiddle">
      <select size="1" name="listView" onChange="javascript:document.forms['listView'].submit();">
<zeroio:debug value="projectAssignmentsInfo"/>
        <option <%= projectAssignmentsInfo.getOptionValue("all") %>>All Activities</option>
        <option <%= projectAssignmentsInfo.getOptionValue("open") %>>Open Activities</option>
        <option <%= projectAssignmentsInfo.getOptionValue("closed") %>>Closed Activities</option>
      </select>
<zeroio:debug value="priority list"/>
<%
    PriorityList.setJsEvent("onChange=\"javascript:document.forms['listView'].submit();\"");
    PriorityList.addItem(-1, "All Priorities");
%>
<zeroio:debug value="GET SELECT"/>
      <%= PriorityList.getHtmlSelect("listFilter1", projectAssignmentsInfo.getFilterValue("listFilter1")) %>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="0" cellspacing="0" width="100%" border="1" rules="cols">
  <tr class="section">
    <td width="60%" nowrap colspan="2"><strong>Plan Outline</strong></td>
    <td width="8%" align="center"><strong>Pri</strong></td>
    <td width="8%" align="center" nowrap><strong>Assigned To</strong></td>
    <td width="8%" align="center"><strong>Effort</strong></td>
    <td width="8%" align="center"><strong>Start</strong></td>
    <td width="8%" align="center" nowrap><strong>&nbsp;End&nbsp;</strong></td>
  </tr>
<zeroio:debug value="DO REQUIREMENT"/>
<%
  Requirement thisRequirement = requirement;
%>    
<zeroio:debug value="<%= "REQ: " + requirement.getId() %>"/>
  <tr class="section">
    <td align="center">
      #
    </td>
    <td valign="top" width="60%">
      <%--
      <img alt="" src="images/tree/treespace.gif" border="0" align="absmiddle" height="18" width="19"/>
      --%>
      &nbsp;
      <img border="0" src="images/icons/stock_list_bullet-16.gif" align="absmiddle">
      <a class="rollover" name="r<%= thisRequirement.getId() %>" id="r<%= thisRequirement.getId() %>" href="javascript:displayMenu('r<%= thisRequirement.getId() %>', 'menuRequirement',<%= Project.getId() %>,<%= thisRequirement.getId() %>,-1,-1,-1,-1);"
         onMouseOver="window.status='Click to show drop-down menu';return true;"
         onmouseout="window.status='';hideMenu('menuRequirement');"><%= toHtml(thisRequirement.getShortDescription()) %></a>
      (<%= mapList.size() %> item<%= (mapList.size() == 1?"":"s") %>)
    </td>
    <td width="8%">
      &nbsp;
    </td>
    <td width="8%">
      &nbsp;
    </td>
    <td valign="top" align="center" width="8%" nowrap>
      <%= thisRequirement.getEstimatedLoeString() %>
    </td>
    <td valign="top" align="center" width="8%" nowrap>
      <% if(!User.getTimeZone().equals(thisRequirement.getStartDateTimeZone())){%>
      <zeroio:tz timestamp="<%= thisRequirement.getStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="--"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisRequirement.getStartDate() %>" dateOnly="true" timeZone="<%= thisRequirement.getStartDateTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
    <td valign="top" align="center" width="8%" nowrap>
      <% if(!User.getTimeZone().equals(thisRequirement.getDeadlineTimeZone())){%>
      <zeroio:tz timestamp="<%= thisRequirement.getDeadline() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="yes" default="--"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisRequirement.getDeadline() %>" dateOnly="true" timeZone="<%= thisRequirement.getDeadlineTimeZone() %>" showTimeZone="yes" default="&nbsp;"/>
      <% } %>
    </td>
  </tr>
<%
    int rowid = 0;
    HashMap nodeStatus = new HashMap();
    Iterator iMapList = mapList.iterator();
    int lastPosition = 0;
    while (iMapList.hasNext()) {
      RequirementMapItem mapItem = (RequirementMapItem) iMapList.next();
      nodeStatus.put(new Integer(mapItem.getIndent()), new Boolean(mapItem.getFinalNode()));
      rowid = (rowid != 1?1:2);
%>
  <tr class="sectionrow<%= rowid %>">
    <td valign="top" nowrap>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
<%--
          <td valign="middle">
            <input type="checkbox" name="batch<%= mapItem.getPosition() %>" value="ON" />
          </td>
--%>
          <td valign="middle" align="right">
            <dhv:evaluate if="<%= lastPosition + 1 != mapItem.getPosition() %>"><font color="red"></dhv:evaluate>
            &nbsp;<%= mapItem.getPosition() %>.&nbsp;
            <dhv:evaluate if="<%= lastPosition + 1 != mapItem.getPosition() %>"></font></dhv:evaluate>
          </td>
        </tr>
      </table>
    </td>
    <td valign="top">
      <%--
      <img alt="" src="images/tree/treespace.gif" border="0" align="absmiddle" height="18" width="19"/>
      --%>
      &nbsp;
<%
      for (int count = 0; count < mapItem.getIndent(); count++) {
        Boolean isClosed = (Boolean) nodeStatus.get(new Integer(count));
        if (isClosed == null) {
          isClosed = new Boolean(false);
        }
%>
      <%-- Show spacing --%>
      <dhv:evaluate if="<%= isClosed.booleanValue() %>">
        <img alt="" src="images/tree/treespace.gif" border="0" align="absmiddle" height="18" width="19"/>
      </dhv:evaluate>
      <%-- Show more nodes --%>
      <dhv:evaluate if="<%= !isClosed.booleanValue() %>">
        <img alt="" src="images/tree/tree2.gif" border="0" align="absmiddle" height="18" width="19"/>
      </dhv:evaluate>
<%
      }
      if (mapItem.getChildren().isEmpty()) {
        Boolean isClosed = (Boolean) nodeStatus.get(new Integer(mapItem.getIndent()));
        if (isClosed == null) {
          isClosed = new Boolean(false);
        }
%>
      <%-- Show final node --%>
      <dhv:evaluate if="<%= isClosed.booleanValue() %>">
        <img alt="" src="images/tree/tree4.gif" border="0" align="absmiddle" height="18" width="19"/>
      </dhv:evaluate>
      <%-- Show more nodes --%>
      <dhv:evaluate if="<%= !isClosed.booleanValue() %>">
        <img alt="" src="images/tree/tree3.gif" border="0" align="absmiddle" height="18" width="19"/>
      </dhv:evaluate>
<%
      } else {
%>
      <%-- Show Last Node with children --%>
      <dhv:evaluate if="<%= mapItem.getFinalNode() %>">
        <img alt="" src="images/tree/tree6o.gif" border="0" align="absmiddle" height="18" width="19"/>
      </dhv:evaluate>
      <%-- Show Node with children --%>
      <dhv:evaluate if="<%= !mapItem.getFinalNode() %>">
        <img alt="" src="images/tree/tree5o.gif" border="0" align="absmiddle" height="18" width="19"/>
      </dhv:evaluate>
<%
      }
      if (mapItem.getAssignmentId() > -1) {
        Assignment thisAssignment = (Assignment) assignments.getAssignment(mapItem.getAssignmentId());
%>
      <%= thisAssignment.getStatusGraphicTag() %>
      <a class="rollover" name="a<%= thisAssignment.getId() %>" id="a<%= thisAssignment.getId() %>" href="javascript:displayMenu('a<%= thisAssignment.getId() %>', 'menuActivity',<%= Project.getId() %>,<%= thisRequirement.getId() %>,-1,<%= thisAssignment.getId() %>,<%= mapItem.getId() %>,<%= mapItem.getIndent() %>);"
         onMouseOver="window.status='Click to show drop-down menu';return true;"
         onmouseout="window.status='';hideMenu('menuActivity');"><%= toHtml(thisAssignment.getRole()) %></a>
    </td>
    <td valign="top" align="center" nowrap>
      <%= toHtml(PriorityList.getValueFromId(thisAssignment.getPriorityId())) %>
    </td>
    <td valign="top" align="center" nowrap>
      <dhv:username id="<%= thisAssignment.getUserAssignedId() %>"/>
    </td>
    <td valign="top" align="center" nowrap>
      <%= thisAssignment.getEstimatedLoeString() %>
    </td>
    <td valign="top" align="center" nowrap>
      <zeroio:tz timestamp="<%= thisAssignment.getEstStartDate() %>" dateOnly="true"/>
    </td>
    <td valign="top" align="center" nowrap>
      <%= thisAssignment.getRelativeDueDateString(thisAssignment.getDueDateTimeZone(), User.getLocale()) %>
     <% if (!User.getTimeZone().equals(thisAssignment.getDueDateTimeZone())){ %>
      <br />
            <%= thisAssignment.getRelativeDueDateString(User.getTimeZone(), User.getLocale()) %>
      <% } %>
    </td>
<%
      } else if (mapItem.getFolderId() > -1) {
        //Assignment Folder
        AssignmentFolder thisFolder = (AssignmentFolder) folders.getAssignmentFolder(mapItem.getFolderId());
%>
      <img border="0" src="images/icons/stock_open-16-19.gif" align="absmiddle">
      <a class="rollover" name="f<%= thisFolder.getId() %>" id="f<%= thisFolder.getId() %>" href="javascript:displayMenu('f<%= thisFolder.getId() %>', 'menuFolder',<%= Project.getId() %>,<%= thisRequirement.getId() %>,<%= thisFolder.getId() %>,-1,<%= mapItem.getId() %>,<%= mapItem.getIndent() %>);"
         onMouseOver="window.status='Click to show drop-down menu';return true;"
         onmouseout="window.status='';hideMenu('menuFolder');"><%= toHtml(thisFolder.getName()) %></a>
      <dhv:evaluate if="<%= hasText(thisFolder.getDescription()) %>">
        <a href="javascript:popURL('ProjectManagementAssignmentsFolder.do?command=FolderDetails&pid=<%= Project.getId() %>&folderId=<%= mapItem.getFolderId() %>&popup=true','Folder_Details','650','375','yes','yes');"><img src="images/icons/stock_insert-note-16.gif" border="0" align="absmiddle"/></a>
      </dhv:evaluate>
    </td>
    <td>
      &nbsp;
    </td>
    <td>
      &nbsp;
    </td>
    <td>
      &nbsp;
    </td>
    <td valign="top" align="center" nowrap>
      &nbsp;
    </td>
    <td valign="top" align="center" nowrap>
      &nbsp;
    </td>
  </tr>
<%
      }
      lastPosition = mapItem.getPosition();
    }
%>
<%-- Menu system for selected items --%>
  <tr>
    <td colspan="7" class="section">
      &nbsp;
    </td>
  </tr>
</table>
<%--
<img src="images/icons/stock_new-dir-16.gif" border="0" align="absmiddle" height="16" width="16"/>
<a href="javascript:thisProjectId=<%= Project.getId() %>;thisRequirementId=<%= requirement.getId() %>;folderId=-1;thisActivityId=-1;addFolder();">Add Activity Folder</a>
|
<img src="images/New.png" border="0" align="absmiddle" height="16" width="16"/>
<a href="javascript:thisProjectId=<%= Project.getId() %>;thisRequirementId=<%= requirement.getId() %>;folderId=-1;thisActivityId=-1;addActivity()">Add Activity</a>
<br>
--%>
<br>
<%-- legend --%>
<table border="0" width="100%">
  <tr>
    <td>
      <img border="0" src="images/box.gif" alt="Incomplete" align="absmiddle">
      Item is incomplete<br>
      <img border="0" src="images/box-checked.gif" alt="Completed" align="absmiddle">
      Item has been completed<br>
      <img border="0" src="images/box-closed.gif" alt="Closed" align="absmiddle">
      Item has been closed<br>
      <img border="0" src="images/box-hold.gif" alt="On Hold" align="absmiddle">
      Item is on hold
    </td>
  </tr>
</table>
