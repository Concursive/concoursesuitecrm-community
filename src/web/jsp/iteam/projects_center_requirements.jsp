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
<jsp:useBean id="SKIN" class="java.lang.String" scope="application"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="projectRequirementsInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="initPopupMenu.jsp" %>
<%@ include file="projects_center_requirements_menu.jsp" %>
<%-- Preload image rollovers --%>
<script language="JavaScript" type="text/javascript">
  loadImages('select_<%= SKIN %>');
</script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<table border="0" cellpadding="1" cellspacing="0" width="100%">
  <tr class="subtab">
    <td>
      <img border="0" src="images/icons/stock_list_bullet2-16.gif" align="absmiddle">
      Outlines
    </td>
  </tr>
</table>
<br>
<zeroio:permission name="project-plan-outline-add">
<img border="0" src="images/icons/stock_new_bullet-16.gif" align="absmiddle">
<a href="ProjectManagementRequirements.do?command=Add&pid=<%= Project.getId() %>">New Outline</a><br>
&nbsp;<br>
</zeroio:permission>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <form name="reqView" method="post" action="ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=<%= Project.getId() %>">
    <td align="left">
      <img alt="" src="images/icons/stock_filter-data-by-criteria-16.gif" align="absmiddle">
      <select name="listView" onChange="javascript:document.forms['reqView'].submit();">
        <option <%= projectRequirementsInfo.getOptionValue("open") %>>Open Outlines</option>
        <option <%= projectRequirementsInfo.getOptionValue("closed") %>>Closed Outlines</option>
        <option <%= projectRequirementsInfo.getOptionValue("all") %>>All Outlines</option>
      </select>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="projectRequirementsInfo"/>
    </td>
    </form>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8"><strong>Action</strong></th>
    <th width="86" nowrap><strong>Start Date</strong></th>
    <th width="100%"><strong>Description</strong></th>
    <th><strong>Progress</strong></th>
    <th width="118"><strong>Effort</strong></th>
  </tr>
<%    
  RequirementList requirements = Project.getRequirements();
  if (requirements.size() == 0) {
%>
  <tr class="row2">
    <td colspan="5">No outlines to display.</td>
  </tr>
<%
  }
  int rowid = 0;
  int count = 0;
  Iterator i = requirements.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    ++count;
    Requirement thisRequirement = (Requirement) i.next();
%>    
  <tr>
    <td class="row<%= rowid %>" valign="top" align="center" nowrap>
      <a href="javascript:displayMenu('select_<%= SKIN %><%= count %>', 'menuItem', <%= thisRequirement.getId() %>, <%= Project.getId() %>);"
         onMouseOver="over(0, <%= count %>)"
         onmouseout="out(0, <%= count %>); hideMenu('menuItem');"><img 
         src="images/select_<%= SKIN %>.gif" name="select_<%= SKIN %><%= count %>" id="select_<%= SKIN %><%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td class="row<%= rowid %>" valign="top" nowrap>
      <% if(!User.getTimeZone().equals(thisRequirement.getStartDateTimeZone())){%>
      <zeroio:tz timestamp="<%= thisRequirement.getStartDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisRequirement.getStartDate() %>" dateOnly="true" timeZone="<%= thisRequirement.getStartDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
    </td>
    <td class="row<%= rowid %>" valign="top">
      <%-- <%= thisRequirement.getAssignmentTag("ProjectManagement.do?command=ProjectCenter&section=Requirements&pid=" + Project.getId() + "&" + (thisRequirement.isTreeOpen()?"contract":"expand") + "=" + thisRequirement.getId()) %> --%>
      <%= thisRequirement.getStatusGraphicTag() %> 
      <a href="ProjectManagement.do?command=ProjectCenter&section=Assignments&rid=<%= thisRequirement.getId() %>&pid=<%= Project.getId() %>"><%= toHtml(thisRequirement.getShortDescription()) %></a>
      <a href="javascript:popURL('ProjectManagementRequirements.do?command=Details&pid=<%= Project.getId() %>&rid=<%= thisRequirement.getId() %>&popup=true','Outline_Details','650','375','yes','yes');"><img src="images/icons/stock_insert-note-16.gif" border="0" align="absbottom"/></a><br>
      <i>
      <dhv:evaluate if="<%= hasText(thisRequirement.getSubmittedBy()) || hasText(thisRequirement.getDepartmentBy()) %>">
        Requested By
      </dhv:evaluate>
      <dhv:evaluate if="<%= hasText(thisRequirement.getSubmittedBy()) %>">
        <%= toHtml(thisRequirement.getSubmittedBy()) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= hasText(thisRequirement.getSubmittedBy()) && hasText(thisRequirement.getDepartmentBy()) %>">
        /
      </dhv:evaluate>
      <dhv:evaluate if="<%= hasText(thisRequirement.getDepartmentBy()) %>">
        <%= toHtml(thisRequirement.getDepartmentBy()) %>
      </dhv:evaluate>
    </td>
    <td class="row<%= rowid %>" valign="top" align="right" nowrap>
      <table cellpadding="1" cellspacing="1" class="empty">
        <td>Progress:</td>
        <dhv:evaluate if="<%= (thisRequirement.getPercentComplete() == 0 && thisRequirement.getPlanActivityCount() == 0) || thisRequirement.getPercentComplete() > 0 %>">
          <dhv:evaluate if="<%= thisRequirement.getPlanActivityCount() == 0 %>">
            <td width="<%= thisRequirement.getPercentComplete() %>" bgColor="#CCCCCC" nowrap></td>
          </dhv:evaluate>
          <dhv:evaluate if="<%= thisRequirement.getPlanActivityCount() > 0 %>">
            <td width="<%= thisRequirement.getPercentComplete() %>" bgColor="green" nowrap></td>
          </dhv:evaluate>
        </dhv:evaluate>
        <dhv:evaluate if="<%= (thisRequirement.getPercentComplete() == 0 && thisRequirement.getPlanActivityCount() > 0) || thisRequirement.getPlanActivityCount() != thisRequirement.getPlanClosedCount() %>">
          <td width="<%= thisRequirement.getPercentIncomplete() %>" bgColor="red" nowrap></td>
        </dhv:evaluate>
      </table>
      <dhv:evaluate if="<%= thisRequirement.getPlanActivityCount() == 0 %>">
        (0 activities)
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisRequirement.getPlanActivityCount() > 0 %>">
        (<%= thisRequirement.getPlanClosedCount() %> of <%= thisRequirement.getPlanActivityCount() %>
        activit<%= (thisRequirement.getPlanActivityCount() == 1?"y":"ies") %>
        <%= (thisRequirement.getPlanClosedCount() == 1?"is":"are") %> complete)
      </dhv:evaluate>
    </td>
    <td class="row<%= rowid %>" valign="top" nowrap>
      Due:
      <% if(!User.getTimeZone().equals(thisRequirement.getDeadlineTimeZone())){%>
      <zeroio:tz timestamp="<%= thisRequirement.getDeadline() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisRequirement.getDeadline() %>" dateOnly="true" timeZone="<%= thisRequirement.getDeadlineTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %><br />
      LOE: <%= thisRequirement.getEstimatedLoeString() %>
    </td>
  </tr>
<%    
    if (thisRequirement.isTreeOpen() && thisRequirement.getAssignments().size() > 0) {
      Iterator assignments = thisRequirement.getAssignments().iterator();
      while (assignments.hasNext()) {
        Assignment thisAssignment = (Assignment)assignments.next();
%>
  <tr>
    <td class="row<%= rowid %>" valign="top" colspan="2">
      &nbsp;
    </td>
    <td class="row<%= rowid %>" valign="top">
      <img border="0" src="images/treespace.gif" align="absmiddle">
      <%= thisAssignment.getStatusGraphicTag() %>
      <a href="javascript:popURL('ProjectManagementAssignments.do?command=Modify&pid=<%= Project.getId() %>&aid=<%= thisAssignment.getId() %>&popup=true&return=ProjectRequirements&param=<%= Project.getId() %>','CFS_Assignment','600','325','yes','no');" style="text-decoration:none;color:black;" onMouseOver="this.style.color='blue';window.status='Update this assignment';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><%= toHtml(thisAssignment.getRole()) %></a>
      (<dhv:username id="<%= thisAssignment.getUserAssignedId() %>"/>)
    </td>
    <td class="row<%= rowid %>" valign="top" nowrap>
      Due: <%= thisAssignment.getRelativeDueDateString(User.getTimeZone(), User.getLocale()) %><br />
      LOE: <%= thisAssignment.getEstimatedLoeString() %>
    </td>
  </tr>
<%
      }
    }
  }
%>
</table>
<br>
<dhv:pagedListControl object="projectRequirementsInfo"/>
<br>
<table border="0" width="100%">
  <tr>
    <td>
      <img border="0" src="images/box.gif" alt="Incomplete" align="absmiddle" />
      Item is incomplete<br />
      <img border="0" src="images/box-checked.gif" alt="Completed" align="absmiddle" />
      Item has been completed (or closed)<br />
      <img border="0" src="images/box-hold.gif" alt="On Hold" align="absmiddle" />
      Item has not been approved
    </td>
  </tr>
</table>

